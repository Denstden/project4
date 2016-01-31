package commands.admin;

import commands.factory.Command;
import dao.DAOFactory;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;

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
