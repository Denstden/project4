package dao;

import model.User;
import java.util.List;

/**
 * Interface to work with users in database.
 */
public interface UserDAO {
    boolean create(User user);
    User readByLoginPassword(String login, String password);
    boolean changePassword(String login, String newPassword);
    boolean incAttempts(Integer attempts, String login);
    boolean block(String login);
    User read(long id);
    User readByLogin(String login);
    boolean update(Long id, String login, String password, String phone, String email, String role, Integer isBlocked);
    boolean deleteByLogin(String login);
    List findAll();
}
