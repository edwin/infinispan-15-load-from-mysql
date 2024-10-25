# Loading Caches Directly from Database

A sample for storing cache keys and values to a MySQL database.

## Cache Configuration
```json
{
  "employee": {
    "replicated-cache": {
      "mode": "SYNC",
      "remote-timeout": "17500",
      "statistics": true,
      "encoding": {
        "media-type": "application/x-protostream"
      },
      "locking": {
        "concurrency-level": "1000",
        "acquire-timeout": "15000",
        "striping": false
      },
      "persistence": {
        "table-jdbc-store": {
          "shared": true,
          "segmented": false,
          "dialect": "MYSQL",
          "table-name": "t_employee",
          "schema": {
            "message-name": "Employee",
            "package": "proto",
            "embedded-key": true
          },
          "connection-pool": {
            "username": "***",
            "password": "***",
            "driver": "com.mysql.cj.jdbc.Driver",
            "connection-url": "jdbc:mysql://localhost:3306/test_db"
          },
          "write-behind": {
            "modification-queue-size": "2048",
            "fail-silently": true
          }
        }
      },
      "indexing": {
        "enabled": true,
        "storage": "local-heap",
        "indexed-entities": [
          "proto.Employee"
        ]
      },
      "state-transfer": {
        "timeout": "60000"
      }
    }
  }
}
```

## Table Structure
```sql
create table test_db.t_employee
(
    gender    varchar(1)   null,
    id        bigint auto_increment
        primary key,
    firstname varchar(100) null,
    lastname  varchar(100) null
);
```

## How to Test
```
$  curl -kv http://localhost:8080/employee -X POST \ 
    -d '{"firstname":"Ryoko", "lastname":"Hirosue", "id":29,"gender":"M"}' \ 
    -H "Content-Type: application/json"
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> POST /employee HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 65
>
< HTTP/1.1 201
< Content-Length: 0
< Date: Fri, 25 Oct 2024 02:51:24 GMT
<
* Connection #0 to host localhost left intact


$ curl -kv http://localhost:8080/employee/29
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /employee/29 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
>
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 25 Oct 2024 06:41:33 GMT
<
* Connection #0 to host localhost left intact
{"gender":"F","id":29,"firstname":"Ryoko","lastname":"Hirosue"}        


$ curl -kv http://localhost:8080/employee
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /employee HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
>
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 25 Oct 2024 02:51:42 GMT
<
* Connection #0 to host localhost left intact
[{"gender":"M","id":1,"firstname":"random","lastname":"name"},{"gender":"M","id":6,"firstname":"gabriel","lastname":"batistuta"},{"gender":"M","id":3,"firstname":"nama","lastname":"satu"},{"gender":"F","id":29,"firstname":"Ryoko","lastname":"Hirosue"},{"gender":"F","id":2,"firstname":"some","lastname":"random"},{"gender":"F","id":11,"firstname":"miss bloody","lastname":"valentine"}] 

```