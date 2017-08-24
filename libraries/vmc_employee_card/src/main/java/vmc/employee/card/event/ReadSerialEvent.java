package vmc.employee.card.event;

/**
 * Created by lixiaodaoaaa on 2017/8/24.
 */

public class ReadSerialEvent {

    String msg ="";

    public ReadSerialEvent() {
    }

    public ReadSerialEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
