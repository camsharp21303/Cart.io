package graphicalUI;

import application.Main;
import javafx.geometry.HorizontalDirection;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import types.Customer;
import types.Employee;
import types.User;

public class MyAccount {
	private BorderPane node;
	private Label nameL, numberL, addressL, emailL, phoneL;
	private Button editAddressB, editEmailB, editPhoneB, logoutB;
	
	public MyAccount(User user) {
		node = new BorderPane();
		nameL = new Label(user.getFname() + " " + user.getLname());
		numberL = new Label("Account Number: #" + user.getNumber());
		emailL = new Label("Email: " + user.getEmail());
		phoneL = new Label("Phone: " + user.getPhone());
		
		editEmailB = new Button("Edit");
		editPhoneB = new Button("Edit");
		
		logoutB = new Button("Logout");
		logoutB.setOnAction(e -> Main.logout());
		
		node.setBottom(logoutB);
		
		VBox userInfo;
		if(user instanceof Customer) {
			addressL = new Label("Address: " + ((Customer)user).getAddress());
			userInfo = new VBox(nameL, numberL, addressL, emailL, phoneL);
		}
		else {
			userInfo = new VBox(nameL, numberL, emailL, phoneL);
		}
		
		node.setCenter(userInfo);
	}
	
	public Node getScene() {
		return node;
	}
}
