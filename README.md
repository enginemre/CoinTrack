# Coin Track

Coin Track allows users to view a list of coins obtained from coingecko.com's API, and enables them to see details, latest prices, descriptions, and more about these coins. Additionally, users can mark their favorite coins for easier tracking and perform searches among all available coins.

## Features

- Coin List: View a list of coins using the Coingecko.com API.
- Coin Details: View details, prices, descriptions, and specifics about coins.
- Favorites: Mark coins as favorites for easy tracking.
- Search: Search through all available coins.

## Architecture and Technologies

Following S.O.L.I.D principles, the project has a feature and layer-based architecture, with each feature segregated into Data-Domain-UI layers. The architectural design is based on the MVVM pattern.

### Technologies Used

- Version Catalog
- Navigation Component
- Flows Reactive Programming
- Dagger - Hilt for Dependency Injection
- Retrofit
- Gson for Serialization
- Glide for Image Caching and Loading
- Room for Local Database
- Lottie
- Swipe Refresh Layout
- Firestore
- Firebase Auth

## Test User Information

- **Email:** test@gmail.com
- **Password:** test123

## Installation and Usage

To run the project, follow these steps:

1. Clone or download the project files.
2. Open the project in Android Studio.
3. Install required dependencies and configure settings.
4. Create a Firebase account and integrate necessary configurations into the project.
5. Test the application by logging in with the provided test user information.

Note: AGP version should be 8.2.0 and the minimum Android Studio Hedegog should be installed.
