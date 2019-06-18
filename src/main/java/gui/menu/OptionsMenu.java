package gui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import server.connection.ChangeServerGUI;
import server.login.ChangePW;
import gui.GUI;
import gui.measurementGUI.MeasurmentGUI;

public class OptionsMenu {
	
	Menu getOptionsMenu() {
		Menu options = new Menu("Options");
		
	options.getItems().add(testItem());	
	options.getItems().add(serverItem());	
		
		
		
				
		return options;
	}
	
	private MenuItem testItem() {
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
		gui.initialized();
		testItem.setOnAction(e -> {
			GUI.print("Change Server data");
			gui.showGUI();
			
		});	
//		measurment = new MeasurmentGUI();
//		measurmentGUI = measurment.initialized();
//		MenuItem testItem = new MenuItem("Measurment");
//		testItem.setOnAction(e -> {
//			measurment.setUID(1);
//			measurment.reset(1);
//			measurmentGUI.show();
//		});	
		
		return testItem;
	}

}
