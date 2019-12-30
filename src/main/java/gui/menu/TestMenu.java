package gui.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import client.MainClient;
import gui.GUI;
import server.login.LoginGUI;

public class TestMenu {

	Menu getTestMenu() {
		Menu testMenu = new Menu("Test");
		
	testMenu.getItems().add(testLoginAgain());	
	testMenu.getItems().addAll(setAllNull());
		
		
		
				
		return testMenu;
	}
	
	private MenuItem testLoginAgain() {
		MenuItem loginAgainItem = new MenuItem("Login Again");
		loginAgainItem.setOnAction(e -> {
			GUI.print("in Login Again");
			LoginGUI loginGUI = new LoginGUI();
			loginGUI.initialize();
			loginGUI.show();
			GUI.hideStage();
			
			//launch GUI to confirm old PW and enter new PW
			//
			
		});		
		
		return loginAgainItem;		
	}
	
	private Menu setAllNull() {
		Menu menu = new Menu("Test mode");
		ToggleGroup tGroup = new ToggleGroup();
		RadioMenuItem[] data = new RadioMenuItem[2];

		data[0] = new RadioMenuItem("Test Mode");
		data[0].setSelected(true);
		data[0].setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	GUI.print("setting all data to \"null\"");
		    	MainClient.TESTMODE = true;
		        System.out.println("radio toggled");
		    }
		});
		data[0].setToggleGroup(tGroup);
		data[1] = new RadioMenuItem("Playing for real mode");
		data[1].setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	MainClient.TESTMODE = false;
		        GUI.print("keep all data unedited");
		    }
		});
		data[1].setToggleGroup(tGroup);
		menu.getItems().addAll(data);
		
		return menu;
	}
}
