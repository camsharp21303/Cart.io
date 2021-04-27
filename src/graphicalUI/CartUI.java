package graphicalUI;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import cartSQL.SQLcommands;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import types.Cart;
import types.Customer;
import types.Item;
import types.Order;
import types.Order.ORDER_STAT;

public class CartUI {
	private static VBox vbox;
	private static Cart cart;
	private static Customer customer;
	private static ArrayList<Item> items;
	private static Button purchase;

	public static Node getNode(Customer customer) {
		CartUI.customer = customer;
		cart = SQLcommands.getCart(customer);

		vbox = new VBox();
		vbox.setSpacing(15);

		if (cart != null) {
			items = cart.getItems();

			for (int i = 0; i < items.size(); i++) {
				if (items.get(i) != null) {
					boolean itemExist = false;
					for (Node it : vbox.getChildren()) {
						if (((ItemView) it).getItem().getNumber().equals(items.get(i).getNumber())) {
							((ItemView) it).upQuantity();
							itemExist = true;
						}
					}
					if (!itemExist)
						vbox.getChildren().add(new ItemView(items.get(i), 1));
				} else {
					cart.removeItem(items.get(i));
				}
			}
		} else {
			cart = new Cart(customer);
		}
		purchase = new Button("Purchase");
		purchase.setOnAction(e -> purchase());

		vbox.getChildren().add(purchase);

		return vbox;
	}

	private static void purchase() {
		if (cart.getItems().isEmpty())
			return;

		TextInputDialog dialog = new TextInputDialog(customer.getAddress());
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Confirm Your Address");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			double total = 0;
			for (Item i : cart.getItems()) {
				total += i.getPrice();
			}
			
			String price = Double.toString(((double) (int) (total * 100)) / 100);
			
			new Order(customer.getNumber(), price, result.get(), ORDER_STAT.Processing, cart.getItems()).commit();
			vbox.getChildren().clear();
			vbox.getChildren().add(purchase);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Order Recieved");
			alert.setContentText(String.format("Your order for %s items ($%s) was completed successfully.", cart.getItems().size(), price));
			alert.showAndWait();
			
			cart.clearItems();
		}
	}

	public static void addItem(Item item, int quantity) {
		for(int i = 0; i < quantity; i++) {
			cart.addItem(item);
		}
		
		boolean itemExist = false;
		for (Node i : vbox.getChildren()) {
			if (i instanceof ItemView) {
				if (((ItemView) i).getItem().getNumber().equals(item.getNumber())) {
					((ItemView) i).upQunatity(quantity);
					itemExist = true;
				}
			}
		}
		if (!itemExist)
			vbox.getChildren().add(0, new ItemView(item, quantity));
	}

	private static class ItemView extends BorderPane {
		private Item item;
		private TextField quantity;
		private int quant;

		public ItemView(Item item, int number) {
			super();
			this.item = item;
			this.setPrefWidth(800);
			quant = number;

			Label name, price;
			Button delete, update;
			ImageView image;

			if (item.getImage() != null) {
				image = new ImageView(new Image(new ByteArrayInputStream(item.getImage())));
			} else {
				image = new ImageView(new Image(Main.class.getResourceAsStream("default.png")));
			}

			image.setFitHeight(125);
			image.setFitWidth(125);
			image.preserveRatioProperty().set(true);
			;

			name = new Label(item.getName());
			price = new Label("$" + item.getPrice());

			HBox rightBox = new HBox();
			rightBox.setSpacing(10);
			rightBox.setAlignment(Pos.CENTER);

			delete = new Button("X");
			delete.setOnAction(e -> deleteItem());

			update = new Button("^");
			update.setOnAction(e -> updateQuantity());

			quantity = new TextField(Integer.toString(quant));

			rightBox.getChildren().addAll(new Label("Quantity: "), quantity, update, delete);

			setAlignment(rightBox, Pos.CENTER);
			setMargin(rightBox, new Insets(15, 15, 15, 15));

			setRight(rightBox);

			HBox itemBox = new HBox(image, name, price);
			itemBox.setAlignment(Pos.CENTER);
			itemBox.setSpacing(20);
			setLeft(itemBox);
		}

		private void deleteItem() {
			cart.removeItem(item);
			vbox.getChildren().remove(this);
		}

		private void updateQuantity() {
			int diff = Integer.parseInt(quantity.getText()) - quant;
			if (diff < 0) {
				for (int i = 0; i > diff; i--) {
					cart.removeItem(item, -diff);
				}
			} else {
				for (int i = 0; i < diff; i++) {
					cart.addItem(item);
				}
			}
			quant = Integer.parseInt(quantity.getText());
		}

		public Item getItem() {
			return item;
		}

		public void upQunatity(int up) {
			quantity.setText(Integer.toString(Integer.parseInt(quantity.getText()) + up));
			updateQuantity();
		}

		public void upQuantity() {
			quant = Integer.parseInt(quantity.getText()) + 1;
			quantity.setText(Integer.toString(quant));

		}
	}
}
