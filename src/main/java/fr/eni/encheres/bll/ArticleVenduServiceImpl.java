package fr.eni.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	
	private ArticleVenduDAO articleVenduDAO ;
	public ArticleVenduServiceImpl (ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}

	
	public ArticleVendu findById(int id) {
		return articleVenduDAO.findById(id);
	}

	
	
	
}
