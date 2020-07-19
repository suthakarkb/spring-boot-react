/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.jpa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sample.data.jpa.service.UsersService;
import sample.data.jpa.domain.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class SampleController {
	private final Logger log = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private UsersService usersService;
	
	@RequestMapping(path="/welcome", method=RequestMethod.GET)
	public String helloWorld() {
		return "Welcome to springboot jpa services";
	} 	
	
	@RequestMapping(value="/userByUserId/{id}", method=RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public Users getUserByUserId(@PathVariable("id") String id) throws Exception{
		log.info("Fetching User with Login userid {}",id);
		Users users = usersService.getUsers(id);
		if(users != null) {
			return users; }
		else {
			return new Users("User Not Found","Role Not Found");
		}
	}	
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public Users getUser(@PathVariable("id") Long id) throws Exception{
		log.info("Fetching User with id {}",id);
		Users users = usersService.getUser(id);
		if(users != null) {
			return users; }
		else {
			return null;
		}
	}	
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Users> getUsers() throws Exception{
		List<Users> users = usersService.getUsers();
		if(users != null) {
			return users; }
		else {
			return null;
		}
	}	
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public Users addUser(@RequestBody Users user) {
		log.info("Add a new user : ");
		try {
			user = usersService.add(user);
			log.info("user:"+user.toString());
		} catch (Exception exception) {
			log.info("Error while Insertion : " + exception.getLocalizedMessage());
		}
		return user;
	}	
	
	@RequestMapping(value = "/user/update", method = RequestMethod.PUT)
	public Users updateUser(@RequestBody Users user) {
		log.info("Update existing user : ");
		try {
			user = usersService.update(user);
			log.info("user:"+user.toString());
		} catch (Exception exception) {
			log.info("Error while Update : " + exception.getLocalizedMessage());
		}
		return user;
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
	public Users deleteUser(@RequestBody Users user) {
		log.info("Delete user : "+user);
		try {
			Users user1 = usersService.delete(user);
			log.info("user deleted successfully");
			return user;
		} catch (Exception exception) {
			//exception.printStackTrace();
			log.info("Error while Deletion : " + exception.getLocalizedMessage());
		}
		return user;
	}		

}
