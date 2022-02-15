
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getTicketList")
public class GetTicketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTicketListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai10";
		final String id = "jsonkadai10";
		final String pass = "JsonKadai10";

		try {
			Class.forName(driverName);
			Connection connection = DriverManager.getConnection(url, id, pass);

			PreparedStatement st = connection.prepareStatement("select * from ticket where user_id = ? and dept_id = ?");
			

			String user = request.getParameter("USER_ID");
			String tenpo = request.getParameter("TENPO_ID");
			String ticket = null;
			
			
			st.setString(1, user);
			st.setString(2, tenpo);
			ResultSet rs = st.executeQuery();
			
			java.util.List<String[]> list = new ArrayList<>();
			while (rs.next() == true) {
				String[] s = new String[3];
				// System.out.println(result.getString("X"));

				
				s[0] = rs.getString("ticket_id");
				s[1] = rs.getString("ticket_name");
				s[2] = rs.getString("point");
				list.add(s);

			}
			for (String[] s : list) {
				System.out.println(s[0]);
			}
			request.setAttribute("list", list);



			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
			rd.forward(request, response);
			//request.getRequestDispatcher("https://192.168.54.190:8080/R03JsonKadai10/getPoint?userId=548269&shopId=909938").forward(request, response);
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
