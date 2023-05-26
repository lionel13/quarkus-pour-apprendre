package fr.varex13.quarkuspourapprendre.configuration;

import fr.varex13.quarkuspourapprendre.domain.person.PersonRepository;
import fr.varex13.quarkuspourapprendre.domain.person.PersonService;
import fr.varex13.quarkuspourapprendre.domain.person.impl.DomainPersonService;
import jakarta.enterprise.context.ApplicationScoped;

public class ApplicationConfiguration {

    @ApplicationScoped
    public PersonService personService(final PersonRepository personRepository) {
        return new DomainPersonService(personRepository);
    }
}
