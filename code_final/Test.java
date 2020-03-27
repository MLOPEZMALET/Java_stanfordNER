package Projet_stanfordNLP;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ReconnaissanceEN a = new ReconnaissanceEN("/Users/noah/MesDocuments/TAL/TAL_M2_INALCO/Semestre_2/java/projet/corpus_mini");
		
		System.out.println("Toutes EN du corpus par fichier: " + a.getAllEN());
		System.out.println("Liste des Tags du corpus: " + a.getListTag());
		System.out.println("Liste des EN dans un fichier: " + a.getENInFile("15.txt"));		
		System.out.println("Liste des EN d'un seul tag dans un fichier: " + a.getENInFileByTag("18.txt", "PERSON"));
		System.out.println("Textes contenant un tag: " + a.getTexts("PERSON"));
		System.out.println("Textes contenant une EN correspondant à un tag: " + a.getTextsTag("Alec","PERSON"));
		System.out.println("EN du corpus correspondant à un tag: " + a.getENByTag("PERSON"));
	}

}
