package com.krazymood.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krazymood.app.entities.Contents;
import com.krazymood.app.services.ContentService;

@RestController
public class RestControllerRequestHandler {
	
	@Autowired ContentService contentService;
	
	@GetMapping("getAllContents")
	public List<Contents> getAllContents(){
		List<Contents> listOfContents = contentService.findAllContents();
		return listOfContents;
	}
}
