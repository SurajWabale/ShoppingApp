package shoppingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		int spid = Integer.parseInt(request.getParameter("selectedP"));
		HttpSession session = request.getSession();
		List<Integer> products = (List<Integer>)session.getAttribute("cart");
		if(products == null)
		{
			products = new ArrayList<>();
		}
		products.add(spid);
		session.setAttribute("cart", products);
		
		out.println("Selected Product "+spid+" Is Added To the Cart<br>");
		out.println("Total No OF Products In Cart : "+products.size()+"<br>");
		
		out.println("<a href='viewcart.jsp'>View Cart</a>");
		out.println("<a href='home'>Go To Home</a>");

	}

}
