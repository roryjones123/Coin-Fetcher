# **Android Times Test**

Hello, this is my submission for the Times Android take home test, thanks for taking the time to review the code and 
considering me for the position.

## **Preface**

This code involves some setup for modules/DI from a template to reduce the time I had to spend on it.

### **Clean/Solid Architecture**

This code attempts to adhere to Clean/SOLID architecture, to decouple, improve maintainability, testability  
and so on, separating the code  between three layers: 

1) data
2) domain
3) presentation

While also using a series of mapper/DO's to prevent any overlapping. Use cases use to further adhere to Clean/Solid
and separate some logic from the VM's.

### **Modules**

Modules broken down to provide scalability/maintainability.  

1) app

Main Application entry point to app, plus database.

2) basic feature

Handles logic for the features, including a presentation/domain/data package for separation of key logic according to 
Clean/SOLID

3) core

Some more basic elements to be shared accross the app, such as navigation, base view models, networking. Could be later 
broken down into more modules to adhere to better practise.

### **Hilt DI**

This is for me a more subjective choice due to the simplicity of setup and use + size of project.

### **Flows**

Kotlin flows used to handle the data from the API's, helps handle different intents like loading and opens up an easy
way of handling caching if added later.

### **Caching**

Originally I planned on including caching, but removed for simplicities sake. Some of the original DB setup is still
in the code as I might flesh it out at a later date.

### **Tests**

Tests have been setup to cover VM/UseCase/Compose elements. Some of the VM tests currently fail ATM due to a
misunderstanding on how they're being tested against the thread after making sorting of coins against filters not
run on main thread.

**Potential fix now added in PR https://github.com/roryjones123/Coin-Fetcher/pull/5**


### **Description of Application**
Application connects to paprika and lists crypto coins and tags. 

Clicking on each item navigates to a dialog with more information on the coin.

Press refresh button to refresh downloaded data.

Press filter to filter the cryptocurrencies by those that have the tag selected.

## **TODO at later stage**:

1. **VM TEST DISPATCHER BUG**
   VM tests all worked until I changed intent handling to io thread due to heavy sort load, need to check this ASAP
   but spent a lot of time on this project already. **Potential fix now added in PR https://github.com/roryjones123/Coin-Fetcher/pull/5**
2. Provide proper mapping for different error types
3. Provide more logging
4. Remove some VM logic, especially intent logic for filtering
5. Clean up ui
   1. Change filters to a drop down or some other way of being things more clearly
   2. Scrolling functionality
   3. Add proper screen for complex coin
6. Provide proper data objects for information in the Coin at all appropriate levels.
7. Flesh out local DB and re-add caching, create appropriately normalised tables.
   1. Tags
   2. Complex Coin Data
   3. Coin Data
8. Properly handle complex coin and coin, ie investigate better way of handling
9. Write more tests
   1. Dialog
   2. Get Coin use case
   3. Expand UI tests
   4. Test mapper (technically already tested through vm)
10. Find better way to scroll dialog


