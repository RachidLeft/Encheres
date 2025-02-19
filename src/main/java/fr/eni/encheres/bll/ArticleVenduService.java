
package fr.eni.encheres.bll;



import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduService {
	
	List<ArticleVendu> consulterLesArticles(String nomArticle, int idCategorie, int noUtilisateur);


	void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException;


	ArticleVendu findById(int id);


	static void update(ArticleVendu articleVendu) {
		
	}
		

	List<ArticleVendu> flitrerLesArticles(int idCategorie, String nomArticle, int noUtilisateur, List<String> check);
}
