package fr.eni.encheres.bll;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CategorieDAO;

import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.exception.BusinessException;


@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	
	
	ArticleVenduDAO articleVenduDAO;
	CategorieDAO categorieDAO;
	RetraitDAO retraitDAO;
	
	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO, RetraitDAO retraitDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
		this.retraitDAO = retraitDAO;

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
	public void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException{
	BusinessException be = new BusinessException();
	
		boolean valide = validerDateDebutEnchereArticle(articleVendu.getDateDebutEncheres(), be);
				valide &= validerDateFinEnchereArticle(articleVendu.getDateDebutEncheres(),articleVendu.getDateFinEncheres(), be);
		
		if (valide) {
			this.articleVenduDAO.creerArticle(articleVendu);
			this.retraitDAO.create(articleVendu);
		} else {
			throw be;
		}
	}

	//Validation pour créer un Article
		
		private boolean validerDateDebutEnchereArticle(LocalDateTime dateDebutEncheres, BusinessException be) {
			boolean valide = true;
			
			if(dateDebutEncheres.isBefore(LocalDateTime.now())){
	            be.addCleErreur("validation.articleVendu.dateDebutEncheres");
	            valide = false;
	        }
			
			return valide;
		}
		
		private boolean validerDateFinEnchereArticle (LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, BusinessException be) {
			boolean valide = true;
			
			if (dateFinEncheres.isBefore(LocalDateTime.now())) {
				be.addCleErreur("validation.articleVendu.dateFinEncheres");
	            valide = false;
			}
			
			if (dateFinEncheres.isEqual(dateDebutEncheres) || dateFinEncheres.isBefore(dateDebutEncheres)) {
		        be.addCleErreur("validation.articleVendu.dateFinAvantDebut");
		        valide = false;
		    }

			
			return valide;
		}


				
}