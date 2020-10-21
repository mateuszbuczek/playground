# create network
docker network create rabbits

# create rabbitmq inside network
docker run -d --rm --net rabbits --hostname rabbit-1 --name rabbit-1 rabbitmq:3.8

# control 
docker exec -it rabbit-1 bash
rabbitmqctl

# expose management tool
docker run -d --rm --net rabbits --hostname rabbit-1 --name rabbit-1 -p 8080:15672 rabbitmq:3.8
docker exec -it rabbit-1 bash
rabbitmq-plugins enable rabbitmq_management

# publisher
docker build . -t rmq/publisher
docker run -it --rm --net rabbits -e RABBIT_HOST=rabbit-1 -e RABBIT_PORT=5672 -e RABBIT_USERNAME=guest -e RABBIT_PASSWORD=guest -p 80:80 rmq/publisher

#consumer 
docker build . -t rmq/consumer
docker run -it --rm --net rabbits -e RABBIT_HOST=rabbit-1 -e RABBIT_PORT=5672 -e RABBIT_USERNAME=guest -e RABBIT_PASSWORD=guest rmq/consumer