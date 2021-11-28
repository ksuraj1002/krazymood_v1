package com.krazymood.app.services;

import com.krazymood.app.component.Category;
import com.krazymood.app.entities.CategoryList;
import com.krazymood.app.entities.Contents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityService {
    @Autowired CategoryList categoryList;

    public List<Category> getCategoryBean(){
        List<Category> list = categoryList.getListOfCategoryBean();
        return  list;
    }

    public List<String> getCategories(){
        List<String> list = categoryList.getListOfCategory();
        return list;
    }



    public String getStrippedContentforSocialSharing(Contents contents) {
        String content=contents.getText().replaceAll("\\<.*?\\>", "").replaceAll("&nbsp;"," ");
        return content;
    }

}
