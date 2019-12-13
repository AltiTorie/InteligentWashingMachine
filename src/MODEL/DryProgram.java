package MODEL;

import java.io.Serializable;

public class DryProgram implements DryingProgram, Serializable {
    private String name;
    private int time;
    private int detergentVolume;
    private int temperature;

    public DryProgram(String _name, int _remainingTime, int _detergentVolume, int _temperature) {
        name = _name;
        time = _remainingTime;
        detergentVolume = _detergentVolume;
        temperature = _temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int remainingTime) {
        this.time = remainingTime;
    }

    public int getDetergentVolume() {
        return detergentVolume;
    }

    @Override
    public void setDetergentVolume(int detergentVolume) {
        this.detergentVolume = detergentVolume;
    }

    @Override
    public String toString() {
        return name + " will dry for: " +
                time + "minutes, " +
                detergentVolume + "ml of scent, " +
                "in " + temperature + " °C ";

    }
}
