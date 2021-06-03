package com.ensta.librarymanager.dao.impl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.utils.EstablishConnection;
import com.ensta.librarymanager.model.EnumAbo;

public class MembreDaoImpl implements MembreDao {
	
	private static MembreDaoImpl instance;
	
	private MembreDaoImpl() {}	
	
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement("SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;");
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Membre m = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"),
						res.getString("adresse"), res.getString("email"), res.getString("telephone"),
						EnumAbo.valueOf(res.getString("abonnement")));
				membres.add(m);
			}
			System.out.println("GET: " + membres);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres");
		} 
		return membres;
	}

	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement("SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;");
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(EnumAbo.valueOf(res.getString("abonnement")));
			}
			
			System.out.println("GET: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre: id=" + id);
		}
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO Membre (nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nom );
			preparedStatement.setString(2, prenom );
			preparedStatement.setString(3, adresse );
			preparedStatement.setString(4, email );
			preparedStatement.setString(5, telephone );
			preparedStatement.setString(6, EnumAbo.BASIC.name() );
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: " + nom + " " + prenom);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du membre " + nom + " " + prenom);
		} 
		return id;
	}

	@Override
	public void update(Membre membre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE Membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=?  WHERE id=?;");
			preparedStatement.setString(1, membre.getNom() );
			preparedStatement.setString(2, membre.getPrenom() );
			preparedStatement.setString(3, membre.getAdresse() );
			preparedStatement.setString(4, membre.getEmail() );
			preparedStatement.setString(5, membre.getTelephone() );
			preparedStatement.setString(6, membre.getAbonnement().name());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du membre: " + membre);
		} 
	}

	@Override
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Membre WHERE id=?;");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: " + id);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du membre: " + id);
		} 
	}
	
	@Override
	public int count() throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS count FROM membre;");
			res = preparedStatement.executeQuery();
			if ( !res.next() )  return 0;
			else return res.getInt("count");		
		} catch (SQLException e) {
			throw new DaoException("Problème lors du comptage des membres");
		}
	}

}
