metadata.name - define deployment name

spec.template.metadata.labels.<name> - define label to connect to       other apps

spec.template.metadata.spec.containers.ports.containerPort - define     exposing port inside container 

service exposes container for usage to others (inside cluster or outside in host)


kubectl create -f deployment.yaml

kubectl describe deployment webapp1