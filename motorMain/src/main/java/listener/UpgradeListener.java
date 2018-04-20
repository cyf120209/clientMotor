package listener;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.RemoteObject;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.acknowledgement.AcknowledgementService;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.Sequence;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import common.Common;
import update.presenter.IUpgrade;
import update.presenter.UpdatePresenterImpl;
import util.MyLocalDevice;
import update.presenter.UpdatePresenter;
import update.view.UpdateView;
import util.Public;
import util.STExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/19.
 */
public class UpgradeListener extends DeviceEventAdapter {

    private final IUpgrade mUpgrade;

    /**
     * 找到指定modelname的设备
     */
    boolean isFound = false;

    /**
     * 记录原始设备的个数，若和sourceAddress相等，则说明找全设备
     */
    private int originCount = 0;

    /**
     * 去重处理
     */
    private List<Integer> remoteDeviceIDList = new ArrayList<>();
    private boolean Debug = false;


    public UpgradeListener(IUpgrade upgrade) {
        this.mUpgrade = upgrade;
    }

    @Override
    public void iAmReceived(RemoteDevice d) {
        super.iAmReceived(d);
        Integer id = Integer.valueOf(d.getInstanceNumber());
        boolean exist = remoteDeviceIDList.contains(id);
        if (exist) {
            System.out.println("exits id: "+id);
            return;
        }
        System.out.println("id: "+id);
        remoteDeviceIDList.add(id);
        STExecutor.submit(new FilterRemoteDevice(d));
    }

    class FilterRemoteDevice implements Runnable {

        RemoteDevice d;

        public FilterRemoteDevice(RemoteDevice d) {
            this.d = d;
        }

        @Override
        public void run() {
            //若flag为升级后 的状态，则更新Mylocaldevice
            if (mUpgrade.getFlag() == 3) {
                try {
                    String reg = Public.getAllString(mUpgrade.getFirmWareType(), "[A-za-z0-9-/]");
                    //墙面开关
                    if (Public.matchString(reg, "^WS-")) {
                        String substring = reg.substring(3);
                        String[] split = substring.split("/");
                        boolean match = false;
                        for (int i = 0; i < split.length; i++) {
                            if (Public.matchString(Public.readModelName(d), "WS-" + split[i])) {
                                match = true;
                                break;
                            }
                        }
                        if (match) {
                            isFound = true;
                            mUpgrade.addJListDevice(d);
                            MyLocalDevice.updateRemoteDevice(d);

                        }
                    } else {
                        if (Public.matchString(Public.readModelName(d), reg)) {
                            isFound = true;
                            mUpgrade.addJListDevice(d);
                            MyLocalDevice.updateRemoteDevice(d);
//                            System.out.println("match: "+isFound);
                            //mUpdateView.showUpgradeInformation(" ----------------send to jList");
                        }
                    }
                } catch (BACnetException e) {
                    e.printStackTrace();
                }
            } else if (mUpgrade.getFlag() == 1 || mUpgrade.getFlag() == 2) {
                String reg = Public.getAllString(mUpgrade.getFirmWareType(), "[A-za-z0-9-/]");
//                    mUpdateView.showUpgradeInformation(reg);
                if (Public.matchString(reg, "^WS-")) {
                    wallSwitch(reg);
                } else {
                    other(reg);
                }
            }

            //判断原始数据阶段是否找全所有设备
            if (mUpgrade.getFlag() == 1) {
                originCount++;
//                System.out.println("总个数 "+MyLocalDevice.getAddressList().size()+"/"+originCount);
                if (MyLocalDevice.getAddressList().size() == originCount) {
                    if (isFound) {
                        mUpgrade.findOriginDevice(Common.DEVICE_FOUND_ALL);
                        isFound = false;
                    } else {
//                        mUpdateView.showConfirmDialog("no device found! upgrade exit!");
                    }
                    if (Debug) {
//                        mUpdateView.showUpgradeInformation("-----+  origin 找到所有电机");
                    }
                }
            }
        }

        //墙面开关
        private void wallSwitch(String reg){
//          mUpdateView.showUpgradeInformation("-墙面开关");
            String substring = reg.substring(3);
            String[] split = substring.split("/");
            boolean match = false;
            for (int i = 0; i < split.length; i++) {
                if (Public.matchString(Public.readModelName(d), "WS-" + split[i])) {
                    match = true;
                    break;
                }
            }
            if (match) {
                isFound = true;
                mUpgrade.addJListDevice(d);
            }
        }

        private void other(String reg) {
            if (Public.matchString(Public.readModelName(d), reg)) {
                isFound = true;
                mUpgrade.addJListDevice(d);
                //mUpdateView.showUpgradeInformation(" ----------------send to jList");
            }
        }
    }

    @Override
    public void privateTransferReceived(UnsignedInteger vendorId, UnsignedInteger serviceNumber, Encodable serviceParameters) {
        //super.privateTransferReceived(vendorId, serviceNumber, serviceParameters);
        System.out.println("111");
        Sequence parms = (Sequence) serviceParameters;
        if (serviceNumber.intValue() == 7) {
            for (int i = 0; i < 2; i++) {
                String RC = parms.getValues().get("Device RC" + i).toString();
                String GP = parms.getValues().get("Device GP" + i).toString();
                System.out.println(RC + "----" + GP);
            }
        }
    }

    @Override
    public AcknowledgementService privateTransferReceivedComplex(UnsignedInteger vendorId, UnsignedInteger serviceNumber, Encodable serviceParameters, Address address) {
        return mUpgrade.privateTransferReceivedComplex(vendorId, serviceNumber, serviceParameters, address);
    }

    public void clearRemoteDeviceList() {
        originCount = 0;
        isFound=false;
        this.remoteDeviceIDList.clear();
    }

    ReceivedListener listener;

    public void setListener(ReceivedListener listener) {
        this.listener = listener;
    }

    public interface ReceivedListener {
        void received();
    }
}