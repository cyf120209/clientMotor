package suntracking.presenter;

import com.serotonin.bacnet4j.type.constructed.Sequence;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import model.DeviceGroup;
import model.ShadeParameter;
import model.ShutterParameter;
import pojo.LngLat;

public interface SuntrackingPresenter {

    void setLngLat(LngLat lngLat);
    LngLat getLngLat();

    void setShadeParameter(ShadeParameter shadeParameter);
    ShadeParameter getShadeParameter();

    void setShutterParameter(ShutterParameter shutterParameter);
    ShutterParameter getShutterParameter();

    DeviceGroup getDeviceGroup();

    void paraseServiceParameter(UnsignedInteger serviceNumber, Sequence serviceParameters);

    void sendAnnounce();
}
