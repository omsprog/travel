# Minikube

`minikube start --cpus 2 --memory 4gb --driver docker --profile travel`
`minikube start --profile travel`  
`minikube stop --profile travel`  
`minikube delete --profile travel`

# Kubectl

`kubectl get nodes`  
`kubectl config get-contexts`  
`kubectl config use-context travel`  
`cd k8s/dev`  
`kubectl apply -f services` deploy database 
`kubectl delete -f services`  undeploy database
`kubectl get pod`  
`kubectl logs deployment/travel-postgres`  
