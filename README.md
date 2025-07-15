# Fit-AI Graduation Project

## Overview
Fit-AI is a comprehensive fitness and nutrition assistant designed as a graduation project. It leverages machine learning and natural language processing to provide personalized meal plans, nutrition predictions, workout routines, and real-time notifications. The backend is built with Java and Spring Boot, offering a robust RESTful API for integration and extension.

## Features
- **Personalized Meal Planning**: Generate meal plans tailored to users' dietary needs and preferences.
- **Nutrition Prediction**: Predict nutritional content of meals using machine learning algorithms.
- **Workout & Daily Routine Management**: Create, track, and manage workout plans and daily routines.
- **Authentication & Authorization**: Secure login, registration, password reset, and OAuth2 support.
- **Notifications**: Real-time and scheduled notifications for users, including email and in-app alerts.
- **Cloudinary Integration**: Seamless image upload and management for user and workout media.
- **Comprehensive RESTful API**: Modular endpoints for account, progress, plans, notifications, and more.
- **API Documentation**: Interactive Swagger/OpenAPI documentation for easy API exploration.

## Technologies Used
- **Java 17+** & **Spring Boot**: Core backend framework.
- **Maven**: Build tool and dependency management.
- **Cloudinary**: Image storage and management.
- **JWT**: Secure authentication and authorization.
- **WebSocket**: Real-time communication for notifications.
- **Machine Learning**: Nutrition prediction and meal planning logic.
- **Swagger/**OpenAPI: API documentation and testing.

## Project Structure
```
src/main/java/com/abdoatiia542/GraduationProject/
  ├── annotations/           # Custom validation annotations
  ├── cloudnairy/            # Cloudinary integration
  ├── configuration/         # App, security, and third-party configs
  ├── constant/              # Constants (JWT, Regex, etc.)
  ├── controller/            # REST controllers (auth, account, plan, etc.)
  ├── dto/                   # Data Transfer Objects
  ├── filter/                # Security filters (JWT, etc.)
  ├── handler/               # Exception and event handlers
  ├── mapper/                # Entity-DTO mappers
  ├── model/                 # JPA entities and enums
  ├── repository/            # Spring Data repositories
  ├── saver/                 # Notification and data savers
  ├── service/               # Business logic and services
  ├── specification/         # Query specifications
  ├── utils/                 # Utility classes
  ├── validator/             # Custom validators
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- (Optional) Cloudinary account for image management
- (Optional) Firebase credentials for notifications

### Setup Instructions
1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd GraduationProject
   ```
2. **Configure environment:**
   - Edit `src/main/resources/application.yml` with your database, Cloudinary, and Firebase credentials.
   - Place your Firebase service account JSON in `src/main/resources/firebase/` if using notifications.
3. **Install dependencies and build:**
   ```bash
   ./mvnw clean install
   ```
4. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Access the API:**
   - API base URL: `http://localhost:8080/api/`
   - Swagger UI: `http://localhost:8080/swagger-ui.html` or `/api/docs`

## Deployment

### Backend Deployment (Railway)
The backend is ready for cloud deployment using [Railway](https://railway.app/).

**Steps:**
1. Push your code to a GitHub repository.
2. Sign up/log in to Railway and create a new project.
3. Connect your GitHub repo and deploy.
4. Set environment variables (database URL, Cloudinary, Firebase, etc.) in Railway’s dashboard.
5. Railway will build and host your Spring Boot app, providing a public API endpoint.

### Database Hosting (Neon)
The PostgreSQL database is hosted on [Neon](https://neon.tech/).

**Steps:**
1. Create a Neon account and a new project.
2. Set up your database and get the connection string.
3. Update your `application.yml` with the Neon database URL.
4. Run migrations or let Spring Boot auto-create tables.

### AI Model Hosting (PythonAnywhere)
AI/ML models are deployed on [PythonAnywhere](https://www.pythonanywhere.com/).

**Steps:**
1. Upload your trained models and serving scripts to PythonAnywhere.
2. Expose a REST API (e.g., using Flask or FastAPI) for model inference.
3. Update your Java backend to call the PythonAnywhere API for predictions.

## Figma Design

All UI/UX designs and application screens were created using [Figma](https://www.figma.com/design/RDLPaiEr28kyl7KhZYSlB6/Graduation-Project?node-id=0-1).

- The Figma project includes user flows, wireframes, and high-fidelity mockups for all screens.
- [View the Figma project here.](https://www.figma.com/design/RDLPaiEr28kyl7KhZYSlB6/Graduation-Project?node-id=0-1)
- The design ensures a modern, user-friendly experience and serves as a reference for frontend development.

## API Documentation
Interactive API documentation is available via Swagger. Visit `/swagger-ui.html` or `/api/docs` after starting the app to explore and test endpoints.

