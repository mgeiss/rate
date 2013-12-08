package lab.mage.rate.example;

import lab.mage.rate.api.RampUpDelay;
import lab.mage.rate.api.TestScenario;
import lab.mage.rate.api.WorkingHour;

public class RATEExample {

    public RATEExample() {
        super();
    }

    public static void main(String[] args) {
        final TestScenario testScenario = TestScenario.create("1", "some useful description")
                .host("http://localhost:4711")
                .path("api")
                .workingHour(WorkingHour.ONE_HOUR)
                .rampUpDelay(RampUpDelay.FIVE_MINUTES)
                .addRobot(100, NewsFeedRobot.class)
                .build();

        testScenario.execute();
    }
}
