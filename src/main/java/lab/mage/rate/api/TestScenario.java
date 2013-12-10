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

import lab.mage.rate.internal.TestExecutor;

import java.util.ArrayList;
import java.util.List;

public class TestScenario {

    public static class Builder {

        private String name;
        private String description;
        private String host;
        private String documentRoot;
        private WorkingHour workingHour;
        private RampUpDelay rampUpDelay;
        private List<RobotEntry> robotEntries;

        private Builder(String name, String description) {
            super();
            this.name = name;
            this.description = description;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder documentRoot(String documentRoot) {
            this.documentRoot = documentRoot;
            return this;
        }

        public Builder workingHour(WorkingHour workingHour) {
            this.workingHour = workingHour;
            return this;
        }

        public Builder rampUpDelay(RampUpDelay rampUpDelay) {
            this.rampUpDelay = rampUpDelay;
            return this;
        }

        public Builder addRobot(int amount, Class<? extends Robot> robotType) {
            if (this.robotEntries == null) {
                this.robotEntries = new ArrayList<>();
            }
            this.robotEntries.add(new RobotEntry(amount, robotType));
            return this;
        }

        public TestScenario build() {
            return new TestScenario(this.name, this.description, this.host, this.documentRoot, this.workingHour,
                    this.rampUpDelay, this.robotEntries);
        }
    }

    public static class RobotEntry {
        private final int amount;
        private final Class<? extends Robot> robotType;

        private RobotEntry(final int amount, final Class<? extends Robot> robotType) {
            super();
            this.amount = amount;
            this.robotType = robotType;
        }

        public int getAmount() {
            return amount;
        }

        public Class<? extends Robot> getRobotType() {
            return robotType;
        }
    }

    private final String name;
    private final String description;
    private final String host;
    private final String documentRoot;
    private final WorkingHour workingHour;
    private final RampUpDelay rampUpDelay;
    private final List<RobotEntry> robotEntries;

    private TestScenario(final String name, final String description, final String host, final String documentRoot,
                         final WorkingHour workingHour, final RampUpDelay rampUpDelay,
                         final List<RobotEntry> robotEntries) {
        super();
        this.name = name;
        this.description = description;
        this.host = host;
        this.documentRoot = documentRoot;
        this.workingHour = workingHour;
        this.rampUpDelay = rampUpDelay;
        this.robotEntries = robotEntries;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHost() {
        return host;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public WorkingHour getWorkingHour() {
        return workingHour;
    }

    public RampUpDelay getRampUpDelay() {
        return rampUpDelay;
    }

    public List<RobotEntry> getRobotEntries() {
        return robotEntries;
    }

    public static Builder create(final String name, final String description) {
        return new Builder(name, description);
    }

    public void execute() {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.execute(this);
    }
}
