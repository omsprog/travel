# Minikube

`minikube start --cpus 2 --memory 4gb --driver docker --profile travel`
`minikube start --profile travel`  
`minikube stop --profile travel`  
`minikube delete --profile travel`

# Kubectl

`kubectl get nodes`  
`kubectl config get-contexts`  
`kubectl config use-context travel`  

Deploying database

`cd k8s/dev`  
`kubectl apply -f services` 
`kubectl delete -f services`  undeploy database
`kubectl get pod`  
`kubectl logs deployment/travel-postgres`  

Building image for backend and deploying to k8s cluster

`mvn clean package` Optional flag `-DskipTests`  
`docker build -t travel-backend .`  
`minikube image load travel-backend --profile travel`
`kubectl apply -f k8s/dev/application.yml`  
`kubectl get pods`  
`kubectl get all -l app=travel-backend`  
`kubectl logs deployment/travel-backend`  
`kubectl port-forward service/travel-backend 8080:8080`  