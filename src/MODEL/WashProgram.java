package MODEL;

import java.io.Serializable;

public class WashProgram implements WashingProgram, Serializable {

    private String name;
    private int remainingTime;
    private int detergentVolume;
    private int softenerVolume;

    public WashProgram(String _name, int _remainingTime, int _detergentVolume, int _softenerVolume) {
        name = _name;
        remainingTime = _remainingTime;
        detergentVolume = _detergentVolume;
        softenerVolume = _softenerVolume;
    }



    @Override
    public void setTime(int _remainingTime) {
        remainingTime = _remainingTime;
    }

    @Override
    public void setDetergentVolume(int _detergentVolume) {
        detergentVolume = _detergentVolume;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getDetergentVolume() {
        return detergentVolume;
    }

    public int getSoftenerVolume() {
        return softenerVolume;
    }

    public void setSoftenerVolume(int softenerVolume) {
        this.softenerVolume = softenerVolume;
    }


    @Override
    public String toString() {
        return name + ": will take: " + remainingTime +
                " minutes, " + detergentVolume +
                "ml of detergent and " + softenerVolume + "ml of Softener";
    }
}
