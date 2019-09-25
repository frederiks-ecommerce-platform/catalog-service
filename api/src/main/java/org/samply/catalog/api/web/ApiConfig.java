package org.samply.catalog.api.web;

import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

@OpenAPIDefinition(info = @Info(title = "Catalog-API", version = "1.0.0"))
@SecuritySchemes({
  @SecurityScheme(
    securitySchemeName = "oauth2",
    type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(
      password = @OAuthFlow(
        tokenUrl = "http://localhost:8090/auth/realms/samply/protocol/openid-connect/token"
      )
    )
  )
})
public class ApiConfig extends Application {


}
