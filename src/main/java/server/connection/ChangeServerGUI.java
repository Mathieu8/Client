package server.connection;

import java.util.Arrays;
import gui.GUI;
import gui.WelcomeGUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangeServerGUI {
	Stage stage = new Stage();
	Data data = new Data();
	
	
	public void initialized() {
		data.getData();
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(0, 50, 50, 50));
		// Adding HBox
		HBox hb = new HBox();
		hb.setPadding(new Insets(20, 20, 20, 30));
		// Adding GridPane
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		// Implementing Nodes for GridPane
		Label lblHostname = new Label("hostname");
		final TextField txtHostname = new TextField(data.getHostname());
		Label lblSocket = new Label("socket");
		final TextField txtSocket = new TextField("" + data.getSocket());
		Label lblObjectSocket = new Label("objectSocket");
		final TextField txtObjectSocket = new TextField("" + data.getObjectSocket());
		Button btnSave = new Button("save");
		final Label lblMessage = new Label();
		// Adding Nodes to GridPane layout
		VBox fields = new VBox();
		
		gridPane.add(lblHostname, 0, 0);
		gridPane.add(txtHostname, 1, 0);
		gridPane.add(lblSocket, 0, 1);
		gridPane.add(txtSocket, 1, 1);
		gridPane.add(lblObjectSocket, 0, 2);
		gridPane.add(txtObjectSocket, 1, 2);
		gridPane.add(btnSave, 3, 2);
		fields.getChildren().add(gridPane);
		fields.getChildren().add(lblMessage);
//			gridPane.add(lblMessage, 2, 3);
		// Reflection for gridPane
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		gridPane.setEffect(r);
		// DropShadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);
		// Adding text and DropShadow effect to it
		Text text = new Text("Server Data");
		text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		text.setEffect(dropShadow);
		// Adding text to HBox
		hb.getChildren().add(text);
		// Add ID's to Nodes
		bp.setId("bp");
		gridPane.setId("root");
		btnSave.setId("Save");
		btnSave.setDefaultButton(true);
		text.setId("text");
		// Action for btnLogin
		
		
		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String[] temp = { txtHostname.getText(), txtSocket.getText(), txtObjectSocket.getText() };
				GUI.print("updating data");
				data.updateFile(temp);
				
				GUI.print("consuming event");
				event.consume();
				GUI.print("closing stage");
//				stage.hide();
				stage.close();
			}
			
		};
		btnSave.setOnAction(buttonHandler);
		// Add HBox and GridPane layout to BorderPane Layout
		bp.setTop(hb);
		bp.setCenter(fields);
		
		Scene scene = new Scene(bp);
		stage.setScene(scene);
	}

	public void showGUI() {

		stage.show();

	}

}
