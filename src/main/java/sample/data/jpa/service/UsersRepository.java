package sample.data.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sample.data.jpa.domain.Users;

import java.util.List;

interface UsersRepository extends Repository<Users, Long> {
	
	Page<Users> findAll(Pageable pageable);
	Page<Users> findByNameAllIgnoringCase(String name, Pageable pageable);
	Users findByUserIdAllIgnoringCase(String userId);
	Users getOne(Long id);
	List<Users> findAll();
	Users save(Users user);
	Users delete(Users user);

}
