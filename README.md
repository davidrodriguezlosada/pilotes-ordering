# Pilotes ordering

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
      <ul>
        <li><a href="#api-documentation">Api documentation</a></li>
      </ul>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About the project

Simple Spring project with a small REST API.

The project represents an ordering system that allows external users to order pilotes, a Majorcan recipe consisting of a
meatball stew. Each time an order is created or modified, the project notifies the external system that will handle the
cooking of the pilotes. Currently the notification system is handled just inserting the events in the database in JSON
format, but this could be handled by sending the notifications to a message queue system, like RabbitMQ or Kafka.

The project includes an example implementation of some features like:

* REST controllers.
* OpenAPI documentation provided by [springdoc-openapi-ui](https://springdoc.org/).
* A [ControllerAdvice](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html)
to handle error responses.
* A simple Spring security configuration
  using [basic authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html)
  to some endpoints.
* Database initialization both for DDL and sample data.
* Beans mapping using [MapStruct](https://mapstruct.org/).
* Getters, setters and builders generated with [Lombok](https://projectlombok.org/).
* Custom bean validations using
  java [ConstraintValidation](https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintValidator.html).
* A [Maven Wrapper](https://maven.apache.org/wrapper/).
* [RSQL library](https://github.com/jirutka/rsql-parser) for filtering API and [JPA queries](https://github.com/tennaito/rsql-jpa)

<p align="right">(<a href="#top">back to top</a>)</p>

### Built with

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Hibernate](https://hibernate.org/)
* [HSQLDB](http://hsqldb.org/)
* [Lombok](https://projectlombok.org/)
* [MapStruct](https://mapstruct.org/)
* [JUnit](https://junit.org/junit5/)
* [Maven](https://maven.apache.org/)

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting started

### Prerequisites

You will need Java JDK 11 to execute this project. You can use the distribution you prefer, if you don't have it
installed yet, you can get the one provided by [AdoptOpenJDK](https://adoptopenjdk.net/).

This project includes a maven wrapper, so you won't have to install Maven.

<p align="right">(<a href="#top">back to top</a>)</p>

### Installation

1. Clone the repo
2. Execute a Maven install to download all dependencies and construct the project:

<pre>./mvnw clean install</pre>

if you are using Linux or

<pre>mvnw.cmd clean install</pre>

if you are using Windows.

3. Launch the application

<pre>./mvn spring-boot:run</pre>

<p align="right">(<a href="#top">back to top</a>)</p>

### Building a docker image

You can create a docker image of the project by following this steps:

<pre>./mvnw clean package</pre>

<pre>docker build --tag pilotes-ordering:latest .</pre>

<pre>docker run -p8080:8080 pilotes-ordering</pre>

<p align="right">(<a href="#top">back to top</a>)</p>

## Usage

When the application launches, it already has a list of four clients already created to make testing easier. They have
the following codes: 0001, 0002, 0003 and 0004.

Some endpoints are protected by a simple HttpBasic authorization. The user and password needed to access these endpoints
are:

* user/userPass: with the role USER
* admin/adminPass: with the role ADMIN

<p align="right">(<a href="#top">back to top</a>)</p>

### API documentation

API documentation can be accessed on http://localhost:8080/swagger-ui.html once you launch the application.

<p align="right">(<a href="#top">back to top</a>)</p>

## License

Distributed under the Apache License 2.0. See LICENSE file for more information

<p align="right">(<a href="#top">back to top</a>)</p>

## Contact

David Rodríguez Losada - davidrodriguezlosada@gmail.com - [LinkedIn](https://www.linkedin.com/in/david-rodriguez-losada/)

<p align="right">(<a href="#top">back to top</a>)</p>