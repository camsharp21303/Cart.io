package types;

public class Item {
	private String number, name, lastSold, price, stock;
	private byte[] image;
	
	public Item(String number, String name, String lastSold, String price, int stock, byte[] image) {
		this(name, lastSold, price, stock, image);
		this.number = number;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public Item(String name, String lastSold, String price, int stock, byte[] image) {
		this.name = name;
		this.lastSold = lastSold;
		this.price = price;
		this.stock = Integer.toString(stock);
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastSold() {
		return lastSold;
	}

	public void setLastSold(String lastSold) {
		this.lastSold = lastSold;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = Integer.toString(stock);
	}

	public String getNumber() {
		return number;
	}
}
