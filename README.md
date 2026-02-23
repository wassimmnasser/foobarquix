# FoobarQuix Transformation Service

Spring Boot application providing:

- REST API for number transformation
- Spring Batch job for file processing

The transformation follows the FOO BAR QUIX rules.

---

## 🔧 Tech Stack

- Java 17
- Spring Boot 3
- Spring Batch
- Maven


---

## 📦 Build the project

mvn clean install

The executable JAR will be generated in:

target/foobarquix-0.0.1-SNAPSHOT.jar

---

## 🚀 Run the REST API

java -jar target/foobarquix-0.0.1-SNAPSHOT.jar --spring.profiles.active=api

### Endpoint

GET /v1/transform?value=15

### Example

Request:
GET http://localhost:8080/v1/transform?value=15

Response:
FOOBARBAR

---

## 📁 Run the Batch job

java -jar target/foobarquix-service-0.0.1-SNAPSHOT.jar \
--spring.profiles.active=batch \
--batch.in=file:./input.txt \
--batch.out=./output.txt

---

## 📥 Input file example

1
3
5
7
15
abc
150

Invalid lines are skipped (empty, not numeric, or out of range).

---

## 📤 Output example

1 -> "1"
3 -> "FOOFOO"
5 -> "BARBAR"
7 -> "QUIX"
15 -> "FOOBARBAR"

---

## 🧪 Running tests

mvn test

---

## 🏗 Architecture Overview

- core → business transformation engine
- api → REST controller layer
- batch → Spring Batch configuration
- config → bean configuration

The transformation logic is isolated from the infrastructure layers.

---

## 🐳 Docker (optional)

Build image:

docker build -t foobarquix-service .

Run API:

docker run -e SPRING_PROFILES_ACTIVE=api -p 8080:8080 foobarquix-service

---

## 📌 Notes

- The "divisible by" rule has priority over the "contains" rule.
- Digit analysis is performed from left to right.
- If no rule applies, the number is returned as a string.
