package ua.kiev.univ.courses.project4.hotel.dao;

import com.sun.istack.internal.Nullable;
import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.ApartmentClass;
import ua.kiev.univ.courses.project4.hotel.model.BusyApartment;
import org.apache.log4j.Logger;
import ua.kiev.univ.courses.project4.hotel.utils.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import static org.apache.log4j.Logger.getLogger;

/**
 * DAO implementation for Apartment and MySQLdb.
 */
public class MySQLApartmentDAO implements ApartmentDAO {
    public static final Logger logger = getLogger(MySQLApartmentDAO.class);
    /**
     * Properties file with queries.
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("queries", Locale.getDefault());
    /**
     * Properties file with configuration information.
     */
    private static ResourceBundle conf = ResourceBundle.getBundle("conf", Locale.getDefault());

    /**
     * Saves apartment in a database.
     *
     * @param countPlaces - count of places in apartment
     * @param apartmentClass - class of apartment
     * @param number - number of apartment
     * @return true if apartment was created, else false
     */
    @Override
    public boolean create(Integer countPlaces, ApartmentClass apartmentClass, Integer number) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("createApartment"));
            statement.setInt(1, countPlaces);
            statement.setString(2, apartmentClass.toString());
            switch (apartmentClass){
                case LUXURY: statement.setDouble(3, Double.parseDouble(conf.getString("pricePerPersonLuxury"))*countPlaces);
                case AVERAGE: statement.setDouble(3, Double.parseDouble(conf.getString("pricePerPersonAverage"))*countPlaces);
                case ECONOMY: statement.setDouble(3, Double.parseDouble(conf.getString("pricePerPersonEconomy"))*countPlaces);
            }
            statement.setInt(4,number);
            if (statement.executeUpdate()>0){
                logger.info("Create apartment: " + countPlaces + " " + apartmentClass + " " + number);
                return true;
            }
            logger.warn("Create apartment error: " + countPlaces + " " + apartmentClass + " " + number);
            return false;
        } catch (Exception e) {
            logger.error("Create apartment: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Create apartment: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Reads apartment from database by id.
     *
     * @param id - id of apartment
     * @return Apartment if exist with such id, else null
     */
    @Override
    @Nullable
    public Apartment read(long id) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getApartmentById"));
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Apartment apartment = new Apartment();
            while (resultSet.next()) {
                apartment.setId(resultSet.getLong(1));
                apartment.setCountPlaces(resultSet.getInt(2));
                apartment.setPrice(resultSet.getDouble(3));
                String apartmentClass = resultSet.getString(4);
                switch (apartmentClass) {
                    case "LUXURY":
                        apartment.setApartmentClass(ApartmentClass.LUXURY);
                        break;
                    case "AVERAGE":
                        apartment.setApartmentClass(ApartmentClass.AVERAGE);
                        break;
                    case "ECONOMY":
                        apartment.setApartmentClass(ApartmentClass.ECONOMY);
                        break;
                }
                apartment.setNumber(resultSet.getInt(5));
            }
            logger.info("Read by id apartment: " + apartment.toString());
            return apartment;
        } catch (SQLException e) {
            logger.error("Read by id apartment: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Read by id apartment: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    /**
     * Read busy apartments.
     *
     * @return Map: key = apartment id, value - list of busy apartments
     */
    @Override
    @Nullable
    public Map getByIdBusyApartments() {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getBusyApartments"));
            ResultSet resultSet = statement.executeQuery();
            Apartment apartment;
            BusyApartment busyApartment;
            Map<Long,List<BusyApartment>> map = new HashMap<>();
            while (resultSet.next()) {
                busyApartment = new BusyApartment();
                apartment = new Apartment();
                apartment.setId(resultSet.getLong(1));
                apartment.setCountPlaces(resultSet.getInt(2));
                String apartmentClass = resultSet.getString(3);
                switch (apartmentClass) {
                    case "LUXURY":
                        apartment.setApartmentClass(ApartmentClass.LUXURY);
                        break;
                    case "AVERAGE":
                        apartment.setApartmentClass(ApartmentClass.AVERAGE);
                        break;
                    case "ECONOMY":
                        apartment.setApartmentClass(ApartmentClass.ECONOMY);
                        break;
                }
                busyApartment.setDateFrom(new Date(resultSet.getTimestamp(4).getTime()));
                busyApartment.setDateTo(new Date(resultSet.getTimestamp(5).getTime()));
                busyApartment.setId(resultSet.getLong(6));
                apartment.setNumber(resultSet.getInt(7));
                busyApartment.setApartment(apartment);
                if (map.containsKey(resultSet.getLong(1))){
                    map.get(resultSet.getLong(1)).add(busyApartment);
                }
                else{
                    List<BusyApartment> busyApartments = new ArrayList<>();
                    busyApartments.add(busyApartment);
                    map.put(resultSet.getLong(1),busyApartments);
                }
            }
            logger.info("Get by id busy apartments.");
            return map;
        } catch (SQLException e) {
            logger.error("Get by id busy apartments: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Get by id busy apartments: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    /**
     * Read all apartments.
     *
     * @return List of apartments.
     */
    @Override
    @Nullable
    public List findAll() {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("findAllApartments"));
            ResultSet resultSet = statement.executeQuery();
            Apartment apartment;
            List<Apartment> apartments = new ArrayList<>();
            while (resultSet.next()){
                apartment = new Apartment();
                apartment.setId(resultSet.getLong(1));
                apartment.setCountPlaces(resultSet.getInt(2));
                apartment.setPrice(resultSet.getDouble(4));
                apartment.setNumber(resultSet.getInt(5));
                String apartmentClass = resultSet.getString(3);
                switch (apartmentClass) {
                    case "LUXURY":
                        apartment.setApartmentClass(ApartmentClass.LUXURY);
                        break;
                    case "AVERAGE":
                        apartment.setApartmentClass(ApartmentClass.AVERAGE);
                        break;
                    case "ECONOMY":
                        apartment.setApartmentClass(ApartmentClass.ECONOMY);
                        break;
                }
                apartments.add(apartment);
            }
            logger.info("FindAll apartments.");
            return apartments;
        } catch (SQLException e) {
            logger.error("FindAll apartments: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("FindAll apartment: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }
}
