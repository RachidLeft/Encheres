package fr.eni.encheres.bll;



import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	
	
	ArticleVenduDAO articleVenduDAO;
	CategorieDAO categorieDAO;
	
	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
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

	@Override
	public void creerArticleAVendre(ArticleVendu articleVendu){
			articleVenduDAO.creerArticle(articleVendu);
	}
	

}