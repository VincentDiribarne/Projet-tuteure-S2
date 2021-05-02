package Version_Enseignant;

public class ExerciceModeEntrainement extends Exercice {

	//Constructeurs
	public ExerciceModeEntrainement(String nom, Consigne consigne, Ressource ressource, Aide aide) {
		super(nom, consigne, ressource, aide);
	}
	
	public ExerciceModeEntrainement(String nom, Consigne consigne, Ressource ressource) {
		super(nom, consigne, ressource);
	}

}
