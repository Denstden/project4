package ua.kiev.univ.courses.project4.hotel.dao;

import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.User;
import java.util.List;

/**
 * Interface to work with invoices in database.
 */
public interface InvoiceDAO {
    boolean save(User client, Apartment apartment, String dateFrom, String dateTo, Double coeff);
    List getUserInvoices(User user);
}
