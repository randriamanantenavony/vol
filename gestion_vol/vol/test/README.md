## Includer sprint7.0.jar dans le lib de votre projet

## Creation du fchier web.xml pour configurer votre url par defaut

## Url par defaut :
 Une fois que ouvrez par defaut votre ptojet qui se termine par /
 (Modifiable dans web.xml)
 Vous voyez lister un message de bienvenu "Welcome Here"

## Déclarer une classe comme controller
Pour qu'une classe soit considérée comme un controller par le framework:
1-importer le package "mg.annotation.*"
2-mettre une annotation @Controller

## Détection du package des controllers
Dans le fichier "web.xml", repérez la section "<init-param>".
Dans la balise "<param-value>", mettre le nom du package de vos controllers.

## Détéction de la classe et de la methode en fonction de l'url tapée
Annoter votre classe avec @Controller
Ensuite annoter votre methode en @Get(/url)
Taper ceci "/nomDuProjet/url" dans le naviguateur pour retouver la classe et la méthode correspondante 

## Execution des methode que vous avez annotees 
taper dans le navigauteur ce lien ci-dessous 
"localhost:8080/nomProjet/url associe a la methode"
ou url associe a la methode est l'url dans Get que vous avez attribue a la methode


## Exceptions a gerer
- le nom du package declare dans le web.xml vide
- Les url des methodes dupliques
- Type de retour autre que String ou ModelAndView

## Envoyer des donnees du view vers le controller
   * Cas de type primitif : 
       - Annoter les parametres de la fonction avec @RequestParam
       - Ce type d'annotation doit correspondre au name declare dans votre formulaire dans le view
       - Si vous ne voulez pas l'annoter avec @RequestParam assurez vous de faire correspondre le nom de l'argument avec le name dans le formulaire
       - quand vous cliquer sur valider , appeler la methode que vous avez annote @Get en passant ce url dans le action du formulaire

    * Cas de type complexe : 
       - Annoter les parametres de la fonction avec @ModelAttribute
       - Si le nom des attributs de la classe ne correspond pas au name dans le formulaire, annoter le avec @Column
       - Si vous ne voulez pas l'annoter avec @Column assurez vous de faire correspondre le nom de l'argument avec le name dans le formulaire
       -Si vous utiliser un type primitif vous pouvez l'annoter avec @RequestParam ou non mais suivez les regles pre-etablis tout a l'heure
       - quand vous cliquer sur valider , appeler la methode que vous avez annote @Get en passant ce url dans le action du formulaire
   
## Emploi des sessions :
    - Utiliser la classe MySession(cle,valeur)
    - Votre classe qui va utiliser l'attribut MySession doit avoir un attribut MySession(getter et setter y compris)
    - Pour ajouter une valeur dans MySession on suit ces etapes :
       - instancier la classe 
       - utiliser la methode add(key,value)
    - Pour recuperer la valeur de la session on utilise : 
       - Mysession.get(key)
    - Pour supprimer la sessiion on utilise la fonction 
    MySession.delete(key)
    - Vous pouvez utiliser votre session dans le naviguateur et y acceder dans que vous ne l'avez pas effacer

## upload des fichiers
   - 
## important : 
   - mettre un constructeur par defaut dans les classes en java