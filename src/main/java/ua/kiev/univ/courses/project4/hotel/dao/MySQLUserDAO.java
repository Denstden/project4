package ua.kiev.univ.courses.project4.hotel.dao;

import com.sun.istack.internal.Nullable;
import ua.kiev.univ.courses.project4.hotel.model.Role;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;
import ua.kiev.univ.courses.project4.hotel.utils.ConnectionPool;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.log4j.Logger.getLogger;

/**
 * DAO implementation for User and MySQLdb.
 */
public class MySQLUserDAO implements UserDAO {
    public static final Logger logger = getLogger(MySQLUserDAO.class);
    /**
     * Properties file with queries.
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("queries", Locale.getDefault());

    /**
     * Saves user in a database.
     *
     * @param user - user that is written in the database.
     * @return
     */
    @Override
    public boolean create(User user) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("createUser"));
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateRegistration = simpleDateFormat.format(user.getRegistrationDate());
            String dateChangePassword = simpleDateFormat.format(user.getChangePasswordDate());
            statement.setString(5, dateRegistration);
            statement.setString(6, dateChangePassword);
            statement.setInt(7, user.getCountIncorrectAttemptsToLogin());
            statement.setString(8, user.getRole().toString());
            if (statement.executeUpdate() > 0){
                logger.info("Create user: " + user.toString());
                return true;
            }
            logger.warn("Create error: " + user.toString());
            return false;
        } catch (SQLException e) {
            logger.error("Create user: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Create user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * /**
     * Reads user from database by login and password.
     *
     * @param id - user id
     * @return object of class User if exists with such id, null else
     */
    @Nullable
    @Override
    public User read(long id) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getUserById"));
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhone(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setRegistrationDate(resultSet.getDate(6));
                user.setChangePasswordDate(resultSet.getDate(7));
                user.setCountIncorrectAttemptsToLogin(resultSet.getInt(8));
                user.setRole(getRoleByString(resultSet.getString(9)));
                user.setIsBlocked(resultSet.getBoolean(10));
            }
            logger.info("Read user: " + user);
            return user;
        } catch (SQLException e) {
            logger.error("Read user: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Read user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    /**
     * Reads user from database by login.
     *
     * @param login - user login
     * @return object of class User if exists with such login, null else
     */
    @Nullable
    @Override
    public User readByLogin(String login) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getUserByLogin"));
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhone(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setRegistrationDate(resultSet.getDate(6));
                user.setChangePasswordDate(resultSet.getDate(7));
                user.setCountIncorrectAttemptsToLogin(resultSet.getInt(8));
                user.setRole(getRoleByString(resultSet.getString(9)));
                user.setIsBlocked(resultSet.getBoolean(10));
            }
            logger.error("Read user by login: " + user);
            return user;
        } catch (SQLException e) {
            logger.error("Read user by login: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Read user by login: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    /**
     * Reads user from database by login and password.
     *
     * @param login - user login
     * @param password - user password
     * @return object of class User if exists with such login and password, null else
     */
    @Nullable
    @Override
    public User readByLoginPassword(String login, String password) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("getUserByLoginPassword"));
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user= new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhone(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setRegistrationDate(resultSet.getDate(6));
                user.setChangePasswordDate(resultSet.getDate(7));
                user.setCountIncorrectAttemptsToLogin(resultSet.getInt(8));
                user.setRole(getRoleByString(resultSet.getString(9)));
                user.setIsBlocked(resultSet.getBoolean(10));
            }
            logger.info("Read user by login and password: " + user);
            return user;
        } catch (SQLException e) {
            logger.error("Read user by login and password: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Read user by login and password: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    /**
     * Changes user password.
     *
     * @param login - user login whose password is changed
     * @param newPassword - new password
     * @return true if password was changed, false - else
     */
    @Override
    public boolean changePassword(String login, String newPassword) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("changePasswordForUser"));
            statement.setString(1, newPassword);
            statement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
            statement.setString(3, login);
            if (statement.executeUpdate() > 0){
                logger.info("Change password, user: " + login + " " + newPassword);
                return true;
            }
            logger.warn("Change password error, user: " + login + " " + newPassword);
            return false;
        } catch (SQLException e) {
            logger.error("Change password, user: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Change password, user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Updates attempts to login
     *
     * @param attempts - new count of attempts
     * @param login - user login whose attempts to login are changed
     * @return true if attempts was updated, false - else
     */
    @Override
    public boolean updateAttempts(Integer attempts, String login) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("incAttemptsForUser"));
            statement.setInt(1, attempts);
            statement.setString(2, login);
            if (statement.executeUpdate() > 0){
                logger.info("Updates attempts by login for user: " + login);
                return true;
            }
            logger.warn("Updates attempts by login error for user: " + login);
            return false;
        } catch (SQLException e) {
            logger.error("Updates attempts by login error for user: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Updates attempts by login error user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Blocks user.
     *
     * @param login - user login for the lock
     * @return true if user was blocked, false - else
     */
    @Override
    public boolean block(String login) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("blockUser"));
            statement.setString(1, login);
            if (statement.executeUpdate() > 0){
                logger.info("Block user: " + login);
                return true;
            }
            logger.warn("Block user error: " + login);
            return false;
        } catch (SQLException e) {
            logger.error("Block user error: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Block user error: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Updates user information.
     *
     * @param id - user id for update
     * @param login - new user login
     * @param password - new user password
     * @param phone - new user phone
     * @param email - new user email
     * @param role - new user role
     * @param isBlocked - blocked user(1) or no(0)
     * @return true if user was updated, false - else
     */
    @Override
    public boolean update(Long id, String login, String password, String phone, String email, String role, Integer isBlocked) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("updateUser"));
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, role.toUpperCase());
            statement.setInt(6, isBlocked);
            statement.setLong(7, id);
            PreparedStatement statement1 = null;
            if (isBlocked==0) {
                statement1 = connection.prepareStatement(resourceBundle.getString("unlockUser"));
                statement1.setLong(1, id);
            }

            if (statement.executeUpdate() > 0 && (statement1 != null ? statement1.executeUpdate() : 1) > 0){
                logger.info("Update user: " + id);
                return true;
            }
            logger.info("Update user error: " + id);
            return false;
        } catch (SQLException e) {
            logger.error("Update user: " + Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Update user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Deletes user from a database by login.
     *
     * @param login - user login for delete
     * @return true if user was deleted, false - else
     */
    @Override
    public boolean deleteByLogin(String login) {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("deleteUserByLogin"));
            statement.setString(1, login);
            if (statement.executeUpdate()>0){
                logger.info("Delete user: " + login);
                return true;
            }
            logger.warn("Delete user: " + login);
            return false;
        } catch (SQLException e) {
            logger.error("Delete user: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Delete user: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return false;
    }

    /**
     * Reads all users.
     *
     * @return List of users.
     */
    @Nullable
    @Override
    public List findAll() {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("findAllUsers"));
            ResultSet resultSet = statement.executeQuery();
            User user;
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhone(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setRegistrationDate(resultSet.getDate(6));
                user.setChangePasswordDate(resultSet.getDate(7));
                user.setCountIncorrectAttemptsToLogin(resultSet.getInt(8));
                user.setRole(getRoleByString(resultSet.getString(9)));
                user.setIsBlocked(resultSet.getBoolean(10));
                users.add(user);
            }
            logger.info("FindAll users.");
            return users;
        } catch (SQLException e) {
            logger.error("FindAll users: " + Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("FindAll users: " + Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

    private Role getRoleByString(String str){
        switch (str) {
            case "ADMIN":
                return Role.ADMIN;
            case "USER":
                return Role.USER;
            default:
                return null;
        }
    }
}