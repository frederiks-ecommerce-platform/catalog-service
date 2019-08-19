package org.samply.catalog.api.model;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value
public class SellerId {

    @JsonValue
    @NotBlank
    private final String value;

}
