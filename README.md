# HappyStore :)

This is a comprehensive sample Android application demonstrating various modern development practices and technologies, including Clean Architecture, Modularization, MVI (Model-View-Intent) pattern, Jetpack Compose, Room Persistence Library, Hilt for dependency injection, Work Manager for background tasks, Unit Testing, and GitHub Actions for continuous integration.

## Introduction

This Android application serves as a sample project showcasing best practices in modern Android app development. It focuses on implementing Clean Architecture principles, modularization, and utilizing the latest tools and libraries provided by the Android Jetpack ecosystem.

## Features

- **Show a list of products**: Display a list of products retrieved from a remote server or from local storage.
- **Filter products by categories**: Allow users to filter products based on different categories to easily find what they're looking for.
- **Select multiple products and add to shopping cart**: Enable users to select multiple products and add them to their shopping cart for purchase.
- **Change shopping cart products quantity**: Allow users to adjust the quantity of products in their shopping cart.
- **Show product list while in offline mode**: Ensure that users can still view their product list even when they are offline, using locally cached data.
- Show stores on Map: Allow users to select store and see the related photos on Google map.

## Technologies Used

- **Clean Architecture**: Separation of concerns and maintainability.
- **Modularization**: Splitting the app into modules for better scalability and reusability.
- **MVI (Model-View-Intent) pattern**: Unidirectional data flow architecture for a predictable state management.
- **Jetpack Compose**: Declarative UI toolkit for building native Android UI.
- **Room Persistence Library**: Android's native SQLite database library for local data storage.
- **Hilt**: Dependency injection library for Android, built on top of Dagger.
- **Work Manager**: Android's recommended solution for deferrable background tasks.
- **Google Maps**: Map to show near by stores 
- **Unit Testing**: Writing tests to ensure code quality and reliability.
- **GitHub Actions**: Automating CI/CD pipelines for continuous integration.

## Setup and Configuration

To set up the project locally, follow these steps:

1. Clone the repository: `git clone <repository_url>`
2. Open the project in Android Studio.
3. Sync Gradle and install required dependencies.

## Testing

The project includes unit tests for testing business logic.

## Continuous Integration

GitHub Actions is set up for continuous integration. Every push to the repository triggers a build process that runs unit tests and checks the code quality.

## Architecture

  <img src="https://github.com/ymatinfard/HappyStore-ecommerceApp/blob/develop/happystore_architecture_screenshot.png" alt="Happy Store Map Screenshot" width="500" height="700" /> 

## Note
This project is under development :)


<p float="left">
  <img src="https://github.com/ymatinfard/HappyStore-ecommerceApp/assets/16016916/ab248c09-dfc3-4172-8dd0-7242d3727665/happystore_main_screenshot.png" alt="Happy Store Main Screenshot" width="400" height="800" />
  <img src="https://github.com/ymatinfard/HappyStore-ecommerceApp/blob/develop/happystore_map_screenshot.jpg" alt="Happy Store Map Screenshot" width="400" height="800" /> 
</p>


