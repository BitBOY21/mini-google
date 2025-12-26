# Mini-Google  Distributed Microservices Search Engine

<p align="center">
  <img src="./assets/search-demo.gif" width="600">
</p>

A fully functional, distributed search engine architecture built with **Spring Boot**, **Apache Kafka**, and **Elasticsearch**, all containerized using **Docker**. This project demonstrates a production-grade pipeline: from web crawling to real-time indexing and fuzzy search.

## 🏗 Architecture & Services
```mermaid
graph TD
    %% Actors
    User((User))
    
    %% Frontend
    subgraph Client_Layer [Client Layer]
        UI[Frontend UI - Vanilla JS/HTML5]
    end

    %% Microservices and Infrastructure
    subgraph Docker_Compose_Environment [Docker Containerized Environment]
        
        subgraph Data_Collection [Data Collection & Ingestion]
            Crawler[Crawler Service :8084]
            Ingestion[Ingestion Service :8081]
        end

        subgraph Messaging_Stream [Async Messaging]
            Kafka[(Apache Kafka)]
        end

        subgraph Storage_and_Indexing [Processing & Storage]
            Processing[Processing Service :8082]
            Elasticsearch[(Elasticsearch 7.17)]
        end

        subgraph Search_API [Search Layer]
            Search[Search Service :8083]
        end

    end

    %% Data Flow - Crawling
    User -->|Enters URL| UI
    UI -->|1. Request Crawl| Crawler
    Crawler -->|2. Fetch Content| Web((Internet))
    Web -->|HTML Content| Crawler
    Crawler -->|3. Push Data| Ingestion
    Ingestion -->|4. Stream Message| Kafka

    %% Data Flow - Indexing
    Kafka -->|5. Consume| Processing
    Processing -->|6. Index Document| Elasticsearch

    %% Data Flow - Searching
    User -->|Enters Query| UI
    UI -->|7. API Call| Search
    Search -->|8. Fuzzy Search| Elasticsearch
    Elasticsearch -->|9. Highlighted Results| Search
    Search -->|10. JSON Response| UI
    UI -->|Display Results| User

    %% Styling
    style Kafka fill:#f96,stroke:#333,stroke-width:2px
    style Elasticsearch fill:#00bfb3,stroke:#333,stroke-width:2px,color:#fff
    style Docker_Compose_Environment fill:#f5f5f5,stroke:#666,stroke-dasharray: 5 5
    style UI fill:#fff,stroke:#333,stroke-width:2px
```
## 🛠 Technology Stack

* **Backend:** Java 17, Spring Boot 3.
* **Messaging:** Apache Kafka & Zookeeper.
* **Search Engine:** Elasticsearch 7.17.
* **DevOps:** Docker & Docker Compose.
* **Frontend:** Vanilla JavaScript, HTML5, CSS3.

## 🚀 Getting Started

### Prerequisites
* Docker Desktop installed.
* Java 17 & Maven.


### 1. Build the Project
Package the microservices into JAR files:
```bash
mvn clean package -DskipTests
```

### 2. Launch with Docker Compose

Run the entire stack (all 8 containers) with a single command:

```bash
docker-compose up --build -d
```

* The `--build` flag ensures your latest JAR files are used to create the Docker images.
* The `-d` flag runs the containers in detached mode (background).



## 🔍 Usage

### Web Interface

1. Open `frontend/index.html` in your web browser.
2. Type a search query (e.g., "Java") and click **Search**.
3. The UI will display results fetched from the **Search Service** with real-time hit highlighting.

### Crawling a New Page

To add new content to the search engine, send a POST request to the **Crawler Service**:

* **Endpoint**: `http://localhost:8084/api/crawler/crawl`
* **Method**: `POST`
* **Body (JSON)**:

```json
{ 
  "url": "https://en.wikipedia.org/wiki/Java_(programming_language)" 
}
```

## 📈 Key Features

* **Fuzzy Search**: Handles typos and similar terms using Elasticsearch fuzzy queries, ensuring users find what they need even with imperfect input.
* **Decoupled Architecture**: Uses Apache Kafka as a message broker to ensure reliable, asynchronous data flow between services.
* **Service Discovery**: Services communicate within a private Docker network using internal service names instead of hardcoded IP addresses.
* **Real-time Highlighting**: Provides visual context in search results by highlighting the exact snippets where matches were found.
* **Scalable Design**: Each component is containerized and can be scaled independently to handle increased load.







