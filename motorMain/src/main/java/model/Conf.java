package model;

import java.io.Serializable;

public class Conf implements Serializable{

    private String modelName;

    private FirmWareInformation firmWareInformation;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public FirmWareInformation getFirmWareInformation() {
        return firmWareInformation;
    }

    public void setFirmWareInformation(FirmWareInformation firmWareInformation) {
        this.firmWareInformation = firmWareInformation;
    }
}
