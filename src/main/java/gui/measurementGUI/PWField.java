package gui.measurementGUI;

import java.util.Arrays;
import java.util.stream.Stream;

import javafx.scene.control.PasswordField;

public class PWField {
	private char[] pw;
	private PasswordField pf = new PasswordField();
	
	public char[] getPW() {
		var temp = pf.getCharacters();
		pw = new char[temp.length()];
		Stream.iterate(0, n->n+1).limit(temp.length()).forEach(a->pw[a]=temp.charAt(a));
		return pw;
	}
	
	public PasswordField getPWField() {
		return pf;
	}
	
	public void resetField() {
	Arrays.fill(pw, 'p');
		pf.setText("");
	}
	

}
