package model;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup {

    private Integer deviceId;

    private Integer groupId;


    List<Integer> shadeList =new ArrayList<>();

    public DeviceGroup(Integer deviceId, Integer groupId) {
        this.deviceId = deviceId;
        this.groupId = groupId;
    }

    public DeviceGroup(Integer deviceId, Integer groupId, List<Integer> shadeList) {
        this.deviceId = deviceId;
        this.groupId = groupId;
        this.shadeList = shadeList;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getShadeList() {
        return shadeList;
    }

    public void setShadeList(List<Integer> shadeList) {
        this.shadeList = shadeList;
    }

    @Override
    public String toString() {
        return "deviceId=" + deviceId + ", groupId=" + groupId ;
    }
}
