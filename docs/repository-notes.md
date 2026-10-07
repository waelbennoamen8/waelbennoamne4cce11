# Spring Data JPA - Repository Notes

## 1. Introduction

Dans ce projet, Spring Data JPA est utilisé pour faciliter
l'accès aux données de la base de données.

Les repositories permettent de réaliser les opérations CRUD
sans écrire directement les requêtes SQL.

Spring Data JPA génère automatiquement une implémentation
(proxy) des interfaces repository au démarrage de l'application.

---

## 2. Choix de JpaRepository

Spring Data propose plusieurs interfaces :

- `Repository<T, ID>` : interface marqueur, sans méthodes CRUD.
- `CrudRepository<T, ID>` : opérations CRUD de base.
- `ListCrudRepository<T, ID>` : CRUD avec des collections de type `List`.
- `PagingAndSortingRepository<T, ID>` : tri et pagination.
- `JpaRepository<T, ID>` : interface complète adaptée à une
  application JPA.

Dans le projet AutoLoc, `JpaRepository` a été choisi pour les
neuf entités car il fournit :

- les opérations CRUD ;
- des résultats sous forme de `List` ;
- le tri et la pagination ;
- `flush()` ;
- `saveAndFlush()` ;
- `getReferenceById()` ;
- les opérations de suppression en lot.

Le support de l'Atelier 3 retient `JpaRepository` pour AutoLoc
afin de disposer d'une interface complète pour la couche
Repository.

---

## 3. Repositories du projet

Les neuf repositories du projet sont :

| Entité | Repository | Interface utilisée | Justification |
|---|---|---|---|
| Agence | `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD complet et accès aux méthodes JPA |
| Employe | `IEmployeRepository` | `JpaRepository<Employe, Long>` | CRUD complet et manipulation sous forme de List |
| Vehicule | `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | CRUD complet et méthodes JPA disponibles |
| Equipement | `IEquipementRepository` | `JpaRepository<Equipement, Long>` | CRUD complet et accès aux données |
| Client | `IClientRepository` | `JpaRepository<Client, Long>` | CRUD complet et recherche des clients |
| Reservation | `IReservationRepository` | `JpaRepository<Reservation, Long>` | CRUD complet et méthodes de persistance JPA |
| Contrat | `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet, List et méthodes JPA |
| Paiement | `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Lecture et CRUD des paiements |
| Maintenance | `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | CRUD complet pour les maintenances |

Tous les repositories utilisent une clé primaire de type `Long`.

Exemple :

```java
public interface IVehiculeRepository
        extends JpaRepository<Vehicule, Long> {
}