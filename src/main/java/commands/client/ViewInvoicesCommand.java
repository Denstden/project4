package commands.client;

import commands.factory.Command;
import dao.DAOFactory;
import model.Invoice;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;

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
