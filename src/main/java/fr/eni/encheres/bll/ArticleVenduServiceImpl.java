package fr.eni.encheres.bll;



import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.EnchereDAO;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	
	
	ArticleVenduDAO articleVenduDAO;
	CategorieDAO categorieDAO;
	EnchereDAO enchereDAO;
	
	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO, EnchereDAO enchereDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
		this.enchereDAO = enchereDAO;
	}
	

	@Override
	public List<ArticleVendu> consulterLesArticles(String nomArticle, int idCategorie, int noUtilisateur) {
		
		List<String> listeAchat = new ArrayList<String>();
		
		listeAchat.add("achat1");
		
		
		return this.articleVenduDAO.findAll(idCategorie, nomArticle, noUtilisateur, listeAchat);
	}



	@Override
	public List<ArticleVendu> flitrerLesArticles(int idCategorie, String nomArticle, int noUtilisateur, List<String> check) {
				
		return this.articleVenduDAO.findAll(idCategorie, nomArticle, noUtilisateur, check);
		
	}
	

	
	
	

	
	public ArticleVendu findById(int id) {
		return articleVenduDAO.findById(id);

	}

	@Override
	public void creerArticleAVendre(ArticleVendu articleVendu){
			articleVenduDAO.creerArticle(articleVendu);
	}




}