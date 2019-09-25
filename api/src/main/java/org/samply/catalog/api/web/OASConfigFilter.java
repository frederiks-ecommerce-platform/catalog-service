package org.samply.catalog.api.web;

import java.util.Optional;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.security.SecurityScheme;

public class OASConfigFilter implements OASFilter {

    @Override
    public SecurityScheme filterSecurityScheme(final SecurityScheme securityScheme) {
        Config config = ConfigProvider.getConfig();

        Optional<String> tokenUrl = config.getOptionalValue(
                "samply.oauth2.token-url",
                String.class
        );
        tokenUrl.ifPresent(securityScheme.getFlows().getPassword()::setTokenUrl);

        return securityScheme;
    }

}
