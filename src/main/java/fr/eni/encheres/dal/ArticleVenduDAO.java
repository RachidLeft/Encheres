package fr.eni.encheres.dal;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;


public interface ArticleVenduDAO {
	
	void creerArticle(ArticleVendu articleVendu);
	
	List<ArticleVendu> findAll();
		
	List<ArticleVendu> findArticleById(int noCategorie);
	
	List<ArticleVendu> findArticleByName(String nomArticle);

	List<ArticleVendu> findArticleByNameAndCategorie(int noCategorie, String nomArticle);
	
	List<ArticleVendu> findByEnchereEnCour(LocalDateTime dateDebutEncheres);
	
	public ArticleVendu findById(int noArticle);
	

	

}
