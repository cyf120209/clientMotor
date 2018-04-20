package update.view;

import com.serotonin.bacnet4j.RemoteDevice;
import model.FirmWareInformation;

import java.util.List;

/**
 * Created by lenovo on 2017/1/20.
 */
public interface UpdateView {

    void updateFileText(String text);

    int getDelay();

    String getFileName();

    RemoteDevice getdevBoxSelectedItem();

    void updateVersionLabel(String text);

    void updateDevBox(List<RemoteDevice> remoteDevices);

    void updateDevBox(RemoteDevice remoteDevice);

    void updateVersionAndType(FirmWareInformation firmWareInformation);

    void showError(String str);

    /**
     * 获取版本号
     * @param version
     */
    void showOriginalDeviceVersion(String version);

    /**
     * 更新之前的版本号
     * @param version
     */
    void showBeforeDeviceVersion(String version);

    /**
     * 更新之后的版本号
     * @param version
     */
    void showAfterDeviceVersion(String version);

    /**
     * 显示是否升级对话框
     */
    void showConfirmDialog(String str);

    /**
     * 更新升级信息
     * @param version
     */
    void showUpgradeInformation(String version);

    void updateFinish();

    void onStart() ;

    void onCancel();

    void onFinish() ;

    void onProgressChanged(Integer masterProgress,Integer slaveProgress);

}
