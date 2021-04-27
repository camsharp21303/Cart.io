package types;

public class Employee extends User {
	public enum POSITION {
		STANDARD, MANAGER, BOARD
	};

	private POSITION position;

	public Employee(String number, String fname, String lname, String username, String phone, String email,
			POSITION position, byte[] image) {
		super(number, fname, lname, username, phone, email, image);
		this.position = position;
	}

	public Employee(String fname, String lname, String username, String phone, String email, POSITION position,
			byte[] image) {
		this("", fname, lname, username, phone, email, position, image);
	}

	public Employee.POSITION getPosition() {
		return position;
	}

	public void setPosition(Employee.POSITION position) {
		this.position = position;
	}

}
