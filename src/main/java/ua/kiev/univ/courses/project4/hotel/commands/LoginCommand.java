package ua.kiev.univ.courses.project4.hotel.commands;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.Role;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows the user to login.
 */
public class LoginCommand implements Command {
    public static final Logger logger = getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf", Locale.getDefault());

        User user = daoFactory.getUserDAO().readByLoginPassword(login, password);
        if (user!=null){
            if (!user.getIsBlocked()){//user isn't blocked
                if ((new Date().getTime()-user.getChangePasswordDate().getTime())/3600/1000/24>=
                        Long.valueOf(resourceBundle.getString("timePassword"))){//password expired
                    request.setAttribute("login", login);
                    request.setAttribute("need_to_change_password", RESOURCE_BUNDLE.getString("need_to_change_password"));
                    logger.info("Need to change password for user " + login);
                    return "/changePassword.jsp";
                }
                request.getSession().setAttribute("user", user);
                if (user.getRole().equals(Role.USER)){
                    logger.info("User login success");
                    return "/dashboard.jsp";
                }
                else{
                    logger.info("Admin login success");
                    return "/admin.jsp";
                }
            }
            else{//user is blocked
                request.setAttribute("blocked", RESOURCE_BUNDLE.getString("user") + " \"" + login + "\" " +
                        RESOURCE_BUNDLE.getString("blocked"));
                logger.warn("User "+user.getLogin()+" is blocked ");
                return "/index.jsp";
            }
        }
        else{
            user = daoFactory.getUserDAO().readByLogin(login);
            if (user!=null) {
                if (user.getCountIncorrectAttemptsToLogin() + 1 == Integer.valueOf(resourceBundle.getString("countAttempts"))) {
                    daoFactory.getUserDAO().block(login);
                    request.setAttribute("blocked", RESOURCE_BUNDLE.getString("user") + " \"" + login + "\" " +
                            RESOURCE_BUNDLE.getString("blocked"));
                    logger.warn("User " + user.getLogin() + " was blocked ");
                } else {
                    daoFactory.getUserDAO().updateAttempts(user.getCountIncorrectAttemptsToLogin() + 1, login);
                    request.setAttribute("incorrect_login_password", RESOURCE_BUNDLE.getString("incorrect_login_password"));
                    logger.warn("Incorrect login and password for user " + login);
                }
            }
            else{
                request.setAttribute("no_user", RESOURCE_BUNDLE.getString("no_user"));
                logger.warn("No such user with login "+login);
            }
            return "/index.jsp";
        }
    }
}
