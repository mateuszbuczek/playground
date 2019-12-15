Kubernetes services.

Cluster IP

    Cluster IP is the default approach when creating a Kubernetes Service. The service is allocated an internal IP that other components can use to access the pods.

    By having a single IP address it enables the service to be load balanced across multiple Pods.

    It can be accessed inside host but not outside it. Get cluster ip from kubectl describe svc/<name>, then curl <IP>:<PORT>

Target Ports

    Target ports allows us to separate the port the service is available on from the port the application is listening on. TargetPort is the Port which the application is configured to listen on. Port is how the application will be accessed from the outside.

NodePort

    While TargetPort and ClusterIP make it available to inside the cluster, the NodePort exposes the service on each Node’s IP via the defined static port. No matter which Node within the cluster is accessed, the service will be reachable based on the port number defined. it can be accessed now from node ip address

External IPs

    making a service available outside of the cluster is via External IP addresses.

Load Balancer

    When running in the cloud, such as EC2 or Azure, it's possible to   configure and assign a Public IP address issued via the cloud     provider. This will be issued via a Load Balancer such as ELB. This     allows additional public IP addresses to be allocated to a  Kubernetes cluster without interacting directly with the cloud   provider.
