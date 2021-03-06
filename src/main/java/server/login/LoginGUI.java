package server.login;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import gui.GUI;
import gui.WelcomeGUI;
import gui.measurementGUI.PWField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import server.connection.ToServer;

public class LoginGUI {
	Duration d = Duration.seconds(2); 

	Timeline twoSecondsTillExit = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			GUI gui = new GUI();
			gui.initialize();
			gui.showStage();
			stage.hide();
		}
	}));

	private String Username;
	private static int counter = 0;
	private Stage stage;
	private Scene scene;

	public void initialize() {
		char[] pw;
		if (stage == null) {
			stage = new Stage();
			stage.setTitle("JavaFX 2 Login");
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
			final TextField txtUserName = new TextField();
			Label lblPassword = new Label("Password");
			final PWField pf = new PWField();
			Button btnLogin = new Button("Login");
			Button btnNewUser = new Button("New User");
			final Label lblMessage = new Label();
			// Adding Nodes to GridPane layout
			gridPane.add(lblUserName, 0, 0);
			gridPane.add(txtUserName, 1, 0);
			gridPane.add(lblPassword, 0, 1);
			gridPane.add(pf.getPWField(), 1, 1);
			gridPane.add(btnLogin, 2, 1);
			gridPane.add(btnNewUser, 0, 3);
			gridPane.add(lblMessage, 1, 2);
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
			btnLogin.setPrefSize(50, 50);
			text.setId("text");
			// Action for btnLogin

			EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("in actionhandler");
					String userName = txtUserName.getText().toString();

					User.setUserName(userName);

//					String checkPw = pf.getText().toString();
					char[] pw = pf.getPW();

					boolean temp = ToServer.makeConnection().sendPW(userName, pw);
					if (temp) {
						new WelcomeGUI().setLoginValid(temp);
						lblMessage.setText("Congratulations!");
						startTimer();
						lblMessage.setTextFill(Color.GREEN);

					} else {
						lblMessage.setText("Incorrect user or pw.");
						lblMessage.setTextFill(Color.RED);
					}
					txtUserName.setText("");
					pf.resetField();

					event.consume();
				}
			};
			btnLogin.setOnAction(buttonHandler);
			EventHandler<ActionEvent> newUser = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					new NewUserGUI().newUser(stage);
				}
			};
			btnNewUser.setOnAction(newUser);
			// Add HBox and GridPane layout to BorderPane Layout
			bp.setTop(hb);
			bp.setCenter(gridPane);
			// Adding BorderPane to the scene and loading CSS
			scene = new Scene(bp);
//			scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
			stage.titleProperty()
					.bind(scene.widthProperty().asString().concat(" : ").concat(scene.heightProperty().asString()));
			// stage.setResizable(false);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				public void handle(WindowEvent we) {
					counter = 0;
				}

			});
		}
//		return stage;
	}

	public void show() {
		stage.setScene(scene);
		if (counter == 0) {
			stage.show();
			counter = 1;
		}
	}

	void resetCounter() {
		counter = 0;
	}

	void startTimer() {
		twoSecondsTillExit.play();
		resetCounter();
		WelcomeGUI.hideStage();
	}

}