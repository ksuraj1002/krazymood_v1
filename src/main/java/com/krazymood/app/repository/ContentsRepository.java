package com.krazymood.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krazymood.app.entities.Contents;


public interface ContentsRepository extends JpaRepository<Contents, Long> {
	
	List<Contents> findByCategory_TitleAndSubCategory_subCatName(String category, String subcategory);



	Page<Contents> findAllByLanguage(Pageable pageListBySort, String lan);
	/*Page<Contents> findAllByLanguage(String lan);*/

	Page<Contents> findByCategory_TitleAndSubCategory_subCatNameAndLanguage(String category, String subCategory,
                                                                            String lan, Pageable pageListBySort);

	Page<Contents> findByCategory_TitleAndLanguage(String category, String lan, Pageable pageListBySort);


   // List<Contents> findAllByLanguageAndTextLike(String hindi, String header);


	List<Contents> findAllByLanguageOrderByCreatedOnDesc(String lan);

	//List<Contents> findAllByLanguageAndHeaderLike(String hindi, String s);

	
	List<Contents> findAllByLanguageAndHeaderLikeOrLanguageAndEngHeaderLike(String hindi, String string2,
                                                                            String hinglish, String string4);

	List<Contents> findAllByLanguageAndIsSpecificOrderByIdDesc(String lan, boolean b);

	@Query("select new com.krazymood.app.entities.Contents(c.id,c.imgname,c.text,c.engHeader,c.header,c.category,c.subCategory,c.views) from Contents c where c.language= ?#{#lan} and c.subCategory.id=?#{#cid} and c.views=?#{#cviews} ORDER BY c.views desc")
	List<Contents> findContentsforMostViewed(String lan, int cid, long cviews);
    // new com.krazymood.app.entities.Contents(c.id,c.imgname,c.text,c.engHeader,c.header,c.category,c.subCategory,c.views)
	@Query("select c from Contents c  where c.language= ?#{#lan} and c.subCategory.subCatName=?#{#subCat} group by c.views ORDER BY c.views desc")
	List<Contents> getAllContentsByMostViewedAndsubCategory(String lan, String subCat);

	Page<Contents> findByCategory_TitleAndLanguageOrSubCategory_subCatNameAndLanguage(String category, String hindi, String subcategory, String hindi1, Pageable pageListBySort);

	Contents findByEngHeader(String engHeader);

	//List<Contents> findAllByLanguageAndEngHeaderLike(String string, String string2);
}
