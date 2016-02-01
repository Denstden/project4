package ua.kiev.univ.courses.project4.hotel.commands.client;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.dao.DAOFactory;
import ua.kiev.univ.courses.project4.hotel.model.Invoice;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to load all user invoices to request.
 */
public class ViewInvoicesCommand implements Command {
    public static final Logger logger = getLogger(SendRequestCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SupportedFactories.MYSQL);
        List<Invoice> invoices = daoFactory.getInvoiceDAO().getUserInvoices((User) request.getSession().getAttribute("user"));
        request.setAttribute("invoices", invoices);
        logger.info("View invoices");
        return "/invoices.jsp";
    }
}
