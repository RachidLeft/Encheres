package fr.eni.encheres.bo;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
	// Attributs
	private int noUtilisateur;
	private String pseudo;
	
	@Size(min = 2, max = 50, message = "Le nom doit faire entre 2 et 50 caractères")
	@NotBlank(message = "Le nom est obligatoire")
	private String nom;

	@Size(min = 2, max = 50, message = "Le prénom doit faire entre 2 et 50 caractères")
	@NotBlank(message = "Le prénom est obligatoire")
	private String prenom;

	@Email(message = "L'adresse email est invalide")
	@NotBlank(message = "L'adresse email est obligatoire")
	private String email;

	@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Le numéro de téléphone est invalide")
	@NotBlank(message = "Le numéro de téléphone est obligatoire")
	private String telephone;

	@Size(min = 2, max = 100, message = "La rue doit faire entre 2 et 100 caractères")
	@NotBlank(message = "La rue est obligatoire")
	private String rue;

	@Min(value = 10000, message = "Ceci n'est pas un Code Postal valide")
	@Size(min = 5, max = 5, message = "Le code postal doit être un nombre à 5 chiffres")
	@NotNull(message = "Le code postal est obligatoire")
	private String codePostal;

	@Size(min = 2, max = 50, message = "La ville doit faire entre 2 et 50 caractères")
	@NotBlank(message = "La ville est obligatoire")
	private String ville;


	private String motDePasse;


	private int credit;


	private boolean administrateur;


	
	private List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();
	private List<ArticleVendu> articlesAchetes = new ArrayList<ArticleVendu>();
	private List<Enchere> encheres = new ArrayList<Enchere>();
	
	//CONSTRUCTEURS
	public Utilisateur() {
	}
	
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur,
			List<ArticleVendu> articlesVendus, List<ArticleVendu> articlesAchetes, List<Enchere> encheres) {
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.articlesVendus = articlesVendus;
		this.articlesAchetes = articlesAchetes;
		this.encheres = encheres;
	}


	//METHODES

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", articlesVendus=" + articlesVendus + ", articlesAchetes="
				+ articlesAchetes + ", encheres=" + encheres + "]";
	}




	//GETTERS ET SETTERS
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	public List<ArticleVendu> getArticlesVendus() {
		return articlesVendus;
	}
	public void setArticlesVendus(List<ArticleVendu> articlesVendus) {
		this.articlesVendus = articlesVendus;
	}
	public List<Enchere> getEncheres() {
		return encheres;
	}
	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public List<ArticleVendu> getArticlesAchetes() {
		return articlesAchetes;
	}

	public void setArticlesAchetes(List<ArticleVendu> articlesAchetes) {
		this.articlesAchetes = articlesAchetes;
	}

}
