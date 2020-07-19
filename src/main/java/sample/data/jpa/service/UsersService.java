package sample.data.jpa.service;

import sample.data.jpa.domain.Users;

import java.util.List;

public interface UsersService {
	
	Users getUsers(String userId);
	Users getUser(Long id);
	List<Users> getUsers();
	Users add(Users user);
	Users update(Users user);
	Users delete(Users id);
}