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
package lab.mage.rate.example;

import lab.mage.rate.api.RampUpDelay;
import lab.mage.rate.api.TestScenario;
import lab.mage.rate.api.WorkingHour;

public class RATEExample {

    public RATEExample() {
        super();
    }

    public static void main(String[] args) {
        final TestScenario testScenario = TestScenario.create("My first test scenario", "Some useful description")
                .host("http://localhost:4711")
                .documentRoot("api")
                .workingHour(WorkingHour.FIVE_MINUTES)
                .rampUpDelay(RampUpDelay.ONE_MINUTE)
                .addRobot(100, NewsFeedRobot.class)
                .build();

        testScenario.execute();
    }
}
