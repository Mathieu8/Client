package gui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import server.connection.ChangeServerGUI;
import server.login.ChangePW;
import gui.GUI;

public class OptionsMenu {
	
	Menu getOptionsMenu() {
		Menu options = new Menu("Options");
		
	options.getItems().add(changePWItem());	
	options.getItems().add(serverItem());	
		
		
		
				
		return options;
	}
	
	private MenuItem changePWItem() {
		MenuItem testItem = new MenuItem("Change Password");
		ChangePW gui = new ChangePW();
		gui.initialized();
		testItem.setOnAction(e -> {
			GUI.print("in change PW");
			gui.show();
			//launch GUI to confirm old PW and enter new PW
			//
			
		});		
		
		return testItem;		
	}
	
	private MenuItem serverItem() {
		MenuItem testItem = new MenuItem("Change Server data");
		ChangeServerGUI gui = new ChangeServerGUI();
		Stage stage = gui.initialized();
		testItem.setOnAction(e -> {
			GUI.print("Change Server data");
			gui.showGUI();
			
		});	
		
		return testItem;
	}

}
