package ua.kiev.univ.courses.project4.hotel.commands;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.Role;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to register a new user.
 */
public class RegisterCommand implements Command {
    public static final Logger logger = getLogger(RegisterCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO()
                .create(new User(login, password, phone, email, Role.USER))){
            logger.info("Success registration for "+login);
            request.setAttribute("success_registration", RESOURCE_BUNDLE.getString("success_registration"));
            return "/index.jsp";
        }
        else{
            logger.warn("Registration error");
            request.setAttribute("incorrect_register_data", RESOURCE_BUNDLE.getString("incorrect_register_data"));
            return "/register.jsp";
        }
    }
}
