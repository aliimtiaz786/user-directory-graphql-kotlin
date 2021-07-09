# user-directory-graphql-kotlin

## Introduction

This API is a graphql service which one query and one mutation. Query returns list of users while
mutations return authentication token by passing correct username and password combination.

API is built by using expedia-graphql-kotlin library which provides schema generation by scanning
code and also provides endpoints /graphql, /playground and /sdl for querying and schema response.

## Schema

```

type Mutation {
  getUserAuthToken(password: String!, userName: String!): String!
}

type Query {
  users: [User!]!
}

type User {
  email: String!
  firstName: String!
  lastName: String!
  password: String!
  userName: String!
}

```

## Steps to run application via intelliJ

1. Run class `UsersDirectoryKotlinApplication`
2. You can do query/mutation at `localhost:8080/playground`
3. You can also use post method by following curl request

```swagger codegen
curl --location --request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/graphql' \
--header 'Accept: application/json' \
--data-raw 'query { users { lastName firstName } }'
```


## Steps to run unit test
1. Make sure you have java 16 and maven installed.
2. Run ``mvn test``
