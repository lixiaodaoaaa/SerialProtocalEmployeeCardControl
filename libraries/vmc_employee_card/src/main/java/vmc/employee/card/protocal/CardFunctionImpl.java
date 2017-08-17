package vmc.employee.card.protocal;

import android.content.Context;

/**
 * Created by lixiaodaoaaa on 2017/8/16.
 */

public class CardFunctionImpl implements ICardFunction {

    CardFunctionProxy cardFunctionProxy;


    @Override
    public void init(Context context) {
        cardFunctionProxy = new CardFunctionProxy();
        cardFunctionProxy.init(context);
    }


    @Override
    public void cutAmount(float amount) {
        cardFunctionProxy.cutAmount(amount);
    }

    @Override
    public void cancelCutAmount() {
        cardFunctionProxy.cancelCutAmount();
    }

    @Override
    public void readLastTimeAvailbleAmount() {
        cardFunctionProxy.readLastTimeAvailbleAmount();
    }

    @Override
    public void readMachineID() {
        cardFunctionProxy.readMachineID();
    }

    @Override
    public void readConnectServerStatus() {
        cardFunctionProxy.readConnectServerStatus();
    }
}
