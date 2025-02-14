package fr.eni.encheres.bll;


import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	

	private ArticleVenduDAO articleVenduDAO;
		

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}
	

	@Override
	public List<ArticleVendu> consulterLesArticles() {

		return this.articleVenduDAO.findAll();
	}



	@Override
	public List<ArticleVendu> consulterLesArticlesNomEtCategorie(String nomArticle, int idCategorie) {
		
		List<ArticleVendu> articleVendu = new ArrayList<ArticleVendu>();
		
		if ((nomArticle.isBlank()) && (idCategorie != 0)) {
			articleVendu = this.articleVenduDAO.findArticleById(idCategorie);
		}
		if ((!nomArticle.isBlank()) && (idCategorie == 0)) {
			articleVendu = this.articleVenduDAO.findArticleByName("%" + nomArticle + "%");
		}
		if ((!nomArticle.isBlank()) && (idCategorie != 0)) {
			articleVendu = this.articleVenduDAO.findArticleByNameAndCategorie(idCategorie, "%" + nomArticle + "%");
		}
		if ((nomArticle.isBlank()) && (idCategorie == 0)) {
			articleVendu = this.articleVenduDAO.findAll();
		}
				
		
		return articleVendu;

	}

	
	public ArticleVendu findById(int id) {
		return articleVenduDAO.findById(id);

	}

	
	

}
