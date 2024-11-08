# Local setup instructions

## Required Software (follow links for your platform's instructions)

- [ git ] (https://git-scm.com/)
- [ JAVA jdk/jre ] (https://www.oracle.com/java/technologies/downloads/) version 17 is required
- [ Maven ] (https://maven.apache.org/download.cgi)
- [ Docker ] (https://www.docker.com)

## Local application setup

- if it doesn't exist, create a project directory (e.g /apps or /projects in your home directory)
- make the project directory the current directory
- clone the GitHub repo:  
`git clone https://github.com/EdouardNapolov/agile-monkey-test.git`  


## Open Application in IDE  

- If not already running, open IDE of your choice (IntelliJ IDEA preferred)
- Import project from pom.xml file in agile-monkey-test directory


## Build and Run Application
- Open terminal window and navigate to the project directory
- To build application from command line:
`mvn clean package`
- To build Docker image from command line:
`docker build -t agilemonkey/crm . `
- To run docker image from command line:
`docker run --env-file ./default.env -p 8080:8080 -t agilemonkey/crm`

## Configuration
- Application uses embedded database H2 which is initialized at startup time and adds single admin user. To change default user edit file: ```src/main/resources/import.sql```
- To change database to external type, edit application configuration: ```src/main/resources/application.yml```
- Application uses OAuth2 authentication with GitHub as provider. Client ID and secret are in ```default.env``` file.

