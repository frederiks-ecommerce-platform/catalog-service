package org.samply.catalog.api.web;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;
import javax.inject.Inject;
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
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.samply.catalog.api.domain.model.Error;
import org.samply.catalog.api.domain.model.ItemCreationDTO;
import org.samply.catalog.api.domain.model.ItemDTO;
import org.samply.catalog.api.domain.model.SellerId;
import org.samply.catalog.api.domain.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.reactivex.Single;


@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirements({
        @SecurityRequirement(name = "oauth2")
})
public class ItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(ItemResource.class);

    @Inject
    ItemService itemService;

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
    public Single<Response> addItem(@Valid @NotNull @HeaderParam("X-User-Id") SellerId sellerId,
                                    @Valid @NotNull @Named("item") ItemCreationDTO item) {
        LOG.info("POST Item for {}", sellerId);

        return itemService.addItem(item, sellerId)
                          .map(i -> Response.status(CREATED).entity(i).build());
    }

}
