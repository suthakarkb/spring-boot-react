package sample.data.jpa.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import sample.data.jpa.domain.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component("usersService")
@Transactional
class UsersServiceImpl implements UsersService {
	
	private final UsersRepository usersRepository;	
	private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}	
	
	@Override
	public Users getUsers(String userId) {
		log.info("Inside getUser call");
		Assert.notNull(userId, "Users Id must not be null");
		return this.usersRepository.findByUserIdAllIgnoringCase(userId);
	}
	
	@Override
	public Users getUser(Long id) {
		log.info("Inside getUser call");
		Assert.notNull(id, "id must not be null");
		//log.info(this.usersRepository.getOne(id));
		return this.usersRepository.getOne(id);
	}	
	
	@Override
	public List<Users> getUsers() {
		log.info("Inside getUsers call");
		return this.usersRepository.findAll();
	}

	@Override
	public Users add(Users user) {
		log.info("Inside add user call:"+user.toString());
		Users user1 = this.usersRepository.save(user);
		return user1;
	}
	
	@Override
	public Users update(Users user) {
		log.info("Update existing user call:"+user.toString());
		Users user1 = this.usersRepository.save(user);
		return user1;
	}
	
	@Override
	public Users delete(Users user) {
		log.info("delete user call:"+user.toString());
		Users user1 = this.usersRepository.delete(user);
		return user1;
	}	
}
