package fr.varex13.quarkuspourapprendre.domain.person;

import java.time.LocalDate;
import java.util.UUID;

public class Person {

    private final UUID uuid;
    private final String nom;
    private final LocalDate dateNaissance;

    private Person(final PersonBuilder personBuilder) {
        this.uuid = personBuilder.uuid;
        this.nom = personBuilder.nom;
        this.dateNaissance = personBuilder.dateNaissance;
    }

    public static PersonBuilder personBuilder() {
        return new PersonBuilder();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

   public static class PersonBuilder {
        private UUID uuid;
        private String nom;
        private LocalDate dateNaissance;

        public PersonBuilder uuid(final UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public PersonBuilder nom(final String nom) {
            this.nom = nom;
            return this;
        }

        public PersonBuilder dateNaissance(final LocalDate dateNaissance) {
            this.dateNaissance = dateNaissance;
            return this;
        }

        public Person build() {
            if(uuid == null) {
                throw new IllegalArgumentException("uuid ne doit pas être null");
            }
            if(nom == null) {
                throw new IllegalArgumentException("nom ne doit pas être null");
            }
            if(dateNaissance == null) {
                throw new IllegalArgumentException("dateNaissance ne doit pas être null");
            }
            if(nom.length() < 1) {
                throw new IllegalArgumentException("nom doit être au moins de longueur 1");
            }
            if(dateNaissance.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("dateNaissance ne doit pas être dans le futur");
            }
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "uuid=" + uuid +
                ", nom='" + nom + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }
}
