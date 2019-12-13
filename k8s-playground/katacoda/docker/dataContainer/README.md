Responsibility of data container is to store/manage data

<!-- create data container with mapping to /config container -->
docker create -v /config --name dataContainer busybox

<!-- copy config file to data container -->
docker cp config.conf dataContainer:/config/

<!-- mount dataContainer volume inside ubuntu -->
docker run --volumes-from dataContainer ubuntu ls /config