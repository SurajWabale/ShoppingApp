package shoppingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getproduct")
public class GetProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con ;
	@Override
	public void init() throws ServletException {
		super.init();
		con = (Connection)getServletContext().getAttribute("jdbccon");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}								

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		PreparedStatement ps = null;
		ResultSet rs = null ;
		
		try 
		{
			ps = con.prepareStatement("select * from product where cat_id=?");
			String cid =request.getParameter("cid");
			ps.setString(1, cid);
			rs=ps.executeQuery();
			out.print("<h1>Select Products</h1><br><hr>");
			out.print("<form action='addtocart' method='get'>");
			out.print("<select name ='selectedP'>");
			
			while(rs.next())
			{
				out.print("<option value ="+rs.getString(1)+">"+rs.getString(2)+"</option>");
			}
			out.print("</select>");
			out.print("<br><input type ='submit' value='Add to Cart'/>");
			out.print("</form>");
		} 
		
		catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
	}

}
