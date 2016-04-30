package myapp.model;

public class User {
	private String	email;
	private String	passwd;
	
	User() {
		this(null, null);
	}
	
	User(String email, String passwd) {
		this.email = email;
		this.passwd = passwd;
	}
	
	public String getBody() {
		return "email:" + this.email + ", password:" + this.passwd;
	}
}
