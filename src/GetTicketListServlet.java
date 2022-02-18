
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

			PreparedStatement st = connection.prepareStatement("select * from ticket where dept_id = ?");
			PreparedStatement st1 = connection.prepareStatement("select * from point where user_id = ? and dept_id = ?");

			String user = request.getParameter("USER_ID");
			String tenpo = request.getParameter("TENPO_ID");

			int point = 0;
			
			st.setString(1,tenpo);
			
			st1.setString(1, user);
			st1.setString(2, tenpo);
			
			ResultSet rs = st.executeQuery();
			ResultSet rs1 = st1.executeQuery();
			
			if(rs1.next() == true) {
				point = rs1.getInt("point");
				
			}
			
			java.util.List<String[]> list = new ArrayList<>();
			while (rs.next() == true) {
				int pointo = rs.getInt("point");

				String[] s = new String[3];
				
				if(pointo >= point) {
				s[0] = rs.getString("ticket_id");
				s[1] = rs.getString("ticket_name");
				s[2] = rs.getString("point");
				list.add(s);
				}

			}

			
			request.setAttribute("list", list);



			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
			rd.forward(request, response);
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
