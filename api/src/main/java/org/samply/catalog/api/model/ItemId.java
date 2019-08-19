package org.samply.catalog.api.model;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Value;

@Value(staticConstructor = "of")
@Schema(type = STRING)
public class ItemId {
    @JsonValue
    private final String value;
}
