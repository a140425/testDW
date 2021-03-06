import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("POST!!!");
		StringBuffer sb = new StringBuffer("Application starts at :");

		SocketServer ss = new SocketServer();
		ss.start();
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF8");
		int nTime = Integer.valueOf(request.getParameter("date"));
		sb.append(Calendar.getInstance().getTimeInMillis());
		sb.append("<br>Application stops at :");
		System.out.println("The input time is: " + nTime);
		try {
			SocketClient(nTime);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		sb.append(Calendar.getInstance().getTimeInMillis());

		outputResponse(response, sb);
		System.out.println("POST end");
	}

	private void outputResponse(HttpServletResponse response, StringBuffer sb)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.print(sb.toString());
		out.flush();
		out.close();
	}

	private void SocketClient(int nTime) throws IOException,
			InterruptedException, ExecutionException {

		String address = "localhost";
		int port = 4520;
		Socket client = new Socket();

		InetSocketAddress isa = new InetSocketAddress(address, port);
		try {
			client.connect(isa, 100000);
			BufferedOutputStream out = new BufferedOutputStream(
					client.getOutputStream());
			out.write(String.valueOf(nTime).getBytes());
			out.flush();
			out.close();
			client.close();

		} catch (java.io.IOException e) {
			System.out.println("Socket connection failed");
			System.out.println("IOException :" + e.toString());
		}
	}
}
