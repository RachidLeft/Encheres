package fr.eni.encheres.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.ArticleVendu;

public class RetraitDAOImpl implements RetraitDAO{
	
	private static final String INSERT = "INSERT INTO RETRAITS no_article, rue, code_postal, ville values :noArticle, :rue, :codePostal, :ville";
	
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public void create(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", article.getNoArticle());
		map.addValue("rue", article.getLieuRetrait().getRue());
		map.addValue("codePostal", article.getLieuRetrait().getCodePostal());
		map.addValue("ville", article.getLieuRetrait().getVille());
		
		namedParameterJdbcTemplate.update(INSERT, map);
		
	}

}
