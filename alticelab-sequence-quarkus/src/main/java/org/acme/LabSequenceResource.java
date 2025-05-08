package org.acme;

import org.acme.dto.ResponseDTO;
import org.acme.services.LabSequenceService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/labseq")
@Produces(MediaType.APPLICATION_JSON)
public class LabSequenceResource {

    @Inject
    LabSequenceService labSequenceService;

    @GET()
    @Path("/{n}")
    @Operation(
            summary = "Returns a value from the labseq sequence.",
            description = "Calculates the sequence of {n}. Returns 400 if n is negative"
    )
    @APIResponses({
        @APIResponse(
                responseCode = "200",
                description = "Successful response",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ResponseDTO.class),
                        examples = {
                            @ExampleObject(
                                    name = "Success Example",
                                    value = "{\"time\":65200,\"result\":21}"
                            )
                        }
                )
        ),
        @APIResponse(
                responseCode = "400",
                description = "Invalid input. Example response: { error: \"n must be a non-negative\" }"
        )
    })
    public ResponseDTO calculateLabSequence(@Parameter(
            description = "Index of the labseq value. Must be a non-negative integer.",
            example = "10"
    ) @PathParam("n") int n) {
        ResponseDTO response = labSequenceService.calculateLabSeq(n);
        return response;
    }
}
