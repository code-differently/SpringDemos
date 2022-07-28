# WaterTrackerApi

Sample `application.yml`

```
logging:
  level:
    root: info
    com.codedifferently.watertrackerapi: debug
spring:
  datasource:
    driverClassName: <driver for database>
    url: <url to connect to database>
    username: <user name for mysql>
    password: <password for mysql>
  jpa:
    show-sql: false
    properties:
      hibernate:
        jdbc: time_zone = EST
security:
  firebase-props:
    database-url: ${FIREBASE_DATABASE}
    enable-strict-server-session: false
    enable-check-session-revoked: false
    enable-logout-everywhere: false
    session-expiry-in-days: 5
  cookie-props:
    max-age-in-minutes: 7200
    http-only: true
    secure: true
    domain: ${DOMAIN}
    path: /
  allow-credentials: true
  allowed-origins:
    - https://${DOMAIN}
    - http://localhost:3000
  allowed-methods:
    - GET
    - POST
    - PUT
    - PATCH
    - DELETE
    - OPTIONS
  allowed-headers:
    - Authorization
    - Origin
    - Content-Type
    - Accept
    - Accept-Encoding
    - Accept-Language
    - Access-Control-Allow-Origin
    - Access-Control-Allow-Headers
    - Access-Control-Request-Method
    - X-Requested-With
    - X-Auth-Token
    - X-Xsrf-Token
    - Cache-Control
    - Id-Token
  allowed-public-apis:
    - /favicon.ico
    - /session/login
    - /info/**
    - /**/userprofile/create
  exposed-headers:
    - X-Xsrf-Token
```