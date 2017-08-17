package vmc.employee.card.utils;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public class FormatUtils {


    //举例 01  formatter to  01
    public static String intValueTo2HexString(int value) {
        return String.format("%02x", value);
    }


    //举例 01  formatter to  01 00
    public static String intValueTo4HexString(int value) {
        return String.format("%04x", value);
    }

    /**
     * 将String转化为byte[]数组
     *
     * @param arg 需要转换的String对象
     * @return 转换后的byte[]数组
     * Exam:
     * form :"AABB010A0000007001"
     * To_sHEX_Bytes:AABB010A0000007001
     */
    public static byte[] toByteArray(String arg) {
        int len = arg.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(arg.charAt(i), 16) << 4)
                    + Character.digit(arg.charAt(i + 1), 16));
        }
        return data;
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

    /**
     * 重新排序 string
     * 输入 00000001
     * 输出：01 00 00 00
     *
     * @return
     */
    public static String revert4String(String str) {
        if (str.trim().toString().length() != 8) {
            return str;
        }
        String header = str.substring(0, 4);
        String endBody = str.substring(4, 8);
        return revertString(endBody) + revertString(header);
    }

    /**
     * 从数据位的Data
     * 到真实的数字金额
     *
     * @return
     */
    public static float fromAmountDataToAmount(String amountData) {
        String hexValue = revert4String(amountData);
        Long amount = Long.parseLong(hexValue, 16);
        float realAmount = amount / (float) 10;
        System.out.println("" + realAmount);
        return realAmount;
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
        String hexValue = String.format("%08x", calIntegerValue);
        return revert4String(hexValue);
    }
}
