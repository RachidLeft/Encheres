package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	
	private final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie)"
			+ " VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :no_utilisateur, :no_categorie)";
	
	private final String FIND_ALL = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, av.date_fin_encheres, av.prix_initial, av.prix_vente , av.no_utilisateur, av.no_categorie, u.pseudo, u.no_utilisateur AS u_no_utilisateur, e.no_utilisateur AS u2_no_utilisateur, e.montant_enchere FROM ARTICLES_VENDUS AS av"
			+ " JOIN UTILISATEURS AS u ON av.no_utilisateur = u.no_utilisateur JOIN CATEGORIES AS c ON av.no_categorie = c.no_categorie LEFT JOIN ENCHERES e ON e.no_article = av.no_article WHERE 1=1 ";
	
	private final String FIND_ARTICLE_BY_CATEGORIE = "AND av.no_categorie = :no_categorie";
	
	private final String FIND_ARTICLE_BY_NAME = " AND av.nom_article LIKE :nom_article";
	
	private final String DATE_ENCHERE_EN_COURS = " AND av.date_debut_encheres < GETDATE() AND av.date_fin_encheres > GETDATE()";
	
	private final String DATE_FIN_ENCHERE = " AND av.date_fin_encheres < GETDATE()";	
	
	private final String ENCHERE_UTILISATEUR = " AND e.no_utilisateur = :no_utilisateur";
	
	private final String MES_VENTES = " AND u.no_utilisateur = :no_utilisateur";
	
	private final String DATE_ENCHERE_NON_DEBUTEE = " AND av.date_debut_encheres > GETDATE()";	
	
	
	
	
	private final String FIND_ARTICLE_BY_ID = "SELECT "
	        + "av.no_article AS av_no_article, "
	        + "av.nom_article AS av_nom_article, "
	        + "av.description AS av_description, "
	        + "av.prix_initial AS av_prix_initial, "
	        + "av.date_debut_encheres AS av_date_debut_encheres, "
	        + "av.date_fin_encheres AS av_date_fin_encheres, "
	        + "av.prix_vente AS av_prix_vente, "
	        + "c.no_categorie AS c_no_categorie, "
	        + "c.libelle AS c_libelle, "
	        + "u.no_utilisateur AS u_no_utilisateur, "
	        + "u2.no_utilisateur AS u2_no_utilisateur, "
	        + "u.pseudo AS u_pseudo_vendeur, "
	        + "u2.pseudo AS u2_pseudo_encherisseur, "
	        + "r.code_postal AS r_code_postal, "
	        + "r.rue AS r_rue, "
	        + "r.ville AS r_ville, "
	        + "e.montant_enchere AS e_montant_enchere "
	        + "FROM ARTICLES_VENDUS av "
	        + "LEFT JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur "
	        + "LEFT JOIN CATEGORIES c ON av.no_categorie = c.no_categorie "
	        + "LEFT JOIN RETRAITS r ON r.no_article = av.no_article "
	        + "LEFT JOIN ENCHERES e ON e.no_article = av.no_article "
	        + "LEFT JOIN UTILISATEURS u2 ON e.no_utilisateur = u2.no_utilisateur "
	        + "WHERE av.no_article = :no_article "
	        + "AND (e.montant_enchere = ("
	        + "    SELECT MAX(montant_enchere) "
	        + "    FROM ENCHERES "
	        + "    WHERE no_article = av.no_article"
	        + ") OR e.montant_enchere IS NULL)";







	

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void creerArticle(ArticleVendu articleVendu) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nom_article", articleVendu.getNomArticle());
		mapSqlParameterSource.addValue("description", articleVendu.getDescription());
		mapSqlParameterSource.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
		mapSqlParameterSource.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
		mapSqlParameterSource.addValue("prix_initial", articleVendu.getMiseAPrix());
		mapSqlParameterSource.addValue("no_utilisateur", articleVendu.getVend().getNoUtilisateur());
		mapSqlParameterSource.addValue("no_categorie", articleVendu.getCategorie().getNoCategorie());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			articleVendu.setNoArticle(keyHolder.getKey().intValue());
		}
	}
	
	
	/*
	 * Utilisation de la requete WHERE 1=1 
	 * teste les conditions des requetes pour chaque filtre dans une seule methode
	 * 
	 */
	
	@Override
	public List<ArticleVendu> findAll(int noCategorie, String nomArticle, int noUtilisateur, List<String> check) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		String requeteSql = FIND_ALL;
		
		
		if (noCategorie != 0) {
			requeteSql += FIND_ARTICLE_BY_CATEGORIE;
			mapSqlParameterSource.addValue("no_categorie", noCategorie);
		}
		if (nomArticle != null && !nomArticle.isBlank() ) {
			requeteSql += FIND_ARTICLE_BY_NAME;
			mapSqlParameterSource.addValue("nom_article", "%" + nomArticle + "%");
		}
		for (String filtreChoix : check) {
			if (filtreChoix.equals("achat1")) {
				requeteSql += DATE_ENCHERE_EN_COURS;
			}
			
			if (filtreChoix.equals("achat2")) {
				requeteSql += DATE_ENCHERE_EN_COURS + ENCHERE_UTILISATEUR;
				mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
			}
			
			if (filtreChoix.equals("achat3")) {
				requeteSql += DATE_FIN_ENCHERE + ENCHERE_UTILISATEUR;	
				mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
			}
			
			if (filtreChoix.equals("vente1")) {
				requeteSql += DATE_ENCHERE_EN_COURS + MES_VENTES;
				mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
			}
			
			if (filtreChoix.equals("vente2")) {
				requeteSql += DATE_ENCHERE_NON_DEBUTEE + MES_VENTES;
				mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
			}
			
			if (filtreChoix.equals("vente3")) {
				requeteSql += DATE_FIN_ENCHERE + MES_VENTES;
				mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
			}
		}
		
		return jdbcTemplate.query(requeteSql, mapSqlParameterSource, new ArticleVenduRowMapper());

	}



	@Override
	public ArticleVendu findById(int noArticle) {
	    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
	    mapSqlParameterSource.addValue("no_article", noArticle);



	    ArticleVendu article = jdbcTemplate.queryForObject(FIND_ARTICLE_BY_ID, mapSqlParameterSource, new ArticleVenduDetailRowMapper());


	    System.out.println("Article récupéré : " + article);

	    return article;
	}
	
	

	class ArticleVenduRowMapper implements RowMapper<ArticleVendu>{
		
		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException{
			ArticleVendu av = new ArticleVendu();
			av.setNoArticle(rs.getInt("no_article"));
			av.setNomArticle(rs.getString("nom_article"));
			av.setDescription(rs.getString("description"));
			av.setDateDebutEncheres(rs.getObject("date_debut_encheres", LocalDateTime.class));
			av.setDateFinEncheres(rs.getObject("date_fin_encheres", LocalDateTime.class));
			av.setMiseAPrix(rs.getInt("prix_initial"));
			av.setPrixVente(rs.getInt("prix_vente"));
						
			
			
			Utilisateur vend = new Utilisateur();
			vend.setNoUtilisateur(rs.getInt("u_no_utilisateur"));
			vend.setPseudo(rs.getString("pseudo"));
			av.setVend(vend);
			
			Categorie cat = new Categorie();
			cat.setNoCategorie(rs.getInt("no_categorie"));
			av.setCategorie(cat);
			
			 // Mapper l'enchère
		    Enchere enchere = new Enchere();
		    enchere.setMontantEnchere(rs.getInt("montant_enchere"));
			
			// Mapper l'encherisseur
		    Utilisateur encherisseur = new Utilisateur();
		    encherisseur.setNoUtilisateur(rs.getInt("u2_no_utilisateur"));
		    enchere.setEncherisseur(encherisseur);
			
		    av.setEnchere(Arrays.asList(enchere));
		    
			return av;
		}
	}


	class ArticleVenduDetailRowMapper implements RowMapper<ArticleVendu>{
		
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
			 vend.setPseudo(rs.getString("u_pseudo_vendeur"));
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
			
			 // Mapper l'enchère
		    Enchere enchere = new Enchere();
		    enchere.setMontantEnchere(rs.getInt("e_montant_enchere"));
			
			// Mapper l'encherisseur
		    Utilisateur encherisseur = new Utilisateur();
		    encherisseur.setNoUtilisateur(rs.getInt("u2_no_utilisateur"));
		    encherisseur.setPseudo(rs.getString("u2_pseudo_encherisseur"));
		    enchere.setEncherisseur(encherisseur);
		    
		    av.setEnchere(Arrays.asList(enchere));
		    
			return av;
		}
	}

	
}
