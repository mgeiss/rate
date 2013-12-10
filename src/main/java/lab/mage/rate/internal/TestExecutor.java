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
        final String path = testScenario.getDocumentRoot();
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
            final Connection connection = JDBCConnectionProvider.getInstance().getConnection();

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
            preparedStatement.setString(5, testScenario.getDocumentRoot());
            preparedStatement.setString(6, testScenario.getWorkingHour().getUnit());
            preparedStatement.setString(7, testScenario.getRampUpDelay().getUnit());
            preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
