package vmc.employee.card.utils;

/**
 * Created by lixiaodaoaaa on 2017/8/16.
 */

public interface CardProtocalDefine {

    String HEADER_DATA1 = "AA";
    String HEADER_DATA2 = "BB";
    String HEADER = HEADER_DATA1 + HEADER_DATA2;

    String CUT_HEADER_COMMAND_DATA = HEADER_DATA1 + HEADER_DATA2 + CardFunctionInterface.FUNCTION_CUT;
    String DATA_EMPTY = "0000" + "0000";

    /**
     * 扣款成功
     */
    String DATA_CUT_SUCCESS = "F0";
    /**
     * 通讯成功
     */
    String DATA_CONNECT_SUCCESS = "FE";
    /**
     * 取消成功
     */
    String DATA_CANCEL_SUCCESS = "F1";
    /**
     * 操作延时退出
     */
    String DATA_TIME_OUT = "FD";

    /**
     * 卡片损坏 ，请找管理员修复
     */
    String DATA_CARD_BAD = "03";


    /**
     * 写卡未完成
     */
    String DATA_WRITE_CARD_NOT_COMPLETE = "04";

    /**
     * 非同一张卡操作
     */
    String DATA_CARD_DIFFER = "05";

    /**
     * 此卡已在别的设备消费
     */
    String DATA_CARD_OVER_DUE = "06";

    /**
     * 扇区密码错误
     */
    String DATA_ERROR_PASSWORD = "07";

    /**
     * 此卡级别未开放
     */
    String DATA_LEVER_NOT_OPEN = "10";

    /**
     * 此卡级别未开放
     */
    String DATA_CARD_BLACK = "11";


    /**
     * 此卡级别未开放
     */
    String DATA_VALID_ERROR1 = "12";

    /**
     * 此卡级别未开放
     */
    String DATA_VALID_ERROR2 = "13";

    /**
     * 此卡未发卡
     */
    String DATA_NO_ACCESS_CARD = "14";


    /**
     * 此卡已退卡
     */
    String DATA_CARD_EXIT = "15";

    /**
     * 此卡发卡异常
     */
    String DATA_CARD_UNUSUAL = "16";

    /**
     * 余额不足
     */
    String DATA_CARD_MONEY_LOW = "20";

    /**
     * 不在消费时段
     */
    String DATA_NOT_IN_THE_TIME = "21";

    /**
     * 超过最大次数
     */
    String DATA_LIMIT_OUT = "22";

    /**
     * 超过最大消费额度
     */
    String DATA_EXCEED_PAY_MAX = "23";

    /**
     * 超过最大充值金额
     */
    String DATA_EXCEED_RECHARGE_MAX = "24";

    /**
     * 消费餐段禁止
     */
    String DATA_EXCEE_BAND_TIME_PART = "25";


    /**
     * 此卡无补贴
     */

    String DATA_CARD_NO_SUPPLY = "30";
    /**
     * 此卡已领取不贴
     */
    String DATA_HAS_GET_SUPPLY = "31";

    /**
     * 补贴超过额度
     */
    String DATA_EXCEED_SUPPLY = "32";

    /**
     * 未到领取时间
     */

    String DATA_GET_TIME_ERROR = "33";

    /**
     * 此卡未订餐
     */

    String DATA_CARD_NO_ORDER = "34";

    /**
     * 此卡已订餐
     */
    String DATA_CARD_HAS_ORDER = "35";

    /**
     * 输入金额有误
     */
    String DATA_MONEY_ERROR = "36";

    /**
     * 记录已满
     */
    String DATA_RECORD_FULL = "50";
    /**
     * 间隔不足
     */
    String DATA_INTERNEL_NOT_FULL = "51";
    /**
     * 连续刷卡
     */
    String DATA_CONTINUE_READ_CARD = "52";
}
