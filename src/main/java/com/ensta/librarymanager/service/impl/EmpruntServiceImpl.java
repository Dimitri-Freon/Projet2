package com.ensta.librarymanager.service.impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.impl.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.EnumAbo;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;

public class EmpruntServiceImpl implements EmpruntService  {
	
	private static EmpruntServiceImpl instance;
	
	private EmpruntServiceImpl() {}	
	
	public static EmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntServiceImpl();
		}
		return instance;
	}
	

	@Override
	public List<Emprunt> getList() throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();		
		try {
			emprunts = empruntDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();		
		try {
			return empruntDao.getListCurrent();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();		
		try {
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();		
		try {
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();	
		try {
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			Emprunt emprunt = empruntDao.getById(id);
			emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}

	@Override
	public int count() throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();	
		int i = 0;
		try {
			i = empruntDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return i;
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		List<Emprunt> emprunts = this.getListCurrentByLivre(idLivre);
		for (int i=0; i<emprunts.size();i++) {
			if (emprunts.get(i).getDateRetour() != null)
				return true;			
		}
		return false;		
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		List<Emprunt> emprunts = this.getListCurrentByMembre(membre.getId());
		int nombre_max = 0;
		if (membre.getAbonnement() == EnumAbo.BASIC)
			nombre_max = 2;
		else if (membre.getAbonnement() == EnumAbo.PREMIUM)
			nombre_max = 5;
		else if (membre.getAbonnement() == EnumAbo.VIP)
			nombre_max = 20;	
		return (emprunts.size() < nombre_max);
	}
}
