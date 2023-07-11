package shoppingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/viewcart")
public class ViewCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		con = (Connection) getServletContext().getAttribute("jdbccon");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		// sps - selected Products
		List<Integer> sps = (List<Integer>) session.getAttribute("cart");
		if (sps == null) {
			out.print("No Product Is Selected");
			out.println("<a href='home'>Go to Home</a>");
		} else {
			out.println("<table border=1>");
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement("select * from product where p_id = ?");
				int cnt = 0;
				float tprice = 0;
				for (int n : sps) {
					ps.setInt(1, n);
					rs = ps.executeQuery();
					if (rs.next()) {
						out.println("<tr>");
						out.print("<td>" + (++cnt) + "</td>");
						out.print("<td>" + rs.getString(2) + "</td>");
						out.print("<td>" + rs.getString(4) + "</td>");
						out.print("<td> <a href='deletefromcart?pid=" + (cnt-1) + "'> delete </a>  </td>");
						out.println("</tr>");
						tprice += Float.parseFloat(rs.getString(4));
					}
				}
				session.setAttribute("tprice", tprice);
				out.println("<tr>");
				out.print("<td colspan='2'> Total price </td>");
				out.print("<td>" + tprice + "</td>");
				out.println("</tr>");
				out.println("</table>");
				out.print("<br/> <br/> <a href='confirmcart'> Confirm Cart <a/>");
				out.print("<br/> <br/> <a href='home'> Go back to Home <a/>");
			} catch (Exception e) {
				e.printStackTrace();
			} finally { 
				try {
					rs.close();
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
