package fr.eni.encheres.dal;

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
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	
	private final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie)"
			+ " VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :no_utilisateur, :no_categorie)";
	private final String FIND_ALL = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, av.date_fin_encheres, av.prix_initial, av.no_utilisateur, av.no_categorie, u.pseudo FROM ARTICLES_VENDUS AS av"
			+ " JOIN UTILISATEURS AS u ON av.no_utilisateur = u.no_utilisateur JOIN CATEGORIES AS c ON av.no_categorie = c.no_categorie";
	private final String FIND_ARTICLE_BY_CATEGORIE = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, av.date_fin_encheres, av.prix_initial, av.no_utilisateur, av.no_categorie, u.pseudo FROM ARTICLES_VENDUS AS av"
			+ " JOIN UTILISATEURS AS u ON av.no_utilisateur = u.no_utilisateur JOIN CATEGORIES AS c ON av.no_categorie = c.no_categorie WHERE av.no_categorie = :no_categorie";
	private final String FIND_ARTICLE_BY_NAME = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, av.date_fin_encheres, av.prix_initial, av.no_utilisateur, av.no_categorie, u.pseudo FROM ARTICLES_VENDUS AS av"
			+ " JOIN UTILISATEURS AS u ON av.no_utilisateur = u.no_utilisateur JOIN CATEGORIES AS c ON av.no_categorie = c.no_categorie WHERE av.nom_article LIKE :nom_article";
	private final String FIND_ARTICLE_BY_NAME_AND_CATEGORIE = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, av.date_fin_encheres, av.prix_initial, av.no_utilisateur, av.no_categorie, u.pseudo FROM ARTICLES_VENDUS AS av"
			+ " JOIN UTILISATEURS AS u ON av.no_utilisateur = u.no_utilisateur JOIN CATEGORIES AS c ON av.no_categorie = c.no_categorie WHERE av.nom_article LIKE :nom_article AND av.no_categorie = :no_categorie";

	

	
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
	

	@Override
	public List<ArticleVendu> findAll() {
		
		return jdbcTemplate.query(FIND_ALL, new ArticleVenduRowMapper());
	}

	
	@Override
	public List<ArticleVendu> findArticleById(int noCategorie) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_categorie", noCategorie);
		return jdbcTemplate.query(FIND_ARTICLE_BY_CATEGORIE, mapSqlParameterSource, new ArticleVenduRowMapper());
	}

	@Override
	public List<ArticleVendu> findArticleByName(String nomArticle) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nom_article", nomArticle);
		return jdbcTemplate.query(FIND_ARTICLE_BY_NAME, mapSqlParameterSource, new ArticleVenduRowMapper());
	}
	
	@Override
	public List<ArticleVendu> findArticleByIdAndCategorie(int noCategorie, String nomArticle) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_categorie", noCategorie);
		mapSqlParameterSource.addValue("nom_article", nomArticle);
		return jdbcTemplate.query(FIND_ARTICLE_BY_NAME_AND_CATEGORIE, mapSqlParameterSource, new ArticleVenduRowMapper());
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
			
			
			
			Utilisateur vend = new Utilisateur();
			vend.setNoUtilisateur(rs.getInt("no_utilisateur"));
			vend.setPseudo(rs.getString("pseudo"));
			av.setVend(vend);
			
			Categorie cat = new Categorie();
			cat.setNoCategorie(rs.getInt("no_categorie"));
			av.setCategorie(cat);
			
			return av;
		}
	}
	
}
