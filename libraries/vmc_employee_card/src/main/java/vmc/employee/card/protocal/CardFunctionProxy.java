package vmc.employee.card.protocal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import vmc.employee.card.event.ReadSerialEvent;
import vmc.employee.card.utils.CardFunctionInterface;
import vmc.employee.card.utils.CardProtocalDefine;
import vmc.employee.card.utils.CardProtocalUtils;
import vmc.employee.card.utils.ConverUtils;
import vmc.employee.card.utils.FormatUtils;
import vmc.serialport.SerialPort;

/**
 * Created by lixiaodaoaaa on 2017/8/16.
 */

public class CardFunctionProxy {


    private SerialPort serialPort;
    private FileOutputStream mOutputStream;
    private StringBuilder dataReadBuilder;
    private boolean isStop = false;
    private static String EMPLOYEE_DEVICE_NAME = "/dev/ttyS0";

    public CardFunctionProxy() {
    }

    public void init(Context context) {
        try {
            serialPort = new SerialPort(new File(EMPLOYEE_DEVICE_NAME), 9600, 0);
            Log.i("CardFunctionImpl", "open serial success");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            Log.i("CardFunctionImpl", "open serial exception");
        }
        mOutputStream = (FileOutputStream) serialPort.getOutputStream();
        dataReadBuilder = new StringBuilder();
        Executors.newSingleThreadExecutor().execute(new ReadRunnable());
    }

    public void cutAmount(float amount) {
        sendDataToSerial(CardProtocalUtils.getCutAmountCommand(amount));
    }

    public void cancelCutAmount() {
        sendDataToSerial(CardProtocalUtils.getCancelCutCommand());
    }

    public void readLastTimeAvailbleAmount() {
        sendDataToSerial(CardProtocalUtils.getReadLastTimeAvailbleAmountCommand());
    }

    public void readConnectServerStatus() {
//        sendDataToSerial(CardProtocalUtils.getReadConnectServerStatus());
    }


    public void readCustmerCardNumber() {
        sendDataToSerial(CardProtocalUtils.getReadCustmerCardNumberCommond());
    }


    public void readMachineID() {
        sendDataToSerial(CardProtocalUtils.getMachineIdCommand());
    }


    private void sendDataToSerial(String command) {
        if (mOutputStream == null) {
            return;
        }
        try {
            Log.i("CardFunctionImpl", "send command is " + command);
            final byte[] bytes = FormatUtils.toByteArray(command);
            mOutputStream.write(bytes, 0, bytes.length);
            mOutputStream.flush();
            Log.i("CardFunctionImpl", "send  data  success to the serial");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("CardFunctionImpl", " data not send successful");
        }
    }

    private class ReadRunnable implements Runnable {
        @Override
        public void run() {
            byte[] buffer = new byte[64];
            InputStream inputStream = null;
            int length;
            if (serialPort != null) {
                inputStream = serialPort.getInputStream();
            }
            while (!isStop) {
                try {
                    length = inputStream.read(buffer);
                    if (length > 0) {
                        onDataReceiver(buffer, length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onDataReceiver(byte[] data, int length) {
        for (int i = 0; i < length; i++) {
            String hexValue = FormatUtils.intValueTo2HexString(ConverUtils.byteToInt(data[i]));
            dataReadBuilder.append(hexValue);
        }
        String readHeader = dataReadBuilder.toString().trim().substring(0, 2).toUpperCase();
        if (!readHeader.equals(CardProtocalDefine.HEADER_DATA1)) {
            dataReadBuilder.setLength(0);
            return;
        }
        //字节数目不对 继续接收收据
        if (dataReadBuilder.toString().trim().length() < 2 * 9) {
            return;
        }
        analyzeReadData(dataReadBuilder);
    }

    private void analyzeReadData(StringBuilder stringBuilder) {
        String readData = stringBuilder.toString().trim();
        Log.i("CardFunctionProxy", "read data  is " + readData);
        String dataFunc = readData.toUpperCase().replace(CardProtocalDefine.HEADER, "").substring(0, 2);
        String data = readData.substring(readData.indexOf(dataFunc) + 2, readData.indexOf(dataFunc) + 10);
        String data1 = data.substring(0, 2);
        ReadSerialEvent readSerialEvent = new ReadSerialEvent();

        //读取卡序列号 内部卡号  消费金额 卡余额
        if (dataFunc.toUpperCase().equals(CardFunctionInterface.FUNCTION_READ_CUSTMER_CARD_NUMBER)) {
            if (dataReadBuilder.toString().trim().length() < 2 * 21) {
                return;
            } else {
                readSerialEvent.setMsg(readDataToCustomerInfo(readData));
                resetReadBuilder(readSerialEvent);
                return;
            }
        }


        //读取余额功能
        if (dataFunc.toUpperCase().equals(CardFunctionInterface.FUNCTION_READ_AVAIBLE_AMOUNT)) {
            Log.i("CardFunctionProxy", " 余额为：" + FormatUtils.fromAmountDataToAmount(data));
            readSerialEvent.setMsg(" 读取余额成功  " + "余额为：" + FormatUtils.fromAmountDataToAmount(data));
            resetReadBuilder(readSerialEvent);
            return;
        }

        //读取机器ID
        if (dataFunc.toUpperCase().equals(CardFunctionInterface.FUNCTION_READ_MACHINE_ID)) {
            Log.i("CardFunctionProxy", " 机器ID为：" + data);
            readSerialEvent.setMsg(" 机器ID为  " + "机器ID为：" + data);
            resetReadBuilder(readSerialEvent);
            return;
        }


        Log.i("CardFunctionProxy", "dataFunc is " + dataFunc);
        Log.i("CardFunctionProxy", "data is " + data);
        Log.i("CardFunctionProxy", "data1 is " + data1);


        switch (data1.toUpperCase()) {
            case CardProtocalDefine.DATA_CONNECT_SUCCESS:
                Log.i("CardFunctionProxy", " 通信成功");
                readSerialEvent.setMsg(" 通信成功");
                break;
            case CardProtocalDefine.DATA_CUT_SUCCESS:
                Log.i("CardFunctionProxy", " 扣款成功");
                readSerialEvent.setMsg(" 扣款成功");
                break;
            case CardProtocalDefine.DATA_CANCEL_SUCCESS:
                Log.i("CardFunctionProxy", " 取消成功");
                readSerialEvent.setMsg(" 取消成功");
                break;
            case CardProtocalDefine.DATA_TIME_OUT:
                Log.i("CardFunctionProxy", " 未刷卡  超时退出");
                readSerialEvent.setMsg(" 未刷卡  超时退出");
                break;
            case CardProtocalDefine.DATA_CARD_MONEY_LOW:
                Log.i("CardFunctionProxy", "余额不足");
                readSerialEvent.setMsg(" 余额不足");
                break;
            case CardProtocalDefine.DATA_MONEY_ERROR:
                Log.i("CardFunctionProxy", " 输入金额有误");
                readSerialEvent.setMsg(" 输入金额有误");
                break;

            case CardProtocalDefine.DATA_CARD_BLACK:
                Log.i("CardFunctionProxy", " 此卡已挂失");
                readSerialEvent.setMsg(" 此卡已挂失");
                break;
            default:
                break;
        }
        resetReadBuilder(readSerialEvent);
    }


    private String readDataToCustomerInfo(String readData) {
        StringBuilder stringBuilder = new StringBuilder();
        /**
         b14731fc //卡序列号
         2d000000 //内部卡号
         0a000000 //消费金额
         3ec89a3b //卡内余额
         */
        int stepLength = 8;
        int startPoint = CardProtocalDefine.HEADER.length() + 2;
        String cardNumberData = readData.substring(startPoint, startPoint + stepLength);
        startPoint += stepLength;
        String innerCardNumberData = readData.substring(startPoint, startPoint + stepLength);
        startPoint += stepLength;
        String cutMoney = readData.substring(startPoint, startPoint + stepLength);
        startPoint += stepLength;
        String totalMoney = readData.substring(startPoint, startPoint + stepLength);
        Log.i("CardFunctionInterface", "cardNumberData  " + cardNumberData);
        Log.i("CardFunctionInterface", "innerCardNumberData  " + innerCardNumberData);
        Log.i("CardFunctionInterface", "cutMoney  " + cutMoney);
        Log.i("CardFunctionInterface", "totalMoney  " + totalMoney);
        stringBuilder.append("卡序列号为：" + FormatUtils.fromDataToCardNumber(cardNumberData) + "\n");
        stringBuilder.append("内部卡号为：" + FormatUtils.fromDataToCardNumber(innerCardNumberData) + "\n");
        stringBuilder.append("消费金额为：" + FormatUtils.fromAmountDataToAmount(cutMoney) + "元\n");
        stringBuilder.append("卡内余额为：" + FormatUtils.fromAmountDataToAmount(totalMoney) + "元\n");
        return stringBuilder.toString();
    }

    private void resetReadBuilder(ReadSerialEvent readSerialEvent) {
        if (!TextUtils.isEmpty(readSerialEvent.getMsg())) {
            EventBus.getDefault().post(readSerialEvent);
        }
        dataReadBuilder.setLength(0);
    }
}
