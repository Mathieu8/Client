package server.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;

import server.login.Token;

public interface Options{
	default String inputOptions(DataInputStream input) throws IOException {
		var option = input.readUTF();
		System.out.println("options: option = " + option);
		switch (option) {
		case "token":
			var tkn = input.readUTF();
			Token token = new Token();
			token.deleteFile();
			token.createFile(tkn);
			return inputOptions(input);
		// Welcome
		case "welcome":
		case "correct pw":
		case "correct token":
			return "welcome";
		// issues with login
		case "different pw's":
		case "Wrong token":
		case "wrong pw":
		case "wrong old PW":
		case "username allready taken":
			return option;

		}
		
		
		System.out.println("error: no valid option for a return is given");
		return "error: no valid option for a return is given";
	}

	default void sendCharArray(DataOutputStream output, char... cs) throws IOException {
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

}
