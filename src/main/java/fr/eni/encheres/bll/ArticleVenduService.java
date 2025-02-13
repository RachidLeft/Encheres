package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduService {
	
	void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException;
}
