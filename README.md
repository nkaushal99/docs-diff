# Docs Diff

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---
![Docs Diff](documentation/UI.png)

Based on the **Event-driven Microservices architecture**, this web-app allows users to compare two documents at once, highlighting any textual differences between
the two.

![Comparison](documentation/diff.png)

## Architecture

![Architecture](documentation/architecture.png)

## Services

1. Service Discovery
2. Gateway
3. Document Service
4. Diff Service - Extracts the differences between the texts.
5. Highlight Service - Highlights the differences between the texts.
6. Kafka - To promote event-driven architecture

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)
- [Docker](https://docs.docker.com/get-docker/)

