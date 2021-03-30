package types;

public abstract class User {
	protected String number, username;
	protected String fname, lname, phone, email;
	
	protected User(String number, String fname, String lname, String username, String phone, String email){
		this.number = number;
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.phone = phone;
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public String getUsername() {
		return username;
	}
	
}
