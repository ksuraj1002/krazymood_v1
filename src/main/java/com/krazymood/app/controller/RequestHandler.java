package com.krazymood.app.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.ImageGallery;
import com.krazymood.app.entities.Users;
import com.krazymood.app.entities.Visitors;
import com.krazymood.app.model.Acceptor;
import com.krazymood.app.repository.CategoryRepository;
import com.krazymood.app.repository.GalleryRepository;
import com.krazymood.app.services.ContentService;
import com.krazymood.app.services.FirebaseService;

@Controller
public class RequestHandler {
	
	@Autowired CategoryRepository categoryRepository;
	@Autowired ContentService contentService;
	@Autowired FirebaseService firebaseService;
	@Autowired GalleryRepository galleryRepository;

	//hindi by default
	@RequestMapping(value = {"","/","index","home"}, method = RequestMethod.GET)
	public String getHomeforHindi(Model model) {
		String lan = "hindi";
		List<Contents> listOfContents = contentService.findAllContentforHomeWithLan(lan);
		model.addAttribute("contentList", listOfContents);
		model.addAttribute("content", listOfContents.get(0));
		model.addAttribute("mostViewedList", contentService.findContentforMostViewed(lan));
		model.addAttribute("specificContentList", contentService.findContentforSpecific(lan));
		model.addAttribute("lan",lan);
		return "index";
	}

	@GetMapping(value = "/storage/public_images/{images}")
	public void fetchFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = request.getRequestURI();
		filename = filename.substring(9);
		System.err.println(filename);
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
		/*System.out.println(trnslateVal);*/
		List<Contents> contents = contentService.findContentBySearchStrings(trnslateVal);
		return contents;
	}

	@PostMapping(value = "/uploadImg")
	public ResponseEntity uploadImage(MultipartFile imageFile) {
		try {
			ImageGallery g = new ImageGallery(firebaseService.uploadFile(imageFile));
			galleryRepository.save(g);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image uploaded successfully.");
		} catch (Exception e) {
			e.printStackTrace(); // see note 2
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message :" + e.getMessage());
		}
	}



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

	@RequestMapping(value="/{category}/page/{idx}",method=RequestMethod.GET )
	public String getHomeByCategory(Model model,@PathVariable("category") String category,@PathVariable("idx") Integer idx,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("index",idx);
		return "redirect:/"+category;
	}

	@RequestMapping(value="/{category}",method=RequestMethod.GET )
	public ModelAndView retrunValue(ModelAndView mv,@PathVariable("category") String category, @ModelAttribute("index") final Object idx){
		int index=0;
		try{
			index=Integer.parseInt(String.valueOf(idx));
		}catch(Exception e){}
		Page<Contents> page=null;
		if(category.equals("shero-shayari")){
			page = contentService.findAllContentswithlan(index==0?0:index-1,"hindi");
		}else if(!category.equals("shero-shayari")){
			page = contentService.findAllContentsByCategoryNameAndLanguageOrSubCategoryNameAndLanguage(category,"hindi",category,"hindi",index==0?0:index-1);
		   }
		if(page.getContent().size()==0){
			Contents contents =  contentService.findContentByEngHeader(category);
			mv.addObject("contentList", contentService.getAllContentsByMostViewedAndsubCategory(contents.getSubCategory().getSubCatName()));
			mv.addObject("content", contents);
			mv.setViewName("feedback");
			return mv;
		}
		mv.addObject("page",page);
		mv.setViewName("landing");
		return mv;
	}

	/*comments controller*/
	/*@RequestMapping(value="/categp/{subCategory}/{engHeader}", method = RequestMethod.GET)
	public String addComment(Model model, @PathVariable("subCategory") String subCategory,@PathVariable("engHeader") String engHeader) {
		Contents contents =  contentService.findContentByEngHeader(engHeader);
		model.addAttribute("contentList", contentService.getAllContentsByMostViewedAndsubCategory(subCategory));
		model.addAttribute("content", contents);
		return "feedback";
	}*/






	/*
	 * private List<Contents> getRecentContents(List<Contents> listOfContents) {
	 * List<Contents> listContents=new ArrayList<Contents>(); for(Contents
	 * contents:listOfContents) { SimpleDateFormat sdformat = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * if(contents.getDate().toString().equals(sdformat.format(new Date()))) {
	 * listContents.add(contents); } } return listContents; }
	 */


	
	/*@RequestMapping(value="{lan}/{category}/{subcategory}/page/{idx}", method=RequestMethod.GET)
	public String getHomeByName(Model model,@PathVariable("category") String category,@PathVariable("subcategory") String subCategory,@PathVariable("idx") Integer idx, @PathVariable("lan") String lan) {

		Page<Contents> page = contentService.findByCategoryAndSubCategoryAndLanguage(category,subCategory,lan,idx-1);
		model.addAttribute("page",page);
		model.addAttribute("category", category);
		model.addAttribute("subcategory", subCategory);
		model.addAttribute("lan", lan);
		return "landing";
	}*/

	//nepali by choice
/*	@RequestMapping(value = "/nepali", method = RequestMethod.GET)
	public String getHomeforNepali(Model model) {
		String lan = "nepali";
		model.addAttribute("lan",lan);
		return "index";
	}*/


	

	
	
	
	
	
	
	
	
	
	
	
	
	  /*May be use in later*/
	/* @ResponseBody
	 @RequestMapping(value="/processemail/text", method = RequestMethod.POST)
	 public void processEmail(Acceptor acceptor) {
		 
		System.err.print(acceptor.getCntnsId()+" "+acceptor.getVstrsEmail()+""+" get indise the email ");
	 }

	@ResponseBody
	@RequestMapping(value="/checkexistence",method = RequestMethod.GET)
	public String checkExistence(@RequestParam("cnxnmode") String cnxnMode){
		Visitors visitors=contentService.checkByEmailorMobile(cnxnMode);
		if(visitors==null) {

			return "null";
		}
		return "kull";
	}*/
	
	/* sharing text and images to whatsapp */
	
	/*
	 * public void shareContents() { String text = "Look at my awesome picture"; Uri
	 * pictureUri = Uri.parse("file://my_picture"); Intent shareIntent = new
	 * Intent(); shareIntent.setAction(Intent.ACTION_SEND);
	 * shareIntent.putExtra(Intent.EXTRA_TEXT, text);
	 * shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
	 * shareIntent.setType("image/*");
	 * shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
	 * startActivity(Intent.createChooser(shareIntent, "Share images...")); }
	 */



}
