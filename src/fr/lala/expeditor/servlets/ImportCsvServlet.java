package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImportCsvServlet
 */
public class ImportCsvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REDIRECT = "/WEB-INF/jsp/manager/importcsv.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportCsvServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.getRequestDispatcher(REDIRECT).forward(request, response);
	}

}
