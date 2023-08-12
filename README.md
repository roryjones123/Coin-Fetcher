<div align="center">

# 🪙 **Coin Fetcher** 🪙

_An elegant cryptocurrency app showcasing Clean/MVI architecture with a splash of Hilt DI, Compose navigation, and comprehensive UI testing._

</div>

---

## 🏗 **Architecture: Clean/SOLID**

Our codebase aligns with the Clean/SOLID architecture principles. This ensures:

- **Decoupling** for modular design
- Improved **maintainability**
- Enhanced **testability**

The code is segregated across three distinct layers:

1. **Data Layer**: Source of truth for data
2. **Domain Layer**: Business logic resides here
3. **Presentation Layer**: UI/UX elements and controls

We've incorporated mappers/DO's to prevent potential overlaps between these layers.

---

## 📦 **Modules**

For optimal scalability and maintainability, our modules are:

1. **App**:
   - Houses the Android framework components.

2. **Basic Features**:
   - Manages the logic for features.
   - Contains presentation, domain, and data packages for a clear separation, aligned with Clean/SOLID principles.

3. **Core**:
   - Offers basic elements like navigation, base view models, and networking.
   - Designed for potential modular expansion in the future for best practices.

---

## 💉 **Hilt DI**

**Hilt** is our chosen framework for dependency injection due to:

- Its easy setup.
- Removing dependencies from their implementations.
- Reducing boilerplate compared to manual DI.
- Simplifying object relationships and lifecycle management.

---

## 🌊 **Flows**

We employ **Kotlin Flows** to channel data from our API/DAO's to the UI. Why not LiveData?

- **Compose Integration**: `collectAsState` ensures lifecycle-aware flows.
- **Backpressure**: Flows gracefully handle overwhelming UI demands.
- **Built-in Functionalities**: Effortless error handling and transformations.

---

## 🧪 **Tests**

Our testing suite covers:

- ViewModel (VM)
- Use Cases
- Composable elements 

We also feature UI tests for individual composables and entire screen flows.

---

## 📱 **Application Overview**

- **Source**: Connects to **Paprika** to list cryptocurrency coins and tags.
- **Navigation**: Click on a coin for a detailed dialog box.
- **Refresh**: Update your data with a simple button press.
- **Filtering**: Use tags to filter the displayed cryptocurrencies.

---

## ⏳ **Upcoming Enhancements**

1. **Coin Screen**:
   - Pass coin ID
   - Fetch and display data
2. **Cache Integration**
3. **UI Overhaul**: Tag section improvements.
4. **Media**: Introduction of videos and architecture diagrams.

---

<div align="center">

**Thank you for checking out Coin Fetcher!** ✨

</div>





