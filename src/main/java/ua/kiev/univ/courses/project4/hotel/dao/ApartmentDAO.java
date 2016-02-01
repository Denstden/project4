package ua.kiev.univ.courses.project4.hotel.dao;

import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.ApartmentClass;
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
