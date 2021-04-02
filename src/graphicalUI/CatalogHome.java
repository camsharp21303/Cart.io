package graphicalUI;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import application.Main;
import cartSQL.ItemsSQL;
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
	private FlowPane node;
	
	public CatalogHome() {
		ArrayList<Item> items = new ArrayList<>();
		items = new ItemsSQL().getAllItems();
		
		node = new FlowPane();
		
		for(Item i : items) {
			node.getChildren().add(new ItemView(i));
		}
	}
	
	public Node getNode() {
		return node;
	}
	
	private class ItemView extends VBox{
		private Label name, price;
		private Button addCart;
		private ImageView image;
		
		public ItemView(Item item){
			super();
			if(item.getImage() != null) {
				image = new ImageView(new Image(new ByteArrayInputStream(item.getImage())));
			}
			else {
				image = new ImageView(new Image(Main.class.getResourceAsStream("default.png")));
			}
			image.setFitHeight(175);
			image.setFitWidth(175);
			image.preserveRatioProperty().set(true);
			
			name = new Label(item.getName());
			price = new Label(item.getPrice());
			addCart = new Button("Add To Cart");
			
			getChildren().add(image);
			getChildren().add(name);
			getChildren().add(price);
			getChildren().add(addCart);
			setAlignment(Pos.TOP_CENTER);
		}
	}
}
