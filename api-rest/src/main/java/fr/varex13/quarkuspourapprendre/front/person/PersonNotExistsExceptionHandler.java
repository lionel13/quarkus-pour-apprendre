package fr.varex13.quarkuspourapprendre.front.person;

import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;
import fr.varex13.quarkuspourapprendre.front.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PersonNotExistsExceptionHandler implements ExceptionMapper<PersonNotExistsException> {

    @Override
    public Response toResponse(final PersonNotExistsException e) {

        return Response.status(Response.Status.NOT_FOUND).
                entity(new ErrorMessage(e.getMessage(), false)).build();
    }
}