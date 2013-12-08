package lab.mage.rate.internal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionProvider {

    private JDBCConnectionProvider() {
        super();
    }

    public static Connection create() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rate", "postgres", "postgres");
    }
}
