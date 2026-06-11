FROM quay.io/keycloak/keycloak:26.2.5 AS builder

ENV KC_DB=postgres
ENV KC_METRICS_ENABLED=false
ENV KC_HEALTH_ENABLED=false

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.2.5

COPY --from=builder /opt/keycloak/ /opt/keycloak/
COPY realm-export.json /opt/keycloak/data/import/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

# Comando ultra-optimizado para 512MB en Render Monoinstancia
CMD ["start", "--optimized", "--import-realm", "--cache=local", "-Dquarkus.infinispan-embedded.jgroups.stack=local"]
