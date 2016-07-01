package by.murashko.sergey.dao.interfaces;

import java.util.List;

import by.murashko.sergey.entities.*;

public interface UserDAO {

	public void addUser(Users user);

	public void removeUser(int id);

	public List<Users> getAllUsers();
	public boolean acceptUser(Users user);
	
	public Users getUserFromDbByName(String name);
}