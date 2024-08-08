# Spring Security PoC

This repository shows you how to implement multiple ways of authentication to access your resources using `Spring Security`. In this project, `HttpBasic` and `Jwt` are used as a way to authenticate the user.

## Run the Code

Before anything else, you need to create a `.env` file with variables listed below.

```bash
# .env

POSTGRES_USER=<username>
POSTGRES_PASSWORD=<password>
POSTGRES_DB=<db_name>
```

I've used `docker` to simplify the development environment. Thus, `docker` must be installed in your system. Use the command below to run the application.

```bash
docker compose up
```

The command will also run the `lq (liquibase)` service which migrates the database changelogs in `src/main/resources/db/changelog/migrations` automatically. If you want to add models and changelogs manually while the application is running, you can use the commands below to migrate new sql changelogs and rollback changes.

```bash
# check the liquibase status
docker compose run --rm lq status

# migrate the new changelogs
docker compose run --rm lq update

# rollback `n` number of changelogs
docker compose run --rm lq rollback-count --count=n

# Note: lq is the name of the liquibase docker service in the docker-compose.yml
```

## How It Works

In this project, there are two simple authentication providers that authenticates the user based on the authentication type. These are:

1. [BasicAuthenticationProvider](https://github.com/lyndonn03/spring-sec-ap/blob/main/src/main/java/io/lpamintuan/spring_sec_ap/configs/authProviders/BasicAuthenticationProvider.java) for `HttpBasic`
2. [JwtAuthenticationProvider](https://github.com/lyndonn03/spring-sec-ap/blob/main/src/main/java/io/lpamintuan/spring_sec_ap/configs/authProviders/JwtAuthenticationProvider.java) for `JWT`

These two authentication providers are managed by the `ProviderManager` bean, which is an implementation of `AuthenticationManager`.

### BasicAuthenticationProvider

The BasicAuthenticationProvider is used if the user prefer to use `HttpBasic` authentication type. The user will provide his/her username and password, encode and bind it to the header with key "Authorization" and value "Basic \<the-encoded64-username:password>". For example:

```bash
#using curl

curl --location 'localhost:8080/' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ='

# where dXNlcm5hbWU6cGFzc3dvcmQ= is the encoded string of username:password in base64.
```

The provider will then validates the user credentials, stores the user details in `SecurityContext`, if successful or throw an `AuthenticationException` that will lead to `401 Unauthorized` Http error, if the user credentials are invalid.

### JwtAuthenticationProvider

The JwtAuthenticationProvider works if the request has a header "Authorization: Bearer \<jwt-token>". The token can be retrieved by logging in using user credentials in route `localhost:port/account/login`. This route will generate a valid signed jwt which you can bind to the header of every request. Note that the application will generate new `SecretKey` every time you run the application. So the previous jwt will be invalid when you rerun the program. Example of a request:

```bash
#using curl

curl --location 'localhost:8080/' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c='

```

Similarly, the provider will then validates the signed token, then store the user details in `SecurityContext`, if it is verified and throws `AuthenticationException` when the token is not valid.

## License

[MIT](https://choosealicense.com/licenses/mit/)
