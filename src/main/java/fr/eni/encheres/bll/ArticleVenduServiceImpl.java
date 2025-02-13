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
	public void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException {
		BusinessException be = new BusinessException();
		
		boolean valide = validerNomArticle(articleVendu.getNomArticle(), be);
				valide &= validerCategorie(articleVendu.getCategorie(), be);
				valide &= validerDescriptionArticle(articleVendu.getDescription(), be);
				valide &= validerPrixVenteArticle(articleVendu.getMiseAPrix(), be);
				valide &= validerDateDebutEnchereArticle(articleVendu.getDateDebutEncheres(), be);
				valide &= validerDateFinEnchereArticle(articleVendu.getDateFinEncheres(), be);
		if (valide) {
			articleVenduDAO.creerArticle(articleVendu);
		} else {
			throw be;
		}
		
		
	}
	
	//Validation pour créer un Article
	
	private boolean validerNomArticle(String nom, BusinessException be) {
		
		if (nom == null || nom.isBlank()) {
			be.addErreur("validation.article.nom.isBlank)");
			return false;
		}
		
		if (nom.length()< 5 || nom.length() > 30) {
			be.addErreur("validation.article.nom.length");
			return false;
		}
		
		return true;
	}
	
	private boolean validerDescriptionArticle(String description, BusinessException be) {
		if (description == null || description.isBlank()) {
			be.addErreur("validation.article.description.isBlank)");
			return false;
		}
		
		if (description.length()< 5 || description.length() > 300) {
			be.addErreur("validation.article.descritpion.length");
			return false;
		}
		
		return true;
	}
	
	private boolean validerCategorie(Categorie categorie, BusinessException be) {
		Categorie categorieTrouve = this.categorieDAO.read(categorie.getNoCategorie());
		boolean valide = true;
		
		if(categorieTrouve == null) {
			valide = false;
			be.addErreur("validation.article.categorie.id.inconnu");
		}
		
		return valide;
	}
	
	private boolean validerPrixVenteArticle(int miseAPrix, BusinessException be) {
		 
		if (miseAPrix <= 0) {
			be.addErreur(null);
			return false;
		}
		 
		 return true;
	}
	
	private boolean validerDateDebutEnchereArticle(LocalDateTime dateDebut, BusinessException be) {
		if(dateDebut.isBefore(LocalDateTime.now())){
            be.addErreur("validation.article.datedebut");
            return false;
        }
		
		return true;
	}
	
	private boolean validerDateFinEnchereArticle (LocalDateTime dateFin, BusinessException be) {
		if (dateFin.isBefore(LocalDateTime.now())) {
			be.addErreur("validation.article.datefin");
            return false;
		}
		
		return true;
	}
	
	
	
}
