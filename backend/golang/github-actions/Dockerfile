FROM golang:alpine as builder

RUN mkdir /build
ADD . /build/
WORKDIR /build
RUN go build -o main main.go

FROM alpine
COPY --from=builder /build/main /app/
WORKDIR /app
CMD ["/app/main"]
