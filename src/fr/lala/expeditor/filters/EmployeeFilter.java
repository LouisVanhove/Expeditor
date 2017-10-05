package fr.lala.expeditor.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;

/**
 * Servlet Filter implementation class EmployeeFilter
 */
public class EmployeeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EmployeeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Employee user = (Employee) ((HttpServletRequest) request).getSession().getAttribute("User");
		if ( user.getProfile() == Profile.SHIPPING_CLERK) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/jsp/forbidden.jsp").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
