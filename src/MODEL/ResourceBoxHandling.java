package MODEL;

public interface ResourceBoxHandling {
    void fill();
    void fillByAmount(int resourceVolume);
    int checkLevel();
    boolean criticalLevel();
    boolean empty();
    boolean LowLevel();
    boolean isEnough(int Volume);
    boolean syphonOut(int Volume);

}
