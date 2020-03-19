# Java_stanfordNER: Recherche d'entités nommées   

- Tâche à effectuer: 
     - Compréhension du fonctionnement de la classe StanfordCoreNLP ( voir plus bas )
     - Constitution d'un corpus de test
     - Définition d'une mini  API de recherche d'EN comprenant au moins:
        - un constructeur ( pour donner l'adresse du corpus )
        - une méthode pour lancer le calcul
            * des méthodes permettant de récupérer les infos de la recherche e. g.:
            * la liste des tags trouvés, 
            * pour une EN et un tag donné, la liste des textes les contenant
            * Pour un tag donné, la liste de toutes les EN associées
        etc..
    On programmera un exemple d'utilisation de l' API 
    
    
    La problématique du stockage du résultat de la recherche ne sera pas abordée.
    e. g. une nouvelle recherche est faite avant chaque interrogation


La javadoc : http://nlp.stanford.edu/nlp/javadoc/javanlp/
*BasicPipelineExample : http://stanfordnlp.github.io/CoreNLP/api.html
chargement des "models" : http://stackoverflow.com/questions/14735212/specifying-a-path-to-models-for-stanfordcorenlp
Demo ( dans la distrib ) : <yourdistrib>/stanford-corenlp-full-2016-10-31\stanford-corenlp-full-2016-10-31\StanfordCoreNlpDemo.java
