package il.ac.mta;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Stock_java_mtaServlet extends HttpServlet {
	
	public final double PIE = Math.PI;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("Hello, karin chechik");
		
		// variables declaration:
		int radius;
		double area;
		int angleB;
		int hypotenuse;
		double opposite;
		int base;
		int exp;
		double result;
		double angleBDegrees;
		
		// variable assignment:
		radius = 50;
		angleB = 30;
		hypotenuse = 50;
		base = 20;
		exp = 13;
		angleBDegrees = Math.toRadians(angleB);
		
		area = radius * radius * PIE;
		opposite = hypotenuse * Math.sin(angleBDegrees);
		result = Math.pow(base, exp);
		
		String line1 = new String("<h1>Calculation 1: Area of circle with radius " + radius + " is " + area + " square-cm." + "</h1>");
		String line2 = new String("<h1>Calculation 2: Length of opposite where angle B is " + angleB + " degrees and Hypotenuse length is " + hypotenuse + " cm is: " + opposite + "." + "</h1>");
		String line3 = new String("<h1>Calculation 3: Power of " + base + " with exp " + exp + " is: " + result + "</h1>");

		String resultStr = line1 + "<br>" + line2 + "<br>" + line3;

		// add result to print line:
		resp.getWriter().println(resultStr);
	}
}
