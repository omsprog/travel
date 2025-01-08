# Minikube

`minikube start --cpus 2 --memory 4gb --driver docker --profile travel`
`minikube delete --profile travel`
`minikube start --profile travel`  
`minikube stop --profile travel`  

# Kubectl

`kubectl get nodes`  
`kubectl config get-contexts`  
`kubectl config use-context travel`  

## Deploying databases

`kubectl apply -f k8s/dev/services` 
`kubectl delete -f k8s/dev/services` undeploy database
`kubectl get pods`  
`kubectl logs deployment/travel-postgres`  
`kubectl logs deployment/travel-redis`  

## Building image for backend services and deploying to k8s cluster

`cd dashboard-service && mvn clean package` Optional flag `-DskipTests`  
`docker build -t dashboard-service .`  
`minikube image load dashboard-service --profile travel`
`kubectl delete -f k8s/dev/dashboard-service.yml` undeploy service   

`cd tour-service && mvn clean package` Optional flag `-DskipTests`  
`docker build -t tour-service .`  
`minikube image load tour-service --profile travel`
`kubectl delete -f k8s/dev/tour-service.yml` undeploy service   

`cd api-gateway && mvn clean package` Optional flag `-DskipTests`  
`docker build -t api-gateway .`  
`minikube image load api-gateway --profile travel`
`kubectl delete -f k8s/dev/api-gateway.yml` undeploy service  

`kubectl apply -f k8s/dev/applications`  
`minikube ssh docker images --profile travel` Images loaded in minikube  

`kubectl get pods`    
`kubectl logs deployment/dashboard-service`  
`kubectl logs deployment/tour-service`  
`kubectl logs deployment/api-gateway`  
`kubectl port-forward service/api-gateway 9000:9000`  