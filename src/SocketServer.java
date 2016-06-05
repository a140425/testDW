import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class SocketServer extends java.lang.Thread {

	private boolean OutServer = false;
	private ServerSocket server;
	private final int ServerPort = 4520;// �n�ʱ���port

	public SocketServer() throws IOException {
		server = new ServerSocket(ServerPort);
	}

	public void run() {
		Socket socket;
		java.io.BufferedInputStream in;

		System.out.println("���A���w�Ұ� !");
		while (!OutServer) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("���o�s�u : InetAddress = "
						+ socket.getInetAddress());
				// TimeOut�ɶ�
				socket.setSoTimeout(600000);

				in = new java.io.BufferedInputStream(socket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = in.read(b)) > 0) {
					data += new String(b, 0, length);
				}
				System.out.println("�ڨ��o����:" + data);
				waitFor(Integer.valueOf(data));
				in.close();
				in = null;
				socket.close();

			} catch (java.io.IOException e) {
				System.out.println("Socket�s�u�����D !");
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