## 2) Implement Further Logic

In this chapter of this workshop structure in code will be shortly discussed.

In case of this workshop we create a spring boot web application enabling a user to retrieve and store weather data.

To keep a good overview, categories (observable as folders) were chosen to create the structure of the source code.
In the following list motivations and reasons for single categories are given. Still, there is a variety of possibilities,
just make sure your code is well-structured.

- Controller
    - Handle requests from the frontend
    - Use services to get data to answer requests
- Service
    - Implements entire backend logic
    - Uses the database or external providers to retrieve information
    - Does not need to know anything about Controllers
- Model
    - Contains all data model classes of this project
- Provider
    - Contains all external endpoints (in this case implemented as simple class)
- Repository
    - Contains the database calls of this application

If you want to check the structure of this project, it is recommended to start in the Controller and work through 
single RequestHandlers to other elements of this code. 

This workshop focuses more on cloud native concepts, so the code itself is not explained here, but feel free to look through
single classes or methods.

---
Last chapter: [Chapter 01 - Set up a Spring Boot Application](chapter-1.md)

Next chapter: [Chapter 03 - Docker](chapter-3.md)