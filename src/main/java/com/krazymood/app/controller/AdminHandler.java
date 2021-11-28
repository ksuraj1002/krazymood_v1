package com.krazymood.app.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.krazymood.app.entities.Users;
import com.krazymood.app.services.AdminService;
import com.krazymood.app.services.UtilityService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;

@Controller
@RequestMapping(value="/admin",method = RequestMethod.GET)
public class AdminHandler {

	@Autowired AdminService adminService;
	@Autowired UtilityService utilityService;
	
	@RequestMapping()
	public String getAdminDashboardBySlash() {
		return "redirect:/admin/dashboard";
	}
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	public String getAdminDashboard(Model model) {
		model.addAttribute("categories", utilityService.getCategoryBean());
		return "/admin/index";
	}

	@PostMapping("/addcategory")
	@ResponseBody
	public Category addCategoryAndSubcategory(@RequestBody Category category) {
		Set<SubCategory> set = category.getSubCategory();
		Set<SubCategory> subCategory = new HashSet<SubCategory>();
		for(SubCategory sub : set ) {
			sub.setCategory(category);
			subCategory.add(sub);
		}
		category.setSubCategory(subCategory);
		adminService.addCategoryAndSubcategory(category);
		return category;
	}

	
	@ResponseBody
	@RequestMapping(value="/getsubcategories",method = RequestMethod.GET)
	public List<SubCategory> getSubCategories(@RequestParam(name="category") Integer category) {
		List<SubCategory> subCategory=adminService.findByCategory_Id(category,utilityService.getCategoryBean());
		return subCategory;
	}
	

	@PostMapping("/content")
	public ResponseEntity<Object> saveContent(@RequestParam(required = true, value = "fileimage") MultipartFile fileimage, @RequestParam(required = false, value = "jsondata") String jsondata) throws ParseException, IOException {
		 adminService.saveContent(fileimage,jsondata);
		 return new ResponseEntity<>("Data is uploaded successfully", HttpStatus.OK);
	}


	@ResponseBody
	@RequestMapping(value="/getNotifications",method = RequestMethod.GET)
	public List<Users> getNotifications() {
		List<Users> users = adminService.findAllByIsWatched(false);
		return users;
	}
	
	
}
