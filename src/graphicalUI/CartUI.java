package graphicalUI;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import cartSQL.OrdersSQL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import types.Cart;
import types.Customer;
import types.Item;

public class CartUI {
	private static VBox vbox;
	private static Cart cart;
	private static Customer customer;
	private static ArrayList<Item> items;
	
	public static Node getNode(Customer customer) {
		CartUI.customer = customer;
		cart = OrdersSQL.getCart(customer);
		
		vbox = new VBox();
		
		if(cart != null) {
			items = cart.getItems();
			
			for(int i = 0; i < items.size(); i++) {
				if(items.get(i) != null) {
					vbox.getChildren().add(
						new ItemView(items.get(i)));
				}
				else {
					cart.removeItem(items.get(i));
				}
			}
		}
		else {
			OrdersSQL.createCart(customer);
			cart = OrdersSQL.getCart(customer);
		}
		Button purchase = new Button("Purchase");
		purchase.setOnAction(e -> purchase());
		
		vbox.getChildren().add(purchase);
		
		return vbox;
	}
	
	private static void purchase() {
		if(cart.getItems().isEmpty()) return;
		
		TextInputDialog dialog = new TextInputDialog(customer.getAddress());
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Confirm Your Address");
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			OrdersSQL.addOrder(customer, cart.getItems(), result.get());
		}
	}
	
	public static void addItem(Item item) {
		cart.addItem(item);
		vbox.getChildren().add(0, new ItemView(item));
	}
	
	private static class ItemView extends HBox{
		private Item item;
		
		public ItemView(Item item) {
			super();
			this.item = item;
			
			Label name, price;
			Button delete;
			ImageView image;
			
			if(item.getImage() != null) {
				image = new ImageView(new Image(
						new ByteArrayInputStream(item.getImage())));
			}
			else {
				image = new ImageView(new Image(
						Main.class.getResourceAsStream("default.png")));
			}
			
			image.setFitHeight(125);
			image.setFitWidth(125);
			image.preserveRatioProperty().set(true);;
			
			name = new Label(item.getName());
			price = new Label("$"+item.getPrice());
			delete = new Button("X");
			delete.setOnAction(e -> deleteItem());
			
			getChildren().add(image);
			getChildren().add(name);
			getChildren().add(price);
			getChildren().add(delete);
			
			setAlignment(Pos.CENTER_LEFT);
		}
		
		private void deleteItem() {
			cart.removeItem(item);
			vbox.getChildren().remove(this);
		}
	}
}
