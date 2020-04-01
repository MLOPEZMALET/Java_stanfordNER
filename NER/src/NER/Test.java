package NER;

import java.io.IOException;

/**
 * Cette classe permet de tester l'API de reconnaissance d'entités nommées. 
 * Modus operandi:
 * - créer un objet ReconnaissanceEN en lui donnant en paramètre le chemin du dossier contenant le corpus
 * - appeler les méthodes nécessaires pour récupérer les résultats souhaitées
 * @author LOPEZ-SUN
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		
		//insérez le chemin du dossier contenant votre corpus
		ReconnaissanceEN a = new ReconnaissanceEN("/home/mlopezmalet/MLOPEZMALET/m2/java/NER_projet/minicorpus");
		
		//liste des méthodes pouvant être appélees et description de leurs retours
		System.out.println("Toutes EN du corpus par fichier: " + a.getAllEN());
		System.out.println("Liste des Tags du corpus: " + a.getListTag());
		System.out.println("Liste des EN dans un fichier: " + a.getENInFile("15.txt"));		
		System.out.println("Liste des EN d'un seul tag dans un fichier: " + a.getENInFileByTag("18.txt", "PERSON"));
		System.out.println("Textes contenant un tag: " + a.getTexts("PERSON"));
		System.out.println("Textes contenant une EN correspondant à un tag: " + a.getTextsTag("Alec","PERSON"));
		System.out.println("EN du corpus correspondant à un tag: " + a.getENByTag("PERSON"));
	}

}
