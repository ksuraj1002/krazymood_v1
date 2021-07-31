package com.krazymood.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.krazymood.app.entities.Legends;
import com.krazymood.app.entities.Users;
import com.krazymood.app.repository.*;
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
import com.krazymood.app.services.ContentService;

@Controller
@RequestMapping(value="/admin",method = RequestMethod.GET)
public class AdminHandler {
	
	@Autowired CategoryRepository categoryRepository;
	@Autowired SubCategoryRepository subcategoryRepository;
	@Autowired ContentsRepository loveShayriRepository;
	@Autowired LegendRepository legendRepository;
	
	@Autowired ContentService contentService;

	@Autowired
	UsersRepository usersRepository;
	
	
	@RequestMapping()
	public String getAdminDashboardBySlash() {
		return "redirect:/admin/dashboard";
	}
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	public String getAdminDashboard(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("lengendList", legendRepository.findAll());
		return "admin/index";
	}
	
	
	
	@PostMapping("/addcategory")
	@ResponseBody
	public Category saveContentof(@RequestBody Category category) {
		Set<SubCategory> set = category.getSubCategory();
		Set<SubCategory> subCategory = new HashSet<SubCategory>();
		
		for(SubCategory sub : set ) {
			sub.setCategory(category);
			subCategory.add(sub);
		}
		
		category.setSubCategory(subCategory);
		categoryRepository.save(category);
		return category;
	}

	@PostMapping("/addlegends")
	@ResponseBody
	public List<Legends> saveLegends(@RequestBody Legends legends) {
		List<Legends> legendsList =  new ArrayList<Legends>();
		Category category =  new Category();
		category.setId(legends.getCategoryId());

		for(String legenName : legends.getTranlegendName()){
			legendsList.add(new Legends(legends.getLanguage(),category,legenName));
		}
		return legendRepository.saveAll(legendsList);
	}
	 
	
	@ResponseBody
	@RequestMapping(value="/getsubcategories",method = RequestMethod.GET)
	public Set<SubCategory> getSubCategories(@RequestParam(name="category") Integer category) {                  
		Set<SubCategory> subCategory = subcategoryRepository.findByCategory_Id(category);
		return subCategory;
	}
	

	@PostMapping("/content")
	public ResponseEntity<Object> saveContent(@RequestParam(required = true, value = "fileimage") MultipartFile fileimage, @RequestParam(required = false, value = "jsondata") String jsondata) throws ParseException, IOException {
		 contentService.persistContent(fileimage,jsondata);
		 return new ResponseEntity<>("Data is uploaded successfully", HttpStatus.OK);
	}

	@PostMapping("/savelegend")
	public ResponseEntity<Object> savelegend(@RequestParam(required = true, value = "profilePic") MultipartFile fileimage, @RequestParam(required = false, value = "jsondata") String jsondata) throws ParseException, IOException, java.text.ParseException {
		 contentService.saveLegend(fileimage,jsondata);
		 return new ResponseEntity<>("Data is uploaded successfully", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value="/getLegendsByCategoryAndLan",method = RequestMethod.GET)
	public Set<Legends> getLegendsByCategoryAndLan(@RequestParam(name="category") Integer category,@RequestParam(name="subctgory") Integer subctgory,@RequestParam(name="ctgryOrSubCtgry") String ctgryOrSubCtgry , @RequestParam(name="language") String language) {                  
		Set<Legends> legends =null;
		if(subctgory==0) {
			legends = legendRepository.findByLanguageAndCategory_Id(language,category);
		}else {
			legends = legendRepository.findByLanguageAndSubCategory_Id(language,subctgory);
		}
		return legends;
	}

	@ResponseBody
	@RequestMapping(value="/getNotifications",method = RequestMethod.GET)
	public Set<Users> getNotifications() {
		Set<Users> users = usersRepository.findAllByIsWatched(false);
		return users;
	}
	
	
}
