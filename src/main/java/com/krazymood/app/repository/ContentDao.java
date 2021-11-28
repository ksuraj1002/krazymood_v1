package com.krazymood.app.repository;

import com.krazymood.app.entities.Contents;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ContentDao{
    @PersistenceContext
    EntityManager entityManager;

    public List<Contents> findContentBySearchStrings(String trnslateVal) {
        String sql = "SELECT C FROM Contents C WHERE C.engHeader LIKE '"+trnslateVal+"' OR C.header LIKE '"+trnslateVal+"'";
        List<Contents> contents = (List<Contents>) entityManager.createQuery(sql,Contents.class);
        return contents;
    }

    public List<Contents> getContentsByPageNumberAndCategories(String category,int offset,int limit) {
        String sql=null;
        if(category.equalsIgnoreCase("shero-shayari")){
            sql="SELECT C FROM Contents C";
        }else{
            sql="SELECT C FROM Contents C WHERE C.category.title='"+category+"' OR C.subCategory.subCatName='"+category+"'";
        }
       return entityManager.createQuery(sql,Contents.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Contents> getAllContentsByMostViewedAndsubCategory(String subCategory) {
        List<Contents> contents = entityManager.createQuery("select new Map(c.id as id,c.imgname as imgname,c.text as text,c.engHeader as engHeader,c.header as header,c.createdOn as createdOn,c.views as views,c.category as category,c.subCategory as subCategory,c.updatedOn as updatedOn) from Contents c  where c.subCategory.subCatName='"+subCategory+"' ORDER BY c.views desc").getResultList();
        return contents;
    }

    public List<Contents> findMostViewedContents() {
        String sql="SELECT C FROM Contents C GROUP BY C.subCategory.subCatName ORDER BY C.views DESC";
        List<Contents> mostViewed = entityManager.createQuery(sql,Contents.class).getResultList();
        return mostViewed;
    }

    public Long getTotalCountsOfContents(String category) {
        if(category.equalsIgnoreCase("shero-shayari")){
            return (Long) entityManager.createQuery("SELECT COUNT(c) FROM Contents c").getSingleResult();
        }else{
            return (Long) entityManager.createQuery("SELECT COUNT(c) FROM Contents c WHERE c.category.title ='"+category+"' OR c.subCategory.subCatName='"+category+"'").getSingleResult();
        }
    }


    public List<Contents> getContentsByPageNumberAndCategoriesNotEqualTo(String category, int offset, int limit, boolean isCategory) {
        String sql=null;
        if(isCategory){
            sql="SELECT C FROM Contents C WHERE C.category.title!='"+category+"' GROUP BY C.category.title ORDER BY C.views DESC";
        }else{
            sql="SELECT C FROM Contents C WHERE  C.subCategory.subCatName!='"+category+"' GROUP BY C.subCategory.subCatName ORDER BY C.views DESC";
        }
        return entityManager.createQuery(sql,Contents.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

}
