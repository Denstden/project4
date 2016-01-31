package commands;

import commands.factory.Command;

import javax.servlet.http.HttpServletRequest;

public class NoCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
