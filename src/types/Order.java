package types;

public class Order {
	enum ORDER_STAT{
		DELIVERED, PROCESSING, SHIPPED, CANCELLED, RETURNED
	}
	
	private String[] items;
	private String id, customerID, price, address;
	
	public Order(String id, String customerID, String price, String address, String[] items) {
		this.id = id;
		this.customerID = customerID;
		this.price = price;
		this.address = address;
		this.items = items;
	}
	
	public Order(String customerID, String price, String address, String[] items) {
		this(null, customerID, price, address, items);
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
	}
	public String[] getItems() {
		return items;
	}
	public String getId() {
		return id;
	}
	public String getCustomerID() {
		return customerID;
	}
	private ORDER_STAT status;
	
}
