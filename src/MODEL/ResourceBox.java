package MODEL;

public abstract class ResourceBox implements ResourceBoxHandling {

    private final int MAX_LEVEL = 1000;
    private int resourceLevel;

    @Override
    public void fill() {
        resourceLevel = MAX_LEVEL;
    }

    @Override
    public void fillByAmount(int _resourceLevel) {
        resourceLevel = _resourceLevel;
    }

    @Override
    public int checkLevel() {
        return resourceLevel;
    }

    @Override
    public boolean criticalLevel() {
        return resourceLevel < 75;
    }

    @Override
    public boolean empty() {
        return resourceLevel == 0;
    }

    @Override
    public boolean LowLevel() {
        return resourceLevel < 250;
    }

    @Override
    public boolean isEnough(int Volume) {
        return Volume <= resourceLevel;
    }

    @Override
    public boolean syphonOut(int Volume) {
        if (isEnough(Volume)) {
            resourceLevel -= Volume;
            return true;
        } else {
            return false;
        }
    }
}
