package org.samply.catalog.api.web;

import java.util.Optional;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.eclipse.microprofile.openapi.models.security.SecurityScheme;

public class OASConfigFilter implements OASFilter {

    @Override
    public SecurityScheme filterSecurityScheme(final SecurityScheme securityScheme) {
        Config config = ConfigProvider.getConfig();

        Optional<String> tokenUrl = config.getOptionalValue(
                "catalog-api.oauth.token-url",
                String.class
        );
        tokenUrl.ifPresent(securityScheme.getFlows().getPassword()::setTokenUrl);

        return securityScheme;
    }

    @Override
    public Parameter filterParameter(Parameter parameter) {
        Config config = ConfigProvider.getConfig();

        boolean disableUserIdHeader = config.getValue(
                "catalog-api.disable-user-id-header",
                Boolean.class
        );

        if (disableUserIdHeader && "X-User-Id".equals(parameter.getName())) {
            return null;
        }

        return parameter;
    }

}
