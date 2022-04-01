list:
	$(eval kafka_docker_id=$(shell docker ps | grep kafka | head -n 1 | awk '{print $$1}'))
	 docker exec $(kafka_docker_id) /bin/kafka-topics --list --bootstrap-server kafka:9092

describe:
	$(eval kafka_docker_id=$(shell docker ps | grep kafka | head -n 1 | awk '{print $$1}'))
	 docker exec $(kafka_docker_id) /bin/kafka-topics --describe --bootstrap-server kafka:9092

subscribe:
	$(eval kafka_docker_id=$(shell docker ps | grep kafka | head -n 1 | awk '{print $$1}'))
	docker exec $(kafka_docker_id) kafka-console-consumer \
                                --topic $(TOPIC) \
                                --property print.key=true \
                                --property print.value=true \
                                --bootstrap-server kafka:9092 \
                                --from-beginning

produce-customer:
	$(eval kafka_docker_id=$(shell docker ps | grep kafka | head -n 1 | awk '{print $$1}'))
	docker exec $(kafka_docker_id) bash -c "echo \
		 '1-{ \"id\": 12345678,\
          \"firstName\": \"test-firstName\",\
          \"lastName\": \"test-lastName\",\
          \"email\": \"test-email\",\
          \"address\": \"test-address\",\
          \"level\": \"BRONZE\" }' \
	 | kafka-console-producer --bootstrap-server kafka:9092 --topic customers \
	 --property 'key.separator=-' --property 'parse.key=true' "

produce-payment:
	$(eval kafka_docker_id=$(shell docker ps | grep kafka | head -n 1 | awk '{print $$1}'))
	docker exec $(kafka_docker_id) bash -c "echo \
		 'paymentod-1:{ \"id\": \"paymentid-1\",\
          \"orderId\": \"orderid-1\",\
          \"ccy\": \"test-ccy\",\
          \"amount\": \"12345\" }' \
	 | kafka-console-producer --bootstrap-server kafka:9092 --topic payments \
	 --property 'key.separator=:' --property 'parse.key=true'"
