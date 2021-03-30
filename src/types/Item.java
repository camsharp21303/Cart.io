package types;

import javafx.beans.property.SimpleStringProperty;

public class Item {
	private SimpleStringProperty number, name, lastSold, price, stock;
	
	public Item(String number, String name, String lastSold, String price, int stock) {
		this.number = new SimpleStringProperty(number);
		this.name = new SimpleStringProperty(name);
		this.lastSold = new SimpleStringProperty(lastSold);
		this.price = new SimpleStringProperty(price);
		this.stock = new SimpleStringProperty(Integer.toString(stock));
	}
	
	public Item(String name, String lastSold, String price, int stock) {
		this.name = new SimpleStringProperty(name);
		this.lastSold = new SimpleStringProperty(lastSold);
		this.price = new SimpleStringProperty(price);
		this.stock = new SimpleStringProperty(Integer.toString(stock));
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getLastSold() {
		return lastSold.get();
	}

	public void setLastSold(String lastSold) {
		this.lastSold.set(lastSold);
	}

	public String getPrice() {
		return price.get();
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public String getStock() {
		return stock.get();
	}

	public void setStock(int stock) {
		this.stock.set(Integer.toString(stock));
	}

	public String getNumber() {
		return number.get();
	}
}
