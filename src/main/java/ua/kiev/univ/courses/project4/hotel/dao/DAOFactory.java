package ua.kiev.univ.courses.project4.hotel.dao;

/**
 * Abstract factory for work with database.
 */
public abstract class DAOFactory {
    public enum SupportedFactories{
        MYSQL
    }

    public abstract InvoiceDAO getInvoiceDAO();
    public abstract ApartmentDAO getApartmentDAO();
    public abstract UserDAO getUserDAO();
    public abstract RequestDAO getRequestDAO();

    public static DAOFactory getDAOFactory(SupportedFactories factory){
        switch (factory){
            default:return new MySQLDAOFactory();
        }
    }
}
