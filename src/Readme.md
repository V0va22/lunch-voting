#Lunch vote project

requires java 8, maven, postrgeSql

###add restaurant 
__POST__ http://example.com/restaurant/
body: new restaurant
return created status

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -d '{
    "name": "Stargorod"
}' 'http://localhost:8080/restaurant/'
```

###add menu
__POST__ http://example.com/menu/
body: new menu
return created status

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -d '{
    "restaurantId": 3,
    "date": "2015-11-12T20:02:40.106Z"
}' 'http://localhost:8080/menu/'
```

###update menu
__PUT__ http://example.com/menu/{id}

body:  new version of menu
param id: menu id
return not found status if menu does not exists otherwise no content status

```
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -d '{
    "id": 1,
    "restaurant": {
      "id": 3,
      "name": "Kumpel"
    },
    "dishes": [
      {
        "id": 1,
        "name": "plov",
        "price": 20.7
      }
    ],
    "date": 1447279200000
  }' 'http://localhost:8080/menu/1'
 ```

###add dish to existing menu
__PUT__ http://example.com/menu/{menuId}/addDish/
body:  dish to be added
param id: menu id
return not found status if menu does not exists otherwise no content status
     
```
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -d '{
    "name": "MacMenu",
    "price": 12
  }' 'http://localhost:8080/menu/2/addDish'
```

###vote for particular menu
__POST__ http://example.com/vote/{menuId}/
no body
param menuId: id of  chosen menu
return not found if menu does not exists, bed request status if too late to vote otherwise no content status

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Basic dXNlcjp1c2Vy" -d '' 'http://localhost:8080/vote/1'
```

###generate report for particular date
__GET__ http://example.com/vote/report/{date}/
param date: date of report in milliseconds
return report in json object format e.g.

```
     {
         "KFC": 55,
         "McDonalds": 2,
         ...
     }
```   
```
curl -X GET -H "Content-Type: application/json" -H "Authorization: Basic dXNlcjp1c2Vy" 'http://localhost:8080/vote/report/1447279200000'
```

###Retrieve all votes
__GET__ http://example.com/vote/

```
curl -X GET -H "Authorization: Basic YWRtaW46YWRtaW4=" 'http://localhost:8080/vote/'
```

###Retrieve all restaurants
__GET__ http://example.com/restaurant/

```
curl -X GET -H "Authorization: Basic YWRtaW46YWRtaW4=" 'http://localhost:8080/restaurant/'
```

###Retrieve all users
__GET__ http://example.com/user/

```
curl -X GET -H "Authorization: Basic YWRtaW46YWRtaW4=" 'http://localhost:8080/user/'
```

###Create user
__POST__ http://example.com/user/
body: user to be created
return bed request status if user already exists otherwise no content status

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW46YWRtaW4=" -d '{
    "login": "test",
    "password": "test",
    "role": [
        {
            "role":"USER"
        }
    ],
    "enabled": true
}' 'http://localhost:8080/user/'
```

###Retrieve all menus
__GET__ http://example.com/menu/

```
curl -X GET -H "Authorization: Basic YWRtaW46YWRtaW4=" 'http://localhost:8080/menu/'
```