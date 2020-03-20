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
import edu.stanford.nlp.pipeline.CoreDocument;
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

		//===============les étapes pour créer un pipeline==================
		Properties prop = new Properties();
		prop.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
		//==================================================================

		for(String fichier : fichiers) {

			//===lire le fichier et stocker le contenu dans la variable "text".===
			Path path = Paths.get(repertoire+"/"+fichier);
			byte[] data = Files.readAllBytes(path);
			String text = new String(data, "utf-8");
			//====================================================================


			// créer un objet de la classe Annotation
			Annotation document = new Annotation(text);
			// annoter le document
			pipeline.annotate(document);
			//récupération des annotations
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

			//System.out.println(entitesParFichier);
			entites.put(fichier, entitesParFichier);
		}
			System.out.println(entites.toString());
	}

	protected ArrayList<String> getListTag(){
		ArrayList<String> listeDesTags = new ArrayList<String>();
		
//		for(String fichier : entites.keySet()) {
//			for(ArrayList<TaggedEntite> entite : entites.get(fichier)) {
//				if(listeDesTags.contains(entite.getTag())) {
//					continue;
//				}else {
//					listeDesTags.add(entite.getTag());
//				}
//			}
//		}
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

}
