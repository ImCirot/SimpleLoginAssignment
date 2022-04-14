package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserBean;
import model.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// do nothing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		Encoder encoder = Base64.getEncoder();
		String password64 = encoder.encodeToString(request.getParameter("password").getBytes());
		UserBean user = checkLogin(username, password64);
		
		if(user != null) {
			request.setAttribute("name", user.getName());
			request.setAttribute("surname", user.getSurname());
			
			RequestDispatcher view = request.getRequestDispatcher("./UserLogged.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("./InvalidLogin.jsp");
		}
	}
	
	private UserBean checkLogin(String username, String password64) {
		UserDAO database = new UserDAO();
		UserBean user = new UserBean();
		
		try {
			user = database.doRetrieveByKey(username);
			
			if(user.getUsername().equals(username) && user.getPassword().equals(password64)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
