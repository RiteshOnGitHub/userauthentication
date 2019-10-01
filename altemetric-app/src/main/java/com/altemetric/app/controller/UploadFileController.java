package com.altemetric.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.altemetric.app.service.impl.FileServiceImpl;


@RestController
@RequestMapping("excel") 
public class UploadFileController {
	
	@Autowired
	FileServiceImpl fileServices;
	
	  @PostMapping("/upload")
	    public void uploadMultipartFile(@RequestParam("file") MultipartFile file) {
				fileServices.store(file);	
	        
	    }
}
