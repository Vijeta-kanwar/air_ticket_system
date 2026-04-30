# ✈ Air Ticket Booking Management System - Complete CI/CD Pipeline

A complete Air Ticket Booking Management System built with Spring Boot, demonstrating an end-to-end DevOps CI/CD pipeline using **Git, Jenkins, Maven, Docker, Kubernetes,** and **Ansible**.

## 🎯 Project Overview

This project implements a full CI/CD automation pipeline that takes an air ticket booking application from source code commit all the way to production deployment in a Kubernetes cluster, with no manual intervention.

## 🏗️ Architecture

```
Developer → Git Push → Jenkins Trigger
                         ↓
                    Maven Build
                         ↓
                    Unit Tests (JUnit)
                         ↓
                    Package JAR
                         ↓
                    Docker Image Build
                         ↓
                    Push to Docker Registry
                         ↓
                    Ansible Automation
                         ↓
                    Kubernetes Deployment
                         ↓
                ✈ Booking System Live (3 Pods)
```

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Application** | Java 17, Spring Boot 3.2 |
| **Database** | H2 (In-Memory) |
| **Version Control** | Git / GitHub |
| **CI/CD** | Jenkins |
| **Build Tool** | Apache Maven |
| **Containerization** | Docker |
| **Orchestration** | Kubernetes |
| **Configuration Mgmt** | Ansible |

## 📁 Project Structure

```
airticket-booking-system/
├── src/                              # Application source code
│   ├── main/java/com/airticket/
│   │   ├── AirTicketBookingApplication.java
│   │   ├── controller/               # REST API controllers
│   │   │   ├── FlightController.java
│   │   │   ├── BookingController.java
│   │   │   └── HomeController.java
│   │   ├── model/                    # JPA entity models
│   │   │   ├── Flight.java
│   │   │   └── Booking.java
│   │   ├── repository/               # JPA repositories
│   │   └── service/                  # Business logic
│   ├── main/resources/
│   │   ├── application.properties
│   │   └── data.sql
│   └── test/java/                    # JUnit tests
├── k8s/                              # Kubernetes manifests
│   ├── namespace.yaml
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── hpa.yaml
├── ansible/                          # Ansible automation
│   ├── inventory.ini
│   ├── deploy-playbook.yml
│   └── setup-prerequisites.yml
├── Dockerfile                        # Container image definition
├── Jenkinsfile                       # CI/CD pipeline definition
├── pom.xml                           # Maven build file
├── .gitignore
├── .dockerignore
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.9+
- Docker 20+
- Kubernetes (Minikube/kind/k3s)
- Ansible 2.9+
- Jenkins 2.400+

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/airticket-booking-system.git
cd airticket-booking-system
```

### 2️⃣ Build with Maven
```bash
mvn clean package
```

### 3️⃣ Run Locally
```bash
java -jar target/airticket-booking-system.jar
# Access at http://localhost:8080
```

### 4️⃣ Build Docker Image
```bash
docker build -t airticket-booking-system:latest .
docker run -p 8080:8080 airticket-booking-system:latest
```

### 5️⃣ Deploy with Ansible
```bash
ansible-playbook -i ansible/inventory.ini ansible/deploy-playbook.yml
```

### 6️⃣ Deploy to Kubernetes (Manual)
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml -n airticket-app
kubectl apply -f k8s/deployment.yaml -n airticket-app
kubectl apply -f k8s/service.yaml -n airticket-app
kubectl apply -f k8s/hpa.yaml -n airticket-app

kubectl get pods -n airticket-app
kubectl get svc -n airticket-app
```

## 🔄 Jenkins Pipeline Stages

| # | Stage | Description |
|---|-------|-------------|
| 1 | Checkout from Git | Clone repository |
| 2 | Build with Maven | Compile source code |
| 3 | Run Unit Tests | Execute JUnit tests |
| 4 | Package Application | Build JAR file |
| 5 | Code Quality Check | Static analysis |
| 6 | Build Docker Image | Create container image |
| 7 | Push to Registry | Upload to Docker Hub |
| 8 | Deploy with Ansible | Run automation playbook |
| 9 | Deploy to Kubernetes | Apply K8s manifests |
| 10 | Verify Deployment | Health checks |

## 🌐 REST API Endpoints

### ✈ Flight Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/flights` | List all flights |
| GET | `/api/flights/{id}` | Get flight by ID |
| POST | `/api/flights` | Add a new flight |
| PUT | `/api/flights/{id}` | Update flight |
| DELETE | `/api/flights/{id}` | Delete flight |
| GET | `/api/flights/search?source=X&destination=Y` | Search flights |
| GET | `/api/flights/health` | Health check |

### 🎫 Booking Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/bookings` | List all bookings |
| GET | `/api/bookings/{id}` | Get booking by ID |
| POST | `/api/bookings` | Book a ticket |
| PUT | `/api/bookings/{id}/cancel` | Cancel booking |
| DELETE | `/api/bookings/{id}` | Delete booking |
| GET | `/api/bookings/passenger?email=X` | Get bookings by email |

### 📝 Sample API Requests

**Add a new flight:**
```bash
curl -X POST http://localhost:8080/api/flights \
  -H "Content-Type: application/json" \
  -d '{
    "flightNumber": "AI707",
    "airline": "Air India",
    "source": "Delhi",
    "destination": "Mumbai",
    "departureTime": "08:00",
    "arrivalTime": "10:30",
    "price": 5500.00,
    "availableSeats": 150
  }'
```

**Book a ticket:**
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "passengerName": "John Doe",
    "passengerEmail": "john@example.com",
    "passengerPhone": "9876543210",
    "flightId": 1,
    "seatsBooked": 2
  }'
```

**Search flights:**
```bash
curl "http://localhost:8080/api/flights/search?source=Delhi&destination=Mumbai"
```

## 🧪 Setting Up Jenkins Pipeline

1. **Install Required Jenkins Plugins:**
   - Git Plugin
   - Pipeline Plugin
   - Docker Pipeline Plugin
   - Kubernetes CLI Plugin
   - Ansible Plugin

2. **Configure Tools in Jenkins:**
   - Maven (named: `Maven-3.9`)
   - JDK 17 (named: `JDK-17`)

3. **Add Credentials:**
   - DockerHub: `dockerhub-credentials`
   - Kubernetes config: `kubeconfig`

4. **Create New Pipeline:**
   - New Item → Pipeline
   - Pipeline script from SCM
   - Repository URL: your Git repo
   - Script Path: `Jenkinsfile`

5. **Run the Pipeline** by clicking *Build Now*.

## 📸 Submission Screenshots

Capture screenshots of:
- `01-jenkins-pipeline.png` — Jenkins pipeline execution
- `02-docker-build.png` — Docker image build output
- `03-docker-images.png` — `docker images` output
- `04-kubectl-pods.png` — Running K8s pods
- `05-kubectl-services.png` — K8s services
- `06-app-running.png` — Application UI in browser
- `07-api-response.png` — REST API response (e.g. POST booking)

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Maven build fails | Check Java version: `java -version` |
| Docker push denied | Re-login: `docker login` |
| Pod CrashLoopBackOff | `kubectl logs <pod-name> -n airticket-app` |
| Service not accessible | Check NodePort: `kubectl get svc -n airticket-app` |
| Git push rejected | `git pull origin main --allow-unrelated-histories` then push |

## 🎬 Demo Steps

1. Start application: `mvn spring-boot:run`
2. Open browser: `http://localhost:8080`
3. Get all flights: `curl http://localhost:8080/api/flights`
4. Search flights: `curl "http://localhost:8080/api/flights/search?source=Delhi&destination=Mumbai"`
5. Book ticket via POST request
6. View bookings: `curl http://localhost:8080/api/bookings`

## 👨‍💻 Author

**DevOps Lab Project**
Air Ticket Booking Management System with integrated CI/CD pipeline.

## 📄 License

This project is for educational purposes.
