package graphicalUI;

import javafx.scene.control.ScrollPane;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import application.Main;
import cartSQL.ItemsSQL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import types.Item;

public class CatalogHome {
	public static Node getNode() {
		ArrayList<Item> items = new ArrayList<>();
		items = new ItemsSQL().getAllItems();
		
		FlowPane node = new FlowPane();
		node.setVgap(10);
		node.setHgap(10);
		
		ScrollPane scroll = new ScrollPane(node);
		scroll.fitToWidthProperty().set(true);
		
		for(Item i : items) {
			node.getChildren().add(new ItemView(i));
		}
		
		return scroll;
	}
	
	private static class ItemView extends VBox{
		private Label name, price;
		private Button addCart;
		private ImageView image;
		private Item item;
		
		public ItemView(Item item){
			super();
			this.item = item;
			if(item.getImage() != null) {
				image = new ImageView(new Image(new ByteArrayInputStream(item.getImage())));
			}
			else {
				image = new ImageView(new Image(Main.class.getResourceAsStream("default.png")));
			}
			image.setFitHeight(175);
			image.setFitWidth(175);
			image.preserveRatioProperty().set(true);
			image.minHeight(175);
			image.minHeight(175);
			
			name = new Label(item.getName());
			price = new Label(item.getPrice());
			addCart = new Button("Add To Cart");
			addCart.setOnAction(e-> addCart());
			
			getChildren().addAll(image, name, price, addCart);
			setAlignment(Pos.TOP_CENTER);
			this.setId("catalogItem");
			this.setSpacing(5);
			this.setMinWidth(180);
		}
		
		private void addCart() {
			CartUI.addItem(item);
		}
	}
}
