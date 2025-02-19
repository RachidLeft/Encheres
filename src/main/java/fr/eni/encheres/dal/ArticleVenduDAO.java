package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;


public interface ArticleVenduDAO {
	
	void creerArticle(ArticleVendu articleVendu);
	
		
	public ArticleVendu findById(int noArticle);
	

	List<ArticleVendu> findAll(int noCategorie, String nomArticle, int no_utilisateur, List<String> check);
	

	

}
