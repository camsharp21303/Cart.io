package types;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import cartSQL.ParentSQL;
import cartSQL.SQLcommands;

public class Order extends ParentSQL {
	public enum ORDER_STAT {
		Delivered, Shipped, Processing, Canceled
	}

	private ArrayList<Item> items;
	private String id, customerID, price, address;
	private ORDER_STAT status;

	public Order(String id, String customerID, String price, String address, ORDER_STAT status, String[] items) {
		this.id = id;
		this.customerID = customerID;
		this.price = price;
		this.address = address;
		this.items = new ArrayList<>();
		try {
			if (items != null) {
				for (int i = 0; i < items.length; i++) {
					this.items.add(SQLcommands.getItem(items[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
		this.status = status;
	}

	public Order(String customerID, String price, String address, ORDER_STAT status, ArrayList<Item> items) {
		this(null, customerID, price, address, status, null);
		this.items = items;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ORDER_STAT getStatus() {
		return status;
	}

	public void setStatus(ORDER_STAT status) {
		this.status = status;
		InsertQuery(String.format("UPDATE orders SET status='%s' WHERE id='%s';", status.toString(), this.getId()));
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public String getId() {
		return id;
	}

	public String getCustomerID() {
		return customerID;
	}

	public static ArrayList<String> getStatusOptions() {
		ArrayList<String> options = new ArrayList<String>();

		for (ORDER_STAT o : ORDER_STAT.values()) {
			options.add(o.toString());
		}

		return options;
	}

	public void commit() {
		try {
			connect();
			PreparedStatement ps = c.prepareStatement(
					String.format("INSERT INTO orders VALUES(nextval('orderid'), '%s', '%s', ?, %s, '%s');",
							this.customerID, Order.ORDER_STAT.Processing.toString(), price, address));
			String[] ids = new String[items.size()];
			for (int i = 0; i < items.size(); i++) {
				ids[i] = items.get(i).getNumber();
			}

			ps.setArray(1, c.createArrayOf("text", ids));
			ps.executeUpdate();
			ps.close();
			c.close();
		} catch (Exception e) {
			error(e);
		}
	}

	@Override
	protected String getDatabase() {
		// TODO Auto-generated method stub
		return "collections";
	}
}
