# Installation

```
mvn -B clean package
export VERSION=`git rev-parse HEAD`
export OAUTH_TOKEN_URL=http://keycloak-http.default.svc.cluster.local/auth/realms/samply/protocol/openid-connect/token
eval $(minikube docker-env)
docker build -f api/src/main/docker/Dockerfile.jvm -t local/samply/catalog-api:${VERSION} ./api
helm package ./helm/catalog-api --app-version $VERSION
helm upgrade --install catalog-api catalog-api-0.0.1.tgz --wait
```