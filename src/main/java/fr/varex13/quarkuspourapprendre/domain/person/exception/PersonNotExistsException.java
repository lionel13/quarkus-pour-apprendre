package fr.varex13.quarkuspourapprendre.domain.person.exception;

import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.exception.NotExistsException;

public class PersonNotExistsException extends NotExistsException {
    public PersonNotExistsException(final UUID uuid) {
        super("La personne avec l'UUID '" + uuid + "' n'existe pas");
    }
}
