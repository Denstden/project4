package ua.kiev.univ.courses.project4.hotel.commands;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to change the user password.
 */
public class ChangePasswordCommand implements Command{
    public static final Logger logger = getLogger(ChangePasswordCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);

        if (daoFactory.getUserDAO().readByLoginPassword(login,oldPassword)==null){//no user with such login and password
            request.setAttribute("login", login);
            request.setAttribute("incorrect_old_password", RESOURCE_BUNDLE.getString("incorrect_old_password"));
            logger.warn("Incorrect old password "+oldPassword);
            return "/changePassword.jsp";
        }
        else{
            if (oldPassword.equals(newPassword)){//new password and old password match
                request.setAttribute("login", login);
                request.setAttribute("equals_old_new_password", RESOURCE_BUNDLE.getString("equals_old_new_password"));
                logger.warn("Old and new password match");
                return "/changePassword.jsp";
            } else if (!newPassword.equals(confirmPassword)){//new password and confirm password does not match
                request.setAttribute("login", login);
                request.setAttribute("not_match_new_confirm_password", RESOURCE_BUNDLE.getString("not_match_new_confirm_password"));
                logger.warn("New password and confirm password not match");
                return "/changePassword.jsp";
            } else {
                if (daoFactory.getUserDAO().changePassword(login, newPassword)){
                    request.setAttribute("password_changed", RESOURCE_BUNDLE.getString("password_changed"));
                    logger.info("Password changed for user " + login + ", new password " + newPassword);
                    return "/index.jsp";
                }
                else{
                    request.setAttribute("login", login);
                    request.setAttribute("password_change_error", RESOURCE_BUNDLE.getString("password_change_error"));
                    logger.info("Password change error for user "+login);
                    return "/changePassword.jsp";
                }
            }
        }

    }
}
