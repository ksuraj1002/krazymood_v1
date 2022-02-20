package com.krazymood.app.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;
import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Users;
import com.krazymood.app.repository.AdminDao;
import com.krazymood.app.repository.UtilityDao;

@Service
public class AdminService {
    @Autowired
    FirebaseService firebaseService;
    @Autowired AdminDao adminDao;


    FirebaseOptions firebaseOptions;
    Bucket bucket;
    Firestore firestore;


    @Autowired UtilityDao utilityDao;
    @Autowired EmailService emailService;

    @Value("${upload.location.path}")
    private String uploadLocation;

    @Transactional
    public void saveContent(MultipartFile fileImage, String jsondata) throws ParseException, IOException {
        String uDate = new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
       String fileName = firebaseService.uploadFile(fileImage);
        Contents contents = new Gson().fromJson(jsondata,Contents.class);
      
        contents.setCategory(new Category(contents.getCategoryId()));
        contents.setSubCategory(new SubCategory(contents.getSubCategoryId()));
      
        contents.setImgname(uDate+fileName.substring(fileName.lastIndexOf('.')));
        contents.setTxtLngth(contents.getText().length());
        contents.isSpecific(Boolean.parseBoolean(contents.getIsSpecificStr()));
        Map<String,Object> map=utilityDao.saveObject(contents);

        if(Integer.parseInt(map.get("ResCode").toString())==1){
            List<Users> listOfUsers= (List<Users>) utilityDao.getAllModalData(Users.class,"Users","isSubscribed","1","id","ASC");
            emailService.sendUpdatesToUsers(listOfUsers,contents);
        }

    }

   
    private String generateFileName(MultipartFile multiPart) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String uDate = sdf.format(new Date());
        String fileName=uDate+multiPart.getOriginalFilename().substring(multiPart.getOriginalFilename().lastIndexOf('.'));
        return /*new Date().getTime() + "-" +*/ Objects.requireNonNull(fileName);
    }

    public List<Users> findAllByIsWatched(boolean flag) {
       return adminDao.findAllByIsWatched(flag);
    }

    public void addCategoryAndSubcategory(Category category) {
        utilityDao.saveObject(category);
    }

    public Set<SubCategory> findByCategory_Id(Integer category, List<Category> categoryBean) {
    	Set<SubCategory> list= categoryBean.get(category-1).getSubCategory();
        return list;
    }

    public void saveCategoriesOnload(List<Category> categories) {
        Category category = (Category) utilityDao.getAllModalData(Category.class,"Category","id","1","id","ASC").get(0);
        if(ObjectUtils.isEmpty(category)){
           utilityDao.saveObject(categories);
        }
    }
}
