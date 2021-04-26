#### for windows to connect docker with kind k8s cluster
docker run -it --rm --net host -v ${HOME}/.kube/:/root/.kube/ -v ${PWD}:/app webhook sh

apk add --no-cache curl
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
mv ./kubectl /usr/local/bin/kubectl
   
