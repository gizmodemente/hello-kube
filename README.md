# hello-kube project

The original hello project has been generated by the lagom/lagom-scala.g8 template. Kubernetes configuration has been added to show how a Lagom project can be deployed in a kubernetes cluster with Istio.

### Deployment

The steps to follow for deploying this project in kubernetes are:

- Have a kubernetes cluster. For test purposes you can use minikube.<br>
`$ minikube start -p hello-k8s`
- Install [Istio](https://istio.io/docs/setup/install/kubernetes/) in the k8s cluster. 
- Create and publish the Docker container with the service. You can use your preferred method to do that, by example using the sbt docker plugin included in [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/formats/docker.html).<br>
`$ sbt docker:publishLocal`
- Deploy rbac configuration role.<br>
`$ kubectl apply -f kubernetes/discovery-role.yaml`
- Change the ip's for Kafka and Cassandra services and deploy them.<br>
`$ kubectl apply -f kubernetes/cassandra-service.yaml`<br>
`$ kubectl apply -f kubernetes/kafka-service.yaml`
- Enables Istio for individual service or automatically labeling the namespace.<br>
`$ kubectl label namespace default istio-injection=enabled`
- Create the secrets with database and play secret.<br>
`$ kubectl create secret generic db-secret --from-literal=username=<user> --from-literal=password=<password>`<br>
`$ kubectl create secret generic hello-application-secret --from-literal=secret="$(openssl rand -base64 48)"`
- Deploy the microservice.<br>
`$ kubectl apply -f kubernetes/hello-deployment.yaml`