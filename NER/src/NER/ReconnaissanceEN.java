package NER;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * Appelle une pipeline StanfordCoreNLP pour étiqueter les entités nommées des textes d'un dossier.
 * Permet de filtrer les résultats grâce à diverses méthodes précisées ci-dessous.
 * @author LOPEZ-SUN
 *
 */
public class ReconnaissanceEN {
	/**
	 * Chemin relatif ou absolu du dossier contenant les fichiers du corpus.
	 */
	protected String chemin;
	/**
	 * Dictionnaire contenant le nom du document et la liste de ses entités nommées, ainsi que leur catégorie (@see NER.TaggedEntite)
	 */
	protected HashMap<String,ArrayList<TaggedEntite>> entites;

	/**
	 * Construit l'API de reconnaissance d'EN.
	 * @param repertoire: chemin du dossier contenant le corpus
	 * @throws IOException
	 */
	public ReconnaissanceEN(String repertoire) throws IOException {
		entites = new HashMap<String,ArrayList<TaggedEntite>>();
		chemin = repertoire;
		File file = new File(chemin);
		String[] fichiers = file.list();
		
		
		//les étapes pour créer un pipeline StanfordCoreNLP
		Properties prop = new Properties();
		prop.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
		
				
		//lire les fichiers un par un et stocker le contenu dans la variable "text".
		for(String fichier : fichiers) {
			Path path = Paths.get(repertoire+"/"+fichier);
			byte[] data = Files.readAllBytes(path);
			String text = new String(data, "utf-8");

			//annotation des EN des fichiers
			Annotation document = new Annotation(text);
			pipeline.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			
			//stocker les entités nommées de chaque fichier dans une liste
			ArrayList<TaggedEntite>entitesParFichier = new ArrayList<TaggedEntite>();

			for(CoreMap sentence : sentences) {
				for(CoreLabel token : sentence.get(TokensAnnotation.class)) {
					if(token.get(NamedEntityTagAnnotation.class).equals("O")==false) {

						String word = token.get(TextAnnotation.class);
						String ne = token.get(NamedEntityTagAnnotation.class);
						TaggedEntite couple = new TaggedEntite(word,ne);
						entitesParFichier.add(couple);
					}
				}
				
			}
			entites.put(fichier, entitesParFichier);
		}
			System.out.println("Reconnaissance des EN effectuée");
	}
	
	
// MÉTHODES POUR FILTRER LES RÉSULTATS	

	/**
	 * Renvoie l"ensemble des EN avec la présentation suivante : nom_document.txt=[entité: Nom --> tag: PERSON] (@see TaggedEntite.toString())
	 * @return String
	 */
	protected String getAllEN(){
		return entites.toString();
	}
	

	/**
	 * Renvoie une liste des différents tags présents dans le corpus.
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> getListTag(){
		ArrayList<String> listeDesTags = new ArrayList<String>();
		for(ArrayList<TaggedEntite> listeDesEntites : entites.values()) {
			for(TaggedEntite entite : listeDesEntites) {
				if(listeDesTags.contains(entite.getTag())) {
					continue;
				}else {
					listeDesTags.add(entite.getTag());
				}
			}
		}
		return listeDesTags;
	}
	
	/**
	 * Renvoie pour un tag donné la liste des textes le contenant.
	 * @param type: catégorie d'entité nommée
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> getTexts(String type) {
		ArrayList<String> listeDesTextes = new ArrayList<String>();	
        for (HashMap.Entry<String, ArrayList<TaggedEntite>> e : entites.entrySet()) {
            String txt = e.getKey();
            ArrayList<TaggedEntite> listeDesEntites = e.getValue();
            for (TaggedEntite entite : listeDesEntites) {
            	if (type.equals(entite.getTag()) && !listeDesTextes.contains(txt)) {
            		listeDesTextes.add(txt);
            	}
            }
        }
		return listeDesTextes;
	}
	
	/**
	 * Pour un terme et un tag donné, renvoie la liste des textes le contenant.
	 * @param entite: texte de l'entité à matcher dans la recherche
	 * @param tag: type de l'entité recherchée
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> getTextsTag(String entite, String tag) {
		ArrayList<String> listeDesTextes = new ArrayList<String>();	
        for (HashMap.Entry<String, ArrayList<TaggedEntite>> e : entites.entrySet()) {
            String txt = e.getKey();
            ArrayList<TaggedEntite> listeDesEntites = e.getValue();
            for (TaggedEntite en : listeDesEntites) {
            	if (tag.equals(en.getTag()) && entite.contentEquals(en.getEntite()) && !listeDesTextes.contains(txt)) {
            		listeDesTextes.add(txt);
            	}
            }
        }
		return listeDesTextes;
	}
		
	/**
	 * Pour un fichier donné, renvoie la liste des EN qu'il contient.
	 * @param fichier: nom du fichier
	 * @return ArrayList<TaggedEntite>
	 */
	protected ArrayList<TaggedEntite> getENInFile(String fichier) {
		ArrayList<TaggedEntite> listeEN = new ArrayList<TaggedEntite>();
		listeEN = entites.get(fichier);
		return listeEN;
	}
	
	/**
	 * Pour un fichier donné, renvoie la liste des EN qui correspondent au tag indiqué.
	 * @param fichier: nom du fichier
	 * @param type: type de l'entité recherchée
	 * @return ArralyList<String>
	 */
	protected ArrayList<String> getENInFileByTag(String fichier, String type) {
		ArrayList<TaggedEntite> listeEntites = new ArrayList<TaggedEntite>();
		listeEntites = entites.get(fichier);
		ArrayList<String> listeEntitesTags = new ArrayList<String>();
		for ( TaggedEntite en: listeEntites) {
			if (type.equals(en.getTag())) {
				listeEntitesTags.add(en.getEntite());					
			}
		}
		return listeEntitesTags;
	}
		
	/**
	 * Renvoie la liste de toutes les entités du type (tag) donné.
	 * @param type: type de l'entité recherchée
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> getENByTag(String type) {
		ArrayList<String> listeENTag = new ArrayList<String>();
		for(ArrayList<TaggedEntite> listeDesTags : entites.values()) {
			for(TaggedEntite tag : listeDesTags) {
				if(type.equals(tag.getTag()) && !listeENTag.contains(tag.getEntite())) {
					listeENTag.add(tag.getEntite());
				}else {
					continue;
				}
			}
		}
		return listeENTag;
	}

}
