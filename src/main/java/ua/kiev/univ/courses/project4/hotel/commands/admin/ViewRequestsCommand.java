package ua.kiev.univ.courses.project4.hotel.commands.admin;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.BusyApartment;
import ua.kiev.univ.courses.project4.hotel.model.Request;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to load all apartments and requests.
 */
public class ViewRequestsCommand implements Command {
    public static final Logger logger = getLogger(EditUserCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        List<Request> requests = daoFactory.getRequestDAO().findAll();
        List<Apartment> apartments = daoFactory.getApartmentDAO().findAll();
        Map<Long, List<BusyApartment>> apartmentListMap = daoFactory.getApartmentDAO().getByIdBusyApartments();
        request.setAttribute("load", apartmentListMap);
        request.setAttribute("requests", requests);
        request.setAttribute("apartments", apartments);
        logger.info("Load request and apartments");
        return "/requests.jsp";
    }
}
