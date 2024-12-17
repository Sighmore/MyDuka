# MyDuka - Shop Management App

## Features

### Visual Representation of Financial Trends
- Financial trends charts for easy monitoring.

### Credit Management
- Manage customer credits.
- Track credit balances.
- Blacklist feature for credit management.
- Customer information storage.

## UI/UX Features

### Material Design 3
- Dynamic color theming.
- Elegant typography.
- Modern component designs.
- Consistent spacing and layouts.

### Key UI Components

#### Cards
- Stock item cards with quick actions.
- Financial record cards with detailed information.
- Credit customer cards with status indicators.

#### Interactive Elements
- Validated text fields with real-time feedback.
- Intuitive quantity controls.
- Confirmation dialogs for important actions.
- Bottom navigation for easy screen switching.

#### Data Visualization
- Financial trends chart.
- Low stock indicators.
- Credit status indicators.
- Daily totals overview.

#### Responsive Layout
- Adaptive layouts for different screen sizes.
- Proper spacing and alignment.
- Scrollable content areas.
- Efficient use of screen real estate.

### User Experience
- Real-time validation feedback.
- Smooth navigation transitions.
- Clear error messages.
- Intuitive data input.
- Quick access to common actions.
- Professional and clean interface.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Database**: Room
- **State Management**: StateFlow
- **Navigation**: Navigation Compose
- **Design System**: Material 3
- **Build System**: Gradle with Kotlin DSL

## Project Structure

The project follows a modular architecture and adheres to clean code principles to ensure scalability and maintainability. Specific modules include:

1. **App Module**
   - Contains application-level configurations and setup.
   
2. **Data Module**
   - Manages database interactions and data repositories.

3. **Domain Module**
   - Contains business logic and use cases.

4. **UI Module**
   - Manages screens, components, and navigation.

5. **Utils Module**
   - Common utilities and extensions used across the app.


## Screenshots

<div align="center">

<h2>A GIF to Demonstrate Usage</h2>

<p>
  <img src="https://github.com/user-attachments/assets/cbc28598-61ee-42c3-988b-69e035594dca" alt="Usage Demonstration GIF">
</p>

<table>
  <tr>
    <th>Dashboard & Finance</th>
    <th>Stock Management</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/5a687cd8-4cd7-4617-aea2-45ef17e4e47e" width="280" alt="Dashboard and Finance"></td>
    <td><img src="https://github.com/user-attachments/assets/20d9c29c-d062-4074-a6a5-6ffbcdf2013d" width="280" alt="Stock Management"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/bf2da044-ed0c-49e5-872c-7e6c0ed2a4ad" width="280" alt="Dashboard and Finance"></td>
    <td><img src="https://github.com/user-attachments/assets/3678cac2-f8d4-493f-820b-aae996d62165" width="280" alt="Stock Management"></td>
  </tr>
  <tr>
    <th>Credit Management</th>
    <th>Reports & Analytics</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/10c4abb0-52c3-4a04-8975-137ac053d89b" width="280" alt="Credit Management"></td>
    <td><img src="https://github.com/user-attachments/assets/48f2c29d-0370-49ec-9cc9-98b120f4cd37" width="280" alt="Reports and Analytics"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6143a266-4b16-4329-a27c-e2ad8da2f0d3" width="280" alt="Credit Management"></td>
    <td><img src="https://github.com/user-attachments/assets/3871ca68-5ebc-4f21-bc42-9c4983e165c2" width="280" alt="Reports and Analytics"></td>
  </tr>
</table>

</div>


## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/Sighmore/MyDuka.git
   ```

2. Open the project in Android Studio.

3. Build the project to download dependencies:
   ```bash
   ./gradlew build
   ```

4. Run the application on an emulator or a physical device.

## Contribution Guidelines

1. Fork the repository and create your feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Commit your changes with descriptive messages:
   ```bash
   git commit -m "Add your commit message here"
   ```

3. Push to the branch:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Open a Pull Request (PR) with a detailed explanation of your changes.

---

Thank you for contributing to **MyDuka**! Your efforts help make this project better for everyone.
