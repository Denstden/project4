package ua.kiev.univ.courses.project4.hotel.servlets;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import ua.kiev.univ.courses.project4.hotel.commands.factory.CommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class Controller handles the user's request and sends him a response.
 */
public class Controller extends HttpServlet {
    public static final Logger logger = getLogger(Controller.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getInstance().getCommand(request);
        String path = command.execute(request);
        logger.info("Request dispatcher path: "+path);
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
