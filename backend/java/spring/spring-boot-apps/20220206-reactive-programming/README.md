### Publisher or Producer
- will publish an event
- subscribe()

### Subscriber  Consumer
- subscribe event from publisher
- onSubscribe()
- onNext()
- onError()
- onComplete()

### Subscription
- unique relation between subscriber and publisher
- request()
- cancel()

### Processor
- represents a processing stage, which is both a Subscriber and a Publisher


### On reactive data flow 
- can stop execution in the middle of request

## Reactive programming library
- reactor - (supported by spring boot)
- rxjava
- jdk9 flow reactive stream

