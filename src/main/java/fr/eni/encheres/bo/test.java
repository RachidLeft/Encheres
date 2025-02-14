package fr.eni.encheres.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class test  {
	
		private final String FIND_ARTICLE_BY_ID = "SELECT "
	        + "av.nom_article AS av_nom_article, "
	        + "av.description AS av_description, "
	        + "c.libelle AS c_libelle, "
	        + "av.prix_initial AS av_prix_initial, "
	        + "av.date_fin_encheres AS av_date_fin_encheres, "
	        + "u.pseudo AS pseudo_vendeur, "  // Pseudo du vendeur
	        + "r.code_postal AS r_code_postal, "
	        + "r.rue AS r_rue, "
	        + "r.ville AS r_ville, "
	        + "e.no_utilisateur AS e_no_utilisateur, "
	        + "e.montant_enchere AS e_montant_enchere, "
	        + "u2.pseudo AS pseudo_encherisseur "  // Pseudo de l'enchérisseur
	        + "FROM ARTICLES_VENDUS av "
	        + "LEFT JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur "
	        + "LEFT JOIN CATEGORIES c ON av.no_categorie = c.no_categorie "
	        + "LEFT JOIN RETRAITS r ON r.no_article = av.no_article "
	        + "LEFT JOIN ENCHERES e ON e.no_article = av.no_article "
	        + "LEFT JOIN UTILISATEURS u2 ON e.no_utilisateur = u2.no_utilisateur " // Jointure supplémentaire pour l'enchérisseur
	        + "WHERE av.no_article = :no_article "
	        + "AND e.montant_enchere = ("
	        + "    SELECT MAX(montant_enchere) "
	        + "    FROM ENCHERES "
	        + "    WHERE no_article = av.no_article"
	        + ")";






	class ArticleVenduRowMapper implements RowMapper<ArticleVendu>{
		
		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException{
			ArticleVendu av = new ArticleVendu();
	        av.setNoArticle(rs.getInt("av_no_article"));
	        av.setNomArticle(rs.getString("av_nom_article"));
	        av.setDescription(rs.getString("av_description"));
	        av.setDateDebutEncheres(rs.getObject("av_date_debut_encheres", LocalDateTime.class));
	        av.setDateFinEncheres(rs.getObject("av_date_fin_encheres", LocalDateTime.class));
	        av.setMiseAPrix(rs.getInt("av_prix_initial"));

			
			
			
			Utilisateur vend = new Utilisateur();
			vend.setNoUtilisateur(rs.getInt("u_no_utilisateur"));
			 vend.setPseudo(rs.getString("u_pseudo"));
			av.setVend(vend);
			
			Categorie cat = new Categorie();
			cat.setNoCategorie(rs.getInt("c_no_categorie"));
			cat.setLibelle(rs.getString("c_libelle"));
			av.setCategorie(cat);
			
			Retrait retrait =  new Retrait();
			System.out.println("🔍 Récupération lieu de retrait...");
			System.out.println("rue = " + rs.getString("r_rue"));
			System.out.println("code_postal = " + rs.getString("r_code_postal"));
			System.out.println("ville = " + rs.getString("r_ville"));
			retrait.setRue(rs.getString("r_rue"));
			retrait.setCodePostal(rs.getString("r_code_postal"));
			retrait.setVille(rs.getString("r_ville"));
			av.setLieuRetrait(retrait);
			
			return av;
		}
	}




	
	
}
