package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {
	
	private static LivreDaoImpl instance;
	
	private LivreDaoImpl() {}	
	
	public static LivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDaoImpl();
		}
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws DaoException {
		List<Livre> livres = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT id, titre, auteur, isbn FROM livre;");
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn") );
				livres.add(l);
			}
			System.out.println("GET: " + livres);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des livres");
		}
		return livres;
	}

	@Override
	public Livre getById(int id) throws DaoException {
		Livre livre = new Livre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("SELECT id, titre, auteur, isbn "
					+ "FROM livre "
					+ "WHERE id = ?;");
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				livre.setId(res.getInt("id"));
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
			}	
			System.out.println("GET: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre: id=" + id);
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, titre );
			preparedStatement.setString(2, auteur );
			preparedStatement.setString(3, isbn );
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: ");
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du livre ");
		}
		return id;
	}

	@Override
	public void update(Livre livre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;");
			preparedStatement.setString(1, livre.getTitre());
			preparedStatement.setString(2, livre.getAuteur());
			preparedStatement.setString(3, livre.getIsbn());
			preparedStatement.setInt(4, livre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du membre: " + livre);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Livre WHERE id=?;");
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
			preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS count FROM Livre");
			res = preparedStatement.executeQuery();
			if ( !res.next() )  return 0;
			else return res.getInt("count");	
		} catch (SQLException e) {
			throw new DaoException("Problème lors du comptage des livres");
		}
	}

}
