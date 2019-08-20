package org.samply.catalog.api.web;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.samply.catalog.api.model.Error;
import org.samply.catalog.api.model.ItemCreationDTO;
import org.samply.catalog.api.model.ItemDTO;
import org.samply.catalog.api.model.ItemId;
import org.samply.catalog.api.model.SellerId;


@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @POST
    @APIResponses({
            @APIResponse(
                responseCode = "201",
                content = @Content(schema = @Schema(implementation = ItemDTO.class))
            ),
            @APIResponse(
                responseCode = "400",
                content = @Content(
                    schema = @Schema(
                        type = ARRAY,
                        implementation = Error.class
                    )
                )
            )
    })
    public Response addItem(@Valid @NotNull @HeaderParam("X-User-Id") SellerId sellerId,
                            @Valid @NotNull @Named("item") ItemCreationDTO item) {
        ItemDTO createdItem = ItemDTO.of(
                ItemId.of("123"),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory()
        );

        return Response.status(CREATED)
                       .entity(createdItem)
                       .build();
    }

}
