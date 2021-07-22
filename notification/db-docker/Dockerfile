FROM bitnami/postgresql
ENV POSTGRESQL_USERNAME docker
ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB world
ENV BITNAMI_DEBUG=true
COPY initial_dump.sql /docker-entrypoint-initdb.d/world.sql

