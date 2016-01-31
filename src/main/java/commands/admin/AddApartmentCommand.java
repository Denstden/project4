package commands.admin;

import commands.factory.Command;
import dao.DAOFactory;
import model.ApartmentClass;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class AddApartmentCommand implements Command {
    public static final Logger logger = getLogger(AddApartmentCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        Integer countPlaces = Integer.valueOf(request.getParameter("countPlaces"));
        ApartmentClass apartmentClass = ApartmentClass.valueOf(request.getParameter("apartmentClass").toUpperCase());
        Integer number = Integer.valueOf(request.getParameter("number"));
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        if (daoFactory.getApartmentDAO().create(countPlaces,apartmentClass,number)){
            logger.info("Apartment added success");
            request.setAttribute("add_apartment_success", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("add_apartment_success"));
            return "/admin.jsp";
        }
        else{
            logger.warn("Error adding apartment");
            request.setAttribute("add_apartment_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("add_apartment_error"));
            return "/addApartment.jsp";
        }
    }
}
