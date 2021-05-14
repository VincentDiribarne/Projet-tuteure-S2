package Version_Enseignant;

public class Exercice {
	
	private String nom;
	private Consigne consigne;
	private Ressource ressource;
	private Aide aide;
	
	public Exercice(String nom, Consigne consigne, Ressource ressource, Aide aide) {
		this(nom, consigne, ressource);
		this.aide = aide;
	}
	
	public Exercice(String nom, Consigne consigne, Ressource ressource) {
		super();
		this.nom = nom;
		this.consigne = consigne;
		this.ressource = ressource;
	}
}
