### GET ALL TASKS
curl --location --request GET 'localhost:8080/v1/api/tasks'

### GET TASK BY ID
curl --location --request GET 'localhost:8080/v1/api/tasks/1'

### CREATE A NEW TASK
curl --location --request POST 'localhost:8080/v1/api/tasks/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "new 2nd task",
    "status": 1,
    "priority": 1,
    "description":"2244 blah blah task"
}'

### UPDATE A TASK
curl --location --request PUT 'localhost:8080/v1/api/tasks/2' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "blah blah new 2nd task",
    "status": 1,
    "priority": 1,
    "description":"2244 blah blah task"
}'

### DELETE A TASK
curl --location --request DELETE 'localhost:8080/v1/api/tasks/2'