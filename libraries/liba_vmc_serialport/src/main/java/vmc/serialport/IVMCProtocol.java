package vmc.serialport;

/**
 * <b>Create Date:</b> 9/22/16<br>
 * <b>Author:</b> Gordon<br>
 * <b>Description:</b> <br>
 */
public interface IVMCProtocol {

    void onDataReceived(byte[] data, int size);

    void setSerialPortController(ISerialPortController controller);

    ISerialPortController getSerialPortController();
}
