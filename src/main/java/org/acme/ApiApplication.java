package org.acme;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "LabSequence API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Gonçalo Junqueira",
                        email = "danijuncas@hotmail.com"))
)
public class ApiApplication extends Application {

}
