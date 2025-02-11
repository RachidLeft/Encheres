package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;  // Injection du PasswordEncoder

    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void ajouter(Utilisateur utilisateur) {
        String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 100, 0)";

        jdbcTemplate.update(sql, 
            utilisateur.getPseudo(), 
            utilisateur.getNom(), 
            utilisateur.getPrenom(), 
            utilisateur.getEmail(), 
            utilisateur.getTelephone(), 
            utilisateur.getRue(), 
            utilisateur.getCodePostal(), 
            utilisateur.getVille(), 
            passwordEncoder.encode(utilisateur.getMotDePasse()) // Encodage du mot de passe
        );
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM UTILISATEURS";
        return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public Optional<Utilisateur> findById(int id) {
        String sql = "SELECT * FROM UTILISATEURS WHERE id_client = ?";
        List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, new UtilisateurRowMapper(), id);
        return utilisateurs.isEmpty() ? Optional.empty() : Optional.of(utilisateurs.get(0));
    }

    @Override
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, credit = ?, administrateur = ? " +
                     "WHERE id_client = ?";

        jdbcTemplate.update(sql,
            utilisateur.getPseudo(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            utilisateur.getTelephone(),
            utilisateur.getRue(),
            utilisateur.getCodePostal(),
            utilisateur.getVille(),
            passwordEncoder.encode(utilisateur.getMotDePasse()), // Encodage du mot de passe
            utilisateur.getCredit(),
            utilisateur.isAdministrateur(),
            utilisateur.getNoUtilisateur()
        );
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM UTILISATEURS WHERE id_client = ?";
        jdbcTemplate.update(sql, id);
    }

    // Classe interne pour mapper les résultats SQL vers l'objet Utilisateur
    private static class UtilisateurRowMapper implements RowMapper<Utilisateur> {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setTelephone(rs.getString("telephone"));
            utilisateur.setRue(rs.getString("rue"));
            utilisateur.setCodePostal(rs.getString("code_postal"));
            utilisateur.setVille(rs.getString("ville"));
            utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
            utilisateur.setCredit(rs.getInt("credit"));
            utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
            return utilisateur;
        }
    }
}
