package commands.client;

import commands.factory.Command;
import dao.DAOFactory;
import model.ApartmentClass;
import model.Request;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class SendRequestCommand implements Command {
    public static final Logger logger = getLogger(SendRequestCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String countPlaces = request.getParameter("countPlaces");
        ApartmentClass apartmentClass = ApartmentClass.valueOf(request.getParameter("apartmentClass").toUpperCase());
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        format1.setLenient(false);
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = format1.parse(request.getParameter("dateFrom"));
            dateTo = format1.parse(request.getParameter("dateTo"));
        } catch (ParseException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        User client = (User) request.getSession().getAttribute("user");

        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        Request request1 = new Request(client,Integer.parseInt(countPlaces),apartmentClass,dateFrom,dateTo);
        if (daoFactory.getRequestDAO().create(request1)) {
            logger.info("Success send request");
            request.setAttribute("send_request_success", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("send_request_success"));
            return "dashboard.jsp";
        }
        else{
            logger.warn("Error send request");
            request.setAttribute("send_request_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("send_request_error"));
            return "createRequest.jsp";
        }
    }
}
