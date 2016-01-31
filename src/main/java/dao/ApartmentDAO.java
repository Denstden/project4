package dao;

import model.Apartment;
import model.ApartmentClass;
import java.util.List;
import java.util.Map;

/**
 * Interface to work with apartments in database.
 */
public interface ApartmentDAO{
    boolean create(Integer countPlaces, ApartmentClass apartmentClass, Integer number);
    Apartment read(long id);
    Map getByIdBusyApartments();
    List findAll();
}
