package ua.kiev.univ.courses.project4.hotel.dao;

import com.sun.istack.internal.Nullable;
import ua.kiev.univ.courses.project4.hotel.model.ApartmentClass;
import ua.kiev.univ.courses.project4.hotel.model.Request;
import org.apache.log4j.Logger;
import ua.kiev.univ.courses.project4.hotel.utils.ConnectionPool;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static org.apache.log4j.Logger.getLogger;

/**
 * DAO implementation for Request and MySQLdb.
 */
public class MySQLRequestDAO implements RequestDAO {
    public static final Logger logger = getLogger(MySQLRequestDAO.class);
    /**
     * Properties file with queries.
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("queries", Locale.getDefault());

    /**
     * Saves request in a database.
     *
     * @param request - request that is written in the database.
     * @return
     */
    @Override
    public boolean create(Request request) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("createRequest"));
            statement.setString(1, request.getClient().getLogin());
            statement.setInt(2, request.getCountPlaces());
            statement.setString(3, request.getApartmentClass().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateFrom = simpleDateFormat.format(request.getBusyFrom());
            String dateTo = simpleDateFormat.format(request.getBusyTo());
            statement.setString(4, dateFrom);
            statement.setString(5, dateTo);
            if (statement.executeUpdate()>0){
                logger.info("Create request: " + request.toString());
                return true;
            }
            logger.warn("Create request error: " + request.toString());
            return false;
        } catch (SQLException e) {
            logger.error("Create request: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Create request: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Deletes request from a database by id.
     *
     * @param id - request id to delete
     * @return true if request was deleted, false - else
     */
    @Override
    public boolean deleteById(Long id) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("deleteRequestById"));
            statement.setLong(1, id);
            if (statement.executeUpdate()>0) {
                logger.info("Delete request: " + id);
                return true;
            }
            logger.warn("Delete request: " + id);
            return false;
        } catch (SQLException e) {
            logger.error("Delete request: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Delete request: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Reads all requests.
     *
     * @return List of requests.
     */
    @Nullable
    @Override
    public List findAll() {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("findAllRequests"));
            ResultSet resultSet = statement.executeQuery();
            Request request;
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()){
                request = new Request();
                request.setId(resultSet.getLong(1));
                request.setClient(DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().read(resultSet.getLong(2)));
                request.setCountPlaces(resultSet.getInt(3));
                PreparedStatement statement1 = connection.prepareStatement(resourceBundle.getString("getApartmentClassById"));
                statement1.setLong(1, resultSet.getInt(4));
                ResultSet rs = statement1.executeQuery();
                if (rs.next()) {
                    String apartmentClass = rs.getString(1);
                    switch (apartmentClass) {
                        case "LUXURY":
                            request.setApartmentClass(ApartmentClass.LUXURY);
                            break;
                        case "AVERAGE":
                            request.setApartmentClass(ApartmentClass.LUXURY);
                            break;
                        case "ECONOMY":
                            request.setApartmentClass(ApartmentClass.ECONOMY);
                            break;
                    }
                }
                request.setBusyFrom(new Date(resultSet.getTimestamp(5).getTime()));
                request.setBusyTo(new Date(resultSet.getTimestamp(6).getTime()));
                requests.add(request);
            }
            logger.info("FindAll requests.");
            return requests;
        } catch (SQLException e) {
            logger.error("FindAll requests: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("FindAll requests: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }
}
