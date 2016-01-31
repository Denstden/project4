package commands;

import commands.factory.Command;
import dao.DAOFactory;
import model.Role;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.log4j.Logger.getLogger;

public class LoginCommand implements Command {
    public static final Logger logger = getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf", Locale.getDefault());
        /*Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Java\\hotel\\src\\main\\resources\\conf.properties"));
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }*/
        User user = daoFactory.getUserDAO().readByLoginPassword(login, password);
        if (user!=null){
            if (!user.getIsBlocked()){
                if ((new Date().getTime()-user.getChangePasswordDate().getTime())/3600/1000/24>=Long.valueOf(resourceBundle.getString("timePassword"))){
                    request.setAttribute("login", login);
                    request.setAttribute("need_to_change_password", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("need_to_change_password"));
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
            else{
                request.setAttribute("blocked", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("user") + " \"" + login + "\" " +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("blocked"));
                logger.warn("User "+user.getLogin()+" is blocked ");
                return "/index.jsp";
            }
        }
        else{
            user = daoFactory.getUserDAO().readByLogin(login);
            if (user!=null) {
                if (user.getCountIncorrectAttemptsToLogin() + 1 == Integer.valueOf(resourceBundle.getString("countAttempts"))) {
                    daoFactory.getUserDAO().block(login);
                    request.setAttribute("blocked", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("user") + " \"" + login + "\" " +
                            ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("blocked"));
                    logger.warn("User " + user.getLogin() + " was blocked ");
                } else {
                    daoFactory.getUserDAO().incAttempts(user.getCountIncorrectAttemptsToLogin() + 1, login);
                    request.setAttribute("incorrect_login_password", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("incorrect_login_password"));
                    logger.warn("Incorrect login and password for user " + login);
                }
            }
            else{
                request.setAttribute("no_user", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("no_user"));
                logger.warn("No such user with login "+login);
            }
            return "/index.jsp";
        }
    }
}
