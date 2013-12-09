package lab.mage.rate.internal.util;

import com.jolbox.bonecp.BoneCPDataSource;

import java.sql.Connection;

public class JDBCConnectionProvider {

    private static JDBCConnectionProvider _instance;
    private BoneCPDataSource dataSource;

    private JDBCConnectionProvider() throws Exception {
        super();
        this.initialize();
    }

    public static JDBCConnectionProvider getInstance() throws Exception {
        if (JDBCConnectionProvider._instance == null) {
            JDBCConnectionProvider._instance = new JDBCConnectionProvider();
        }

        return JDBCConnectionProvider._instance;
    }

    public Connection getConnection() throws Exception {
        return this.dataSource.getConnection();
    }

    private void initialize() throws Exception {
        Class.forName("org.postgresql.Driver");
        this.dataSource = new BoneCPDataSource();
        this.dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/rate");
        this.dataSource.setUsername("postgres");
        this.dataSource.setPassword("postgres");
        this.dataSource.setMinConnectionsPerPartition(5);
        this.dataSource.setMaxConnectionsPerPartition(200);
    }
}
