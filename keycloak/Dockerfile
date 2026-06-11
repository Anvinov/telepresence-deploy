FROM quay.io/keycloak/keycloak:26.2.5 AS builder

ENV KC_DB=postgres

# Compila el servidor optimizándolo para PostgreSQL
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.2.5

# Copia el resultado de la optimización
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# Copia tu realm para la importación automática
COPY realm-export.json /opt/keycloak/data/import/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

# Forzamos a Keycloak a usar el modo de desarrollo de caché (local-only)
CMD ["start", "--optimized", "--import-realm", "--cache=local"]
