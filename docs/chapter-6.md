## 6) Kustomize

In this chapter Kustomize as an option of simplifying configurations is displayed.

Now we were able to deploy our applications to a local kubernetes cluster. But this was a lot of effort and now imagine
you want to create different configurations like staging, dev or prod. This will lead to lots and lots of code next to the
effort starting this in the command line.

To reduce this effort Kustomize can be used. Kustomize reduces the different configurations to patches being applied in 
so-called overlays. For this inside the `kubernetes` folder a base kustomization.yaml is defined using previous .yaml-files.

```
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

resources:
  - db_config.yaml
  - weather_db_deployment.yaml
  - weather_db_service.yaml
  - app_config.yaml
  - weather_app_deployment.yaml
  - weather_app_service.yaml
```

These are then combined and applied by several patches defining new users for the database, new ports for the services
and much more if necessary. An example of a patch applied is given in `kubernetes/overlays/production/db_config_patch.yaml`:

```
# Configmap patch
apiVersion: v1
kind: ConfigMap
metadata:
  name: db-config
data:
  POSTGRES_DB: weather_db
  POSTGRES_USER: newUser
  POSTGRES_PASSWORD: newPassword
```

Defining the metadata and the kind of file this metadata is, this patch changes the name and userdata in the configmap of
our postgres database.

With this set up, the entire application can be started via a command like:

```
kustomize build <kustomization.yaml directory> | kubectl apply -f -
```

So if you are in the `kubernetes/base` directory, just execute:

```
kustomize build . | kubectl apply -f -
```

to apply the base kubernetes cluster or:

```
kustomize build ../overlays/production | kubectl apply -f -
```

to apply the production kubernetes cluster.

---
Last chapter: [Chapter 05 - Kubernetes](chapter-5.md)

Next chapter: [Chapter 07 - Tilt](chapter-7.md)