package types;

public class Customer extends User{
	private String address;

	public Customer(String number, String fname, String lname, String username, String phone, String email, String address, byte[] image) {
		super(number, fname, lname, username, phone, email, image);
		this.address = address;
	}
	public Customer(String fname, String lname, String username, String phone, String email, String address, byte[] image) {
		super("0", fname, lname, username, phone, email, image);
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
