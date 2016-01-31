package dao;

import model.Request;
import java.util.List;

/**
 * Interface to work with requests in database.
 */
public interface RequestDAO  {
    boolean create(Request request);
    boolean deleteById(Long id);
    List findAll();
}
