package by.murashko.sergey.entities;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Userfff {

	public Userfff() {
		// TODO Auto-generated constructor stub
	}

	public Userfff(String name) {
		super();
		this.name = name;
	}

	@Size(min = 4, message = "{name.size.error}")
	// Имя должно быть больше 5 знаков
	private String name;

	@Size(min = 4, max = 10, message = "{password.size.error}")
	private String password;

	private int isAdmin;
	private boolean guest;

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	@Size(min = 5, max = 20, message = "{mail.size.error}")
	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
