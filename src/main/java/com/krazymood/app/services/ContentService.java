package com.krazymood.app.services;

import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;
import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Users;
import com.krazymood.app.repository.ContentDao;
import com.krazymood.app.repository.UtilityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired UtilityDao utilityDao;
    @Autowired ContentDao contentDao;

    public List<Contents> findAllContents() {
        return (List<Contents>) utilityDao.getAllModalData(Contents.class,"Contents","","","id","desc");
    }

    public List<Contents> findMostViewedContents(List<Contents> listOfContents) {
        Map<SubCategory,List<Contents>> map=listOfContents.stream().collect(Collectors.groupingBy(Contents::getSubCategory));
        Comparator<Contents> comparator= Comparator.comparing(Contents::getViews);
        Set<Map.Entry<SubCategory,List<Contents>>> setOfEntry=  map.entrySet();
        List<Contents> mostViewdList= new ArrayList<Contents>();
        for(Map.Entry entry:setOfEntry){
            List<Contents> contents= (List<Contents>) entry.getValue();
            mostViewdList.add(contents.stream().max(comparator).get());
        }
        return mostViewdList.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }

    public List<Contents> findSpecificContents(List<Contents> listOfContents) {
        Comparator<Contents> comparator=Comparator.comparing(Contents::getId);
        return listOfContents.stream().filter(contents -> contents.isSpecific).sorted(comparator.reversed()).collect(Collectors.toList());
    }

    public Map<String, Object> getCategoryList() {
        List<Category> categories = (List<Category>) utilityDao.getAllModalData(Category.class,"Category","","","id","ASC");
        List<String> strCategory=new ArrayList<String>();
        Map<String,Object> map=new HashMap<String,Object>();
        for(Category category:categories){
            strCategory.add(category.getTitle().trim());
            for(SubCategory sub:category.getSubCategory()){
                strCategory.add(sub.getSubCatName().trim());
            }
        }
        map.put("categories",categories);
        map.put("strCategory",strCategory);
        return map;
    }

    public List<Contents> findContentBySearchStrings(String trnslateVal) {
        trnslateVal = trnslateVal.trim().replace(" ","-");
        return contentDao.findContentBySearchStrings(trnslateVal);
    }

    public void saveUsersPost(Users users) {
        utilityDao.saveObject(users);
    }

    public ModelAndView getContentByPageOrHeader(List<String> listOfCategories, String category, int index) {
        ModelAndView mv=new ModelAndView();
        if(listOfCategories.contains(category) || category.equalsIgnoreCase("shero-shayari")){
            List<Contents> listOfContentsWithDiffCat=null;
            int limit=5;
            int offset=(index-1)*limit;
            Long totalContents = contentDao.getTotalCountsOfContents(category);
            List<Contents> listOfContents= contentDao.getContentsByPageNumberAndCategories(category,offset,limit);
            listOfContents = listOfContents.stream().map(x->{
                x.setTempCategoryName(category);
                x.setPageNum(index);
                return x;
            }).collect(Collectors.toList());
            boolean isCategory=listOfContents.stream().anyMatch(x->x.getCategory().title.equalsIgnoreCase(category));
            listOfContentsWithDiffCat= contentDao.getContentsByPageNumberAndCategoriesNotEqualTo(category,offset,limit,isCategory);

            final int totalPages = (int) Math.ceil(totalContents * 1.0 / limit);
            PagedListHolder page = new PagedListHolder(listOfContents);
            page.setPage(index+1);
            page.setPageSize(5);
            page.setMaxLinkedPages(totalPages);

            mv.addObject("pageData",page);
            mv.addObject("diffCategoryList",listOfContentsWithDiffCat);
            mv.setViewName("landing");
        }else{
            Contents content = findContentByHeader(category);
            mv.addObject("content", content);
            mv.addObject("relatedPostList",contentDao.getAllContentsByMostViewedAndsubCategory(content.getSubCategory().getSubCatName()));
            mv.setViewName("feedback");
        }
        mv.addObject("mostViewedList", contentDao.findMostViewedContents());
        return mv;
    }


    public Contents findContentByHeader(String header) {
        List<Contents> contentsList = (List<Contents>) utilityDao.getAllModalData(Contents.class,"Contents","engHeader",header,"id","ASC");
        Contents contents=null;
        if(!ObjectUtils.isEmpty(contentsList)) {
            contents=contentsList.get(0);
            long views = contents.getViews();
            contents.setViews(++views);
             utilityDao.updateObject(contents);
        }
        return contents;
    }

    public  List<Contents> findMostViewedContents() {
        return contentDao.findMostViewedContents();
    }
}