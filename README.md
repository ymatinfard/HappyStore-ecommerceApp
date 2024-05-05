This is a comprehensive sample Android application demonstrating various modern development practices and technologies, including Clean Architecture, Modularization, MVI (Model-View-Intent) pattern, Jetpack Compose, Room Persistence Library, Hilt for dependency injection, Work Manager for background tasks, Unit Testing, and GitHub Actions for continuous integration.

Table of Contents
Introduction
Features
Technologies Used
Architecture
Project Structure
Demo Flavor
Setup and Configuration
Running the Application
Testing
Continuous Integration

Introduction
This Android application serves as a sample project showcasing best practices in modern Android app development. It focuses on implementing Clean Architecture principles, modularization, and utilizing the latest tools and libraries provided by the Android Jetpack ecosystem.

Features
Show a list of products: Display a list of products retrieved from a remote server or from local storage.
Filter products by categories: Allow users to filter products based on different categories to easily find what they're looking for.
Select multiple products and add to shopping cart: Enable users to select multiple products and add them to their shopping cart for purchase.
Change shopping cart products quantity: Allow users to adjust the quantity of products in their shopping cart.
Show product list while in offline mode: Ensure that users can still view their product list even when they are offline, using locally cached data.
Technologies Used
Clean Architecture: Separation of concerns and maintainability.
Modularization: Splitting the app into modules for better scalability and reusability.
MVI (Model-View-Intent) pattern: Unidirectional data flow architecture for a predictable state management.
Jetpack Compose: Declarative UI toolkit for building native Android UI.
Room Persistence Library: Android's native SQLite database library for local data storage.
Hilt: Dependency injection library for Android, built on top of Dagger.
Work Manager: Android's recommended solution for deferrable background tasks.
Unit Testing: Writing tests to ensure code quality and reliability.
GitHub Actions: Automating CI/CD pipelines for continuous integration.

Architecture
The project follows Clean Architecture principles, dividing the app into multiple layers:

Presentation Layer: Responsible for UI components and user interactions.
Domain Layer: Contains business logic and use cases.
Data Layer: Handles data operations, including fetching data from remote sources or local databases.
Project Structure
The project is structured into modules for better organization and separation of concerns:

app: Contains the main Android application module.
domain: Contains business logic and use cases.
data: Implements data sources and repositories.
presentation: Contains UI components and ViewModels.

Demo Flavor
The application provides a demo flavor that can run independently without relying on server-side APIs. This is useful for testing and development purposes when the server is not available.

Setup and Configuration
To set up the project locally, follow these steps:

Clone the repository: git clone <repository_url>
Open the project in Android Studio.
Sync Gradle and install required dependencies.

Running the Application
You can run the application on an emulator or physical device:

Testing
The project includes unit tests for testing business logic.

Continuous Integration
GitHub Actions is set up for continuous integration. Every push to the repository triggers a build process that runs unit tests and checks the code quality.

<img src="https://github.com/ymatinfard/HappyStore-ecommerceApp/assets/16016916/ab248c09-dfc3-4172-8dd0-7242d3727665/happy_store_screen_shot2.png" alt="Happy Store Screenshot" width="200" height="300">

