package ua.kiev.univ.courses.project4.hotel.commands.factory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public interface Command {
    ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("/languages", Locale.getDefault());
    String execute(HttpServletRequest request);
}
