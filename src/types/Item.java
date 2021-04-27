package types;

import java.io.File;

import cartSQL.ParentSQL;

public class Item extends ParentSQL {
	private String number, name;
	private double price;
	private int stock;
	private byte[] image;

	@Override
	protected String getDatabase() {
		return "inventory";
	}

	public Item(String number, String name, double price, int stock, byte[] image) {
		this(name, price, stock);
		this.image = image;
		this.number = number;
	}

	public Item(String name, double price, int stock) {
		this.name = name;
		this.price = ((double) (int) (price * 100)) / 100;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPriceString(String price) {
		this.price = Float.parseFloat(price);
		InsertQuery(String.format("UPDATE items SET %s='%s' WHERE id='%s';", "price", price, this.getNumber()));
	}

	public String getPriceString() {
		return Double.toString(this.price);
	}

	public void setStockString(String stock) {
		this.stock = Integer.parseInt(stock);
		InsertQuery(String.format("UPDATE items SET %s='%s' WHERE id='%s';", "stock", stock, this.getNumber()));
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

	public void deleteItem() {
		InsertQuery("DELETE FROM items WHERE id='" + this.getNumber() + "';");
	}

	public void commit(File file) {
		String sql = String.format("INSERT INTO items VALUES(nextval('nextitemid'), '%s', '%s', ?, '%s');",
				this.getName(), this.getPrice(), this.getStock());
		InsertQueryImage(sql, file);
	}
}
