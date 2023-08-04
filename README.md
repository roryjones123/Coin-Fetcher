# **Android Times Test**

Hello, this is my submission for the Times Android take home test, thanks for taking the time to review the code and 
considering me for the position.

## **Preface**

As a heads up - I am using one of a few skeletons that I use for personal projects and take-home exercises. 
I don’t believe that setting up DI/Modules/Packages/Navigation every single time I start a small-to-mid-size project is
a good practise, there is a lot of repetition in boiler plate (as it will end up being identical to one of these skeletons
in most cases) so instead I will justify why I made the choice of skeleton: ie why I chose the architecture that I chose 
and would behappy to discuss these choices further. I will also build upon this choice.

In addition to this, this gives me a freedom to demonstrate:

1. One of the main issues of some concepts in this code (ie clean architecture) is slightly longer setup, which is 
   contradictory to the spec.
    1. This is offset by the fact I have skeletons available, which eliminates the boiler plate setup/module setup.
   
2. The spec has tight focus. It has one/two screens and two calls. Complexity is unnecessary especially as the benefits of adhering to Clean/Solid are demonstrated in monolithic projects, not very small projects.
    1. I believe it is important to demonstrate what I see as important in a larger more complex project like the Times or my previous roles/projects, the ideologies that have helped keep code decoupled, testable, maintainable.

So because setup is quick + I think important to represent what I’ve been learning and seeing is good in larger 
production projects, I have decided on showcasing some of the aforementioned concepts (like clean/SOLID). It also is
a good opportunity for me to practise and refresh my knowledge :)

## **Decisions**

### **Clean/Solid Architecture**

### **DI**

### **Caching**

### **Description of Application**
Application connects to paprika and lists crypto coins. 

Data always comes from the local persistence (offline-first approach) and updates when necessary.

Clicking on each item navigates to a page with more information on the coin.

Use swipe-down gesture to refresh downloaded data.

Supports light/dark mode.
