package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
    private JdbcTemplate jdbcTemplate;
    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public void ajouter(Utilisateur utilisateur) {

    }

    @Override
    public List<Utilisateur> findAll() {
        return null;
    }

    @Override
    public Optional<Utilisateur> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void deleteById(int id) {

    }

    class RowMapper implements RowMapper<Utilisateur> {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(rs.getInt("id"));
            utilisateur.setMailPro(rs.getString("mailPro"));
            utilisateur.setMdp(rs.getString("mdp"));
            utilisateur.setUserRole(rs.getString("userRole"));
            return utilisateur;
        }
    }
}


