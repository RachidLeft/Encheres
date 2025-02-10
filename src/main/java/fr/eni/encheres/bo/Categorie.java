package fr.eni.encheres.bo;

import java.util.List;

public class Categorie {
	
	private int noCategorie;
	

	private String libelle;
	
	List<ArticleVendu> categorieArticle;	
	
	//CONSTRUCTEURS
	public Categorie() {
	}

	public Categorie(int noCategorie, String libelle, List<ArticleVendu> categorieArticle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.categorieArticle = categorieArticle;
	}
	
	
	//METHODES
	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + ", categorieArticle="
				+ categorieArticle + "]";
	}
	
	//GETTERS ET SETTERS
	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(List<ArticleVendu> categorieArticle) {
		this.categorieArticle = categorieArticle;
	}
}
