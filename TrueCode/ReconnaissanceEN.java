package Projet_stanfordNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	protected ArrayList<TaggedEntite> entities;
	
	public ReconnaissanceEN(String repertoire) throws IOException {
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
			
			
			// create a document object
		    Annotation document = new Annotation(text);
		    // annoter le document
		    pipeline.annotate(document);
		    //récupération des annotations
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			//liste pour stocker les entités nommées 
			entities = new ArrayList<TaggedEntite>();
			
			for(CoreMap sentence : sentences) {
				for(CoreLabel token : sentence.get(TokensAnnotation.class)) {
					if(token.get(NamedEntityTagAnnotation.class).equals("O")==false) {
						
						String word = token.get(TextAnnotation.class);
						String ne = token.get(NamedEntityTagAnnotation.class);
						TaggedEntite couple = new TaggedEntite(word,ne);
						entities.add(couple);
					}
					
				
				}
				
			}
			System.out.println(entities);
	      }
		
	}
	

}
