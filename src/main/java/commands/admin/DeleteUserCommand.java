package commands.admin;

import commands.factory.Command;
import dao.DAOFactory;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class DeleteUserCommand implements Command {
    public static final Logger logger = getLogger(DeleteUserCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        if (login.equals(((User) request.getSession().getAttribute("user")).getLogin())) {
            logger.warn("Attempt to remove yourself");
            request.setAttribute("delete_user_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("delete_yourself"));
            return "/admin.jsp";
        }
        if (DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().deleteByLogin(login)) {
            request.setAttribute("delete_user_success", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("delete_user_success"));
            logger.info("User deleting success");
            return "/admin.jsp";
        }
        logger.warn("Deleting user error");
        request.setAttribute("delete_user_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("delete_user_error"));
        return "/admin.jsp";
    }
}
