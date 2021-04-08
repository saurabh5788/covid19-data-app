package com.ssingh.covid19.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssingh.covid19.dto.UserDTO;
import com.ssingh.covid19.entity.UserBO;
import com.ssingh.covid19.exception.UserDetailsServiceException;
import com.ssingh.covid19.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Optional<UserDTO> userDTOOp = fetchUserByUsername(username);
		if (!userDTOOp.isPresent()) {
			throw new UsernameNotFoundException("No Username found : "
					+ username);
		}
		UserDTO userDTO = userDTOOp.get();
		return new User(userDTO.getUsername(), userDTO.getPassword(),
				new ArrayList<>());

	}
	
	public Optional<UserDTO> fetchUserByUsername(String username){
		Optional<UserBO> userBOOp = userRepository.findByUsername(username);		
		UserBO userBO = userBOOp.get();		
		UserDTO userDto = new UserDTO();
		try {
			BeanUtils.copyProperties(userDto, userBO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new UserDetailsServiceException(e);
		}
		Optional<UserDTO> userDtoOp = Optional.ofNullable(userDto);		
		return userDtoOp;
	}

	public UserDTO addNewUser(UserDTO user) {
		UserBO userBO = new UserBO();
		userBO.setUsername(user.getUsername());
		userBO.setPassword(passwordEncoder.encode(user.getPassword()));
		userBO.setName(user.getName());

		userBO = userRepository.save(userBO);

		user = new UserDTO(userBO.getUsername(), userBO.getName());
		return user;
	}
}
