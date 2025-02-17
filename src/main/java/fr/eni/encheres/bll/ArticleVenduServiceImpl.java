package fr.eni.encheres.bll;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
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
	public void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException{
	BusinessException be = new BusinessException();
	
		boolean valide = validerNomArticle(articleVendu.getNomArticle(), be);
				valide &= validerCategorie(articleVendu.getCategorie(), be);
				valide &= validerDescriptionArticle(articleVendu.getDescription(), be);
				valide &= validerPrixVenteArticle(articleVendu.getMiseAPrix(), be);
				valide &= validerDateDebutEnchereArticle(articleVendu.getDateDebutEncheres(), be);
				valide &= validerDateFinEnchereArticle(articleVendu.getDateFinEncheres(), be);
		
		if (valide) {
			this.articleVenduDAO.creerArticle(articleVendu);
			this.retraitDAO.create(articleVendu);
		} else {
			throw be;
		}
	}
	
	//Validation pour créer un Article
	
		private boolean validerNomArticle(String nom, BusinessException be) {
			boolean valide = true;
			
			if (nom == null || nom.isBlank()) {
				be.addCleErreur("validation.articleVendu.nomArticle.Blank");
				valide = false;
			}
			
			if (nom.length()< 3 || nom.length() > 30) {
				be.addCleErreur("validation.articleVendu.nomArticle.length");
				valide = false;
			}
			
			return valide;
		}
		
		private boolean validerDescriptionArticle(String description, BusinessException be) {
			boolean valide = true;
			
			if (description == null || description.isBlank()) {
				be.addCleErreur("validation.articleVendu.description.Blank");
				valide = false;
			}
			
			if (description.length()< 5 || description.length() > 300) {
				be.addCleErreur("validation.articleVendu.description.length");
				valide = false;
			}
			
			return valide;
		}
		
		private boolean validerCategorie(Categorie categorie, BusinessException be) {
			Categorie categorieTrouve = this.categorieDAO.read(categorie.getNoCategorie());
			boolean valide = true;
			
			if(categorieTrouve == null) {
				valide = false;
				be.addCleErreur("validation.articleVendu.categorie.noCategorie");
			}
			
			return valide;
		}
		
		private boolean validerPrixVenteArticle(int miseAPrix, BusinessException be) {
			boolean valide = true;
			
			
			if (miseAPrix < 1) {
				be.addCleErreur("validation.articleVendu.miseAPrix");
				valide = false;
			}
			 
			 return valide;
		}
		
		private boolean validerDateDebutEnchereArticle(LocalDateTime dateDebutEncheres, BusinessException be) {
			boolean valide = true;
			
			if(dateDebutEncheres.isBefore(LocalDateTime.now())){
	            be.addCleErreur("validation.articleVendu.dateDebutEncheres");
	            valide = false;
	        }
			
			return valide;
		}
		
		private boolean validerDateFinEnchereArticle (LocalDateTime dateFinEncheres, BusinessException be) {
			boolean valide = true;
			
			if (dateFinEncheres.isBefore(LocalDateTime.now())) {
				be.addCleErreur("validation.articleVendu.dateFinEncheres");
	            valide = false;
			}
			
			return valide;
		}
		

}