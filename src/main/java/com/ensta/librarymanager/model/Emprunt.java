package com.ensta.librarymanager.model;
import java.time.LocalDate;

public class Emprunt {
	protected int id;
	protected Membre membre;
	protected Livre livre;
	protected LocalDate dateEmprunt;
	protected LocalDate dateRetour;
	
	public Emprunt() {
		super();
	}
	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		super();
		this.id = id;
		this.membre= membre;
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}
}
