package filters;

import model.Role;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.log4j.Logger.getLogger;

public class CheckAuthFilter implements Filter {
    public static final Logger logger = getLogger(CheckAuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute("user")==null){
            request.getRequestDispatcher("index.jsp").forward(request, servletResponse);
            logger.info("Empty session");
        }else{
            User user = (User)request.getSession().getAttribute("user");
            if (user.getRole().equals(Role.ADMIN)){
                if (request.getServletPath().equals("/index.jsp")){
                    request.getRequestDispatcher("/admin.jsp").forward(request, servletResponse);
                }
                else if (request.getServletPath().equals("/dashboard.jsp") || request.getServletPath().equals("/createRequest.jsp")
                        || request.getServletPath().equals("/invoices.jsp")){
                    logger.info("Admin do not have access to resource "+request.getServletPath());
                    ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                else{
                    request.getRequestDispatcher(request.getServletPath()).forward(request, servletResponse);
                }
            }
            else{ //USER
                if (request.getServletPath().equals("/index.jsp")){
                    request.getRequestDispatcher("/dashboard.jsp").forward(request, servletResponse);
                }
                else if (request.getServletPath().equals("/admin.jsp")||request.getServletPath().equals("/requests.jsp")
                        ||request.getServletPath().equals("/addApartment.jsp")||request.getServletPath().equals("/users.jsp")){
                    logger.info("User do not have access to resource "+request.getServletPath());
                    ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                else{
                    request.getRequestDispatcher(request.getServletPath()).forward(request, servletResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
