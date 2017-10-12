package vmc.employee.card.utils;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public interface CardFunctionInterface {

    /**
     * 执行扣款功能位
     */
    String FUNCTION_CUT = "01";
    /**
     * 取消扣款功能位
     */
    String FUNCTION_CANCEL_CUT = "02";
    /**
     * 读取机器id
     */
    String FUNCTION_READ_MACHINE_ID = "03";
    /**
     * 读取最后一次消费卡的余额
     */
    String FUNCTION_READ_AVAIBLE_AMOUNT = "04";

    /**
     * 读取是否成功链接服务器成功
     */
    String FUNCTION_READ_CONNECT_SERVER_STATUS = "05";

    /**
     * 读取消费者卡号
     *
     */
    String FUNCTION_READ_CUSTMER_CARD_NUMBER = "06";
}
