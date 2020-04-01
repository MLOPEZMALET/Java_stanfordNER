package NER;

/**
 * La classe TaggedEntite représente des entités nommées. 
 * Chaque objet TaggedEntité comporte deux éléments:
 *  - l'entité, qui est le texte étiqueté par un reconnaisseur d'EN.
 *  - un tag, qui est le type d'étiquette assigné par ce dernier.
 * 
 * @author LOPEZ-SUN
 *
 */

public class TaggedEntite {
	
	protected String entite;
	protected String tag;
	/**
	 * Construit un objet TaggedEntite à partir du texte étiqueté et du type d'étiquette.
	 * @param a1
	 * @param a2
	 */
	public TaggedEntite(String a1, String a2) {
		
		entite = a1;
		tag = a2;
		
	}
	/**
	 * Renvoie une String correspondant au texte d'une entité nommée.
	 * @return String
	 */
	protected String getEntite() {
		return entite;
	}

	/**
	 * Renvoie une String correspondant au tag (type d'étiquette) d'une entité nommée.
	 * @return String
	 */
	protected String getTag() {
		return tag;
	}
	/**
	 * Renvoie une String formatée permettant de lire plus clairement la correspondance entre le texte et le type d'étiquette d'une entité nommée.
	 * @return String
	 */
	@Override
	public String toString(){
		return "entité: "+ getEntite() + "-->" + "tag: " + getTag();
	}
	
}
