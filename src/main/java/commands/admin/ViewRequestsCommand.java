package commands.admin;

import commands.factory.Command;
import dao.DAOFactory;
import model.Apartment;
import model.BusyApartment;
import model.Request;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.apache.log4j.Logger.getLogger;

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
