
# builds the docker image of the local application. Replaces following commands of docker:
# docker build -t springbootweather_app .
docker_build('springbootweather_app', '.')

k8s_yaml(kustomize('k8s/overlays/dev/'))

# maps the port of the application to localhost:18080
k8s_resource('app', port_forwards='18080:8080')
