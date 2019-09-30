package com.altemetric.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altemetric.app.dto.UserDto;
import com.altemetric.app.requestmodel.UserDetailsRequestModel;
import com.altemetric.app.responsemodel.OperationStatusModel;
import com.altemetric.app.responsemodel.RequestOperationName;
import com.altemetric.app.responsemodel.RequestOperationStatus;
import com.altemetric.app.responsemodel.UserResponse;
import com.altemetric.app.service.UserService;

@RestController
@RequestMapping("users") //http://localhost:8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}" , produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponse getUser(@PathVariable String id){
		
		UserResponse returnValue = new UserResponse();
		
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}
	
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserResponse> getUsers(@RequestParam(value="page", defaultValue="0") int page,
			                 @RequestParam(value="limit", defaultValue="25") int limit){
		List<UserResponse> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page,limit);
		
		for(UserDto userDto : users){
			UserResponse userModel = new UserResponse();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}
	
	/*@PostMapping
	public String createUser(){
		return "this is create user";
	}*/
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails){
		
		UserResponse returnValue = new UserResponse();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}" ,consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails){
		UserResponse returnValue = new UserResponse();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		
		return returnValue;
	}
	
	
	@DeleteMapping(path="/{id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String id){
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

}
