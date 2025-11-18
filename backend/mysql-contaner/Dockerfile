FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=pos_blinkit
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root123

EXPOSE=3307

COPY ./init.sql /
docker-entrypoint-initdb.d/