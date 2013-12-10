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
package lab.mage.rate.internal;

import lab.mage.rate.api.RequestProcessor;
import lab.mage.rate.api.Robot;

public class RobotThread extends Thread {

    private final RequestProcessor requestProcessor;
    private final Class<? extends Robot> robotType;
    private final int amount;
    private final long lifetime;
    private final long rampUpDelay;

    public RobotThread(final RequestProcessor requestProcessor, final Class<? extends Robot> robotType, final int amount,
                       final long lifetime, final long rampUpDelay) {
        super();
        this.requestProcessor = requestProcessor;
        this.robotType = robotType;
        this.amount = amount;
        this.lifetime = lifetime;
        this.rampUpDelay = rampUpDelay;
    }

    @Override
    public void run() {
        long delay = this.rampUpDelay / this.amount;

        for (int i = 0; i < this.amount; i++) {

            try {
                final Robot robot = robotType.newInstance();

                new Thread(robot.getClass().getSimpleName() + ":" + i) {
                    @Override
                    public void run() {
                        robot.start(RobotThread.this.lifetime, RobotThread.this.requestProcessor);
                    }
                }.start();

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
