package server.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {
	private String hostname;
	private int socket;
	private int objectSocket;

	private String filename = "data.txt";
	private Path p = Paths.get(System.getProperty("user.dir"), filename);

	void getData() {
		// + "C:\\Users\\Mathieu\\eclipse-workspace\\M-Tool\\client\\token.txt");

//		List<Character> temp = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		try (InputStream in = new BufferedInputStream(Files.newInputStream(p))) {

			int i = 0, j = 0;
			do {
				i = in.read();
				if (i != -1) {
					char c = (char) i;
					if (c == ',') {
						if (j == 0) {
							hostname = temp.toString();
							temp.delete(0, temp.length());
							System.out.println("hostname " + hostname);

						} else if (j == 1) {
							socket = Integer.valueOf(temp.toString());
							temp.delete(0, temp.length());
							System.out.println("socket" + socket);
						} else {
							objectSocket = Integer.valueOf(temp.toString());
							System.out.println("objectSocket" + objectSocket);
							break;
						}
						j++;
					} else {
						temp.append(c);
					}
				}
			} while (i != -1);

		} catch (NoSuchFileException | FileNotFoundException e) {
			e.printStackTrace();
			createFile("localhost,8002,8001,");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private void createFile(String s) {
		byte data[] = s.getBytes();

		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
			out.write(data);// , 0, data.length);
			Files.setAttribute(p, "dos:hidden", true);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	public void updateFile(String[] option, String[] data) {
		getData();
		if (option.length != data.length) {
			throw new ArrayIndexOutOfBoundsException("length of fields  and data doesn't make sense");
		}
		for (int i = 0; i < option.length;i++) {
			switch (option[i]) {
			case "hostname":
				hostname = data[i];
				break;
			case "socket":
				socket = Integer.valueOf(data[i]);
				break;
			case "objectSocket":
				objectSocket = Integer.valueOf(data[i]);
				break;
			}
		}
		
		String temp = hostname + "," + socket + "," + objectSocket + ",";
		byte bytes[] = temp.getBytes();
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
			out.write(bytes);// , 0, data.length);

		} catch (IOException x) {
			System.err.println(x);
		}

	}

	public void updateFile(String[] data) {
		String[] temp = { "hostname", "socket", "objectSocket" };
		updateFile(temp, data);

	}

	String getHostname() {
		return hostname;
	}

	int getSocket() {
		return socket;
	}

	int getObjectSocket() {
		return objectSocket;
	}

}
