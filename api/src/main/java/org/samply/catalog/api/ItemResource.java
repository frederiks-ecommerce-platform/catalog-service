package org.samply.catalog.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.samply.catalog.api.model.ItemCreationDTO;
import org.samply.catalog.api.model.ItemDTO;
import org.samply.catalog.api.model.ItemId;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @POST
    public Response addItem(ItemCreationDTO item) {
        ItemDTO createdItem = ItemDTO.of(
                ItemId.of("123"),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory()
        );

        return Response.status(Status.CREATED)
                       .entity(createdItem)
                       .build();
    }

}
