aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com
docker build -t urlshorten/frontend .
docker tag urlshorten/frontend:latest 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com/urlshorten/frontend:latest
docker push 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com/urlshorten/frontend:latest
echo [%DATE%_%TIME%] Successfully pushed to AWS ECR environment.