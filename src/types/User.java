package types;

import java.io.File;

import cartSQL.ParentSQL;

public abstract class User extends ParentSQL {
	protected String number, username;
	protected String fname, lname, phone, email;
	private byte[] image;

	protected User(String number, String fname, String lname, String username, String phone, String email,
			byte[] image) {
		this.number = number;
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.image = image;
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

	public byte[] getImage() {
		return image;
	}

	public boolean setImage(File image) {
		if (this instanceof Customer) {
			return updateImage("customers", this.getNumber(), image);
		} else if (this instanceof Employee) {
			return updateImage("employees", this.getNumber(), image);
		}
		return false;
	}

	public boolean commit(String password, File imageFile) {
		String query = "";
		if (this instanceof Employee) {
			query = String.format(
					"INSERT INTO employees VALUES(nextval('nextemployee'), '%s', '%s', '%s', '%s', '%s', '%s', '%s', ?);",
					getFname(), getLname(), getUsername(), getPhone(), getEmail(), password,
					((Employee) this).getPosition().toString().toLowerCase());
		} else {
			query = String.format(
					"INSERT INTO customers VALUES(nextval('nextemployee'), '%s', '%s', '%s', '%s', '%s', '%s', '%s', ?);",
					getFname(), getLname(), getUsername(), getPhone(), getEmail(), password,
					((Customer) this).getAddress());
		}

		return InsertQueryImage(query, imageFile);
	}

	@Override
	protected String getDatabase() {
		return "users";
	}
}
