package fr.eni.encheres.bo;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArticleVendu {
	
	
	private int noArticle;
	@NotBlank(message="Le nom de l'article ne peut pas être vide.")
	@Size(min = 3, max = 30, message ="Le nom de l'article doit comporter entre 3 et 30 caractères.")
	private String nomArticle;
	@Size(min = 5, max = 300, message ="La description doit comporter entre 5 et 300 caractères.")
	private String description;
	@NotNull(message="La date de début des enchères est obligatoire")
	private LocalDateTime dateDebutEncheres;
	@NotNull(message="La date de fin des enchères est obligatoire.")
	private LocalDateTime dateFinEncheres;
	@Min(value = 1, message ="Le prix de mise à prix doit être supérieur ou égal à 1.")
	private int miseAPrix;
    private int prixVente;
    private int etatVente;
    
    @NotNull(message="La catégorie est obligatoire.")
    private Categorie categorie;
    private Utilisateur vend;
    private Utilisateur achete;
    
    @Valid
    private Retrait lieuRetrait;
    private List<Enchere> enchere;
    
    //CONSTRUCTEURS
    public ArticleVendu() {
		
	}
    
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, int etatVente, Categorie categorie,
			Utilisateur vend, Utilisateur achete, Retrait lieuRetrait, List<Enchere> enchere) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.categorie = categorie;
		this.vend = vend;
		this.achete = achete;
		this.lieuRetrait = lieuRetrait;
		this.enchere = enchere;
	}
	

	//GETTERS ET SETTERS

	public int getNoArticle() {
		return noArticle;
	}


	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}


	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}


	public int getMiseAPrix() {
		return miseAPrix;
	}


	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public int getEtatVente() {
		return etatVente;
	}


	public void setEtatVente(int etatVente) {
		this.etatVente = etatVente;
	}


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public Utilisateur getVend() {
		return vend;
	}


	public void setVend(Utilisateur vend) {
		this.vend = vend;
	}


	public Utilisateur getAchete() {
		return achete;
	}


	public void setAchete(Utilisateur achete) {
		this.achete = achete;
	}


	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}


	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public List<Enchere> getEnchere() {
		return enchere;
	}

	public void setEnchere(List<Enchere> enchere) {
		this.enchere = enchere;
	}

	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", categorie=" + categorie
				+ ", vend=" + vend + ", achete=" + achete + ", lieuRetrait=" + lieuRetrait + ", enchere=" + enchere
				+ "]";
	}
	
	
    
}
