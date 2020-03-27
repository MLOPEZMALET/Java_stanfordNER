package Projet_stanfordNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ReconnaissanceEN {

	protected String chemin;
	protected HashMap<String,ArrayList<TaggedEntite>> entites;

	public ReconnaissanceEN(String repertoire) throws IOException {
		entites = new HashMap<String,ArrayList<TaggedEntite>>();
		chemin = repertoire;
		File file = new File(chemin);
		String[] fichiers = file.list();

		//les étapes pour créer un pipeline
		Properties prop = new Properties();
		prop.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);

		//lire les fichiers un par un et stocker le contenu dans la variable "text".
		for(String fichier : fichiers) {
			Path path = Paths.get(repertoire+"/"+fichier);
			byte[] data = Files.readAllBytes(path);
			String text = new String(data, "utf-8");

			
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
	
	//renvoie l'ensemble des EN trouvées
	protected String getAllEN(){
		return entites.toString();
	}
	
	
	// donne une liste des différents tags présents dans le corpus
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
	
	
	//pour un tag la liste des textes le contenant. getTexts()
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
	
		
	//TODO: Textes contenant une EN correspondant à un tag getTextsTag()
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
		
	
	//pour un texte du corpus, renvoie la liste des EN
	protected ArrayList<TaggedEntite> getENInFile(String fichier) {
		ArrayList<TaggedEntite> listeEN = new ArrayList<TaggedEntite>();
		listeEN = entites.get(fichier);
		return listeEN;
	}
	
		
	//pour un texte du corpus, renvoie la liste des EN du type indiqué,
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
		
		
		
	//TODO: Pour un type d'EN toutes les strings correspondantes. getTagByType()
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
