FROM quay.io/keycloak/keycloak:26.2.5

COPY realm-export.json /opt/keycloak/data/import/

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

CMD ["start-dev"]
