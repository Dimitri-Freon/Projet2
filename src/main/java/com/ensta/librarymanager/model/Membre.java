package com.ensta.librarymanager.model;

public class Membre {
	
	protected int id;
	protected String nom;
	protected String prenom;
	protected String adresse;
	protected String email;
	protected String telephone;
	protected EnumAbo abonnement;
	
	public Membre() {
		super();
	}
	public Membre(int id, String nom, String prenom, String adresse, String email, String telephone,
			EnumAbo abonnement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.abonnement = abonnement;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public EnumAbo getAbonnement() {
		return abonnement;
	}
	public void setAbonnement(EnumAbo abonnement) {
		this.abonnement = abonnement;
	}	
	
}
