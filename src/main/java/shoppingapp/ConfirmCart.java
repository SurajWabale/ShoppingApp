package shoppingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;


@WebServlet("/confirmcart")
public class ConfirmCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con ;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PreparedStatement ps = null ;
		HttpSession session = request.getSession();
		User u =(User)session.getAttribute("loggedinuser");
		String uid = u.getUid();
		java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());
		float tprice = (float)session.getAttribute("tprice");
		
		try
		{

			ps = con.prepareStatement("insert into shopping(user_id,shoppingDate,totalprice) values(?,?,?)");
			ps.setString(1,uid);
			ps.setTimestamp(2, ts);
			ps.setFloat(3, tprice);
			ps.executeUpdate();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			
		out.println("<p>Order is Confirmed</p>");
		out.println("Your Bill Will Be Emailed At : "+u.getEmail()+"</p>");
		out.println("<p>You Will Receive Message On "+u.getContact()+" Before Order Delivery</p>");
		out.print("<br/> <br/> <a href='home'> Want to set new order? <a/>");
		out.println("<br><hr><a href='logout'>Logout</a>");

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
