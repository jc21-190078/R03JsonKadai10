
import java.beans.Statement;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.mysql.cj.protocol.Resultset;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getPoint")
public class GetPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPointServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai10";
		final String id = "jsonkadai10";
		final String pass = "JsonKadai10";

		try {
			Class.forName(driverName);
			Connection connection = DriverManager.getConnection(url, id, pass);

			PreparedStatement st1 = connection.prepareStatement("select * from point where user_id = ? and dept_id = ?");
			PreparedStatement st2 = connection
					.prepareStatement("insert into point (user_id,dept_id,point) values (?,?,500)");
			

			String user = request.getParameter("USER_ID");
			String tenpo = request.getParameter("TENPO_ID");
			String point = null;
			
			
			st1.setString(1, user);
			st1.setString(2, tenpo);
			ResultSet rs1 = st1.executeQuery();
			
			if(rs1.next() == true) {
				point = rs1.getString("point");
				
			}
			else {
				st2.setString(1, user);
				st2.setString(2, tenpo);
				st2.executeUpdate();
				 point = "500";
			}


			request.setAttribute("point", point);
			request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp").forward(request, response);
			//request.getRequestDispatcher("https://192.168.54.190:8080/R03JsonKadai10/getPoint?userId=548269&shopId=909938").forward(request, response);
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}