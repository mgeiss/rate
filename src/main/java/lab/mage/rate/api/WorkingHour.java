package lab.mage.rate.api;

import java.util.concurrent.TimeUnit;

public enum WorkingHour {

    FIVE_MINUTES("5m", TimeUnit.MINUTES.toMillis(5L)),
    ONE_HOUR("1h", TimeUnit.HOURS.toMillis(1L)),
    FOUR_HOURS("4h", TimeUnit.HOURS.toMillis(4L)),
    EIGHT_HOURS("8h", TimeUnit.HOURS.toMillis(8L));

    private String unit;
    private long duration;

    private WorkingHour(String unit, long duration) {
        this.unit = unit;
        this.duration = duration;
    }

    public String getUnit() {
        return this.unit;
    }

    public long getDuration() {
        return this.duration;
    }
}
