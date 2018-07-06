package pojo;

public class CmdTime {

    private int upTime;
    private int upStopTime;
    private int downTime;
    private int downStopTime;

    public CmdTime() {
    }

    public CmdTime(int upTime, int upStopTime, int downTime, int downStopTime) {
        this.upTime = upTime;
        this.upStopTime = upStopTime;
        this.downTime = downTime;
        this.downStopTime = downStopTime;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public int getUpStopTime() {
        return upStopTime;
    }

    public void setUpStopTime(int upStopTime) {
        this.upStopTime = upStopTime;
    }

    public int getDownTime() {
        return downTime;
    }

    public void setDownTime(int downTime) {
        this.downTime = downTime;
    }

    public int getDownStopTime() {
        return downStopTime;
    }

    public void setDownStopTime(int downStopTime) {
        this.downStopTime = downStopTime;
    }

    @Override
    public String toString() {
        return upTime +
                "," + upStopTime +
                "," + downTime +
                "," + downStopTime;
    }
}
