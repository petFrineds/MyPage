git pull
docker build -t mypage-backend .
docker tag mypage-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/mypage-backend:latest
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin 811288377093.dkr.ecr.us-west-2.amazonaws.com/
docker push 811288377093.dkr.ecr.us-west-2.amazonaws.com/mypage-backend:latest
kubectl delete -f manifests/mypage-deployment.yaml
kubectl apply -f manifests/mypage-deployment.yaml
kubectl get pod
kubectl apply -f manifests/mypage-service.yaml
kubectl get service