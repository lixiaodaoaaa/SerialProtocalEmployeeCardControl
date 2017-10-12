package vmc.employee.card.utils;

import static vmc.employee.card.utils.CardProtocalDefine.CUT_HEADER_COMMAND_DATA;
import static vmc.employee.card.utils.CardProtocalDefine.DATA_EMPTY;
import static vmc.employee.card.utils.CardProtocalDefine.HEADER_DATA1;
import static vmc.employee.card.utils.CardProtocalDefine.HEADER_DATA2;
import static vmc.employee.card.utils.FormatUtils.fromAmountToAmountData;
import static vmc.employee.card.utils.FormatUtils.revertString;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public class CardProtocalUtils {


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

    public static String getReadLastTimeAvailbleAmountCommand() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_READ_AVAIBLE_AMOUNT + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
    }

    public static String getMachineIdCommand() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_READ_MACHINE_ID + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
    }

    public static String getReadConnectServerStatusCommond() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_READ_CONNECT_SERVER_STATUS + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
    }

    public static String getReadCustmerCardNumberCommond() {
        String checkHexData = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_READ_CUSTMER_CARD_NUMBER + DATA_EMPTY;
        String checkResult = getAddCheckResult(checkHexData);
        return checkHexData + checkResult;
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

}
