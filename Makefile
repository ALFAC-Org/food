build:
	@echo "Building the application"
	@mvn compile

build-without-tests:
	@echo "Building the application without running tests"
	@mvn clean install -U -DskipTests

bdd-test:
	@echo "Running BDD tests"
	@mvn test -P bdd-tests

unit-test:
	@echo "Running unit tests"
	@mvn test -P unit-tests

unit-test-coverage:
	@echo "Running unit tests with coverage"
	@mvn clean test -P unit-tests

test: unit-test

package:
	@echo "Packaging the application"
	@mvn package

start-app:
	@echo "Starting the application"
	@mvn spring-boot:start

stop-app:
	@echo "Stopping the application"
	@mvn spring-boot:stop

#docker-build:
#	@echo "Building the docker image"
#	@docker build -t backend:dev -f ./Dockerfile .
#
#docker-start:
#	@echo "Starting the application in docker"
#	docker compose -f ./docker-compose.yaml up --build
#
#docker-stop:
#	@echo "Stopping the application in docker"
#	@docker compose -f ./docker-compose.yaml down
#
#docker-start-detached:
#	@echo "Starting the application in docker"
#	docker compose -f ./docker-compose.yaml up --build -d