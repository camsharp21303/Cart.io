package graphicalUI;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;
import types.Customer;
import types.Employee;
import types.User;

public class AppPane {
	private TabPane tabbedPane;
	private Tab home, inventory, orders, createAccount, cart, myAccount;
	
	public AppPane(User user, Stage stage) {
		tabbedPane = new TabPane();
		tabbedPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabbedPane.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
		
		if (user instanceof Customer) {
			home = new Tab("Home", new CatalogHome().getNode());
			cart = new Tab("Cart");
			tabbedPane.getTabs().add(home);
			tabbedPane.getTabs().add(cart);
		}
		else if(user instanceof Employee) {
			inventory = new Tab("Inventory Management", new InventoryManage((Employee)user, stage).getScene());
			
			orders = new Tab("Orders");
			createAccount = new Tab("Create Account", new CreateAccountAdvanced((Employee)user).getNode());
			tabbedPane.getTabs().add(inventory);
			tabbedPane.getTabs().add(orders);
			tabbedPane.getTabs().add(createAccount);
		}
		
		myAccount = new Tab("My Account", new MyAccount(user).getScene());
		tabbedPane.getTabs().add(myAccount);
		
		Scene scene = new Scene(tabbedPane, 800, 600);
		stage.setScene(scene);
	}
}
