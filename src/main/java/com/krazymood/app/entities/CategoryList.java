package com.krazymood.app.entities;

import com.krazymood.app.component.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CategoryList {
    public List<Category> listOfCategoryBean=new ArrayList<Category>();
    public List<String> listOfCategory=new ArrayList<String>();

    public CategoryList(Map<String,Object>  categoryMap) {
        super();
         this.listOfCategoryBean = (List<Category>)categoryMap.get("categories");
         this.listOfCategory = (List<String>)categoryMap.get("strCategory");
    }
}
