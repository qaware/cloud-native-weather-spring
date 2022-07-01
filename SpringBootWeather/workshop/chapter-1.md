## 1) Creating A Spring Boot Application

In this chapter of this workshop a short introduction into spring boot is given, helping you to create a spring
boot application, which later can be used to apply concepts of cloud native.

To generate a spring boot application simply go to [Spring Initializr](https://start.spring.io/) and customize and generate
the project to your desired values. In case of this workshop we chose:

- Maven Project
- Spring Boot 2.7.1
- Group: de.qaware
- Packaging: Jar
- Java: 17
- Dependencies:
    - Spring Web

All other dependencies will be added later by hand. Alternatively, if your IDE supports Spring Initializr you can create
the spring boot project directly in your IDE itself and don't need to download and import it.

Either way as soon as this process is finished go to the `src/main/java` folder and your spring application will
already be able to run. Just start the application given and watch the process to start. At [Localhost:8080](localhost:8080)
you should now see a small webpage, without much of interest. To change the appearance of your application create a new
html file in the `resources/static` folder. In case of this workshop we called it index.html, and filled the html file with:

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test application</title>
</head>
<body>
    <p><a>Greetings</a></p>
</body>
</html>
```

With this the application will greet you nicely the moment you open it in your browser.

Congrats, you just created your first Spring Boot Application!

---

Next chapter: [Chapter 02 - Structure of code](chapter-2.md)