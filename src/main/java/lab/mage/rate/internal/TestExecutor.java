package lab.mage.rate.internal;

import lab.mage.rate.api.Robot;
import lab.mage.rate.api.TestScenario;
import lab.mage.rate.internal.util.JDBCConnectionProvider;

import java.sql.*;
import java.util.List;

public class TestExecutor {

    public TestExecutor() {
        super();
    }

    public void execute(final TestScenario testScenario) {
        final int testScenarioId = this.storeTestScenario(testScenario);

        final String host = testScenario.getHost();
        final String path = testScenario.getPath();
        final long lifetime = testScenario.getWorkingHour().getDuration();
        final long rampUpDelay = testScenario.getRampUpDelay().getDuration();

        final List<TestScenario.RobotEntry> robotEntries = testScenario.getRobotEntries();
        for (TestScenario.RobotEntry robotEntry : robotEntries) {
            final Class<? extends Robot> robotType = robotEntry.getRobotType();
            final int amount = robotEntry.getAmount();

            final RestJsonRequestProcessor requestProcessor = new RestJsonRequestProcessor(testScenarioId, host, path);
            final RobotThread robotThread = new RobotThread(requestProcessor, robotType, amount,
                    lifetime, rampUpDelay);

            robotThread.start();
        }
    }

    private int storeTestScenario(final TestScenario testScenario) {
        final String insertStatement =
                "insert into test_scenario (id, name, description, host, path, working_hours, delay, created) values (?, ?, ?, ?, ?, ?, ?, ?)";

        int id = -1;
        try {
            final Connection connection = JDBCConnectionProvider.create();

            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("select nextval('test_scenario_id_seq')");
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();

            final PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, testScenario.getName());
            preparedStatement.setString(3, testScenario.getDescription());
            preparedStatement.setString(4, testScenario.getHost());
            preparedStatement.setString(5, testScenario.getPath());
            preparedStatement.setString(6, testScenario.getWorkingHour().getUnit());
            preparedStatement.setString(7, testScenario.getRampUpDelay().getUnit());
            preparedStatement.setDate(8, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
