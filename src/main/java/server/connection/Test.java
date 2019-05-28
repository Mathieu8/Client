package server.connection;

import java.time.LocalDateTime;

import server.login.Token;

public class Test {

	public static void main(String[] args) {
		System.out.println("Local time " + LocalDateTime.now());
		new server.login.Test().deleteFile();
		new server.login.Test().createFile("token test token");
		Token t = new Token();
		System.out.println("printing Token + meta data " + new server.login.Test().getToken());

	}

}