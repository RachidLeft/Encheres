package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.exception.BusinessException;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService{
	
	ArticleVenduDAO articleVenduDAO;
	CategorieDAO categorieDAO;
	
	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
	}


	@Override
	public void creerArticleAVendre(ArticleVendu articleVendu){
			articleVenduDAO.creerArticle(articleVendu);
	}
	

	
	
	
}
