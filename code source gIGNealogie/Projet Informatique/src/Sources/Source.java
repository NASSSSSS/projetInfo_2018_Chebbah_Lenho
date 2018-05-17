package Sources;


/**
 * Classe abstraite source
 * @author formation
 *
 */
public abstract class Source {
	
	private int id_source;	
	private NatureSource nature;
	private Event evenement;
	private String abs_path;
	
	//Constructeur
	
	/**
	 * Constructeur la classe source
	 * @param file
	 */
	public Source(String abs_path) {
		this.abs_path = abs_path;
	}

	
	//setters
	
	public void setId_source(int id_source) {
		this.id_source = id_source;
	}


	public void setNature(NatureSource nature) {
		this.nature = nature;
	}

	public void setEvenement(Event evenement) {
		this.evenement = evenement;
	}
	
	
	//getters
	


	public NatureSource getNature() {
		return this.nature;
	}
	
	public Event getEvenement() {
		return this.evenement;
	}
	
	public String getAbsFile(){
		return this.abs_path;
	}
	
	public int getIdSource() {
		return this.id_source;
	}

	//M�thodes
	
	
	public void modifier_event(String event){
		
		if(event.equals("Naissance")) {
			this.setEvenement(Event.Naissance);
		}
		if(event.equals("Deces")) {
			this.setEvenement(Event.Deces);
		}
		if(event.equals("Bapteme")) {
			this.setEvenement(Event.Bapteme);
		}
		if(event.equals("Mariage")) {
			this.setEvenement(Event.Marriage);
		}
		if(event.equals("Divorce")) {
			this.setEvenement(Event.Divorce);
		}
		if(event.equals("Filiation")) {
			this.setEvenement(Event.Filiation);
		}
		else {
			System.out.println("EVENEMENT NON RECONNU"
					+ "NATURE DE L'EVENEMENT NON ENREGISTRE ");
		}
	}
}

	


