package org.samply.catalog.api.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Value;

@Value
public class ItemCreationDTO {

    @NotBlank private final String title;
    @NotBlank private final String description;
    @NotNull @PositiveOrZero private final BigDecimal price;
    @NotNull private final Category category;

}
