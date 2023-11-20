package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.EquipeComplete;
import modele.Equipe;
import modele.Joueur;
import modele.Partie;
import modele.Poule;
import modele.Tournoi;

public class DaoPartie implements Dao<Partie,Integer>{
	
	private Connexion connexion;
	
	
	public DaoPartie(Connexion connexion) {
		this.connexion = connexion;
		
	}

	@Override
	public void createTable() throws SQLException {
		String createTableSql = "CREATE TABLE Partie("
				+ "Id_Match INT NOT NULL"
				+ "Id_Partie INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "Nom_Equipe VARCHAR(50)"
				+ "PRIMARY KEY(Numero_Partie),"
				+ "FOREIGN KEY(Id_Match) REFERENCES Matche(Id_Match),"
				+ "FOREIGN KEY(Nom_Equipe) REFERENCES Equipe(Nom_Equipe)";
				
		Statement createTable;
		
		createTable = connexion.getConnexion().createStatement();
		createTable.execute(createTableSql);
        System.out.println("Table 'Partie' créée avec succès");
	}

	@Override
	public boolean dropTable() throws SQLException {
		Statement deleteTable;
		deleteTable = connexion.getConnexion().createStatement();
		return deleteTable.execute("drop table Partie");
	}

	@Override
	public List<Partie> getAll() throws Exception {
		Statement getAll = connexion.getConnexion().createStatement();
		ResultSet resultat = getAll.executeQuery("SELECT * FROM Partie");
		List<Partie> sortie = new ArrayList<>();
		while(resultat.next()) {
			Partie partie = new Partie(
					new Equipe(resultat.getString("Nom_Equipe")),
					resultat.getInt("Id_Match"));
			partie.setNumeroPartie(resultat.getInt("Id_Partie"));
			sortie.add(partie);
		}
		return sortie;
	}

	@Override
	public Partie getById(Integer... id) throws Exception {
		PreparedStatement getById = connexion.getConnexion().prepareStatement("SELECT * FROM Partie WHERE Id_Match = ? AND Numero_Partie = ?");
		getById.setInt(1, id[0]);
		getById.setInt(2, id[1]);
		ResultSet resultat = getById.executeQuery();
		Partie partie = new Partie(
				new Equipe(resultat.getString("Nom_Equipe")),
				resultat.getInt("Id_Match"));
		partie.setNumeroPartie(resultat.getInt("Id_Partie"));
		return partie;
	}

	@Override
	public boolean add(Partie value) throws Exception {
		PreparedStatement add = connexion.getConnexion().prepareStatement(
				"INSERT INTO Partie(Id_Match,Nom_Equipe) values (?,?)");
		add.setInt(1, value.getIdMatche());
		return add.execute();
	}

	@Override
	public boolean update(Partie value) throws Exception {
		PreparedStatement update = connexion.getConnexion().prepareStatement(
				"UPDATE Partie SET "
				+ "Nom_Equipe = ?"
				+ "Id_Match = ?"
				+ "WHERE Id_Partie = ?");
		update.setString(1, value.getEquipeGagnante().getNom());
		update.setInt(2, value.getIdMatche());
		update.setInt(3, value.getNumeroPartie());
		return update.execute();
	}

	@Override
	public boolean delete(Integer... value) throws Exception {
		PreparedStatement delete = connexion.getConnexion().prepareStatement(
				"DELETE FROM Partie where Numero_Partie = ?");
		delete.setInt(1,value[0]);
		return delete.execute();
	}
}

