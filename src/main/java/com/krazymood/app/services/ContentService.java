package com.krazymood.app.services;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;
import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Feedbacks;
import com.krazymood.app.entities.Legends;
import com.krazymood.app.entities.Users;
import com.krazymood.app.entities.Visitors;
import com.krazymood.app.model.Mail;
import com.krazymood.app.repository.CategoryRepository;
import com.krazymood.app.repository.ContentsRepository;
import com.krazymood.app.repository.LegendRepository;
import com.krazymood.app.repository.SubCategoryRepository;
import com.krazymood.app.repository.UsersRepository;
import com.krazymood.app.repository.VisitorRepository;
import com.krazymood.app.utility.Utility;

@Service
public class ContentService extends JdbcDaoSupport {

	@Autowired ContentsRepository contentsRepository;
	@Autowired CategoryRepository categoryRepository;
	@Autowired SubCategoryRepository subCategoryRepository;
	@Autowired VisitorRepository visitorRepository;
	@Autowired JavaMailSender mailSender;
	@Autowired EmailService emailService;
	@Autowired
	LegendRepository legendRepository;
	@Autowired UsersRepository usersRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired FirebaseService firebaseService;
	
	@PostConstruct
	private void initialize() {
		setDataSource((DataSource) dataSource);
	}

	int lgndCount=0;
	
	private static Logger logger = LoggerFactory.getLogger(ContentService.class);
	
	@Value("${upload.location.path}")
	private String uploadLocation;
	
	public void persistContent(MultipartFile fileimage, String jsondata) throws ParseException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String uDate = sdf.format(new Date());
		String fileName = firebaseService.uploadFile(fileimage);
		//fileimage.getOriginalFilename()
		
		//String location = uploadLocation+uDate+fileName.substring(fileName.lastIndexOf('.'));
		//File file = new File(location);
		//FileOutputStream fout = new FileOutputStream(file);
		
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(jsondata);

		Category category = categoryRepository.getOne(Integer.valueOf((String) json.get("categories")));
		SubCategory subCategory = subCategoryRepository.getOne(Integer.valueOf((String)json.get("subCategory")));

		String text = (String) json.get("text");
		String userName = (String) json.get("userName");
		String header = (String) json.get("header");
		String engHeader = (String) json.get("engHeader");
		String language = (String) json.get("language");
		boolean isSpecific = Boolean.parseBoolean((String) json.get("isSpecific"));
		long length = text.length();
		long legendId = Integer.valueOf((String) json.get("legendId"));
		
		/*
		 * fout.write(fileimage.getBytes()); fout.flush(); fout.close();
		 */		
		Legends legends = new Legends();
		if(legendId!=0) {
			legends.setId(legendId);
		}else {
			legends=null;
		}
		
		Contents contents =  new Contents(userName,text,header,length,category,subCategory,language,uDate+fileName.substring(fileName.lastIndexOf('.')),legends,engHeader,isSpecific);
		
		contentsRepository.save(contents);
		
	}
	
	
	public List<Contents> findByCategoryAndSubCategory(String category, String subcategory) {
		List<Contents> contents = contentsRepository.findByCategory_TitleAndSubCategory_subCatName(category,subcategory);
		return contents;
	}

	public List<Category> getCategory(){
		return categoryRepository.findAllByVisibility(true);
	}
	
	public List<SubCategory> getSubCategory(){
		return subCategoryRepository.findAllByVisibility(true);
	}

	public Page<Contents> findAllContentswithlan(Integer idx , String lan) {
		Page<Contents> contents = contentsRepository.findAllByLanguage(Utility.getPageListBySort(idx, 5),lan);
		return contents;
	}

	/*public Page<Contents> findAllContentswithlan(String lan) {
		Page<Contents> contents = contentsRepository.findAllByLanguage(lan);
		return contents;
	}*/


	public List<Contents> getTotalSize() {
		return contentsRepository.findAll();
	}


	public Visitors checkByEmailorMobile(String cnxnMode) {
		return visitorRepository.findByMobileOrEmail(cnxnMode,cnxnMode);
	}


	public void doProcess(Contents contents, Visitors visitors, Feedbacks feedbacks, HttpServletRequest request) throws UnknownHostException, SocketException, MessagingException {
		System.out.print("inside the doprecoess "+contents.getId());
		Visitors vst = visitorRepository.findByEmail(visitors.getEmail());
			List<Feedbacks> feedbackList =  new ArrayList<Feedbacks>();
			String macAdd = Utility.getMacAdd();
			
			JSONObject jsonObj =  new JSONObject();
			
			jsonObj.put("conId", contents.getId());
			jsonObj.put("email", visitors.getEmail());
			jsonObj.put("viId", visitors.getId());
			jsonObj.put("mobile", visitors.getMobile());
			jsonObj.put("vName", visitors.getVisitorName());
			jsonObj.put("texts", feedbacks.getTexts());
			
			String jsonData=jsonObj.toJSONString();
			System.out.print("str "+jsonObj);
			
			if(vst == null) {
				 logger.info("Sending Email with Thymeleaf HTML Template Example");
				 Mail mail = new Mail();
				
				 Map<String, Object> model = new HashMap<String, Object>();
				 model.put("cntns", contents);
				 model.put("vstrs", visitors);
				 model.put("fdbcks", feedbacks);
				 
				 mail.setFrom("ksuraj751@gmail.com");
				 mail.setTo(visitors.getEmail());
				 mail.setSubject("Sending Email with Thymeleaf HTML Template Example");
				 
				 mail.setModel(model);
				 emailService.sendSimpleMessage(mail);
				/*
				 * MimeMessagePreparator preparator = new MimeMessagePreparator() {
				 * 
				 * @Override public void prepare(MimeMessage mimeMessage) throws Exception {
				 * mimeMessage.setRecipients(Message.RecipientType.TO,visitors.getEmail());
				 * mimeMessage.setSubject("subject is not good... enough");
				 * 
				 * MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				 * 
				 * helper.setText("<html th:xmlns='https://www.thymeleaf.org'><body>"
				 * +"<input type='text' th:value='${contents.getId()}'" + "</body></html>",
				 * true);
				 * 
				 * }
				 * 
				 * }; mailSender.send(preparator);
				 */
			}else {
				if(vst.getMacAddress().equalsIgnoreCase(macAdd)) {
					feedbacks.setTxtLength(feedbacks.getTexts().length());
					feedbacks.setContents(contents);
					feedbacks.setSubCategory(contents.getSubCategory());
					feedbacks.setVisitors(visitors);
					feedbackList.add(feedbacks);
					visitors.setFeedbacks(feedbackList);
				}else {
					
				}
			}
			
	}


	public Page<Contents> findByCategoryAndSubCategoryAndLanguage(String category, String subCategory,String lan , int idx) {
		Page<Contents> contents = contentsRepository.findByCategory_TitleAndSubCategory_subCatNameAndLanguage(category,subCategory,lan,Utility.getPageListBySort(idx, 5));
		return contents;
	}


	public Page<Contents> findByCategoryAndLanguage(String category, String lan, int idx) {
		Page<Contents> contents = contentsRepository.findByCategory_TitleAndLanguage(category,lan,Utility.getPageListBySort(idx, 5));
		return contents;
	}

	public Contents findByContentId(Long id) {
		return contentsRepository.getOne(id);
	}

    public List<Contents> findContentBySearchStrings(String trnslateVal) {
    	trnslateVal = trnslateVal.trim().replace(" ","-");
		return contentsRepository.findAllByLanguageAndHeaderLikeOrLanguageAndEngHeaderLike("hindi","%"+trnslateVal+"%","hindi","%"+trnslateVal+"%");
    }


	public List<Contents> findAllContentforHomeWithLan(String lan) {
		return contentsRepository.findAllByLanguageOrderByCreatedOnDesc(lan);
	}

    public void saveLegend(MultipartFile fileimage, String jsondata) throws ParseException, java.text.ParseException, IOException {
		lgndCount++;
		Category category =  new Category();
		SubCategory subCategory =  new SubCategory();
		String location = uploadLocation+lgndCount+fileimage.getOriginalFilename();
		File file = new File(location);
		FileOutputStream fout = new FileOutputStream(file);

		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(jsondata);


		category.setId(Integer.parseInt((String) json.get("category")));
		subCategory.setId(Integer.parseInt((String) json.get("subCategory")));
		String language = (String) json.get("language");
		String profilePic = lgndCount+fileimage.getOriginalFilename();
		String legendName = (String) json.get("legendName");
		String description = (String) json.get("description");
		DateFormat simpleDateformat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = simpleDateformat.parse((String)json.get("dob"));
		fout.write(fileimage.getBytes());
		fout.flush();
		fout.close();
		Legends legends =  new Legends(category,subCategory,language,legendName,description,dob,profilePic);

		legendRepository.save(legends);
    }

	
	public String getStrippedContent(Contents contents) {
		String content=contents.getText().replaceAll("\\<.*?\\>", "").replaceAll("&nbsp;"," ");
		String urlLink = "https://www.krazyshayari.com/"+contents.getEngHeader();
		return content.concat("  \n\n"+urlLink);
	}
	
	public String getStrippedContentforSocialSharing(Contents contents) {
		String content=contents.getText().replaceAll("\\<.*?\\>", "").replaceAll("&nbsp;"," ");
		return content;
	}


	public void saveUsersPost(Users users) {
		usersRepository.save(users);
	}

	 public static String getHeader(String str){
			return str.replace("-"," ");
		}

	public List<Contents> findContentforSpecific(String lan) {
		return contentsRepository.findAllByLanguageAndIsSpecificOrderByIdDesc(lan,true);
	}


	public List<Contents> findContentforMostViewed(String lan) {
		List<Contents> listofContents = new ArrayList<Contents>();
		String sql = "SELECT sub_category_id as id, MAX(views) as views FROM contents GROUP BY sub_category_id";
		List<Map<String, Object>> sub = getJdbcTemplate().queryForList(sql);
		
		/*for(int i=0; i<sub.size(); i++) {
			Query query = entityManager.createQuery(
					"select new Map(c.id as id,c.imgname as imgname,c.text as text,c.engHeader as engHeader,c.header as header,c.category as category,c.subCategory as subCategory,c.date as date,c.views as views) from Contents c where c.views=:viewsNo and c.subCategory.id=:subId");
			
			query.setParameter("subId", sub.get(i).get("id"));
			query.setParameter("viewsNo", sub.get(i).get("views"));
			Map<String,Object> list =   (Map<String, Object>) query.getSingleResult();
			System.out.println("next");
			//listofContents.add(list);
		}*/
		
		//return listofContents;
		for(int i=0; i<sub.size(); i++) {
			listofContents.addAll(contentsRepository.findContentsforMostViewed(lan,Integer.parseInt(sub.get(i).get("id").toString()),Long.parseLong(sub.get(i).get("views").toString())));
		}
		Collections.sort(listofContents, new Comparator<Contents>() {
		    @Override
		    public int compare(Contents p1, Contents p2) {
		        return (int) (p2.getViews() - p1.getViews());
		    }

		});
		return listofContents;
	}





	public static String contractLength(String string,String textType) {
		string = string.replaceAll("\\<.*?\\>", "").replaceAll("&nbsp;"," ").replace("\r\n"," ").replace("\n"," ");
		String strArr[]=string.split(" ");
		StringBuilder str=new StringBuilder();
		if(textType.equalsIgnoreCase("shortText")){
			if(strArr.length>20){
				for(int i=0; i<20; i++){
					str.append(strArr[i]).append(" ");
				}
			}else{
				for(int i=0; i<12; i++){
					str.append(strArr[i]).append(" ");
				}
			}
		}else if(textType.equalsIgnoreCase("longText")){
			for(int i=0; i<40; i++){
				str.append(strArr[i]).append(" ");
			}
		}

		return str.toString().trim().concat("....");
	}

	/*public List<Contents> findAllContentsByCategoryNameOrSubCategoryName(String param) {
		return contentsRepository.findByCategory_TitleAndLanguageOrSubCategory_subCatNameAndLanguage(param,"hindi",param,"hindi", Utility.getPageListBySort(index, 5));
	}*/

	public Page<Contents> findAllContentsByCategoryNameAndLanguageOrSubCategoryNameAndLanguage(String category, String hindi, String subcategory, String hindi1, int index) {
		return contentsRepository.findByCategory_TitleAndLanguageOrSubCategory_subCatNameAndLanguage(category,"hindi",subcategory,"hindi",Utility.getPageListBySort(index, 5));
	}

	public Contents findContentByEngHeader(String engHeader) {
		Contents contents = contentsRepository.findByEngHeader(engHeader);

		if(!ObjectUtils.isEmpty(contents)) {
			long views = contents.getViews();
			contents.setViews(++views);
			contentsRepository.save(contents);
		}
		return contents;
	}

	public Object getAllContentsByMostViewedAndsubCategory(String subCategory) {
		//@Query("select new com.krazymood.app.entities.Contents(c.id,c.imgname,c.text,c.engHeader,c.header,c.category,c.subCategory,c.date,c.views) from Contents c  where c.language= ?#{#lan} and c.subCategory.subCatName=?#{#subCat} group by c.views ORDER BY c.views desc")
		List<Contents> contents = entityManager.createQuery("select new Map(c.id as id,c.imgname as imgname,c.text as text,c.engHeader as engHeader,c.header as header,c.createdOn as createdOn,c.views as views,c.category as category,c.subCategory as subCategory) from Contents c  where c.language='hindi' and c.subCategory.subCatName='"+subCategory+"' ORDER BY c.views desc").getResultList();
		return contents;
	}

	public List<Contents> findAllContents() {
		return contentsRepository.findAll();
	}
}
