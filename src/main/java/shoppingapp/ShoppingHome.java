package shoppingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/home")
public class ShoppingHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con ;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		con=(Connection)getServletContext().getAttribute("jdbccon");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/header");
		rd.include(request, response);
		PrintWriter out = response.getWriter();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			ps=con.prepareStatement("select * from category");
			rs=ps.executeQuery();
			out.print("<h1>Home page</h1>");
			while(rs.next())
			{
				out.println("<a href='getproduct?cid="+ rs.getInt(1) +"'> "+ rs.getString(2)+" </a> <br/>");
			}
			out.println("<a href='viewcart'>View Cart</a>");
			out.println("<br><hr><a href='logout'>Logout</a>");

			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		
	}

}
