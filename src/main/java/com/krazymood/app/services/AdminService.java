package com.krazymood.app.services;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;
import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Users;
import com.krazymood.app.repository.AdminDao;
import com.krazymood.app.repository.UtilityDao;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService {
    @Autowired
    FirebaseService firebaseService;
    @Autowired AdminDao adminDao;

    FirebaseOptions firebaseOptions;
    Bucket bucket;
    Firestore firestore;


    @Autowired UtilityDao utilityDao;

    @Value("${upload.location.path}")
    private String uploadLocation;

    @Transactional
    public void saveContent(MultipartFile fileImage, String jsondata) throws ParseException, IOException {
        String uDate = new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
        String fileName = uploadFile(fileImage);
        Contents contents = new Gson().fromJson(jsondata,Contents.class);
        contents.setCategory(new Category(contents.getCategoryId()));
        contents.setSubCategory(new SubCategory(contents.getSubCategoryId()));
        contents.setImgname(uDate+fileName.substring(fileName.lastIndexOf('.')));
        contents.setTxtLngth(contents.getText().length());
        contents.isSpecific(Boolean.parseBoolean(contents.getIsSpecificStr()));
        utilityDao.saveObject(contents);
    }

    public String uploadFile(MultipartFile multipartFile) {
        try {
            Storage storage = bucket.getStorage();
            String filename = "public_images/" + generateFileName(multipartFile);
            BlobId blobId = BlobId.of(bucket.getName(), filename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            Blob blob = storage.create(blobInfo, multipartFile.getBytes());
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public List<SubCategory> findByCategory_Id(Integer category, List<Category> categoryBean) {
       return (List<SubCategory>) categoryBean.get(category).getSubCategory();
    }

    public void saveCategoriesOnload(List<Category> categories) {
        Category category = (Category) utilityDao.getAllModalData(Category.class,"Category","id","1","id","ASC").get(0);
        if(ObjectUtils.isEmpty(category)){
           utilityDao.saveObject(categories);
        }
    }
}
