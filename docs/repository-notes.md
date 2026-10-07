# Spring Data JPA - Repository Notes

## 1. Introduction

Dans ce projet, Spring Data JPA est utilisé pour faciliter
l'accès aux données de la base de données.

Les repositories permettent de réaliser les opérations CRUD
sans écrire directement les requêtes SQL.

---

## 2. JpaRepository

Les repositories du projet héritent de :

`JpaRepository<Entité, Long>`

Cette interface fournit automatiquement plusieurs opérations,
notamment :

- `save()` : ajouter ou modifier une entité
- `findById()` : rechercher une entité par son identifiant
- `findAll()` : récupérer toutes les entités
- `deleteById()` : supprimer une entité par son identifiant

---

## 3. Repositories du projet

Le projet contient les repositories suivants :

- `IAgenceRepository`
- `IClientRepository`
- `IContratRepository`
- `IEmployeRepository`
- `IEquipementRepository`
- `IMaintenanceRepository`
- `IPaiementRepository`
- `IReservationRepository`
- `IVehiculeRepository`

Chaque repository étend `JpaRepository` avec un identifiant
de type `Long`.

Exemple :

```java
public interface IVehiculeRepository
        extends JpaRepository<Vehicule, Long> {
}