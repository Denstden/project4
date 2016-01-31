package commands;

import commands.factory.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static org.apache.log4j.Logger.getLogger;

public class LogoutCommand implements Command {
    public static final Logger logger = getLogger(LogoutCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        logger.info("Logout");
        return "/index.jsp";
    }
}
