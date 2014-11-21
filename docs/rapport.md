# Micro Project ASR/CSC5002

## Choix architecturaux

### Patrons de conceptions utilisés

* Synchronous-call
* Injection de dépendance (ou IoC)
* Factory
* Contract
* Vertical Decomposition
* Container (via glassfish/JEE)

### Utilisation de JMS

Nous avons choisi de ne pas utiliser JMS afin de pouvoir proposer,
de manière simple, une interface publique standard du web (interface REST).

JMS aurait été un très bon choix pour pouvoir proposer un service de *push* des
courriels vers le client (le client n'a pas besoin de vérifier régulièrement si
il a reçu de nouveau message, le serveur se charge de les lui envoyer en temps
réel). En contrepartie, l'utilisation de cette technologie nécessite de déployer
des services complémentaires (serveur de message comme RabbitMQ ou ActiveMQ) et
de gérer un système d'authentification. Il faudrait de plus prévoir une facade
entre le client et le serveur de message qui gère le *push* (ceci afin de ne pas
exposer publiquement le-dit serveur).

### Structures de données SQL

![Vue d'ensemble de la structure SQL](https://raw.githubusercontent.com/fmonniot/mailbox/master/docs/diagram_sql.png)

* **MB_BOX** représente une boite de réception de l'utilisateur *CLIENTID* (si
*BOXTYPE* contient `mailbox`) ou une newsbox (si *BOXTYPE* contient `newsbox`).
* **MB_MESSAGE** représente un message (un courriel) envoyé par *SENDERNAME*. Un
message est déposé dans une boite de reception (attribut *BOX_ID*).
* **SEQUENCE** est une table utilisé par eclipseLink pour génerer
automatiquement les id.
* **MB_USER** représente un utilisateur (entité User) ainsi que son NewsGroupRight associé.

### Structure des deux *Manager*

Cette application est composée en différentes couches. Chacune de ces couches est
abstraite de celle l'utilisant (patron *Vertical decomposition*).

Ainsi les classes du package `api` sont les points d'entrée dans le programme
et sont définies via le standard Java JAX-RS. Ces classes transforment les exceptions
et données en code HTTP et représentation JSON (respectivement).

Viennent ensuite les classes du package `service` qui font les traitements métier et
vérification des données.

Le travail de persistence est laissé aux *DAO* (modèle de conception
*Data Access Object*) du package `persistence` qui se charge de sauvegarder les
entités dans la base de donnée et de remonter des exceptions en cas d'erreur.
Ils permettent aussi d'abstraire JPA des classes métiers (et nous permettrait de
changer facilement de méthode de persistence si nécessaire [passage à un PaaS]).

L'ensemble de ces services est relié ensemble via le méchanisme d'injection de
dépendance (patron de conception *Inversion of Control*) intégré à Jersey (`hk2`).
Ceci est configuré dans le package `config`.

### Explication de la configuration de Jersey

Notre application comporte une configuration (classes du package `config`)
quelque peu particulière. En effet nous avons eu besoin de modifier l'instanciation
de Jackson. Nous allons ici expliquer ce que fait chaque classe.

* `ApplicationConfiguration`: Il s'agit du point d'entrée de la configuration JAX-RS.
C'est la classe qui est définie dans le `META-INF/web.xml`. La méthode `getClasses()`
retourne l'ensemble des classes qui vont faire partie de l'application et la méthode
`getSingletons()` retourne un set d'instances (et non de classe) qui feront partie de
l'application.
* `BinderFeature`: Cette classe permet de faire le lien entre les classes chargées par
Jersey (des `Feature`) et la classe enregistrant les *bindings* (définition des
dépendances pour l'*IoC*).
* `ApplicationBinder`: Déclaration de la résolution des dépendances. On associe
à chaque inteface une implémentation.

## *Directory Manager* et *Mailbox Manager* APIs

### Directory APIs

#### Lister tous les utilisateurs

HTTP: `GET /api/v1/directory/all`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

#### Créer un utilisateur

HTTP: `POST /api/v1/directory/create`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

Corps de message:
```json
{
  "name": "username",
  "permission": {
    "readAccess":true,
    "writeAccess":false
  }
}
```

#### Obtenir les droits d'un utilisateur

HTTP: `GET /api/v1/directory/{userId}`

En-tête HTTP:
```http
Accept: application/json
```

#### Modifier les droits d'un utilisateur

HTTP: `POST /api/v1/directory/{userId}`

En-tête HTTP:
```http
Accept: application/json
```

Corps de message:
```json
{
  "permission": {
    "readAccess":true,
    "writeAccess":false
  }
}
```

#### Supprimer un utilisateur

HTTP: `DELETE /api/v1/directory/{userId}`

### Mailbox APIs

#### Lister toutes les boîtes de reception d'un utilisateur

HTTP: `GET /api/v1/mailbox`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

#### Créer une boite de réception

HTTP: `POST /api/v1/mailbox`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

Corps de message:
```json
{
  "name": "mb",
  "boxType": "mailbox"
}
```

#### Obtenir une boite de réception

HTTP: `GET /api/v1/mailbox/{mailboxId}`

En-tête HTTP:
```http
Accept: application/json
```

#### Supprimer une boite de réception

HTTP: `DELETE /api/v1/mailbox/{mailboxId}`

### Messages APIs

#### Obtenir tous les messages

HTTP: `GET /api/v1/message`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

#### Poster un message

HTTP: `POST /api/v1/message`

En-tête HTTP:
```http
X-Client-ID: <your client id>
Accept: application/json
```

Corps de message:
```json
{
  "senderName":"",
  "receiverName":"",
  "subject":"",
  "body":"",
  "box": {
    "id": 2751
  }
}
```

Note: seul le champ `box.id` est obligatoire.

#### Supprimer un message

HTTP: `DELETE /api/v1/message/{messageId}`

## Vérification des droits par *Mailbox Manager*

Le *Manager* permet de vérifier si un utilisateur à le droit de lire ou do poster
un message. Pour cela on utilise le service `remote.RightsService` qui permet
d'abstraire la dépendance au *Directory Manager*.

Ce service dispose de deux methode:
* `canPost(long clientId, Message msg)` permet de savoir si un client peut poster
le message donné.
* `canRead(long clientId, Box mailbox)` permet de savoir si un client peut lire
la boite de réception donné.

## Automatiser le déploiement

Nous avons utiliser Maven afin de simplifier le déploiement de nos applications.
Ainsi nous pouvons executer les commandes suvantes (dans l'ordre) afin de déployer
et exécuter les tests (clients).

```sh
export GLASSFISH_HOME=chemin/de/glassfish

# Depuis le repertoire principal de l'application
mvn install

mvn -pl directory-manager glassfish:deploy
mvn -pl mailbox-manager glassfish:deploy

mvn -pl admin-client exec:java
mvn -pl mail-client exec:java
```
