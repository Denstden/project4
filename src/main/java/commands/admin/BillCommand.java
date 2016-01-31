package commands.admin;

import commands.factory.Command;
import dao.DAOFactory;
import model.Apartment;
import model.User;
import org.apache.log4j.Logger;
import utils.MailSender;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;

public class BillCommand implements Command {
    public static final Logger logger = getLogger(BillCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        /*String req = request.getParameter("requestsSelect");*/
        String login = request.getParameter("login");
        String countPlaces = request.getParameter("countPlaces");
        String apartmentClass = request.getParameter("apartmentClass");
        String busyFrom = request.getParameter("busyFrom");
        String busyTo = request.getParameter("busyTo");
        String apartmentsId = request.getParameter("apartmentsSelect");
        String requestId = request.getParameter("requestId");
        System.out.println(login+" "+countPlaces+" "+apartmentClass+" "+busyFrom+" "+busyTo+" "+apartmentsId);
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
            request.setAttribute("bill_success", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("bill_success"));
            return "/admin.jsp";
        }
        else {
            logger.info("Billing error");
            request.setAttribute("bill_error", ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("bill_error"));
            return "/requests.jsp";
        }
    }

    private void sendMail(String to, Apartment apartment, Double coeff, String dateFrom, String dateTo) {
        MailSender mailSender = new MailSender();
        mailSender.init();
        mailSender.sendMail(to,
                ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("mail_subject"),
                ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("mail_message_start") + "\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("number") + ": " + apartment.getNumber() + "\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("count_places") + ": " + apartment.getCountPlaces() + "\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("apartment_class") + ": " + apartment.getApartmentClass().toString() + "\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("date") + ": " +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("from") + " \"" + dateFrom + "\" " +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("to") + " \"" + dateTo + "\"\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("price") + ": " + apartment.getPrice() * coeff + "\n" +
                        ResourceBundle.getBundle("/languages", Locale.getDefault()).getString("mail_message_end")
        );

    }
}
