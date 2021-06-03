package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;


import com.ensta.librarymanager.dao.MembreDao;

import com.ensta.librarymanager.dao.impl.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreService;

public class MembreServiceImpl implements MembreService {
	
	private static MembreServiceImpl instance;
	
	private MembreServiceImpl() {}	
	
	public static MembreService getInstance() {
		if(instance == null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<>();		
		try {
			membres = membreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membres;
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Membre> membres = new ArrayList<>();
		List<Membre> membresDispo = new ArrayList<>();
		try {
			membres = membreDao.getList();
			for (int i=0; i<membres.size();i++) {
				if (empruntService.isEmpruntPossible(membres.get(i))) {
					membresDispo.add(membres.get(i));
				}			
			}
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membres;
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();	
		if (nom == "" || prenom == "") {
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
				i = membreDao.create(nom.toUpperCase(),prenom,adresse,email,telephone);
			}  catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			} 
			return i;
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();	
		if (membre.getNom() == null || membre.getNom() == "" || membre.getPrenom() == null || membre.getPrenom() == "") {
			try {	
				throw new ServiceException("hhh");
			} catch (ServiceException e) {
				System.out.println("Nom ou prenom invalide");
			}
		}
		else {
			try {
				membre.setNom(membre.getNom().toUpperCase());
				membreDao.update(membre);
			} catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();	
		try {
			membreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}	
	}

	@Override
	public int count() throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();	
		int i = 0;
		try {
			i = membreDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return i;
	}

}
