package org.samply.catalog.api.model;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class ItemCreationDTO {

    private final String title;
    private final String description;
    private final BigDecimal price;
    private final Category category;

}
