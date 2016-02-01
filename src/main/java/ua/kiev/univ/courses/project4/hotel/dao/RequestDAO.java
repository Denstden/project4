package ua.kiev.univ.courses.project4.hotel.dao;

import ua.kiev.univ.courses.project4.hotel.model.Request;
import java.util.List;

/**
 * Interface to work with requests in database.
 */
public interface RequestDAO  {
    boolean create(Request request);
    boolean deleteById(Long id);
    List findAll();
}
