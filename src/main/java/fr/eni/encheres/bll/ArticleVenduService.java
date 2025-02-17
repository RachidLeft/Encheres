package fr.eni.encheres.bll;



import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduService {
	
	List<ArticleVendu> consulterLesArticles();
	
	
	List<ArticleVendu> consulterLesArticlesNomEtCategorie(String nomArticle, int idCategorie);
	

	void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException;

	ArticleVendu findById(int id);
}