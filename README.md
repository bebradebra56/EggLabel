# 🥚 EggLabel - Egg Marking Reference App

A modern Android application to decode and understand egg markings, helping consumers make informed choices about egg quality, origin, and freshness.

## 📱 Features

### 🏠 Home Screen
- Quick egg code search
- Recent check history
- Quick action buttons for common tasks
- Beautiful welcome card with app introduction

### 🔍 Decoder Screen
- Decode egg codes from shell or package
- Comprehensive information display:
  - Egg category (C0, C1, C2, C3)
  - Housing type (Organic, Free-range, Barn, Cage)
  - Country of origin
  - Producer information
  - Expiry date with status indicator
- Color-coded status system:
  - 🟢 Green: Fresh (safe to consume)
  - 🟡 Yellow: Expiring soon
  - 🔴 Red: Expired
- Save and share functionality

### 📖 Encyclopedia Screen
- Comprehensive reference guide with three tabs:
  - **Categories**: Understanding egg quality categories
  - **Housing**: Different farming methods explained
  - **Countries**: ISO country codes reference
- Expandable cards with detailed descriptions
- Storage tips and best practices

### ⭐ Favorites Screen
- Save trusted producers for quick access
- View saved producer information
- Easy management of favorite items

### ⚙️ Settings Screen
- Language selection
- Dark mode toggle
- Push notification preferences
- Export check history
- Clear history option
- App information and legal documents

## 🎨 Design

### Color Palette
- **Background**: Cream (#FFF9E6) - Clean, warm background
- **Yellow Accent**: (#FFD93D) - Primary accent color
- **Green Fresh**: (#4ACFAC) - Fresh status indicator
- **Red Warning**: (#FF6B6B) - Warning indicator

### UI Style
- Minimalist and clean design
- Card-based layout with rounded corners
- Material 3 design system
- Smooth animations and transitions
- Intuitive navigation with bottom navigation bar

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM pattern
- **Navigation**: Jetpack Navigation Compose
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36

## 📦 Dependencies

```gradle
- androidx.core:core-ktx:1.17.0
- androidx.lifecycle:lifecycle-runtime-ktx:2.9.4
- androidx.activity:activity-compose:1.11.0
- androidx.compose:compose-bom:2024.09.00
- androidx.navigation:navigation-compose:2.8.5
- androidx.material3:material3:1.3.1
- androidx.material:material-icons-extended:1.7.5
- androidx.datastore:datastore-preferences:1.1.1
```

## 🚀 Building the Project

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or later
- Gradle 8.13

### Build Instructions

1. Clone the repository:
```bash
git clone https://github.com/yourusername/egglabel.git
cd egglabel
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Build and run:
```bash
./gradlew assembleDebug
```

Or use Android Studio's build button.

## 📱 Installation

### From Source
1. Build the APK as described above
2. Install on your device:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🎯 How to Use

1. **Check an Egg Code**:
   - Open the app
   - Enter the code from the egg or package
   - Tap "Decode Code" to see full information
   - View category, housing type, origin, and expiry date

2. **Browse Encyclopedia**:
   - Navigate to the Encyclopedia tab
   - Explore different categories:
     - Egg categories and quality levels
     - Housing types and farming methods
     - Country codes and standards

3. **Save Favorites**:
   - After decoding, save trusted producers
   - Access them quickly from the Favorites tab

4. **Customize Settings**:
   - Change language preferences
   - Enable/disable notifications
   - Export your check history

## 🌟 Understanding Egg Codes

### Categories
- **C0**: Premium quality - Highest category
- **C1**: First category - Excellent quality
- **C2**: Second category - Good quality
- **C3**: Third category - Standard quality

### Housing Types
- **0 - Organic**: Free-range organic farming with natural feed
- **1 - Free-Range**: Hens have outdoor access
- **2 - Barn**: Indoor housing with open space
- **3 - Cage**: Conventional cage system

## 📊 Project Structure

```
app/
├── src/main/
│   ├── java/com/egsi/labsi/sog/
│   │   ├── MainActivity.kt           # Main entry point
│   │   ├── data/
│   │   │   └── EggCodeRepository.kt  # Data layer
│   │   ├── model/
│   │   │   └── EggCode.kt            # Data models
│   │   ├── navigation/
│   │   │   ├── Screen.kt             # Screen definitions
│   │   │   └── NavigationItem.kt     # Navigation items
│   │   ├── screen/
│   │   │   ├── HomeScreen.kt         # Home screen
│   │   │   ├── DecoderScreen.kt      # Decoder screen
│   │   │   ├── EncyclopediaScreen.kt # Encyclopedia screen
│   │   │   ├── FavoritesScreen.kt    # Favorites screen
│   │   │   └── SettingsScreen.kt     # Settings screen
│   │   └── ui/theme/
│   │       ├── Color.kt              # Color definitions
│   │       ├── Theme.kt              # App theme
│   │       └── Type.kt               # Typography
│   └── res/                          # Resources
```

## 🔧 Future Enhancements

- [ ] Camera scanning with OCR
- [ ] QR code scanning
- [ ] Real-time expiry notifications
- [ ] Export history to CSV/PDF
- [ ] Multi-language support
- [ ] Dark theme implementation
- [ ] Producer database integration
- [ ] Offline mode support
- [ ] Widget support

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

Created with ❤️ for egg lovers

## 🙏 Acknowledgments

- Material Design Icons
- Jetpack Compose community
- Android development community

## 📞 Support

For support, please open an issue in the GitHub repository.

---

Made with Kotlin + Jetpack Compose

