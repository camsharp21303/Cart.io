package graphicalUI;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import application.Main;
import cartSQL.OrdersSQL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public static Node getNode(Customer customer) {
		cart = new OrdersSQL().getCart(customer);
		
		vbox = new VBox();
		
		if(cart != null) {
			ArrayList<Item> items = cart.getItems();
			
			for(int i = 0; i < items.size(); i++) {
				vbox.getChildren().add(
						new ItemView(items.get(i)));
			}
		}
		else {
			new OrdersSQL().createCart(customer);
			cart = new OrdersSQL().getCart(customer);
		}
		
		
		return vbox;
	}
	
	public static void addItem(Item item) {
		cart.addItem(item);
		vbox.getChildren().add(new ItemView(item));
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
			price = new Label(item.getPrice());
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
