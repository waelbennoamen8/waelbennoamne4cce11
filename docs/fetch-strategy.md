# Fetch Strategy — AutoLoc

## 1. Introduction

Dans le projet AutoLoc, les stratégies de chargement JPA
`FetchType.LAZY` et `FetchType.EAGER` permettent de contrôler le
moment où les données associées sont chargées par Hibernate.

Le choix retenu dans ce projet est principalement `LAZY`, afin
d'éviter de charger automatiquement les données qui ne sont pas
nécessaires.

---

## 2. Association Contrat → Paiement

### Type de relation

`OneToMany / ManyToOne`

### Côté Contrat

```java
@OneToMany(
        mappedBy = "contrat",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
)
private List<Paiement> paiements = new ArrayList<>();