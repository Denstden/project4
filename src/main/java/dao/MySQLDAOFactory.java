package dao;

/**
 * Factory of MySQL DAOs.
 */
public class MySQLDAOFactory extends DAOFactory {
    @Override
    public InvoiceDAO getInvoiceDAO() {
        return new MySQLInvoiceDAO();
    }

    @Override
    public ApartmentDAO getApartmentDAO() {
        return new MySQLApartmentDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }

    @Override
    public RequestDAO getRequestDAO() {
        return new MySQLRequestDAO();
    }
}
