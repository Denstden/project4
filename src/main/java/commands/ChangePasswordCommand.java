package commands;

import commands.factory.Command;
import dao.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class ChangePasswordCommand implements Command{
    public static final Logger logger = getLogger(ChangePasswordCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);

        if (daoFactory.getUserDAO().readByLoginPassword(login,oldPassword)==null){
            request.setAttribute("login", login);
            request.setAttribute("incorrect_old_password", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("incorrect_old_password"));
            logger.warn("Incorrect old password "+oldPassword);
            return "/changePassword.jsp";
        }
        else{
            if (oldPassword.equals(newPassword)){
                request.setAttribute("login", login);
                request.setAttribute("equals_old_new_password", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("equals_old_new_password"));
                logger.warn("Old and new password match");
                return "/changePassword.jsp";
            } else if (!newPassword.equals(confirmPassword)){
                request.setAttribute("login", login);
                request.setAttribute("not_match_new_confirm_password", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("not_match_new_confirm_password"));
                logger.warn("New password and confirm password not match");
                return "/changePassword.jsp";
            } else {
                if (daoFactory.getUserDAO().changePassword(login, newPassword)){
                    request.setAttribute("password_changed", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("password_changed"));
                    logger.info("Password changed for user " + login + ", new password " + newPassword);
                    return "/index.jsp";
                }
                else{
                    request.setAttribute("login", login);
                    request.setAttribute("password_change_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("password_change_error"));
                    logger.info("Password change error for user "+login);
                    return "/changePassword.jsp";
                }
            }
        }

    }
}
