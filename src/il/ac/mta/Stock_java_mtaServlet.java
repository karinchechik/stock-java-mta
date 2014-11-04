package il.ac.mta;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Stock_java_mtaServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("Hello, karin chechik");
		
		// variables declaration:
		int num1;
		int num2;
		int num3;
		
		// variable assignment:
		num1 = 4;
		num2 = 3;
		num3 = 7;
		
		int result = (num1 + num2) * num3;
		
		String resultStr = new String("<h1>Result of (" + num1 + "+" + num2 + ")*" + num3 + " = " + result + "</h1>");
		
		// add result to print line:
		resp.getWriter().println(resultStr);
	}
}
