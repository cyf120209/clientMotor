package updownstop.presenter;

import com.serotonin.bacnet4j.RemoteDevice;
import updownstop.view.IUpDownStop;
import util.Draper;
import util.FileUtils;
import util.MyLocalDevice;

import java.util.Map;

public class UpDownStopPresenterImpl implements IUpDownStopPresenter{

    private IUpDownStop mUpDownStop;

    public UpDownStopPresenterImpl(IUpDownStop upDownStop) {
        this.mUpDownStop = upDownStop;
        String cmdTime = FileUtils.readUserDirFile("cmdTime", "cmdTime.txt");
        String[] times = cmdTime.split(",");
        if(times.length==4){
            mUpDownStop.update(times);
        }
    }

    @Override
    public void stop() {
        try {
            Draper.sendCmd(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
