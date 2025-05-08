/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.acme.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author Kildar
 */
@Provider
public class NegativeNumberExceptionMapper implements ExceptionMapper<NegativeNumberException> {

    @Override
    public Response toResponse(NegativeNumberException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + exception.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}
