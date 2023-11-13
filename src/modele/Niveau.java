package modele;

public enum Niveau {
	
	LOCAL("Local",1.0F),
	REGIONAL("Regional",1.5F),
	NATIONAL("National",2.0F),
	INTERNATIONAL("International",2.25F),
	INTERNATIONAL_CLASSE("International classe",3.0F);
	
	private float coefficient;
	private String nom;
	
	private Niveau(String nom,float coefficient) {
		this.nom=nom;
		this.coefficient=coefficient;
	}

	public float getCoefficient() {
		return coefficient;
	}
	
	public String getNom() {
		return nom;
	}
	
	public Niveau getNiveau(String nom) {
		for(Niveau n : Niveau.values()) {
			if (n.getNom().equals(nom)) {
				return n;
			}
		}
		return null;
	}
}