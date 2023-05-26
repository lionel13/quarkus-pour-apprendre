package fr.varex13.quarkuspourapprendre.configuration;

import fr.varex13.quarkuspourapprendre.domain.person.adapter.PersonRepository;
import fr.varex13.quarkuspourapprendre.domain.person.adapter.PersonService;
import fr.varex13.quarkuspourapprendre.domain.person.adapter.impl.DomainPersonService;
import jakarta.enterprise.context.ApplicationScoped;

public class ApplicationConfiguration {

    @ApplicationScoped
    public PersonService personService(final PersonRepository personRepository) {
        return new DomainPersonService(personRepository);
    }
}
