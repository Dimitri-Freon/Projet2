package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.LivreDaoImpl;

public class LivreServiceImpl implements LivreService {
	
	private static LivreServiceImpl instance;
	
	private LivreServiceImpl() {}	
	
	public static LivreService getInstance() {
		if(instance == null) {
			instance = new LivreServiceImpl();
		}
		return instance;
	}
	@Override
	public List<Livre> getList() throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livres = new ArrayList<>();		
		try {
			livres = livreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livres;
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Livre> livres = new ArrayList<>();
		List<Livre> livresDispo = new ArrayList<>();
		int id;
		try {
			livres = livreDao.getList();
			for (int i=0; i<livres.size();i++) {
				id = (livres.get(i)).getId();
				if (empruntService.isLivreDispo(id)) {
					livresDispo.add(livres.get(i));
				}			
			}
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livres;
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();	
		if (titre == "") {
			try {	
				throw new ServiceException("hhh");
			} catch (ServiceException e) {
				System.out.println("Nom invalide");
				return -1;
			}
		}
		else {
			int i = -1;
			try {
				i = livreDao.create(titre,auteur,isbn);
			}  catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			} 
			return i;
		}
	}
	
	@Override
	public void update(Livre livre) throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();	
		if (livre.getTitre() == null || livre.getTitre() == "") {
			try {	
				throw new ServiceException("hhh");
			} catch (ServiceException e) {
				System.out.println("Nom invalide");
			}
		}
		else {
			try {
				livreDao.update(livre);
			} catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();	
		try {
			livreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}			
	}


	@Override
	public int count() throws ServiceException {
		LivreDao livreDao = LivreDaoImpl.getInstance();	
		int i = 0;
		try {
			i = livreDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return i;
	}

}
