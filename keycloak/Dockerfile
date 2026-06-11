FROM quay.io/keycloak/keycloak:26.2.5 as builder

ENV KC_DB=postgres

# Compila el servidor optimizándolo para PostgreSQL
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.2.5

# Copia el resultado de la optimización
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# Copia tu realm para la importación automática
COPY realm-export.json /opt/keycloak/data/import/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

# Agregamos --optimized y removemos --db=postgres del comando de ejecución
CMD ["start", "--optimized", "--import-realm"]
