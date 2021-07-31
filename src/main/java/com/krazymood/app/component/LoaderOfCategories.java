package com.krazymood.app.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.krazymood.app.repository.CategoryRepository;
import com.krazymood.app.repository.SubCategoryRepository;

@Component
public class LoaderOfCategories implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger LOGGER = LoggerFactory.getLogger(Category.class);

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SubCategoryRepository subCategoryRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCategory();
		LOGGER.info("Data has been loaded....");
	}

	private void loadCategory() {

		Category shayaris = new Category("shayari",true);
		Category jokes = new Category("jokes",true);
		Category memes = new Category("memes",true);
		Category poems = new Category("poems",true);
		Category stories = new Category("stories",true);
		Category couplets = new Category("couplets-dohe",true);
		Category quotes = new Category("quotes",true);

		List<Category> categories = new ArrayList<Category>();

		SubCategory sadShayri = new SubCategory("sad-shayari", shayaris);
		SubCategory loveShayri = new SubCategory("love-shayari", shayaris);
		SubCategory hateShayri = new SubCategory("hate-shayari", shayaris);
		SubCategory sentimentShayri = new SubCategory("sentiment-shayari", shayaris);
		SubCategory romanticShayri = new SubCategory("romantic-shayari", shayaris);
		SubCategory funnyShayri = new SubCategory("funny-shayari", shayaris);
		SubCategory friendshipShayri = new SubCategory("friendship-shayari", shayaris);
		SubCategory sansarShayri = new SubCategory("shayari-sansar", shayaris);
		
		
		SubCategory santaBantaJokes = new SubCategory("santa-banta", jokes);
		SubCategory studentTeacherJokes = new SubCategory("student-teacher", jokes);
		SubCategory parentSonJokes = new SubCategory("parent-son", jokes);
		SubCategory friendshipJokes = new SubCategory("friendship-jokes", jokes);
		SubCategory husbandWifeJokes = new SubCategory("husband-wife", jokes);
		SubCategory bf_gfJokes = new SubCategory("boyfriend-girlfriend", jokes);
		SubCategory padosanJokes = new SubCategory("padosan-jokes", jokes);
		SubCategory sharabiJokes = new SubCategory("sharabi-jokes", jokes);
		SubCategory funnyJokes = new SubCategory("funny-jokes", jokes);

		SubCategory kidsPoem = new SubCategory("kids-poem", poems);
		SubCategory Shringar = new SubCategory("shringar-poem", poems);
		SubCategory Karun = new SubCategory("karun-poem", poems);
		SubCategory Veer = new SubCategory("veerta-bahaduri-poem", poems);
		SubCategory Hasya = new SubCategory("hasya-poem", poems);
		SubCategory kavyaSansar = new SubCategory("kavya-sansar", poems);
		
		SubCategory kidsStories =  new SubCategory("kids-stories", stories);
		SubCategory moralStories =  new SubCategory("moral-stories", stories);
		SubCategory historicStories =  new SubCategory("historic-stories", stories);
		SubCategory loveStories =  new SubCategory("love-stories", stories);
		SubCategory realStories =  new SubCategory("real-stories", stories);
		SubCategory munshiStories =  new SubCategory("munshi-premchand-stories", stories);
		SubCategory sansarStories =  new SubCategory("story-sansar", stories);
		
		
		SubCategory kabirDohe =  new SubCategory("kabir-das", couplets);
		SubCategory rahimDohe =  new SubCategory("rahim-das", couplets);
		SubCategory tulsiDohe =  new SubCategory("tulsi-das", couplets);
		
		SubCategory programmingMemes =  new SubCategory("programming-memes", memes);
		SubCategory socialMemes =  new SubCategory("social-memes", memes);
		SubCategory funnyMemes =  new SubCategory("funny-memes", memes);
		
		
		SubCategory motivationalQuotes =  new SubCategory("motivational-quotes", quotes);
		SubCategory attitudeQuotes =  new SubCategory("attitude-quotes", quotes);
		SubCategory teacherQuotes =  new SubCategory("teacher-quotes", quotes);
		SubCategory lifeQuotes =  new SubCategory("life-quotes", quotes);
		SubCategory positiveQuotes =  new SubCategory("positive-quotes", quotes);
		SubCategory inspirationalQuotes =  new SubCategory("inspirational-quotes", quotes);
		SubCategory beautiQuotes =  new SubCategory("beauty-quotes", quotes);
		SubCategory smileQuotes =  new SubCategory("smile-quotes", quotes);
		SubCategory loveQuotes =  new SubCategory("love-quotes", quotes);
		SubCategory bestQuotes =  new SubCategory("best-quotes", quotes);
	
		Set<SubCategory> subShayri = new HashSet<SubCategory>();
		Set<SubCategory> subJokes = new HashSet<SubCategory>();
		Set<SubCategory> subMemes = new HashSet<SubCategory>();
		Set<SubCategory> subPoems = new HashSet<SubCategory>();
		Set<SubCategory> subStory = new HashSet<SubCategory>();
		Set<SubCategory> subCouplets = new HashSet<SubCategory>();
		Set<SubCategory> subQuotes = new HashSet<SubCategory>();

		subShayri.add(sadShayri);
		subShayri.add(loveShayri);
		subShayri.add(hateShayri);
		subShayri.add(sentimentShayri);
		subShayri.add(romanticShayri);
		subShayri.add(funnyShayri);
		subShayri.add(friendshipShayri);
		subShayri.add(sansarShayri);
		
		subJokes.add(santaBantaJokes);
		subJokes.add(studentTeacherJokes);
		subJokes.add(parentSonJokes);
		subJokes.add(friendshipJokes);
		subJokes.add(husbandWifeJokes);
		subJokes.add(bf_gfJokes);
		subJokes.add(padosanJokes);
		subJokes.add(sharabiJokes);
		subJokes.add(funnyJokes);

		subPoems.add(kidsPoem);
		subPoems.add(Shringar);
		subPoems.add(Karun);
		subPoems.add(Veer);
		subPoems.add(Hasya);
		subPoems.add(kavyaSansar);
		
		subStory.add(kidsStories);
		subStory.add(moralStories);
		subStory.add(historicStories);
		subStory.add(loveStories);
		subStory.add(realStories);
		subStory.add(munshiStories);
		subStory.add(sansarStories);
		
		subCouplets.add(kabirDohe);
		subCouplets.add(rahimDohe);
		subCouplets.add(tulsiDohe);
		
		subMemes.add(programmingMemes);
		subMemes.add(socialMemes);
		subMemes.add(funnyMemes);
		
		subQuotes.add(bestQuotes);
		subQuotes.add(loveQuotes);
		subQuotes.add(smileQuotes);
		subQuotes.add(beautiQuotes);
		subQuotes.add(inspirationalQuotes);
		subQuotes.add(positiveQuotes);
		subQuotes.add(lifeQuotes);
		subQuotes.add(teacherQuotes);
		subQuotes.add(attitudeQuotes);
		subQuotes.add(motivationalQuotes);
		
		
		shayaris.setSubCategory(subShayri);
		jokes.setSubCategory(subJokes);
		poems.setSubCategory(subPoems);
		stories.setSubCategory(subStory);
		couplets.setSubCategory(subCouplets);
		memes.setSubCategory(subMemes);
		quotes.setSubCategory(subQuotes);
		
		categories.add(shayaris);
		categories.add(jokes);
		categories.add(poems);
		categories.add(stories);
		categories.add(couplets);
		categories.add(memes);
		categories.add(quotes);
		
		if(categoryRepository.findAll().isEmpty())categoryRepository.saveAll(categories);

	}

}
