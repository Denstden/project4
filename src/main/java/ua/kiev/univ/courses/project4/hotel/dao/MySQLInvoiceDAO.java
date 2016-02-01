package ua.kiev.univ.courses.project4.hotel.dao;

import com.sun.istack.internal.Nullable;
import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.ApartmentClass;
import ua.kiev.univ.courses.project4.hotel.model.Invoice;
import ua.kiev.univ.courses.project4.hotel.model.User;
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
public class MySQLInvoiceDAO implements InvoiceDAO {
    public static final Logger logger = getLogger(MySQLInvoiceDAO.class);
    /**
     * Properties file with queries.
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("queries", Locale.getDefault());

    /**
     * Saves invoice in a database.
     *
     * @param client - user for payment
     * @param apartment - selected apartment
     * @param dateFrom - desired date of settlement.
     * @param dateTo - desired date of eviction.
     * @param coeff - coefficient, which depends on the residence time in the client booking
     * @return true if invoice was created, else false
     */
    @Override
    public boolean save(User client, Apartment apartment, String dateFrom, String dateTo, Double coeff) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("createInvoice"));
            statement.setLong(1, client.getId());
            statement.setLong(2, apartment.getId());
            statement.setDouble(3, apartment.getPrice()*coeff);
            PreparedStatement statement1 = connection.prepareStatement(resourceBundle.getString("createBusyApartment"));
            statement1.setLong(1, apartment.getId());
            statement1.setString(2, dateFrom);
            statement1.setString(3, dateTo);
            if (statement.executeUpdate()>0 && statement1.executeUpdate()>0){
                logger.error("Save invoice: " + client.getId()+" "+apartment.getId()+" "+apartment.getPrice());
                return true;
            }
            logger.warn("Save invoice error: " + client.getId() + " " + apartment.getId() + " " + apartment.getPrice());
            return false;
        } catch (SQLException e) {
            logger.error("Save invoice: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Save invoice: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Reads user invoices from database.
     *
     * @param user - client whose invoices are returned
     * @return List of invoices
     */
    @Override
    @Nullable
    public List getUserInvoices(User user) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getInvoiceByUser"));
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            Invoice invoice;
            Apartment apartment;
            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()){
                invoice = new Invoice();
                invoice.setId(resultSet.getLong(1));
                invoice.setClient(user);
                apartment = new Apartment();
                apartment.setId(resultSet.getLong(2));
                apartment.setCountPlaces(resultSet.getInt(3));
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
                apartment.setPrice(resultSet.getDouble(5));
                apartment.setNumber(resultSet.getInt(6));
                invoice.setApartment(apartment);
                invoice.setPrice(apartment.getPrice());
                invoices.add(invoice);
            }
            logger.info("Get user invoices.");
            return invoices;
        } catch (SQLException e) {
            logger.error("Get user invoices: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Get user invoices: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }
}
