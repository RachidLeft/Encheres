package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;


public interface ArticleVenduDAO {
	
	void creerArticle(ArticleVendu articleVendu);
	
	List<ArticleVendu> findAll();
		
	List<ArticleVendu> findArticleById(int noArticle);
	
	List<ArticleVendu> findArticleByCategorie(String nomArticle);
	

}
