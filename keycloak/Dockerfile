FROM quay.io/keycloak/keycloak:26.2.5 AS builder

ENV KC_DB=postgres
# Desactivamos métricas y salud desde el build para ahorrar memoria
ENV KC_METRICS_ENABLED=false
ENV KC_HEALTH_ENABLED=false

# Compila el servidor optimizándolo con las características apagadas
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.2.5

# Copia el resultado de la optimización
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# Copia tu realm para la importación automática
COPY realm-export.json /opt/keycloak/data/import/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

# Mantenemos el arranque optimizado e importación del realm
CMD ["start", "--optimized", "--import-realm", "--cache=local"]
