package dao;

import modele.Arbitre;
import modele.Saison;
import modele.Selection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoSelection implements Dao<Selection, Object> {

	private Connexion connexion;
	private DaoArbitre daoarbitre;
	private DaoSaison daosaison;

	public DaoSelection(Connexion connexion) {
		this.connexion = connexion;
		this.daoarbitre = new DaoArbitre(connexion);
		this.daosaison = new DaoSaison(connexion);
	}

	/**
	 * Crée la table d'association Selection qui fait la liaison entre les arbitres et la saison
	 *
	 * @param connexion
	 * @throws SQLException
	 */
	public static void createTable(Connexion connexion) throws SQLException {
		String createTableSql = "CREATE TABLE Selection("
				+ "Nom VARCHAR(50), "
				+ "Prenom VARCHAR(50), "
				+ "Telephone VARCHAR(50), "
				+ "Annee INT, "
				+ "PRIMARY KEY(Nom, Prenom, Telephone, Annee), "
				+ "FOREIGN KEY(Nom,Prenom,Telephone) REFERENCES Arbitre(Nom,Prenom,Telephone), "
				+ "FOREIGN KEY(Annee) REFERENCES Saison(Annee))";

		try (Statement createTable = connexion.getConnection().createStatement()) {
			createTable.execute(createTableSql);
			System.out.println("Table 'Selection' créée avec succès");
		}

	}

	/**
	 * Supprime la table d'association Selection
	 *
	 * @param connexion
	 * @return
	 * @throws SQLException
	 */
	public static boolean dropTable(Connexion connexion) throws SQLException {
		try (Statement deleteTable = connexion.getConnection().createStatement()) {
			System.out.println("Table 'Selection' supprimée avec succès");
			return deleteTable.execute("drop table Selection");
		}
	}

	/**
	 * Renvoie toutes les associations d'arbitres et de saisons
	 */
	@Override
	public List<Selection> getAll() throws Exception {
		try (Statement getAll = connexion.getConnection().createStatement()) {
			ResultSet resultat = getAll.executeQuery("SELECT * FROM Selection");
			List<Selection> sortie = new ArrayList<>();
			Selection selection = null;
			while (resultat.next()) {
				selection = new Selection(
						daoarbitre.getById(resultat.getString("Nom"), resultat.getString("Prenom"), resultat.getString("Telephone")).get(),
						daosaison.getById(resultat.getInt("Annee")).get());
				sortie.add(selection);
			}
			return sortie;
		}
	}

	/**
	 * Renvoie une association d'une saison et d'un arbitre
	 * Les paramètres sont placés dans cet ordre : Nom (STRING), Prenom (STRING), Telephone (INTEGER), Annee (INTEGER)
	 */
	@Override
	public Optional<Selection> getById(Object... id) throws Exception {
		try (PreparedStatement getById = connexion.getConnection().prepareStatement("SELECT * FROM Selection WHERE Nom = ? AND Prenom = ? AND Telephone = ? AND Annee = ?")) {
			getById.setString(1, (String) id[0]);
			getById.setString(2, (String) id[1]);
			getById.setInt(3, (Integer) id[2]);
			getById.setInt(4, (Integer) id[3]);
			ResultSet resultat = getById.executeQuery();
			Selection selection = null;
			if (resultat.next()) {
				selection = new Selection(
						daoarbitre.getById(resultat.getString("Nom"), resultat.getString("Prenom"), resultat.getString("Telephone")).get(),
						daosaison.getById(resultat.getInt("Annee")).get());

			}
			return Optional.ofNullable(selection);
		}
	}

	/**
	 * Permet à partir d'un objet Selection, de lier une saison à un arbitre
	 */
	@Override
	public boolean add(Selection value) throws Exception {
		try (PreparedStatement add = connexion.getConnection().prepareStatement(
				"INSERT INTO Selection(Nom,Prenom,Telephone,Annee) values (?,?,?,?)")) {
			add.setString(1, value.getArbitre().getNom());
			add.setString(2, value.getArbitre().getPrenom());
			add.setString(3, value.getArbitre().getNumeroTelephone());
			add.setInt(4, value.getSaison().getAnnee());
			return add.execute();
		}
	}

	/**
	 * Ne pas utiliser
	 */
	@Override
	public boolean update(Selection value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Supprime à partir de la clé primaire, l'association d'un arbitre et d'une saison
	 * Les paramètres sont placés dans cet ordre : Nom (STRING), Prenom (STRING), Telephone (INTEGER), Annee (INTEGER)
	 */
	@Override
	public boolean delete(Object... value) throws Exception {
		try (PreparedStatement delete = connexion.getConnection().prepareStatement(
				"DELETE FROM Selection where Nom = ? AND Prenom = ? AND Telephone = ? AND Annee = ?")) {
			delete.setString(1, (String) value[0]);
			delete.setString(2, (String) value[1]);
			delete.setString(3, (String) value[2]);
			delete.setInt(4, (Integer) value[3]);
			return delete.execute();
		}
	}

	/**
	 * Renvoie tous les arbitres d'une saison
	 * Les paramètres sont placés dans cet ordre : Annee (INTEGER)
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<Arbitre> getArbitreBySaison(Object... value) throws Exception {
		try (PreparedStatement getArbitreBySaison = connexion.getConnection().prepareStatement(
				"SELECT * FROM Selection WHERE Annee = ?")) {
			getArbitreBySaison.setInt(1, (Integer) value[0]);
			ResultSet resultat = getArbitreBySaison.executeQuery();
			List<Arbitre> sortie = new ArrayList<>();
			while (resultat.next()) {
				sortie.add(daoarbitre.getById(resultat.getString("Nom"), resultat.getString("Prenom"), resultat.getString("Telephone")).get());
			}
			return sortie;
		}
	}


	/**
	 * Renvoie tous les saison d'un arbitre
	 * Les paramètres sont placés dans cet ordre : Nom Arbitre (STRING) , Prenom Arbitre (STRING) , Telephone (STRING)
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<Saison> getSaisonByArbitre(Object... value) throws Exception {
		try (PreparedStatement getSaisonByArbitre = connexion.getConnection().prepareStatement(
				"SELECT * FROM Selection WHERE Nom = ? AND Prenom = ? AND Telephone = ?")) {
			getSaisonByArbitre.setString(1, (String) value[0]);
			getSaisonByArbitre.setString(2, (String) value[1]);
			getSaisonByArbitre.setString(3, (String) value[2]);
			ResultSet resultat = getSaisonByArbitre.executeQuery();
			List<Saison> sortie = new ArrayList<>();
			while (resultat.next()) {
				sortie.add(daosaison.getById(resultat.getInt("Annee")).get());
			}
			return sortie;
		}
	}

	@Override
	public String visualizeTable() throws Exception {
		String s = "_______________Selection_______________________" + "\n";
		List<Selection> l = this.getAll();
		for (Selection a : l) {
			s += a.toString() + "\n";
		}
		s += "\n\n\n";
		return s;
	}

}
