package ua.kiev.univ.courses.project4.hotel.commands;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class allows to change the language localization.
 */
public class ChangeLanguageCommand implements Command {
    public static final Logger logger = getLogger(ChangeLanguageCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter("lang");
        request.getSession().setAttribute("language", language);
        Locale.setDefault(new Locale.Builder().setLanguage(language.substring(0,language.indexOf("_"))).
                setRegion(language.substring(language.indexOf("_")+1,language.length())).build());
        logger.info("Language changed to "+language);
        return "index.jsp";
    }
}
