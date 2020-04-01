package Projet_stanfordNLP;

/**
 * 
 * @author Chen SUN
 *Une classe pour créer un objet qui contient deux {@code String}, un pour 
 *l'entité qu'on a trouvé depuis un corpus l'autre l'étiquette associée à cette entité.
 *
 */
public class TaggedEntite {
	
	protected String entite;
	protected String tag;
	
	/**
	 * Constructeur de la classe
	 * 
	 * @param a1 l'entité trouvée
	 * @param a2 l'étiquette de cette entité
	 */
	public TaggedEntite(String a1, String a2) {
		
		entite = a1;
		tag = a2;
		
	}
	
	/**
	 * 
	 * @return entité trouvée sous format de {@code String}
	 */
	protected String getEntite() {
		return entite;
	}

	
	/**
	 * 
	 * @return l'étiquette de cette entité sous format de {@code String}
	 */
	protected String getTag() {
		return tag;
	}

	/**
	 * @return {@code Override} elle renvoie l'objet de cette classe,
	 * par exemple "entité: Raphael-->tag: PERSON"
	 */
	@Override
	public String toString(){
		return "entité: "+ getEntite() + "-->" + "tag: " + getTag();
	}
	
}
