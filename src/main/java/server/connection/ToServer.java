package server.connection;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.sql.Array;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

import gui.GUI;
import javafx.application.Platform;
import server.login.Login;
import server.login.LoginGUI;
import server.login.Token;

/**
 * @author Mathieu
 * @version 09/27/2018
 */
public class ToServer {
	protected static String host;
	private int socketNr;
	private int objectSocketNr;
	private static ToServer conn = new ToServer();
	Data data = new Data();
	
	protected void refreshData() {
		host = data.getHostname();
		socketNr = data.getSocket();
		objectSocketNr = data.getObjectSocket();
	}

	private ToServer() {
		data.getData();
		refreshData();
	}

	public static ToServer makeConnection() {

		return conn;
	}

	private void sendCharArray(DataOutputStream output, char... cs) throws IOException {
		output.writeInt(cs.length);
		CharBuffer.wrap(cs).chars().forEach(i -> {
			try {
				output.writeChar(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * 
	 * @param o - object that will be send to the server
	 */
	private void sendObject(Object o) {
		GUI.print("");
		GUI.print("in sendObject()");
		GUI.print("");

		try (Socket socket = new Socket(host, socketNr);
				Socket objectSocket = new Socket(host, objectSocketNr);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream token = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			boolean test = sendToken(token, input);
//			GUI.print("sendToken(token, input) = " + test);
			if (!test) {
				LoginGUI l = new LoginGUI();
				l.initialize();
				l.show();

			}
			GUI.print("sending option = \"BasicMeasurements\" ");
			token.writeUTF("BasicMeasurements");
			token.flush();
			GUI.print("sended \"BasicMeasurements\" ");
			Thread.sleep(100);

			GUI.print("sending object ");
			GUI.print(o.toString());
			objectToServer.writeObject(o);
			objectToServer.flush();

			Thread.sleep(100);
			GUI.print("sending option = \"Close\" ");
			token.writeUTF("Close");
			token.flush();
			GUI.print("sended \"Close\" ");
		} catch (IOException | InterruptedException e) {
			System.out.println("error");
			e.printStackTrace();
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	public boolean sendToken() {
		try (Socket socket = new Socket(host, socketNr);
				Socket objectSocket = new Socket(host, objectSocketNr);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			return sendToken(output, input);
		} catch (IOException | InterruptedException e) {
			System.out.println("error");
			e.printStackTrace();
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return false;
	}

	private boolean sendToken(DataOutputStream output, DataInputStream input) throws IOException, InterruptedException {

		Login login = Login.loginEntry();
		Token t = new Token();
		char[] tkn = login.getToken();
		String[] meta = t.getMetadata();

		if (tkn.length == 0) {
			return false;
		}

		System.out.println("sending option = \"Token\"");
		output.writeUTF("Token");
//		token.flush();
		System.out.println("sended option = \"Token\"");

		Thread.sleep(10);
		System.out.println("sending token");
		sendCharArray(output, tkn);

		System.out.println("sended token");
		Thread.sleep(10);
		output.writeUTF(meta[1]);
		output.flush();

		tkn = null;
		login = null;

		System.out.println("waiting for conformation of the token");
		Thread.sleep(10);

		String temp = input.readUTF();
		System.out.println("temp is " + temp);
		if (temp.equals("Correct Token")) {
			System.out.println("temp = " + temp);
			return true;
		} else {
			System.out.println("temp = " + temp);
			return false;
		}
	}

	public boolean sendPW(String User, char[] pw) {
		System.out.println("in sendPW()");
		try (Socket socket = new Socket(host, socketNr);
				Socket objectSocket = new Socket(host, objectSocketNr);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			System.out.println("sending option = \"Password\"");
			output.writeUTF("Password");
			System.out.println("sended option = \"Password\"");

			System.out.println("sending user");
			output.writeUTF(User);
			System.out.println("sending PW");

			sendCharArray(output, pw);

			output.flush();
			pw = null;

			Thread.sleep(10);
			System.out.println("waiting for conformation");
			String temp = input.readUTF();
			if (temp.equals("Correct pw")) {
				System.out.println("Correct pw " + temp + '\n');
				String t = input.readUTF();
				Token tkn = new Token();
				tkn.deleteFile();
				tkn.createFile(t);
				return true;
			} else {
				System.out.println(" incorrect pw " + temp + '\n');
				return false;

			}
		} catch (IOException e) {
//			System.out.println("error");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String sendNewAccount(String User, char[] pw, char[] pw2) {
		System.out.println("in sendNewAccount()");
		try (Socket socket = new Socket(host, socketNr);
				Socket objectSocket = new Socket(host, objectSocketNr);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			System.out.println("sending option = \"sendNewAccount\"");
			output.writeUTF("sendNewAccount");
//			token.flush();
			System.out.println("sended option = \"sendNewAccount\"");

			System.out.println("sending user");
			output.writeUTF(User);
			System.out.println("sending PW");
			sendCharArray(output, pw);

			System.out.println("sending PW2");
			sendCharArray(output, pw2);

			output.flush();
			pw = null;

			Thread.sleep(10);
			System.out.println("waiting for conformation");
			String temp = input.readUTF();
			System.out.println("temp is " + temp);
			if (temp.equals("Welcome")) {
				String token = input.readUTF();
				new Token().createFile(token);
			}
			return temp;

		} catch (IOException e) {
//			System.out.println("error");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String sendChangePW(String username,char[] oldPW, char[] pw, char[] pw2) {
		System.out.println("in sendChangePW()");
		try (Socket socket = new Socket(host, socketNr);
				Socket objectSocket = new Socket(host, objectSocketNr);
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			System.out.println("sending option = \"ChangePW\"");
			output.writeUTF("ChangePW");
			System.out.println("sended option = \"ChangePW\"");
			output.flush();
			Thread.sleep(10);
			System.out.println("sending user");
			output.writeUTF(username);
			output.flush();
			Thread.sleep(10);
			sendCharArray(output, oldPW);
			output.flush();
			Thread.sleep(10);
			System.out.println("sending PW");
			sendCharArray(output, pw);
			output.flush();
			Thread.sleep(10);

			System.out.println("sending PW2");
			sendCharArray(output, pw2);
			output.flush();
			Thread.sleep(10);
			
//			output.flush();
			Arrays.fill(pw2, '0');
			Arrays.fill(pw, '0');
			Arrays.fill(oldPW, '0');

			Thread.sleep(10);
			System.out.println("waiting for conformation");
			String temp = input.readUTF();
			System.out.println("temp is " + temp);
			if (temp.equals("Welcome")) {
				String token = input.readUTF();
				new Token().createFile(token);
			}
			return temp;

		} catch (IOException e) {
//			System.out.println("error");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void sendToServer(Object o) {
		ToServer ts = new ToServer();
		ts.sendObject(o);

	}

	

}
