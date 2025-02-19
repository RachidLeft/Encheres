package fr.eni.encheres.bll;



import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;

public interface ArticleVenduService {
	
	List<ArticleVendu> consulterLesArticles(String nomArticle, int idCategorie, int noUtilisateur);


	void creerArticleAVendre(ArticleVendu articleVendu);

	

	ArticleVendu findById(int id);


	List<ArticleVendu> flitrerLesArticles(int idCategorie, String nomArticle, int noUtilisateur, List<String> check);
}