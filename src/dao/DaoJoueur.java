package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 

import modele.Joueur;

public class DaoJoueur implements Dao<Joueur,Integer>{
	
	private Connexion connexion;
	
	public DaoJoueur(Connexion connexion) {
		this.connexion = connexion;
	}

	@Override
	public void createTable() throws SQLException {
		String createTableSql = "CREATE TABLE Joueur("
				   +"Id_Joueur INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				   +"Pseudo VARCHAR(50),"
				   +"Nom_Equipe VARCHAR(50) NOT NULL,"
				   +"PRIMARY KEY(Id_Joueur),"
				   +"FOREIGN KEY(Nom_Equipe) REFERENCES Equipe(Nom_Equipe)";

		Statement createTable;
		
		createTable = connexion.getConnexion().createStatement();
		createTable.execute(createTableSql);
        System.out.println("Table 'Joueur' créée avec succès");
	}

	@Override
	public boolean dropTable() throws SQLException {
		Statement deleteTable;
		deleteTable = connexion.getConnexion().createStatement();
		return deleteTable.execute("drop table Joueur");
	}

	@Override
	public List<Joueur> getAll() throws Exception {
		Statement getAll = connexion.getConnexion().createStatement();
		ResultSet resultat = getAll.executeQuery("SELECT * FROM Joueur");
		List<Joueur> sortie = new ArrayList<>();
		while(resultat.next()) {
			Joueur joueur = new Joueur(
					resultat.getString("Pseudo"),
					resultat.getString("Nom_Equipe"));
			joueur.setId(resultat.getInt("Id_Joueur"));
			sortie.add(joueur);
		}
		return sortie;
	}

	@Override
	public Joueur getById(Integer... id) throws Exception {
		PreparedStatement getById = connexion.getConnexion().prepareStatement("SELECT * FROM Joueur WHERE Id_Joueur = ?");
		getById.setInt(1, id[0]);
		ResultSet resultat = getById.executeQuery();
		Joueur joueur = new Joueur(
				resultat.getString("Pseudo"),
				resultat.getString("Nom_Equipe"));
		joueur.setId(resultat.getInt("Id_Joueur"));
		return joueur;
	}

	@Override
	public boolean add(Joueur value) throws Exception {
		PreparedStatement add = connexion.getConnexion().prepareStatement(
				"INSERT INTO Joueur(Pseudo,Nom_Equipe) values (?,?)");
		add.setString(1, value.getPseudo());
		add.setString(2, value.getNom());
		return add.execute();
	}

	@Override
	public boolean update(Joueur value) throws Exception {
		PreparedStatement update = connexion.getConnexion().prepareStatement(
				"UPDATE Joueur SET "
				+"Pseudo = ? "
				+"Nom_Equipe = ? "
				+"WHERE Id_Joueur = ?");
		update.setString(1, value.getPseudo());
		update.setString(2, value.getNom());
		update.setInt(3, value.getId());
		return update.execute();		
	}

	@Override
	public boolean delete(Integer... value) throws Exception {
		PreparedStatement delete = connexion.getConnexion().prepareStatement(
				"DELETE FROM Joueur where Id_Joueur = ?");
		delete.setInt(1,value[0]);
		return delete.execute();
	}
	
	public List<Joueur> getJoueurParEquipe(String nom) throws Exception {
		PreparedStatement getAll = connexion.getConnexion().prepareStatement("SELECT * FROM Joueur WHERE Nom_Equipe = ?");
		getAll.setString(0, nom);
		ResultSet resultat = getAll.executeQuery();
		List<Joueur> sortie = new ArrayList<>();
		while(resultat.next()) {
			Joueur joueur = new Joueur(
					resultat.getString("Pseudo"),
					resultat.getString("Nom_Equipe"));
			joueur.setId(resultat.getInt("Id_Joueur"));
			sortie.add(joueur);
		}
		return sortie;
	}
}