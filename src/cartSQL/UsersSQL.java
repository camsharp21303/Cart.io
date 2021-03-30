package cartSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import types.Customer;
import types.Employee;
import types.Employee.POSITION;
import types.User;

public class UsersSQL extends ParentSQL {

	@Override
	protected String getDataBase() {
		return "users";
	}
	
	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM CUSTOMERS");
			
			while(results.next()) {
				customers.add(new Customer(
						results.getString("number"),
						results.getString("fname"), 
						results.getString("lname"),
						results.getString("username"),
						results.getString("phone"),
						results.getString("email"),
						results.getString("address")));
				System.out.println(customers.get(customers.size()-1).getFname());
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return customers;
	}
	
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM EMPLOYEES");
			
			while(results.next()) {
				Employee.POSITION position = POSITION.STANDARD;
				String level = results.getString("level");
				if(level == "board") position = POSITION.BOARD;
				else if(level == "manager") position = POSITION.MANAGER;
				employees.add(new Employee(
						results.getString("number"),
						results.getString("fname"), 
						results.getString("lname"),
						results.getString("username"),
						results.getString("phone"),
						results.getString("email"),
						position));
				System.out.println(employees.get(employees.size()-1).getFname());
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return employees;
	}
	
	public User login(String loginID, String password) {
		String column = "username";
		if(loginID.contains("@") && loginID.contains(".")) column = "email";
		if(loginID.matches("[0-9]+") && loginID.length() == 10) column = "phone";
		
		ResultSet customerResult = readQuery("SELECT * FROM customers WHERE " + column + "='" + loginID + "' AND password='" + password + "';");
		ResultSet employeeResult = readQuery("SELECT * FROM employees WHERE " + column + "='" + loginID + "' AND password='" + password + "';");
		
		try {
			if(customerResult.next()) {
				System.out.println("login success");
				return new Customer(
						customerResult.getString("number"),
						customerResult.getString("fname"), 
						customerResult.getString("lname"),
						customerResult.getString("username"),
						customerResult.getString("phone"),
						customerResult.getString("email"),
						customerResult.getString("address"));
			}
			else if(employeeResult.next()) {
				System.out.println("login success");
				Employee.POSITION position = POSITION.STANDARD;
				String level = employeeResult.getString("level");
				if(level == "board") position = POSITION.BOARD;
				else if(level == "manager") position = POSITION.MANAGER;
				return new Employee(
						employeeResult.getString("number"),
						employeeResult.getString("fname"), 
						employeeResult.getString("lname"),
						employeeResult.getString("username"),
						employeeResult.getString("phone"),
						employeeResult.getString("email"),
						position);
			}
		} catch (SQLException e) {
			error(e);
		}
		
		return null;
	}
	
	public boolean insertUser(User user, String password) {
		String query = "";
		if(user.getClass() == Employee.class) {
			query = String.format(
					"INSERT INTO employees VALUES(nextval('nextemployee'), '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
					user.getFname(), user.getLname(), user.getUsername(), user.getPhone(), user.getEmail(),
					password, ((Employee)user).getPosition().toString().toLowerCase());
		}
		else {
			query = String.format(
					"INSERT INTO customers VALUES(nextval('nextemployee'), '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
					user.getFname(), user.getLname(), user.getUsername(), user.getPhone(), user.getEmail(),
					password, ((Customer)user).getAddress());
		}
		
		return InsertQuery(query);
	}

}
