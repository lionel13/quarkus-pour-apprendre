package fr.varex13.quarkuspourapprendre.front.person;

import static fr.varex13.quarkuspourapprendre.front.person.PersonDto.createPersonfromDomain;
import static fr.varex13.quarkuspourapprendre.front.person.PersonToAddDto.createDomainfromDto;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.varex13.quarkuspourapprendre.domain.person.PersonService;
import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/persons")
@Produces(APPLICATION_JSON)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(final PersonService personService) {
        this.personService = personService;
    }

    @GET
    public Set<PersonDto> findAll() {
        return personService.getAllPersons().stream().map(PersonDto::createPersonfromDomain).collect(Collectors.toSet());
    }

    @GET
    @Path("/{uuid}")
    public PersonDto findByUU(@PathParam(value = "uuid") final String uuid) throws PersonNotExistsException {
        return createPersonfromDomain(personService.getPerson(UUID.fromString(uuid)));
    }
    @ResponseStatus(NO_CONTENT)
    @DELETE
    @Path("/{uuid}")
    public void removePersonByUuid(@PathParam(value = "uuid") final String uuid) {
        personService.removePersonByUuid(UUID.fromString(uuid));
    }

    @POST
    public PersonDto addPerson(final PersonToAddDto personDto) {
       return createPersonfromDomain(personService.addPerson(createDomainfromDto(personDto)));
    }
}