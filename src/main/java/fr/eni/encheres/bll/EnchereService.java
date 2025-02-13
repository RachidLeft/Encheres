package fr.eni.encheres.bll;

import java.util.List;


import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

public interface EnchereService {

	List<Enchere> consulterEnchere();
	
	ArticleVendu consulterArticleParId (int noArticle);
	
	


	
}
