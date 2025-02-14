package fr.eni.encheres.dal;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
	
	void create(ArticleVendu article); 
	
	Retrait read(int noArticle);
}
