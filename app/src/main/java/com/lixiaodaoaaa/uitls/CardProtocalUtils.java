package com.lixiaodaoaaa.uitls;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public class CardProtocalUtils {


    private static String HEADER_DATA1 = "AA";
    private static String HEADER_DATA2 = "BB";

    private static String CUT_HEADER_COMMAND_DATA = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_CUT;
    private static String DATA_EMPTY = "0000" + "0000";


    public static String getCutAmountCommand(float amountValue) {
        String amountCommandData = fromAmountToAmountData(amountValue);
        String checkHexData = getAddCheckResult(CUT_HEADER_COMMAND_DATA + amountCommandData);
        System.out.println(amountValue + "   | " + CUT_HEADER_COMMAND_DATA + amountCommandData + checkHexData);
        return CUT_HEADER_COMMAND_DATA + amountCommandData + checkHexData;
    }

    public static String getCancelCutCommand() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_CANCEL_CUT + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
    }

    public static String getMachineIdCommand() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_READ_MACHINE_ID + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
    }


    /***
     *
     * @param amountValue
     * FF 00 = 255
     * 00 01 = 25.6
     * 00 02  = 51.2
     * amountValue convertTo 16Hex
     * 数据位为 4个字节 data1 data2 data3 data4
     * @return
     */
    public static String fromAmountToAmountData(float amountValue) {
        Float calValue = amountValue * 10;
        int calIntegerValue = calValue.intValue();
        String hexValue = String.format("%04x", calIntegerValue);
        String data3 = "00";
        String data4 = "00";
        //数据的前两位;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
        String dataFrontSecond = revertString(hexValue);
        return dataFrontSecond + data3 + data4;
    }

    public static String getAddCheckResult(String checkData) {
        if (checkData == null || checkData.equals("")) {
            return "";
        }
        int result = 0;
        for (int i = 0; i < checkData.length(); i += 2) {
            result += Integer.parseInt(checkData.substring(i, i + 2), 16);
        }
        return revertString(String.format("%04x", result));
    }


    /**
     * 重新排序 string
     * 举例输入：00 11
     * 输出   :11 00
     * <p>
     * 输入 11 22
     * 输出 22 11
     *
     * @return
     */
    public static String revertString(String str) {
        String data0 = str.substring(2, str.length());
        String data1 = str.substring(0, 2);
        return data0 + data1;
    }

}
