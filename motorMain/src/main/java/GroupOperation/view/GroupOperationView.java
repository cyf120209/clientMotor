package GroupOperation.view;

import com.serotonin.bacnet4j.RemoteDevice;

import java.util.List;

/**
 * Created by lenovo on 2017/1/19.
 */
public interface GroupOperationView {

    int getDeviceNum();

    int getGroupNum();

    void updateDraper(String[] drapers);

    int getdevBoxSelectedItem();

    int getdevBoxSelectedIndex();

    void updateDevice(Object[] arr);

    void updateGroup(Object[] arr);
}
