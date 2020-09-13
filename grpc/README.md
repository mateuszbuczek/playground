####gRPC
    - lightweight, simple, high performance open source rpc framework
    - based on http2
    - not its part of Cloud Native Computing Foundation - CNCF
    - rpc - remote procedure call - protocol that allows to execute program located in other computer
    - types:
        - unary: one request - one response
        - client streaming: many requests - one response
        - server streaming: one request - many responses
        - bidirectional streaming - many requests - many responses
        
#### protocol buffer
    - the services and payload messages are defined using protocol buffer which is then compiled to humand readable language
    - code generators for many languages
    - binary data representation
        - smaller size
        - faster to transport
        - more efficient to serialize/ deserialize
    - strongly typed contract
    - conventions for API evolution backward & forward

#### http/2 vs http/1.1
    - transfer protocol: binary | text
    - headers: compressed | plain text
    - multiplexing: yes | no
    - requests per connection: multiple | no
    - server push: yes | no

#### gRPC vs rest
    - protocol: http/2 | http/1.1
    - payload: protobuf (binary) | JSON (text)
    - API contract: strict, required (.proto) | loose, optional (OpenAPI)
    - code generation: build-in (protoc) | third party tools (swagger)
    - security: tls/ssl
    - streaming: bidirectional | client -> server
    - browser support: limited (required gRPC-web) | yes

##### api contract
    - communication channel: REST, SOAP, gRPC, message queue
    - auth mechanism: Basic, OAuth, JWT
    - payload format: JSON, XML, binary
    - data model
    - error handling

###### compile proto file
    .proto files can be compiled using protobuf compiler (sudo apt install protobuf-compiler & sudo apt install libprotobuf-dev & sudo apt install libprotoc-dev)
    protoc --proto_path=proto proto/*.proto --go_out=plugins=grpc:pb
