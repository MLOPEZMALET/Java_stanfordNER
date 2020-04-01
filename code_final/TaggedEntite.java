package Projet_stanfordNLP;


public class TaggedEntite {
	
	protected String entite;
	protected String tag;
	
	public TaggedEntite(String a1, String a2) {
		
		entite = a1;
		tag = a2;
		
	}

	protected String getEntite() {
		return entite;
	}

	

	protected String getTag() {
		return tag;
	}

	@Override
	public String toString(){
		return "entité: "+ getEntite() + "-->" + "tag: " + getTag();
	}
	
}
