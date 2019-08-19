package org.samply.catalog.api.model;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(example = "1234", type = STRING, required = true)
public class SellerId {

    @JsonValue
    @NotBlank
    private final String value;

}
