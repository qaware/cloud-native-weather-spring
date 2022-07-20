## 5) Kubernetes

In this chapter Kubernetes cluster and deployments are discussed.

### Why Kubernetes?

So far, you are able to locally create a multi-container application, using a local database. Of course, this is already
good for purposes of local developing, but to achieve concepts of a 12 Factor App, we want to be able of a deployment to a
kubernetes cluster independent of local devices.

While docker is used to locally run docker images in containers, kubernetes organizes containers, services and deployments.
This enables developers to watch containers, while kubernetes fully cares about managing running containers, restart 
policies, etc..

### Set Up A Local Kubernetes Cluster

To test the deployment to a kubernetes cluster, we can use docker again. Docker Desktop enables developers
to create a local kubernetes cluster for deployments. To enable this go to your local Docker Desktop settings.

To make sure all your settings are set up correctly, open a command line and type:

```
kubectl config get-contexts
```

If docker-desktop is not already chosen as your context, type:

```
kubectl config use-context docker-desktop
```

to do so. To check all is working correctly you can further execute:

```
kubectl get nodes
```

With the local kubernetes cluster set up correctly, we can now start deploying our application!

### Deploy To A Local Kubernetes Cluster

To achieve a deployment you will need files telling what to deploy. In this case our docker-compose.yml gives us
a good overview, but is sadly not compatible with kubectl. Kubectl needs .yaml-files of the structure given in this project,
which distinguishes between pods, deployments, containers and further information needed to define your wanted cluster setup.

Therefore, in this case for a good overview six .yaml-files where created. Three of those affect the database and three the application.
Each consists of one deployment, one service and one config file leading to the total number of six.

At this point, you probably wonder why we need a service and a deployment and can't combine them. A deployment is necessary
as it is responsible to set up a so-called pod. A pod is necessary to give the framework for your application to run in. 
It decides how many of your applications run in parallel and how it should behave in certain cases. It declares 
dependencies, like in this case the database for the application and cares about the context around your app. A service 
does not care about these points. It uses a pod to run and grants users network access to the application from
the outside.

With this out of the way it should be clear now, that services and deployments work nicely together. To set up a deployment
a yaml file could look something like:

```
apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  name: app
spec:
  replicas: 1
  selector:
    matchLabels:
      springbootweather: web
  template:
    metadata:
      labels:
        springbootweather: web
      creationTimestamp: null
    spec:
      containers:
        - envFrom:
            - configMapRef:
                name: app-config
          image: springbootweather_app
          imagePullPolicy: Never
          name: app
          ports:
            - containerPort: 8080
              name: app-target-port
          resources: {}
      restartPolicy: Always
status: {}

```

As explained before, here general information is given. Next to the apiVersion of your file, the kind of this file is
clarified as well as the name of the desired pod, labels, environment variables (located in an external configMap) and much more. 
An important line in this code is ```ImagePullPolicy: Never```. This prevents your kubernetes of trying to pull a docker
image of docker hub as you want to use your local Docker image. Defining now a service of this app:

```
apiVersion: v1
kind: Service
metadata:
  labels:
    springbootweather: web
  creationTimestamp: null
  name: app
spec:
  ports:
    - protocol: TCP
      port: 8081
      targetPort: 8080
  selector:
    springbootweather: web
status:
  loadBalancer: {}
```

your app will be able to be accessed. Analog files for the database are necessary of course. To check them out, feel free
to look in the yaml files of this application in the `SpringBootWeather/kubernetes/base` folder and scroll through the code and comments to
understand it better. An additional configmap is given there as well, meaning a .yaml file containing necessary
environment variables, which can be used in the other .yaml files.

After you fully understood the .yaml-files, you are now capable of deploying to your local cluster. For this open your
command line at the folder your yaml files are in. After that use these to create your application with the commands:

```
kubectl apply -f <yaml-file>
```

To create the app of this example open the `SpringBootWeather/kubernetes/base` folder and type the commands:

```
kubectl apply -f db_config.yaml
kubectl apply -f weather_db_deployment.yaml
kubectl apply -f weather_db_service.yaml
kubectl apply -f app_config.yaml
kubectl apply -f weather_app_deployment.yaml
kubectl apply -f weather_app_service.yaml
```

After retrieving information of running pods, with the line

```
kubectl get pods
```

or specific logs of a single pod running:

```
kubectl logs <podname>
```

you should see two running pods in your local kubernetes cluster. If that is the case, congrats, you just deployed your
application to a local kubernetes cluster. To now stop your kubernetes cluster, type:

```
kubectl delete -f <yaml-file>
```

and your pods will shut down.

As you probably noticed already, these are a lot of commands to do, so can we simplify that?
Of course, we can.

---
Last chapter: [Chapter 04 - Docker-Compose](chapter-4.md)

Next chapter: [Chapter 06 - Kustomize](chapter-6.md)