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