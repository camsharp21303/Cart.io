package graphicalUI;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import types.Customer;
import types.Employee;
import types.User;

public class AppPane {
	public static void activateAppPane(User user) {
		TabPane tabbedPane;
		Tab home, inventory, orders, createAccount, cart, myAccount;
		
		tabbedPane = new TabPane();
		tabbedPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabbedPane.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
		
		if (user instanceof Customer) {
			home = new Tab("Home", CatalogHome.getNode());
			cart = new Tab("Cart", CartUI.getNode((Customer)user));
			tabbedPane.getTabs().add(home);
			tabbedPane.getTabs().add(cart);
		}
		else if(user instanceof Employee) {
			Employee employee = (Employee)user;
			inventory = new Tab("Inventory Management", InventoryManage.getNode(employee));
			
			orders = new Tab("Orders");
			createAccount = new Tab("Create Account", CreateAccountAdvanced.getNode(employee));
			tabbedPane.getTabs().add(inventory);
			tabbedPane.getTabs().add(orders);
			tabbedPane.getTabs().add(createAccount);
		}
		
		myAccount = new Tab("My Account", new MyAccount(user).getScene());
		tabbedPane.getTabs().add(myAccount);
		
		Scene scene = new Scene(tabbedPane, 800, 600);
		Main.getStage().setScene(scene);
	}
}
