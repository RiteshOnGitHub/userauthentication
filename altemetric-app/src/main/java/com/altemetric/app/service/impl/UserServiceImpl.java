package com.altemetric.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.altemetric.app.dto.UserDto;
import com.altemetric.app.entity.UserEntity;
import com.altemetric.app.repository.UserRepository;
import com.altemetric.app.responsemodel.UserResponse;
import com.altemetric.app.service.UserService;
import com.altemetric.app.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {
		
		//check for already existing user.
		if(userRepository.findUserByEmail(user.getEmail()) != null)
				throw new RuntimeException("user already exists");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		
		userEntity.setUserId(utils.generateUserId(25));
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		UserEntity savedUserDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(savedUserDetails, returnValue);
		
		return returnValue;
	}

	//provided by spring, it loads userdetails from DB.
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		
		if(userEntity == null){
			throw new UsernameNotFoundException(email);
		}
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		
		if(userEntity == null){
			throw new UsernameNotFoundException(email);
		}
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null)
			throw new UsernameNotFoundException(userId);
		
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null)
			throw new UsernameNotFoundException(userId);
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);	
		
		if(userEntity == null)
			throw new UsernameNotFoundException(userId);
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		
		//userRepository.findAll();
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = userPage.getContent();
		
		for(UserEntity userEntity : users){
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
			
		return returnValue;
	}

}
