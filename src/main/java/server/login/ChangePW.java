package server.login;

import java.util.Arrays;

import gui.GUI;
import gui.WelcomeGUI;
import gui.elements.Close;
import gui.measurementGUI.PWField;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.connection.ToServer;

public class ChangePW implements Close{
	Stage stage = new Stage();

	public void initialized() {
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
		Label lblUserName = new Label("Username");
		final TextField usernameFld = new TextField();

		Label lbloldPW = new Label("Old Password");
		final PWField oldPWfld = new PWField();
		Label lblPassword = new Label("New Password");
		final PWField pf = new PWField();
		Label lblPassword2 = new Label("Repeat Password");
		final PWField pf2 = new PWField();
		Button btnLogin = new Button("Create Account");
		final Label lblMessage = new Label();
		// Adding Nodes to GridPane layout
		VBox fields = new VBox();

		gridPane.add(lblUserName, 0, 0);
		gridPane.add(usernameFld, 1, 0);
		gridPane.add(lbloldPW, 0, 1);
		gridPane.add(oldPWfld.getPWField(), 1, 1);
		gridPane.add(lblPassword, 0, 2);
		gridPane.add(pf.getPWField(), 1, 2);
		gridPane.add(lblPassword2, 0, 3);
		gridPane.add(pf2.getPWField(), 1, 3);
		gridPane.add(btnLogin, 3, 3);
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
		Text text = new Text("JavaFX 2 Login");
		text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		text.setEffect(dropShadow);
		// Adding text to HBox
		hb.getChildren().add(text);
		// Add ID's to Nodes
		bp.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		btnLogin.setDefaultButton(true);
		text.setId("text");
		// Action for btnLogin

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("in actionhandler");
				String username = usernameFld.getText();
				char[] oldPW = oldPWfld.getPW();
				String temp = null;
				char[] pw = pf.getPW();

				char[] pw2 = pf2.getPW();

				if (Arrays.equals(pw, null) || Arrays.equals(pw2, null)) {
					temp = "insert all the fields";
				} else {
					temp = ToServer.makeConnection().sendChangePW(username, oldPW, pw, pw2);
				}
				System.out.println(temp);

				switch (temp) {
				case "different pw's":
					lblMessage.setText("Make sure passwords are identical");
					lblMessage.setTextFill(Color.RED);
					break;
				case "username allready taken":
					lblMessage.setText("Email is allready in use");
					lblMessage.setTextFill(Color.RED);
					break;
				case "welcome":
					lblMessage.setText(temp);
					lblMessage.setTextFill(Color.GREEN);
					Duration d = Duration.seconds(2); // seconds for testing
					Timeline twoSecondsWonder = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							GUI gui = new GUI();
							gui.initialize();
							gui.showStage();
//									fiveSecondsWonder.stop();
							new WelcomeGUI().setLoginValid(true);
							stage.hide();
							close();
						}
					}));

					break;
				default:
					lblMessage.setText("error " + temp);
					lblMessage.setTextFill(Color.RED);
				}
//					if (temp) {
//						new WelcomeGUI().setLoginValid(temp);
//						lblMessage.setText("Congratulations!");
				//
//						lblMessage.setTextFill(Color.GREEN);
				//
//					} else {
//						lblMessage.setText("Incorrect user or pw.");
//						lblMessage.setTextFill(Color.RED);
//					}
				oldPWfld.resetField();
				pf.resetField();
				pf2.resetField();
				event.consume();
			}

		};
		btnLogin.setOnAction(buttonHandler);
		// Add HBox and GridPane layout to BorderPane Layout
		bp.setTop(hb);
		bp.setCenter(fields);

		Scene scene = new Scene(bp);
		stage.setScene(scene);
	}

	public void show() {
		stage.show();
	}

	@Override
	public void close() {
		stage.hide();
		// TODO Auto-generated method stub
		
	}
}
