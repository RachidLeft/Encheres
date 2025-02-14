package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

public class RetraitDAOImpl implements RetraitDAO{
	
	private static final String INSERT = "INSERT INTO RETRAITS no_article, rue, code_postal, ville values :noArticle, :rue, :codePostal, :ville";
	private static final String SELECT_BY_ID ="SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article = :noArticle";
	
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

	@Override
	public Retrait read(int noArticle) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, map, new RetraitRowMapper());
	}
	
	
	class RetraitRowMapper implements RowMapper<Retrait> {

		@Override
		public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {

			Retrait r = new Retrait();
			r.setRue(rs.getString("rue"));
			r.setVille(rs.getString("ville"));
			r.setCodePostal(rs.getString("code_postal"));
			
			// Association pour l'article vendu
			ArticleVendu a = new ArticleVendu();
			a.setNoArticle(rs.getInt("no_article"));
			r.setArticle(a);
			return r;
		}

	}
}
