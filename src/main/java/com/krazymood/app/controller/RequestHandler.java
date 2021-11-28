package com.krazymood.app.controller;

import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Users;
import com.krazymood.app.repository.GalleryRepository;
import com.krazymood.app.services.ContentService;
import com.krazymood.app.services.FirebaseService;
import com.krazymood.app.services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.List;

@Controller
public class RequestHandler {

	@Autowired GalleryRepository galleryRepository;
	@Autowired ContentService contentService;
	@Autowired UtilityService utilityService;
	@Autowired FirebaseService firebaseService;

	//hindi by default
	@RequestMapping(value = {"","/","index","home"}, method = RequestMethod.GET)
	public String getHome(Model model) {
		List<Contents> listOfContents = contentService.findAllContents();
		model.addAttribute("contentList", listOfContents);
		model.addAttribute("content", listOfContents.get(0));
		model.addAttribute("mostViewedList", contentService.findMostViewedContents(listOfContents));
		model.addAttribute("specificContentList", contentService.findSpecificContents(listOfContents));
		return "index";
	}

	@GetMapping(value = "/storage/public_images/{images}")
	public void fetchFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = request.getRequestURI();
		filename = filename.substring(9);
		byte[] bytes = firebaseService.fetchFile(filename);
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		response.setContentType(mimeType);
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(bytes);
		outputStream.flush();
		outputStream.close();
	}

	@ResponseBody
	@RequestMapping(value="/search/content", method = RequestMethod.GET)
	public List<Contents> searchContent(@RequestParam("con") String trnslateVal){
		List<Contents> contents = contentService.findContentBySearchStrings(trnslateVal);
		return contents;
	}

	/*@PostMapping(value = "/uploadImg")
	public ResponseEntity uploadImage(MultipartFile imageFile) {
		try {
			ImageGallery g = new ImageGallery(firebaseService.uploadFile(imageFile));
			galleryRepository.save(g);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image uploaded successfully.");
		} catch (Exception e) {
			e.printStackTrace(); // see note 2
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message :" + e.getMessage());
		}
	}*/



	@RequestMapping(value = "/sitemap.xml", produces = {"application/xml", "text/xml"})
	public String sitemap(Model m) {
		List<Contents> contentsList = contentService.findAllContents();
		m.addAttribute("urls",contentsList);
		return "sitemap.xml";
	}

	@RequestMapping(value="/send-to-krazyshayari", method = RequestMethod.POST)
	public String sendToKrazy(Users users,RedirectAttributes redirectAttributes){
		try{
			contentService.saveUsersPost(users);
			redirectAttributes.addFlashAttribute("readmsg", "Content has been saved successfully. It will be shown on site once it get approved.");
			redirectAttributes.addFlashAttribute("alertClass", "alert alert-success alert-dismissible text-center");
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("readmsg", "Content has not been saved");
			redirectAttributes.addFlashAttribute("alertClass", "alert alert-danger alert-dismissible text-center");
		}
		return "redirect:"+users.getHiddenURL();
	}

	@RequestMapping(value="/about-us", method = RequestMethod.GET)
	public String getAboutUs(){
		return "about-us";
	}

	@RequestMapping(value="/privacy-policy", method = RequestMethod.GET)
	public String getprivacyPolicy(){
		return "privacy-policy";
	}

	@RequestMapping(value="/contact-us", method = RequestMethod.GET)
	public String getContactUs(){
		return "contact-us";
	}

	@RequestMapping(value="/{category}/page/{idx}",method=RequestMethod.GET )
	public String getHomeByCategory(Model model,@PathVariable("category") String category,@PathVariable("idx") Integer idx,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("index",idx);
		return "redirect:/"+category;
	}

	@RequestMapping(value="/{category}",method=RequestMethod.GET )
	public ModelAndView retrunValue(ModelAndView mv,@PathVariable("category") String category, @ModelAttribute("index") final Object idx){
 		List<String> listOfCategories = utilityService.getCategories();
		int index=1;
		try{
			index=Integer.parseInt(idx.toString());
		}catch(Exception e){}

		mv=contentService.getContentByPageOrHeader(listOfCategories,category,index);
		return mv;
	}


}
