## 7) Tilt

In this chapter Tilt as a tool of analyzing running container and simplification of workflow is discussed.

At this point you now created a working spring boot application and were able to containerize it, deploy it to a container
inside docker as well as a local kubernetes cluster on your device. Furthermore, you were able to create different configurations,
and reducing the effort by using Kustomize. Now, we want to simplify this processes even more. For this reason we want
to take a closer look onto Tilt. 

Tilt is a microservice not only deploying to the kubernetes cluster as you wish, it also enables a graphic overview of
your running pods and immediate feedback if something goes wrong or crashes. Furthermore, it updates your kubernetes cluster
on any code change automatically and reduces your command line input to `tilt up`. To get a feeling for all this, 
shut down all running kubernetes pods and docker container for now. And go to your command line into the
`SpringBootWeather` folder.

To check, if your tilt is set up correctly and is working, you can type:

```
tilt version
```

an additional time. Now if all is set up correctly, you should be able to run

```
tilt up
``` 

and a short output gives you the option to press spacebar. After you pressed spacebar your standard browser should open
and tilt should start your local kubernetes pods automatically. At this point, check the outputs by taking a closer look
onto all given pods and deployments.

After we saw now how powerful tilt is, let's add tilt to your application. Again, you need to add a setup file for tilt, 
a so called Tiltfile. This Tiltfile contains information of your kubernetes yaml files and Dockerfile as well as further 
information of port forwarding. Looking at the Tiltfile of this project all will become a little clearer, hopefully.

```
print('Hello Tiltfile')

k8s_yaml(['kubernetes/db_config.yaml','kubernetes/weather_db_deployment.yaml', 'kubernetes/weather_db_service.yaml'])
k8s_yaml(['kubernetes/app_config.yaml','kubernetes/weather_app_deployment.yaml', 'kubernetes/weather_app_service.yaml'])

docker_build('springbootweather_app', '.')

k8s_resource('app', port_forwards='8081:8080')
```

The first line creates an output in your tilt webview, which should you have noticed already using the tilt up command.
It is just to create feedback and has no further use for us. Feel free to delete it at any time you want. The second and
third line use our yaml files to create kubernetes deployments and services, followed by the fourth line necessary in case
of an update of sourcecode, which creates a new docker image of your application in case its necessary. The last line
puts the application onto port 8081 of your localhost, which can directly be accessed over tilt as well.

If you add a Tiltfile as easy as this to your application, you now added Tilt to your project and hopefully need to use
a lot less commands in your workflow. 

---
Last chapter: [Chapter 06 - Kustomize](chapter-6.md)
