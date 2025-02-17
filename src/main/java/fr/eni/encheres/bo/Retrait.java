package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Retrait {
	
	@NotBlank
	@Size(max = 30)
	private String rue;
	@NotBlank
	@Pattern(regexp = "\\d{5}")
	private String codePostal;
	@NotBlank
	@Size(max = 30)
	private String ville;
	
	private ArticleVendu article;
	
	//CONSTRUCTEURS
	public Retrait() {
		
	}
	
	
	
	public Retrait(String rue, String codePostal, String ville, ArticleVendu article) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.article = article;
	}
	
	
	
	//GETTERS et SETTERS
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

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}
	
	
}
