package dao;

import model.Apartment;
import model.User;
import java.util.List;

/**
 * Interface to work with invoices in database.
 */
public interface InvoiceDAO {
    boolean save(User client, Apartment apartment, String dateFrom, String dateTo, Double coeff);
    List getUserInvoices(User user);
}
