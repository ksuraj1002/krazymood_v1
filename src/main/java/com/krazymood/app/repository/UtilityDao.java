package com.krazymood.app.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class UtilityDao {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public Map<String,Object> saveObject(Object object){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            entityManager.persist(object);
            entityManager.flush();
            entityManager.close();
            map.put("ResCode",1);
            map.put("ResMsg","Successfully Saved");
        }catch(Exception e){
            map.put("ResCode",0);
            map.put("ResMsg","Failed to save");
        }
        return map;
    }

    @Transactional
    public Map<String,Object> updateObject(Object object){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            entityManager.merge(object);
            entityManager.flush();
            entityManager.close();
            map.put("ResCode",1);
            map.put("ResMsg","Updated Successfully");
        }catch(Exception e){
            map.put("ResCode",0);
            map.put("ResMsg","Failed to update");
        }
        return map;
    }

    public List<?> getAllModalData(Class<?> modal,String className,String column,String field,String orderBy,String orderType){
        String sql;
        if(!(ObjectUtils.isEmpty(column) && ObjectUtils.isEmpty(field))){
            sql="SELECT C FROM "+className+" C WHERE "+column+"='"+field+"' ORDER BY "+orderBy+" "+orderType;
        }else{
            sql="SELECT C FROM "+className+" C ORDER BY "+orderBy+" "+orderType;
        }
        return entityManager.createQuery(sql,modal).getResultList();
    }

}
