package fr.eni.encheres.bo;

import java.time.LocalDateTime;


public class Enchere {
	//Attributs
	private int noEnchere;
	private LocalDateTime dateEnchere;

	private int montantEnchere;

	private Utilisateur encherisseur;
	private ArticleVendu article;
	
	//CONSTRUCTEURS
	
	public Enchere() {
	}
	
	
	public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur encherisseur, ArticleVendu article) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.encherisseur = encherisseur;
		this.article = article;
	}
	
	
	//METHODES
	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", encherisseur="
				+ encherisseur + "]";
	}
	
	
	// GETTERS ET SETTERS
	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public Utilisateur getEncherisseur() {
		return encherisseur;
	}
	public void setEncherisseur(Utilisateur encherisseur) {
		this.encherisseur = encherisseur;
	}
	public ArticleVendu getArticle() {
		return article;
	}
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}


	public int getNoEnchere() {
		return noEnchere;
	}


	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}
}
