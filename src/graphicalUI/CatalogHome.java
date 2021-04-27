package graphicalUI;

import java.io.ByteArrayInputStream;

import application.Main;
import cartSQL.SQLcommands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import types.Item;

public class CatalogHome {
	private static TextField searchTF;
	private static ObservableList<Item> items;
	private static ObservableList<Node> original;
	private static FlowPane node;
	private static HBox search;
	private static Button clear;

	public static Node getNode() {
		items = SQLcommands.getAllItems();

		node = new FlowPane();
		node.setVgap(10);
		node.setHgap(10);

		ScrollPane scroll = new ScrollPane(node);
		scroll.fitToWidthProperty().set(true);

		original = FXCollections.observableArrayList();
		for (Item i : items) {
			original.add(new ItemView(i));
		}
		node.getChildren().addAll(original);

		searchTF = new TextField();
		searchTF.setPromptText("Item ID/name");

		clear = new Button("Clear");
		clear.setOnAction(e -> {
			search.getChildren().remove(clear);
			node.getChildren().clear();
			node.getChildren().addAll(original);
		});

		Button searchB = new Button("Search");
		searchB.setOnAction(e -> search());

		search = new HBox(searchTF, searchB);

		return new VBox(search, scroll);
	}

	private static void search() {
		String query = searchTF.getText().toUpperCase();
		node.getChildren().clear();

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().toUpperCase().contains(query)) {
				node.getChildren().add(new ItemView(items.get(i)));
			}
		}
		for (int i = 0; i < items.size(); i++) {

			if (items.get(i).getNumber().toUpperCase().contains(query)) {
				node.getChildren().add(new ItemView(items.get(i)));
			}
		}

		search.getChildren().add(clear);
	}

	private static class ItemView extends VBox {
		private Label name, price;
		private Button addCart;
		private ImageView image;
		private Item item;
		private TextField quantity;

		public ItemView(Item item) {
			super();
			this.item = item;
			if (item.getImage() != null) {
				image = new ImageView(new Image(new ByteArrayInputStream(item.getImage())));
			} else {
				image = new ImageView(new Image(Main.class.getResourceAsStream("default.png")));
			}
			image.setFitHeight(175);
			image.setFitWidth(175);
			image.preserveRatioProperty().set(true);
			image.minHeight(175);
			image.minHeight(175);

			name = new Label(item.getName());
			price = new Label("$" + item.getPrice());
			addCart = new Button("Add To Cart");
			addCart.setOnAction(e -> addCart());
			quantity = new TextField("1");
			quantity.setMaxWidth(40);

			HBox quantityBox = new HBox(new Label("Quantity: "), quantity);
			quantityBox.setAlignment(Pos.CENTER);
			quantityBox.setSpacing(10);

			getChildren().addAll(image, name, price, quantityBox, addCart);
			setAlignment(Pos.TOP_CENTER);
			this.setId("catalogItem");
			this.setSpacing(5);
			this.setMinWidth(180);
		}

		private void addCart() {
			CartUI.addItem(item, Integer.parseInt(quantity.getText()));
		}

	}
}
