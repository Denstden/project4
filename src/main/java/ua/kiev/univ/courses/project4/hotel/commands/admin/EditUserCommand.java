package ua.kiev.univ.courses.project4.hotel.commands.admin;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to edit user.
 */
public class EditUserCommand implements Command {
    public static final Logger logger = getLogger(EditUserCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("group");
        Integer isBlocked = (request.getParameter("isBlocked")!=null)?1:0;
        if (DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().update(id,login,password,phone,email,role,isBlocked)){
            logger.info("User edited successfully");
            request.setAttribute("edit_user_success", RESOURCE_BUNDLE.getString("edit_user_success"));
            return "/admin.jsp";
        }else {
            logger.info("User editing error");
            request.setAttribute("edit_user_error", RESOURCE_BUNDLE.getString("edit_user_error"));
            return "/admin.jsp";
        }
    }
}
