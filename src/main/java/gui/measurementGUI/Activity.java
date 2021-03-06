package gui.measurementGUI;

import gui.elements.Dropdown;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import measurements.Measurements;

/**
 * the dropdown menu is being created by using the interface Dropdown.<br><br>
 * 
 * 
 * <b> TODO</b> using the users supplied activities.
 * @author mathieu
 * @version 08/23/2018
 */

public class Activity implements Dropdown {
	Measurements m;
	String[] activiteiten = { "werk", "ontspanning", "reizen", "sociaal", "sporten" };
	ComboBox<String> cb;
	VBox vBox = new VBox();

	public VBox activiteitVBox() {
		Label text = new Label("wat ben je nu aan het doen?");
		cb= dropDownVBox(activiteiten);
		vBox.getChildren().addAll(text, cb);
		return vBox;

	}

	/**
	 * 
	 * @param obj - sets the right reference to save the data that is generated by the used input
	 */
	public void setM(Measurements obj) {
		m = obj;
	}

	/**
	 * @param oldValue - not needed here
	 */
	@Override
	public void actionComboBox(String oldValue, String newValue) {
		m.setActivity(newValue);

	}
	
	public void reset() {
		vBox.getChildren().remove(cb);
		cb=null;
		cb = dropDownVBox(activiteiten);
		vBox.getChildren().add(cb);
		
		
		
//		reset(cb);
	}

}
