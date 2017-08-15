package com.lixiaodaoaaa.uitls;

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
    String function_read_avaible_amount = "04";

    /**
     * 读取是否成功链接服务器成功
     */
    String function_read_connect_server_status = "05";
}
