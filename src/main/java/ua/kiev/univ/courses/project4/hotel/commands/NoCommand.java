package ua.kiev.univ.courses.project4.hotel.commands;

import ua.kiev.univ.courses.project4.hotel.commands.factory.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Class allows any query in the address bar to respond adequately to.
 */
public class NoCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
