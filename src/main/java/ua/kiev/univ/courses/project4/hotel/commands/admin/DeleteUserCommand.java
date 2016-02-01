package ua.kiev.univ.courses.project4.hotel.commands.admin;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to delete user.
 */
public class DeleteUserCommand implements Command {
    public static final Logger logger = getLogger(DeleteUserCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        if (login.equals(((User) request.getSession().getAttribute("user")).getLogin())) {
            logger.warn("Attempt to remove yourself");
            request.setAttribute("delete_user_error", RESOURCE_BUNDLE.getString("delete_yourself"));
            return "/admin.jsp";
        }
        if (DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().deleteByLogin(login)) {
            request.setAttribute("delete_user_success", RESOURCE_BUNDLE.getString("delete_user_success"));
            logger.info("User deleting success");
            return "/admin.jsp";
        }
        logger.warn("Deleting user error");
        request.setAttribute("delete_user_error", RESOURCE_BUNDLE.getString("delete_user_error"));
        return "/admin.jsp";
    }
}
