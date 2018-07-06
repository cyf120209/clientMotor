package showdraperInformation.presenter;

import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import showdraperInformation.view.IAllDrapersInformation;
import util.Draper;
import util.MyLocalDevice;
import util.Public;

import java.util.List;
import java.util.Map;

public class AllDrapersInformationPresenterImpl implements IAllDrapersInformationPresenter {

    IAllDrapersInformation mAllDrapersInformation;

    private boolean isWait = false;

    @Override
    public void setWait(boolean wait) {
        System.out.println("wait: "+wait);
        isWait = wait;
    }

    public AllDrapersInformationPresenterImpl(IAllDrapersInformation mAllDrapersInformation) {
        this.mAllDrapersInformation = mAllDrapersInformation;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllInformation();
            }
        }).start();
    }

    @Override
    public void autoGetAllInformation() {
        isWait=false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllInformation();
            }
        }).start();
    }

    private void getAllInformation(){
        mAllDrapersInformation.clearAll();
        List<RemoteDevice> remoteDeviceList = MyLocalDevice.mRemoteUtils.getRemoteDeviceList();
        for (int i = 0; i < remoteDeviceList.size(); i++) {
            System.out.println("i: "+i);
            RemoteDevice remoteDevice = remoteDeviceList.get(i);
            try {
                while (isWait) {
                    Thread.sleep(500);
                }
                if (Public.matchString(Public.readModelName(remoteDevice), "MC-AC")) {
                    isWait=true;
                    System.out.println("id "+remoteDevice.getInstanceNumber());
                    getDraperInformation(remoteDevice);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDraperInformation(final RemoteDevice remoteDevice) {
        try {
            Draper.sendAnnounceDraperInformation(remoteDevice);
        } catch (BACnetException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void extended(Integer id) {
        try {
            Map<Integer, RemoteDevice> remoteDeviceMap = MyLocalDevice.mRemoteUtils.getRemoteDeviceMap();
            Draper.sendCmd(remoteDeviceMap.get(id),4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(Integer id) {
        try {
            Map<Integer, RemoteDevice> remoteDeviceMap = MyLocalDevice.mRemoteUtils.getRemoteDeviceMap();
            Draper.sendCmd(remoteDeviceMap.get(id),1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retracted(Integer id) {
        try {
            Map<Integer, RemoteDevice> remoteDeviceMap = MyLocalDevice.mRemoteUtils.getRemoteDeviceMap();
            Draper.sendCmd(remoteDeviceMap.get(id),3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



