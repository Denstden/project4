package ua.kiev.univ.courses.project4.hotel.commands.admin;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.Apartment;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;
import ua.kiev.univ.courses.project4.hotel.utils.MailSender;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to bill the client for the hotel apartment.
 */
public class BillCommand implements Command {
    public static final Logger logger = getLogger(BillCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String countPlaces = request.getParameter("countPlaces");
        String apartmentClass = request.getParameter("apartmentClass");
        String busyFrom = request.getParameter("busyFrom");
        String busyTo = request.getParameter("busyTo");
        String apartmentsId = request.getParameter("apartmentsSelect");
        String requestId = request.getParameter("requestId");

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Double coeff = null;
        try {
            coeff = (simpleDateFormat.parse(busyTo).getTime()-simpleDateFormat.parse(busyFrom).getTime())/1000./3600/24;
        } catch (ParseException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        Apartment apartment = daoFactory.getApartmentDAO().read(Long.parseLong(apartmentsId));
        User user = daoFactory.getUserDAO().readByLogin(login);
        if (daoFactory.getInvoiceDAO().save(user, apartment, busyFrom, busyTo, coeff) &&
                daoFactory.getRequestDAO().deleteById(Long.parseLong(requestId))){
            sendMail(user.getEmail(), apartment, coeff, busyFrom, busyTo);
            logger.info("Successfully billing");
            request.setAttribute("bill_success", RESOURCE_BUNDLE.getString("bill_success"));
            return "/admin.jsp";
        }
        else {
            logger.info("Billing error");
            request.setAttribute("bill_error", RESOURCE_BUNDLE.getString("bill_error"));
            return "/requests.jsp";
        }
    }

    private void sendMail(String to, Apartment apartment, Double coeff, String dateFrom, String dateTo) {
        MailSender mailSender = new MailSender();
        mailSender.init();
        mailSender.sendMail(to,
                RESOURCE_BUNDLE.getString("mail_subject"),
                RESOURCE_BUNDLE.getString("mail_message_start") + "\n" +
                        RESOURCE_BUNDLE.getString("number") + ": " + apartment.getNumber() + "\n" +
                        RESOURCE_BUNDLE.getString("count_places") + ": " + apartment.getCountPlaces() + "\n" +
                        RESOURCE_BUNDLE.getString("apartment_class") + ": " + apartment.getApartmentClass().toString() + "\n" +
                        RESOURCE_BUNDLE.getString("date") + ": " +
                        RESOURCE_BUNDLE.getString("from") + " \"" + dateFrom + "\" " +
                        RESOURCE_BUNDLE.getString("to") + " \"" + dateTo + "\"\n" +
                        RESOURCE_BUNDLE.getString("price") + ": " + apartment.getPrice() * coeff + "\n" +
                        RESOURCE_BUNDLE.getString("mail_message_end")
        );

    }
}
