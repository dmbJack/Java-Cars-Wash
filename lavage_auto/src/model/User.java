package model;

public class User {
	public int id;
	public String userName;
	public String fullName;
	public String password;
	public boolean isAdmin=false;
	public User(int id, String userName, String fullName, boolean isAdmin, String password) {
		this.id = id;
		this.userName = userName;
		this.fullName= fullName;
		this.isAdmin=isAdmin;
		this.password = password;
	}
}
