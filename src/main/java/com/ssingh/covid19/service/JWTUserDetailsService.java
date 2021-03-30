package com.ssingh.covid19.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssingh.covid19.dto.UserDTO;
import com.ssingh.covid19.entity.UserBO;
import com.ssingh.covid19.repository.UserRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Optional<UserBO> userBOOp = userRepository.findByUsername(username);
		if (!userBOOp.isPresent()) {
			throw new UsernameNotFoundException("No user found with username: "
					+ username);
		}
		UserBO userBO = userBOOp.get();
		return new User(userBO.getUsername(), userBO.getPassword(),
				new ArrayList<>());

	}

	public UserDTO addNewUser(UserDTO user) {
		UserBO userBO = new UserBO();
		userBO.setUsername(user.getUsername());
		userBO.setPassword(user.getPassword());

		userBO = userRepository.save(userBO);
		user = new UserDTO(userBO.getUsername(),null);
		return user;
	}
}
