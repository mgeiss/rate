/**
 * Copyright 2013 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
