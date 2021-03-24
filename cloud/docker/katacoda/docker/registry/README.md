docker run -d -p 5000:5000 \
    -v /root/certs:/certs \
    -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/registry.test.training.katacoda.com.crt \
    -e REGISTRY_HTTP_TLS_KEY=/certs/registry.test.training.katacoda.com.key \
    -v /opt/registry/data:/var/lib/registry \
    --name registry registry:2

curl -i https://registry.test.training.katacoda.com:5000/v2/

 To push/pull images from non-default Registries we need to include the URL in the image name. Generally, an image follows a <name>:<tag> format as Docker defaults to the public registry. The full format is <registry-url>:<name>:<tag>.

 docker pull redis:alpine; docker tag redis:alpine registry.test.training.katacoda.com:5000/redis:alpine

 docker push registry.test.training.katacoda.com:5000/redis:alpine