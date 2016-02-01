package ua.kiev.univ.courses.project4.hotel.commands.admin;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to load all users to request.
 */
public class LoadUsersCommand implements Command {
    public static final Logger logger = getLogger(LoadUsersCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        List<User> users = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().findAll();
        request.setAttribute("users", users);
        logger.info("Load users");
        return "/users.jsp";
    }
}
