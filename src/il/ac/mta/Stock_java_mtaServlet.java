package il.ac.mta;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Stock_java_mtaServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
