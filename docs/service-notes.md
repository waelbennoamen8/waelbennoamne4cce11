# Atelier 4 — IoC, injection de dépendances et couche Service

## A. Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible | Oui, dans le constructeur généré par Lombok avec `@RequiredArgsConstructor` | Non, elle est cachée dans la classe |
| Utilisation hors du conteneur Spring | La dépendance doit être fournie au constructeur | Le champ peut rester `null` si Spring n'effectue pas l'injection |
| Signalement par SonarQube | Bonne pratique recommandée | Signalée par la règle `java:S6813` |
| Avantage principal | Dépendances obligatoires et objet correctement construit | Écriture plus courte |
| Inconvénient principal | Constructeur potentiellement long si trop de dépendances | Dépendance cachée et champ non `final` |

**Conclusion :** dans le projet AutoLoc, l'injection par constructeur est privilégiée. L'annotation Lombok `@RequiredArgsConstructor` génère le constructeur correspondant aux attributs `final`, que Spring utilise automatiquement.
## B. Services et dépendances injectées

| Service | Dépendance injectée | Mode d'injection | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| ClientServiceImpl | IClientRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| ContratServiceImpl | IContratRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Dépendance obligatoire, champ `final`, service en lecture seule. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |
| VehiculeServicesImpl | IVehiculeRepository | Constructeur | Dépendance obligatoire, champ `final`, faible couplage. |

**Remarque :** `@RequiredArgsConstructor` de Lombok génère le constructeur à partir des champs `final`. Spring injecte les repositories correspondants. Le service dépend d’une interface plutôt que d’une implémentation concrète, ce qui facilite les tests et réduit le couplage.

## C. Messages d'erreur du conteneur Spring

### Message 1 — Bean IContratService introuvable

**Exception :** `NoSuchBeanDefinitionException`, généralement signalée au démarrage à travers `UnsatisfiedDependencyException`.

**Causes possibles :**
- La classe `ContratServiceImpl` ne possède pas l'annotation `@Service`.
- La classe se trouve en dehors du package couvert par le scan Spring.

**Corrections :**
- Ajouter `@Service` sur `ContratServiceImpl`.
- Vérifier que le service se trouve dans le package analysé par `@SpringBootApplication` et ses sous-packages.

### Message 2 — Plusieurs beans Notificateur

**Exception :** `NoUniqueBeanDefinitionException`.

**Cause :** Spring trouve deux beans compatibles, `emailNotificateur` et `smsNotificateur`, sans savoir lequel injecter.

**Corrections possibles :**
- Ajouter `@Primary` sur la classe du bean choisi par défaut.
- Ou ajouter `@Qualifier("emailNotificateur")` au paramètre du constructeur qui doit recevoir le bean e-mail.

### Message 3 — Dépendance circulaire

**Erreur :** dépendance circulaire entre `clientServiceImpl` et `reservationServiceImpl`.

**Cause :** chaque service dépend de l'autre. Avec l'injection par constructeur, Spring ne peut pas construire le premier service sans disposer du second.

**Correction :** revoir la conception et extraire la responsabilité commune dans un composant séparé. Les deux services peuvent alors dépendre de ce composant sans se référencer mutuellement.

**Conclusion :** ces erreurs proviennent d'un bean absent, d'une ambiguïté entre plusieurs beans ou d'un cycle de dépendances. Il faut lire la cause réelle de l'erreur et corriger la configuration ou la conception.

## D. Anomalies SonarQube for IDE

### 1. Règle java:S6813 — Injection par attribut

**Problème :** le repository était injecté directement dans un attribut avec `@Autowired`. Cette approche cache la dépendance et ne permet pas de déclarer le champ `final`.

**Correction appliquée :** remplacer l'injection par attribut par l'injection par constructeur, avec `@RequiredArgsConstructor` et un attribut `private final`.

**Résultat :** après la correction, SonarQube for IDE affiche `No Issues` pour `ClientServiceImpl.java`.

### 2. Règle java:S6809 — Appel interne d'une méthode transactionnelle

**Problème :** certaines méthodes `update()` et `deleteById()` appelaient la méthode `findById()` du même service pour récupérer une entité.

**Correction appliquée :** utiliser directement `repository.findById(id).orElseThrow(...)` dans les méthodes concernées, en conservant `ResourceNotFoundException` lorsque l'entité est absente.

**Résultat :** les appels internes concernés ont été remplacés dans le code corrigé. Vérifier l'analyse SonarQube finale pour confirmer l'absence de cette anomalie.

### 3. Règle java:S1192 — Littéral de chaîne répété

**Problème :** le nom de la ressource, par exemple `"Contrat"`, était répété plusieurs fois dans le même fichier.

**Correction appliquée :** déclarer une constante, par exemple `private static final String NOM_RESSOURCE = "Contrat";`, puis la réutiliser.

**Résultat :** le code évite la répétition de la même chaîne et devient plus facile à maintenir.

**Conclusion :** SonarQube for IDE aide à détecter les problèmes de qualité du code. Les corrections doivent être suivies d'une nouvelle analyse pour vérifier le résultat.

## E. Questions de compréhension

### 1. Où est le `new` de `ContratServiceImpl` et qui l'exécute ?

Il n'est pas écrit explicitement dans le code métier. Spring crée l'instance du service au démarrage grâce à `@Service` et au mécanisme d'Inversion de Contrôle.

### 2. Pourquoi un contrôleur dépend-il de `IContratService` plutôt que de `ContratServiceImpl` ?

Parce qu'une interface réduit le couplage entre les couches. Le contrôleur dépend d'un contrat abstrait, ce qui facilite les tests et le remplacement de l'implémentation.

### 3. Pourquoi un service singleton doit-il rester sans état ?

Un service singleton est partagé entre plusieurs appels. Il ne doit pas conserver de données propres à une requête dans des attributs modifiables, car cela pourrait mélanger les données de plusieurs utilisateurs.

### 4. Pourquoi `update()` charge-t-il l'entité existante avant d'appeler `save()` ?

Pour vérifier que l'entité existe et ne modifier que les attributs autorisés. Cela évite d'écraser involontairement des données ou des associations qui ne doivent pas être modifiées.

### 5. Quelle différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` est-il en lecture seule ?

- `@Component` : annotation placée sur une classe pour que Spring la détecte automatiquement.
- `@Bean` : annotation placée sur une méthode de configuration qui retourne l'objet à gérer par Spring.
- `@Primary` : désigne le bean privilégié lorsqu'il existe plusieurs candidats.
- `@Qualifier` : sélectionne explicitement un bean par son nom ou son qualificatif.
- `IPaiementService` expose uniquement `findById()` et `findAll()` afin que les paiements soient consultés en lecture seule ; leur gestion passe par le contrat associé.

