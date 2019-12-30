package gui;

import client.MainClient;
import client.TestMode;
import gui.elements.Close;
import gui.measurementGUI.MeasurmentGUI;
import gui.measurementGUI.Productivity;
import gui.menu.TestMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import measurements.BasicMeasurements;
import measurements.Measurements;
import server.connection.ToServer;

/**
 * This class makes a button, that if pressed all the data will be sent to the
 * server.login to be handled
 * 
 * <br>
 * <br>
 * <b> TODO</b> this class should implement Buttons interface.
 * 
 * @author mathieu
 * @version 09/27/2018
 */

public class SaveBtn {
	private Close GUI;
	
	private BasicMeasurements obj;
	private String text = "save";
	private String tooltip = "save";
	
	public SaveBtn(Close gui) {
		this.GUI=gui;
		
	}

	/**
	 * @return Button
	 */
	public Button createBtn() {
		Button btn = new Button(text);
		Tooltip emotieTemp = new Tooltip(tooltip);
		btn.setTooltip(emotieTemp);
		btn.setMinWidth(50.0);
		btn.setPrefWidth(50.0);
		btn.setMaxWidth(50.0);
		btn.setDefaultButton(true);

		btn.setFont(Font.font("Verdana", 12));
		EventHandler<ActionEvent> btnHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				toSave();
				event.consume();
			}

		};
		btn.setOnAction(btnHandler);
		return btn;
	}

	/**
	 * @return HBox - with the save button in it
	 */
	public HBox saveBtn() {
		HBox saveBox = new HBox();
		saveBox.getChildren().addAll(createBtn());
		return saveBox;

	}

	/**
	 * This makes a connection to the server.login and than passes object obj to the
	 * server.login.
	 */
	private void toSave() {
		if(MainClient.TESTMODE) {
			new TestMode().setAllDataToNull(obj); 
		}
		obj.setDuraction();
		ToServer.sendToServer(obj);
		GUI.close();
	}

	/**
	 * @param o - object that will be later passed on to the server.login
	 */
	public void setObject(BasicMeasurements o) {
		obj = o;
	}

}