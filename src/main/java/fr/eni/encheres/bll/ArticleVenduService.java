package fr.eni.encheres.bll;



import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;

public interface ArticleVenduService {
	
	List<ArticleVendu> consulterLesArticles();
	
	
	List<ArticleVendu> consulterLesArticlesNomEtCategorie(String nomArticle, int idCategorie);
	

	void creerArticleAVendre(ArticleVendu articleVendu);



	ArticleVendu findById(int id);
}