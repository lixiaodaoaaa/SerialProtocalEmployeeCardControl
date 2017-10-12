package vmc.employee.card.protocal;

import android.content.Context;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public interface ICardFunction {

    void init(Context context);

    void cutAmount(float amount);

    void cancelCutAmount();

    void readLastTimeAvailbleAmount();

    void readMachineID();

    void readConnectServerStatus();

    void readCustomerCardNumber();
}
