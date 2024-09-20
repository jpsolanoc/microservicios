# Proyecto de Microservicios con Spring Boot y PostgreSQL

Este proyecto consiste en una aplicación de microservicios construida con Spring Boot, que utiliza PostgreSQL como base de datos. Incluye un microservicio para transacciones y un microservicio para clientes.

## Tabla de Contenidos

- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación](#instalación)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
- [Detener la Aplicación](#detener-la-aplicación)
- [Eliminar Volúmenes (opcional)](#eliminar-volúmenes-opcional)

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Docker
- Docker Compose

## Instalación

Asegúrate de tener instalados los siguientes componentes:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Ejecución del Proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/jpsolanoc/microservicios.git
   cd microservicios
Correr los dos micro-servicios.
* Se debe ejecutar este comando en los dos repositorios para crear su archivo JAR.
    ````
    - gradle clean build
    - chmod 644 init-schema.sql
    - docker-compose build
    - docker-compose up -d
  
* http://localhost:8082 (para el microservicio de transacciones)
* http://localhost:8081 (para el microservicio del cliente)

Para bajar todos los microservicios usar

````
    docker-compose down
