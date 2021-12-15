
import java.io.IOException;
import java.sql.DriverManager;
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
		Conection conn = null;
		String url = "jdbc:mysql://localhost/jdbctestdb";
		String user = "root";
		String password = "";

		try {
					.prepareStatement("select id,name,point from point p ,ticket t");
			ResultSet result = st.executeQuery();

			java.util.List<String[]> list = new ArrayList<>();
			while (result.next() == true) {
				String[] s = new String[3];
				// System.out.println(result.getString("X"));

				s[0] = result.getString("id");
				s[1] = result.getString("name");
				s[2] = result.getString("point");
				list.add(s);

			}
			for (String[] s : list) {
				System.out.println(s[0]);
			}
			request.setAttribute("list", list);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				 if (conn != null){
				      conn.close();
				    }
				  }catch (SQLException e){	
					  e.printStackTrace();
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
		rd.forward(request, response);
	}
}