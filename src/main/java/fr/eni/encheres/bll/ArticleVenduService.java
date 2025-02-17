package fr.eni.encheres.bll;


import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;

public interface ArticleVenduService {
	
	List<ArticleVendu> consulterLesArticles();
	
	
	List<ArticleVendu> consulterLesArticlesNomEtCategorie(String nomArticle, int idCategorie);
	
	



	ArticleVendu findById(int id);

}
