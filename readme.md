# TODO Mancenter
```
docker network create mynet
docker run -p 8080:8080 --network mynet --name mancenter hazelcast/management-center:3.12.5
docker run -p 5701:5701 --network mynet -e MANCENTER_URL="http://mancenter:8080/hazelcast-mancenter" hazelcast/hazelcast:3.12.5

```

After 10 seconds hazelcast will retry to connect to mancenter from the browser
http://localhost:8090/hazelcast-mancenter

```
curl -v -X PUT 'http://localhost:8080/put'
curl -v 'http://localhost:8080/get'
```