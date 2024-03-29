package dao;

import modele.Arbitre;
import modele.Equipe;
import modele.Saison;
import modele.Tournoi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoSaison implements Dao<Saison, Integer> {

	private Connexion connexion;

	public DaoSaison(Connexion connexion) {
		this.connexion = connexion;
	}

	/**
	 * Crée la table Saison
	 *
	 * @param connexion
	 * @throws SQLException
	 */
	public static void createTable(Connexion connexion) throws SQLException {
		String createTableSql = "CREATE TABLE Saison("
				+ "Annee INT,"
				+ "PRIMARY KEY(Annee))";
		try (Statement createTable = connexion.getConnection().createStatement()) {
			createTable.execute(createTableSql);
			System.out.println("Table 'Saison' créée avec succès");
		}
	}

	/**
	 * Supprime la table saison
	 *
	 * @param connexion
	 * @return
	 * @throws SQLException
	 */
	public static boolean dropTable(Connexion connexion) throws SQLException {
		try (Statement deleteTable = connexion.getConnection().createStatement()) {
			System.out.println("Table 'Saison' supprimée avec succès");
			return deleteTable.execute("drop table Saison");
		}
	}

	/**
	 * Renvoie la liste de toutes les saisons (années)
	 */
	@Override
	public List<Saison> getAll() throws Exception {
		try (Statement getAll = connexion.getConnection().createStatement()) {
			ResultSet resultat = getAll.executeQuery("SELECT * FROM Saison");
			List<Saison> sortie = new ArrayList<>();
			while (resultat.next()) {
				Saison saison = new Saison(
						resultat.getInt("Annee"));
				sortie.add(saison);
			}
			return sortie;
		}
	}

	/**
	 * Renvoie une année pécise
	 * Les paramètres sont placés dans cet ordre : Annee (INTEGER)
	 */
	@Override
	public Optional<Saison> getById(Integer... id) throws Exception {
		try (PreparedStatement getById = connexion.getConnection().prepareStatement("SELECT * FROM Saison WHERE Annee = ?")) {
			getById.setInt(1, id[0]);
			ResultSet resultat = getById.executeQuery();
			Saison saison = null;
			if (resultat.next()) {
				saison = new Saison(
						resultat.getInt("Annee"));

			}
			return Optional.ofNullable(saison);
		}
	}

	/**
	 * Ajoute une saison à la table saison à partir d'un objet saison
	 */
	@Override
	public boolean add(Saison value) throws Exception {
		try (PreparedStatement add = connexion.getConnection().prepareStatement(
				"INSERT INTO Saison(Annee) values (?)")) {
			add.setInt(1, value.getAnnee());
			return add.execute();
		}
	}

	/**
	 * ne pas utiliser
	 */
	@Override
	public boolean update(Saison value) throws Exception {
		return false;
	}

	/**
	 * supprime une saison de la table saison
	 */
	@Override
	public boolean delete(Integer... value) throws Exception {
		try (PreparedStatement delete = connexion.getConnection().prepareStatement(
				"DELETE FROM Saison where Annee = ?")) {
			delete.setInt(1, value[0]);
			List<Tournoi> tournois = FactoryDAO.getDaoTournoi(connexion).getTournoiBySaison(FactoryDAO.getDaoSaison(connexion).getById(value[0]).get());
			List<Equipe> equipes = FactoryDAO.getDaoInscription(connexion).getEquipeBySaison(value[0]);
			List<Arbitre> arbitres = FactoryDAO.getDaoSelection(connexion).getArbitreBySaison(FactoryDAO.getDaoSaison(connexion).getById(value[0]).get());
			for (Tournoi t : tournois) {
				FactoryDAO.getDaoTournoi(connexion).delete(t.getSaison().getAnnee(), t.getNom());
			}
			for (Equipe e : equipes) {
				FactoryDAO.getDaoInscription(connexion).delete(value[0], e.getNom());
			}
			for (Arbitre a : arbitres) {
				FactoryDAO.getDaoSelection(connexion).delete(a.getNom(), a.getPrenom(), a.getNumeroTelephone(), value[0]);
			}

			return delete.execute();
		}
	}

	/**
	 * Renvoie la dernière saison ajoutée de la table
	 *
	 * @return
	 * @throws SQLException
	 */
	public Saison getLastSaison() throws SQLException {
		try (PreparedStatement lastInsert = connexion.getConnection().prepareStatement(
				"SELECT Annee "
						+ "FROM Saison "
						+ "ORDER BY Annee DESC "
						+ "FETCH FIRST 1 ROW ONLY")) {
			ResultSet resultat = lastInsert.executeQuery();
			Saison saison = null;
			if (resultat.next()) {
				saison = new Saison(resultat.getInt("Annee"));
			}
			return saison;
		}
	}

	@Override
	public String visualizeTable() throws Exception {
		String s = "_______________Saison_______________________" + "\n";
		List<Saison> l = this.getAll();
		for (Saison a : l) {
			s += a.toString() + "\n";
		}
		s += "\n\n\n";
		return s;
	}

}
