package types;

public class Item {
	private String number, name;
	private float price;
	private int stock;
	private byte[] image;
	
	public Item(String number, String name, float price, int stock, byte[] image) {
		this(name, price, stock, image);
		this.number = number;
	}
	
	public Item(String name, float price, int stock, byte[] image) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}
	
	public void setPriceString(String price) {
		this.price = Float.parseFloat(price);
	}

	public String getPriceString() {
		return Float.toString(this.price);
	}
	
	public void setStockString(String stock) {
		this.stock = Integer.parseInt(stock);
	}
	
	public String getStockString() {
		return Integer.toString(stock);
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNumber() {
		return number;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
}
