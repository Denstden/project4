package ua.kiev.univ.courses.project4.hotel.commands.factory;

import ua.kiev.univ.courses.project4.hotel.commands.*;
import ua.kiev.univ.courses.project4.hotel.commands.admin.*;
import ua.kiev.univ.courses.project4.hotel.commands.client.SendRequestCommand;
import ua.kiev.univ.courses.project4.hotel.commands.client.ViewInvoicesCommand;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<String, Command> commands;
    private static CommandFactory instance;

    private CommandFactory() {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("change_language", new ChangeLanguageCommand());
        commands.put("send_request", new SendRequestCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("view_requests", new ViewRequestsCommand());
        commands.put("bill", new BillCommand());
        commands.put("add_apartment", new AddApartmentCommand());
        commands.put("view_invoices", new ViewInvoicesCommand());
        commands.put("load_users", new LoadUsersCommand());
        commands.put("edit_user", new EditUserCommand());
        commands.put("delete_user", new DeleteUserCommand());
        commands.put("change_password", new ChangePasswordCommand());
        commands.put(null, new NoCommand());
    }

    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        String value = request.getParameter("command");
        return commands.get(value);
    }
}
