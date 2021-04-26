#### Dockerfile:

FROM golang:1.15-alpine as dev-env

WORKDIR /app

#### Mount to local filesystem
cd src
docker build . -t go-dev-env
docker run -it --rm -p 80:80 -v ${PWD}:/app go-dev-env sh
