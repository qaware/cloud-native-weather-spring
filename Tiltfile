# -*- mode: Python -*-
# allow_k8s_contexts('rancher-desktop')

local_resource('weather-service-build', './mvnw package -DskipTests', dir='.', deps=['./pom.xml', './src/'], labels=['Spring'])

# to disable push with rancher desktop we need to use custom_build instead of docker_build
# docker_build('cloud-native-weather-spring', '.', dockerfile='Dockerfile', only=['./target/'])
custom_build('cloud-native-weather-spring', 'docker build -t $EXPECTED_REF .', ['./target/'], disable_push=True)

k8s_yaml(kustomize('./k8s/overlays/dev/'))
k8s_resource(workload='weather-service', port_forwards=[port_forward(18080, 8080, 'HTTP API')], labels=['Spring'])
