<!-- -t lets u specify name and tag (version v1) -->
- docker build -t webserver-image:v1 .
- docker run -d -p 80:80 webserver-image:v1
<!-- curl docker will make http request for localhost:80 -->
- curl docker