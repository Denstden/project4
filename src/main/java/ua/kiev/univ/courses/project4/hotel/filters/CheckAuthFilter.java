package ua.kiev.univ.courses.project4.hotel.filters;

import ua.kiev.univ.courses.project4.hotel.model.Role;
import ua.kiev.univ.courses.project4.hotel.model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.log4j.Logger.getLogger;

/**
 * Class CheckAuthFilter controls user access to certain pages.
 */
public class CheckAuthFilter implements Filter {
    public static final Logger logger = getLogger(CheckAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute("user")==null){// if the user is not authorized go to index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, servletResponse);
            logger.info("Empty session");
        }else{//user is authorized
            User user = (User)request.getSession().getAttribute("user");
            if (user.getRole().equals(Role.ADMIN)){//if user ADMIN
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
