aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com
docker build -t urlshorten/urlshorten-service .
docker tag urlshorten/urlshorten-service:latest 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com/urlshorten/urlshorten-service:latest
docker push 751231970319.dkr.ecr.ap-southeast-1.amazonaws.com/urlshorten/urlshorten-service:latest
