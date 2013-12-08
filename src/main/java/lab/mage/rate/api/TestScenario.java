package lab.mage.rate.api;

import lab.mage.rate.internal.TestExecutor;

import java.util.ArrayList;
import java.util.List;

public class TestScenario {

    public static class Builder {

        private String uniqueId;
        private String description;
        private String host;
        private String path;
        private WorkingHour workingHour;
        private RampUpDelay rampUpDelay;
        private List<RobotEntry> robotEntries;

        private Builder(String uniqueId, String description) {
            super();
            this.uniqueId = uniqueId;
            this.description = description;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
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
            return new TestScenario(this.uniqueId, this.description, this.host, this.path, this.workingHour,
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
    private final String path;
    private final WorkingHour workingHour;
    private final RampUpDelay rampUpDelay;
    private final List<RobotEntry> robotEntries;

    private TestScenario(final String name, final String description, final String host, final String path,
                         final WorkingHour workingHour, final RampUpDelay rampUpDelay,
                         final List<RobotEntry> robotEntries) {
        super();
        this.name = name;
        this.description = description;
        this.host = host;
        this.path = path;
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

    public String getPath() {
        return path;
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

    public static Builder create(final String uniqueId, final String description) {
        return new Builder(uniqueId, description);
    }

    public void execute() {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.execute(this);
    }
}
