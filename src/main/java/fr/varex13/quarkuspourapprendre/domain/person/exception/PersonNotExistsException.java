package fr.varex13.quarkuspourapprendre.domain.person.exception;

import fr.varex13.quarkuspourapprendre.domain.exception.NotExistsException;

public class PersonNotExistsException extends NotExistsException {
    public PersonNotExistsException() {
        super("La personne n'existe pas");
    }
}
