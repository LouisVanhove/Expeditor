package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fr.lala.expeditor.services.OrderService;

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
	 * Traitement de la demande d'affichage de la page d'import.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.getRequestDispatcher(REDIRECT).forward(request, response);
	}
	
	/**
	 * Traitement du formulaire d'import des commandes.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "Importation réalisée avec succès.";
		String empty = "Veuillez sélectionner un fichier.";
		String error = "Erreur avec l'importation du fichier.";
	    try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
				
	        	// Si le paramètre est un fichier (et non un string).
	            if (!item.isFormField()) {
	            	// Converstion du fichier en string.
	                String value = new String(item.getString().getBytes(), "UTF-8"); 
	                if (value.isEmpty())
	                	message = empty;
	                else                
	                	OrderService.importFromFile(value);                
	            }
	        }
	    } catch (FileUploadException e) {
	        message = error;
	    } catch (SQLException e) {
	    	e.printStackTrace();
			message = error;
		}
	    request.setAttribute("import_message", message);
	    response.setContentType("text/html;charset=UTF-8");
		request.getRequestDispatcher(REDIRECT).forward(request, response);
	}
}
