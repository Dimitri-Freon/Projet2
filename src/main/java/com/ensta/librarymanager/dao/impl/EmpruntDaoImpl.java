package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.model.EnumAbo;
import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {
	
	private static EmpruntDaoImpl instance;
	
	private EmpruntDaoImpl() {}	
	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}

	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e"
					+ "INNER JOIN membre ON membre.id = e.idMembre"
					+ "INNER JOIN livre ON livre.id = e.idLivre"
					+ "ORDER BY dateRetour DESC;");
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre") , res.getString("auteur"), res.getString("isbn") );
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), EnumAbo.valueOf( res.getString("abonnement") ) );
				Emprunt e = new Emprunt(res.getInt("id"), membre, livre , res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts");
		}
		return emprunts;
	}
	

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		ResultSet res = null;
		//Connection connection = null;
		//PreparedStatement preparedStatement = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e"
					+ "INNERJOIN membre ON membre.id = e.idMembre"
					+ "INNERJOIN livre ON livre.id = e.idLivre"
					+ "WHERE dateRetour IS NULL;");
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre") , res.getString("auteur"), res.getString("isbn") );
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), EnumAbo.valueOf( res.getString("abonnement") ) );
				Emprunt e = new Emprunt(res.getInt("id"), membre, livre , res.getDate("dateEmprunt").toLocalDate(), null   );
				emprunts.add(e);
			}
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts");
		} 
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e"
					+ "INNERJOIN membre ON membre.id = e.idMembre"
					+ "INNERJOIN livre ON livre.id = e.idLivre"
					+ "WHERE dateRetour IS NULL AND membre.id = ?;");
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre") , res.getString("auteur"), res.getString("isbn") );
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), EnumAbo.valueOf( res.getString("abonnement") ) );
				Emprunt e = new Emprunt(res.getInt("id"), membre, livre , res.getDate("dateEmprunt").toLocalDate(), null   );
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts");
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e"
					+ "INNERJOIN membre ON membre.id = e.idMembre"
					+ "INNERJOIN livre ON livre.id = e.idLivre"
					+ "WHERE dateRetour IS NULL AND livre.id = ?;");
			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre") , res.getString("auteur"), res.getString("isbn") );
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), EnumAbo.valueOf( res.getString("abonnement") ) );
				Emprunt e = new Emprunt(res.getInt("id"), membre, livre , res.getDate("dateEmprunt").toLocalDate(), null   );
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts");
		} 
		return emprunts;
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
					+ "FROM emprunt AS e"
					+ "INNER JOIN membre ON membre.id = e.idMembre"
					+ "INNER JOIN livre ON livre.id = e.idLivre"
					+ "WHERE e.id = ?;");
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre") , res.getString("auteur"), res.getString("isbn") );
			Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), EnumAbo.valueOf( res.getString("abonnement") ) );
			Emprunt emprunt = new Emprunt(res.getInt("id"), membre, livre , res.getDate("dateEmprunt").toLocalDate(), ( (res.getDate("dateRetour") == null) ? null:res.getDate("dateRetour").toLocalDate()  ) );
			return emprunt;
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du emprunt: id=" + id);
		} 
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)"
					+ "VALUES (?, ?, ?, ?);");
			preparedStatement.setInt(1, idMembre );
			preparedStatement.setInt(2, idLivre );
			preparedStatement.setDate(3, Date.valueOf(dateEmprunt) );
			preparedStatement.setDate(4, null );
			preparedStatement.executeUpdate();

			System.out.println("CREATE: ");
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt ");
		} 
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE emprunt "
					+ "SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? "
					+ "WHERE id = ?;");
			preparedStatement.setString(1, String.valueOf( emprunt.getMembre().getId() ) );
			preparedStatement.setString(2, String.valueOf( emprunt.getLivre().getId() )   );
			preparedStatement.setString(3, String.valueOf( emprunt.getDateEmprunt() )  );
			preparedStatement.setString(4, String.valueOf( emprunt.getDateRetour() )  );			
			preparedStatement.setString(5, String.valueOf( emprunt.getId() ) );
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du emprunt: " + emprunt);
		} 
	}

	@Override
	public int count() throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS count FROM emprunt;");
			res = preparedStatement.executeQuery();
			if ( !res.next() )  return 0;
			else return res.getInt("count");	
		} catch (SQLException e) {
			throw new DaoException("Problème lors du comptage des emprunts");
		}
	}

}
