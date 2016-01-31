package commands;

import commands.factory.Command;
import dao.DAOFactory;
import model.Role;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class RegisterCommand implements Command {
    public static final Logger logger = getLogger(RegisterCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL).getUserDAO().create(new User(login, password, phone, email, Role.USER))){
            logger.info("Success registration for "+login);
            request.setAttribute("success_registration", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("success_registration"));
            return "/index.jsp";
        }
        else{
            logger.warn("Registration error");
            request.setAttribute("incorrect_register_data", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("incorrect_register_data"));
            return "/register.jsp";
        }
    }
}
