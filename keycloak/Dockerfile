FROM quay.io/keycloak/keycloak:26.2.5 as builder

ENV KC_DB=postgres

RUN /opt/keycloak/bin/kc.sh build --db=postgres

FROM quay.io/keycloak/keycloak:26.2.5

COPY --from=builder /opt/keycloak/ /opt/keycloak/

COPY realm-export.json /opt/keycloak/data/import/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

CMD ["start","--db=postgres","--import-realm"]
