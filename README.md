# Implémentation du proof of concept

La situation utilisée pour valider le bon fonctionnement du moteur de
ligne de produits est un site web basé sur spring présentant une liste
de produits qui peuvent être likés en spécifiant un nom d'utilisateur.
Dans la sous section \"Ligne de produits\", différents variants qui ont
été pensés et implémentés concernant cette situation, sont détaillés.\

Une représentation de l'interface utilisateur est disponible à l'annexe
2. On peut y voir sur la gauche de l'écran une liste de produits avec un
code, un nom, un ean ainsi qu'une description. Sur la droite de l'écran
se trouve une zone détail qui affiche les informations de l'article
sélectionné. Dans cette zone se trouve également le nombre de like que
le produit a reçu ainsi qu'un formulaire pour ajouter un like. Sur le
haut de l'écran se trouve une barre de navigation où le formulaire du
plugin recherche se trouve. Le diagramme de classes de cette plateforme
web se présente comme ci-dessous:

## Spring

Spring est un framework Java qui offre de nombreuses fonctionnalitées.
Dans de ce projet, Spring à permis de faciliter la création d'un serveur
web avec un accès à une base de données. Mais ce framework a également
servi pour la programmation orientée aspect tel qu'expliqué dans le
chapitre concernant ce paradigme[@Spring].\

Concernant l'accès à la base de données, Spring donne accès à plusieurs
framework ORM tels que Hibernate, JDO, JPA et IBATIS. Ces ORMs sont
responsables de la persistance des données dans une base de données et
de transmettre à spring si une erreur survient[@SpringBook]. Dans cette
implémentation, l'ORM Hibernate est utilisée.

### Hibernate

Hibernate est un famework de persistance de données très connu et
utilisé depuis longtemps[@SpringBook]. Cette ORM a pour but de faire le
lien entre les objets du code source et les modèles relationnels
utilisés en base de données. Grace à Hibernate, la gestion de la
persistance des données se fait de manière beaucoup plus accessible. Un
langage nomé HQL est utilisé et transformé lors de l'exécution de
l'application afin de pouvoir s'adapter à la forme de SQL utilisé par la
base de données[@SpringBook]. Hibernate a été choisi pour ce projet
compte tenu du fait de sa facilité d'accès ainsi que sa solide
documentation et sa bonne intégration avec Spring.\

JPA est une API fournie par Java qui a pour but d'aider la liaison entre
les classes ou objets Java et le modèle relationnel d'une base de
données. Hibernate utilise cette api afin d'accéder aux objets
Java[@Spring2; @Spring]

## Ligne de produits

Dans les chapitres suivants sont détaillés les variants qui ont été
implémentés concernant la situation donnée à l'aide de différents
mécanismes de variabilité. Pour résumer, voici la représentation
visuelle grâce au diagramme de feature. Afin de montrer les capacités du
moteur de ligne de produits, la situation a été réfléchie de sorte que
tous les types de relation possible entre variant soit compris dans la
situation.

-   un et un seul: Le type de SGBD requière un choix entre SQLite et
    PostgeSQL.

-   et: Si le variant like est choisi, il faut obligatoirement avoir le
    variant compte.

-   ou: Les variants recherche et like sont des variant optionnel au
    fonctionnement du site web.

[image]{.image}

### Variation SGBD

Deux possibilités de SGBD ont été implémentées, soit MySQL ou
PostgreSQL. Les paramètres Spring à modifier étant dans un fichier de
configuration, la sélection se fait par un semblant de directive
préprocesseur. Ceci confirme l'ouverture du moteur à une syntaxe
personnelle ainsi que la manipulation des directives préprocesseur pour
les langages supportant ces derniers, Java n'en faisant pas partie
nativement.\

Le point de variation de ce variant se joue dans le fichier
\"*application.properties*\". Ce fichier généré par Spring contient des
paramètres par défaut utilisé par l'application. C'est dans ce fichier
donc qu'il faut modifier les paramètres de connexion à la base de donnée
afin de pouvoir se connecter à une base de donnée MySQL ou PostgreSQL.
Comme expliqué dans le chapitre sur le moteur de ligne de produits, dans
ce fichier, des lignes respectent une certaine syntaxe afin que
certaines lignes soient ou non incluses dans le résultat final de ce
fichier.

### Variation système like

Un des variants implémentés a pour objectif de permettre d'enregistrer
des likes pour un produit donné à l'aide d'un pseudo. Ce variant se lie
au reste du programme en utilisant les principes de la programmation
orientée delta. Ce variant a besoin du variant compte détaillé dans la
section suivante afin de fonctionner correctement.\

Outre les modifications nécessaires pour le variant compte, quatre
fichiers doivent être modifiés afin d'inclure le variant like dans le
produit final.

-   *bibliotheque.html* doit être modifié afin d'ajouter le formulaire
    html qui permet à un utilisateur d'enregistrer son like.

-   *DBManager.java* étant le fichier qui centralise les requêtes pour
    la base de données, celui-ci doit donc comprendre en plus deux
    méthodes. Une qui permet de retrouver un utilisateur enregistré à
    l'aide de son nom et une autre méthode qui est responsable
    d'enregistrer un nouvel utilisateur.

-   *ShopControler.java* ce fichier est le controleur qui reçoit les
    différentes requêtes venant de l'utilisateur. Une méthode doit donc
    être ajoutée afin d'accepter une requête post venant du formulaire
    html qui envoie le like d'un utilisateur.

-   *product.java* ce fichier contient le modèle représentant ce qu'est
    un produit dans le programme. il faut ajouter un lien vers le model
    user afin de pouvoir lier les likes des produits vers le compte qui
    a déposé le like.

### Variation compte

La programmation orientée delta a également été utilisée afin
d'implémenter un variant pour la gestion de compte utilisateur. Grâce à
Spring Data repositories et CrudRepository, seul le modèle correspondant
à un utilisateur doit être défini ainsi que le CrudRepository
correspondant. Ceci est réalisé en ajoutant les fichiers: *User.java* et
*UserRepository.java*. Dans ce variant, un utilisateur est enregistré
dans la base de donnée comme un nom ainsi qu'un id afin de pouvoir
l'identifier.

### Variation log des requêtes

La programmation orientée aspect qui est autorisée par le framework
Spring a permis de pouvoir effectuer un log lors des accès à la base de
données. Spring permet de définir facilement un aspect simplement en
ajoutant une classe et l'annotation \"*Aspect*\". Pour ajouter ce
variant, il suffit donc d'ajouter le fichier \"*dbManagerAspect.java*\"
contenant l'aspect qui log toute action se passant dans le fichier
*dbManager.java*.

### Variation Recherche

La page web principale de ce projet possède une barre de navigation.
Cette dernière est utilisée afin de pouvoir mettre en place un lien vers
d'éventuels plugins. Pour prouver cette utilisation, un plugin de
recherche a été implémenté. Celui-ci utilise un champ dans cette barre
de navigation pour effectuer une recherche sur tous les champs composant
un produit et affiche les détails de ce dernier.\

Durant la conception de ce projet, il a été choisi de laisser libre au
plugin de décider le code html qui serait affiché dans la barre de
navigation afin de permettre à ce plugin d'afficher un formulaire au
lieu d'un simple bouton qui redirigerait vers une nouvelle page. Ce
plugin nécessite donc en plus du fichier \"*Recherche.java*\" qui
définit les méthodes propres au plugin, le fichier
\"*rechercheNavBar.html*\" qui possède le code html qui sera affiché
dans la barre de navigation soit un formulaire avec un champs texte et
un bouton submit.
