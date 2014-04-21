package dylanandapril.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dylanandapril.database.User;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Used for retrieving, updating, or deleting an account
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("logout") != null) {
			if(request.getSession() != null) {
				request.getSession().invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		HttpSession session = null;
		//String action = request.getParameter("action");
		String form = request.getParameter("form");
		String username = request.getParameter("username");
		if(username == null) {
			//request.setAttribute("error", "Invalid login");
			//request.getRequestDispatcher("/login").forward(request, response);
			response.sendRedirect(request.getContextPath()+"/login?error=true");
			return;
		}
		User u = User.findUserByName(username);
		System.out.println("AccountServlet doGet -- Attempting to log in. User retrieved from database: " + u);
		if(form != null && form.equals("login") && u != null) {
			session = request.getSession(true);
			session.setAttribute("user", u);
			response.sendRedirect(request.getContextPath()+"/main");
			return;
		} else {
			//request.setAttribute("error", "Invalid login");
			response.sendRedirect(request.getContextPath()+"/login?error=true");
			//request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
	}

	/**
	 * Used for creating an account
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ENTERED ACCOUNTSERVLET");

		String formType = request.getParameter("form");
		System.out.println("formType = " + formType);
		
		if(formType.equals("signup")) {
			String username = request.getParameter("username");
			System.out.println("username = " + username);
			
			String role = request.getParameter("role");
			System.out.println("role = " + role);
			
			String ageStr = request.getParameter("age");
			System.out.println("ageStr = " + ageStr);
			
			String state = request.getParameter("state");
			System.out.println("state = " + state);
			
			User user = new User(username, role, Integer.parseInt(ageStr), state);
			int id = user.insertIntoDatabase();
			System.out.println("new user id: "+id);
			
			if(id == -1) {
				System.out.println("AccountServlet--User creation NOT successful, fowarding to /login");
				response.sendRedirect(request.getContextPath()+"/login?accountCreation=false");
				return;
			} else {
				System.out.println("AccountServlet--User creation successful, forwarding to /login");
				response.sendRedirect(request.getContextPath()+"/login?accountCreation=success");
				return;
			}

		}
		
	}

}
