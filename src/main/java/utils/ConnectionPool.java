package utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.apache.log4j.Logger.getLogger;

public class ConnectionPool {
    public static final Logger logger = getLogger(ConnectionPool.class);
    private static DataSource dataSource;

    public static synchronized Connection getConnection(){
        if (dataSource==null) {
            try {
                Context initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/hotel");
            } catch (NamingException e) {
                logger.error(e.getStackTrace());
            }
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getStackTrace());
        }
        logger.info("Getting connection: " + connection);
        return connection;
    }
}
