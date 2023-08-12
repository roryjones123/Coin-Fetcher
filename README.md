# **Coin Fetcher**

This is a cryptocurrency app that demonstrates Clean/MVI architecture. It also includes Hilt DI, Compose navigation, UI testing.

### **Clean/Solid Architecture**

This code attempts to adhere to Clean/SOLID architecture, to decouple, improve maintainability, testability  
and so on, separating the code  between three layers: 

1) data
2) domain
3) presentation

While also using a series of mapper/DO's to prevent any overlapping. 

### **Modules**

Modules broken down to provide scalability/maintainability.  

1) app

Android framework components

2) basic features

Handles logic for the features, including a presentation/domain/data package for separation of key logic according to 
Clean/SOLID

3) core

Some more basic elements to be shared accross the app, such as navigation, base view models, networking. Could be later 
broken down into more modules to adhere to better practise.

### **Hilt DI**

Hilt has been chosen for the ease of setup, for injecting our dependencies. Depenency injections allows us to remove the dependency from the implementation of the dependency. 

The framework allows us to reduce the boiler plate required by manual DI, help us simplify the relationship between complex objects and control the creation of the object.

### **Flows**

Kotlin flows used to handle the data from the API/DAO's, through to its expression in the user interface as a state. Why not live data?

Flows provide great integration with compose, with collectAsState giving easily lifecycle aware flows - and the state driven approaches of both meshing together well (ie state change emitted by flow, appropriate ui state update reflected in Compose)

On top of this, Flows provide backpressure handling in the case our UI becomes overwhelmed downstream + great built-in functions for handling things such as transformations or errors.

### **Tests**

Tests have been setup to cover VM/UseCase/Compose elements. This includes user interface tests that test the individual composables and entire screens.

### **Description of Application**
Application connects to paprika and lists crypto coins and tags. 

Clicking on each item navigates to a dialog with more information on the coin.

Press refresh button to refresh downloaded data.

Press filter to filter the cryptocurrencies by those that have the tag selected.

## **TODO at later stage**:

1) Add screen for coin
   1) pass coin id value 
   2) run fetch
   3) display data
2) integrate cache
3) improve tag UI
4) add videos and architecture diagram






