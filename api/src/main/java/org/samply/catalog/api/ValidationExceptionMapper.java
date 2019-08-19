package org.samply.catalog.api;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.google.common.collect.Iterators;
import org.samply.catalog.api.model.Error;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    public Response toResponse(ConstraintViolationException cex) {
        List<Error> errs = StreamSupport.stream(cex.getConstraintViolations().spliterator(), false)
                                        .map(ValidationExceptionMapper::getMessage)
                                        .map(Error::of)
                                        .collect(Collectors.toList());

        return Response.status(BAD_REQUEST).entity(errs).build();
    }

    private static <T> String getMessage(ConstraintViolation<T> cv) {
        String field = Iterators.getLast(cv.getPropertyPath().iterator()).getName();
        return field + " " + cv.getMessage();
    }

}
