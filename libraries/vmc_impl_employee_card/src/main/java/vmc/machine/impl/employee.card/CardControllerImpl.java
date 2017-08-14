package vmc.machine.impl.employee.card;

import vmc.serialport.ISerialPortController;
import vmc.serialport.IVMCProtocol;

/**
 * <b>Create Date:</b> 9/23/16<br>
 * <b>Author:</b> Gordon<br>
 * <b>Description:</b> <br>
 */
public class CardControllerImpl implements IVMCProtocol {


    @Override
    public void onDataReceived(byte[] data, int size) {

    }

    @Override
    public void setSerialPortController(ISerialPortController controller) {

    }

    @Override
    public ISerialPortController getSerialPortController() {
        return null;
    }
}
