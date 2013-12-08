package lab.mage.rate.api;

import java.util.concurrent.TimeUnit;

public enum RampUpDelay {

    ONE_MINUTE("1m", TimeUnit.MINUTES.toMillis(1L)),
    FIVE_MINUTES("5m", TimeUnit.MINUTES.toMillis(5L)),
    FIFTEEN_MINUTES("15m", TimeUnit.MINUTES.toMillis(15L)),
    THIRTEEN_MINUTES("30m", TimeUnit.MINUTES.toMillis(30L)),
    ONE_HOUR("1h", TimeUnit.HOURS.toMillis(1L));

    private String unit;
    private long duration;

    private RampUpDelay(String unit, long duration) {
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
