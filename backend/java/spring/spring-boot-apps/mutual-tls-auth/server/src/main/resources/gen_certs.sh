#!/usr/bin/env bash

# self signed root ca
openssl req -x509 -nodes -sha256 -days 3650 -newkey rsa:4096 -keyout rootCA.key -out rootCA.crt \
-subj "/C=PL/ST=Masovian/L=Warsaw/O=My Inc/OU=DevOps/CN=customca/emailAddress=customca@example.com"

#---------------------------------server

# server csr
openssl req -new -newkey rsa:4096 -keyout server.key -out server.csr -passout pass:password1 \
-subj "/C=PL/ST=FL/L=Ocala/O=Home/CN=server"

# sign server csr
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in server.csr -out server.crt -days 365 -CAcreateserial -extfile server.ext

# create .p12 file
openssl pkcs12 -export -out server.p12 -name "server" -inkey server.key -in server.crt \
-passout pass:password2 \
-passin pass:password1

# create jks file
keytool -importkeystore -srckeystore server.p12 -srcstoretype PKCS12 -destkeystore server-keystore.jks -deststoretype JKS \
-deststorepass password3 \
-srcstorepass password2

# create truststore and add root ca to it
keytool -import -trustcacerts -noprompt -alias ca -file rootCA.crt -keystore server-truststore.jks \
-storepass password4

#---------------------------------client

# create client csr
openssl req -new -newkey rsa:4096 -nodes -keyout client.key -out client.csr -passout pass:password1 \
-subj "/C=PL/ST=FL/L=Ocala/O=Home/CN=client"

# sign client csr
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in client.csr -out client.crt -days 365 -CAcreateserial

# create .p12
openssl pkcs12 -export -out client.p12 -name "client" -inkey client.key -in client.crt \
-passout pass:password2 \
-passin pass:password1

# create jks file
keytool -importkeystore -srckeystore client.p12 -srcstoretype PKCS12 -destkeystore client-keystore.jks -deststoretype JKS \
-deststorepass password3 \
-srcstorepass password2
