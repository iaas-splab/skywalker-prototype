# skywalker-prototype

## Getting started
### Prerequisites
- mongod 4.2.0
- Java 8
- JavaParser 3.15.0
- snakeyaml 1.23
- Spring Boot 2.1.7.RELEASE
- Angular CLI 7.1.4
- Node 10.16.3
- Angular 7.1.4

### Start database server
First, make sure that the MongoDB server is running on port `27017` (standard port for MongoDB) . Start the server by running the following command:  
```bash
    $ mongod --dbpath data/
```
If an error occurs, the database could be corrupted because of several reasons, including unclean shutdowns. In this case the following routine should fix the issue:
```bash
    $ rm data/mongod.lock
    $ mongod --dbpath data/ --repair
    $ mongod --dbpath data/
```

### Run server application
With a running instance of the database, the backend server can be started. Make sure that port `8080` is available. 
#### Using maven
```bash
    $ cd skywalker && mvn install
    $ mvn spring-boot:run
```
#### From an IDE
Typically, any IDE has support for spring boot applications. Just import `skywalker` as a maven project and run it like a simple Java application:  
  > Import -> Existing Maven Projects

### Start the client
Make sure that port `4200` is available, otherwise add the `--port PORT` flag to the following commands:
```bash
    $ cd skywalker-client/ && npm install
    $ ng serve --open
```
The landing page of the skywalker application can be opened on a web browser at  
  > http://localhost:4200/

![skywalker](https://github.com/kaplanan/skywalker-prototype/blob/master/media/skywalker_landing_page.PNG)


