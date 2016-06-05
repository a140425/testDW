import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class SocketServer extends java.lang.Thread {

	private boolean OutServer = false;
	private ServerSocket server;
	private final int ServerPort = 4520;// 要監控的port

	public SocketServer() throws IOException {
		server = new ServerSocket(ServerPort);
	}

	public void run() {
		Socket socket;
		java.io.BufferedInputStream in;

		System.out.println("伺服器已啟動 !");
		while (!OutServer) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("取得連線 : InetAddress = "
						+ socket.getInetAddress());
				// TimeOut時間
				socket.setSoTimeout(600000);

				in = new java.io.BufferedInputStream(socket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = in.read(b)) > 0) {
					data += new String(b, 0, length);
				}
				System.out.println("我取得的值:" + data);
				waitFor(Integer.valueOf(data));
				in.close();
				in = null;
				socket.close();

			} catch (java.io.IOException e) {
				System.out.println("Socket連線有問題 !");
				System.out.println("IOException :" + e.toString());
			}

		}
	}

	private void waitFor(int nTime) {
		try {
			System.out.println("Sleep " + nTime + "ms");
			System.out.println("Start sleep "
					+ Calendar.getInstance().getTimeInMillis());
			Thread.sleep(nTime);
			System.out.println("Stop sleep "
					+ Calendar.getInstance().getTimeInMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}