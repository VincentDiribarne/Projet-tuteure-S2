package Version_Enseignant;

public class ExerciceModeEvaluation extends Exercice {

	//Attributs d'objets
	private int tempsAutorise;
	private boolean termine = false;
	
	//Constructeurs
	public ExerciceModeEvaluation(String nom, Consigne consigne, Ressource ressource, int tempsAutorise) {
		super(nom, consigne, ressource);
		this.tempsAutorise = tempsAutorise;
	}

}
