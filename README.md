# Full Lobby Spring Boot Backend

Backend of gamer social media web application.

## Authentication

AuthenticationController controls the endpoints users hit from the Next App.

Endpoints

**POST** /api/auth/register

**POST** /api/auth/login

**POST** /api/auth/verify

## Images

Serves images from the ./img folder by passing the image filename as a pathvariable.

**GET** /api/images/{filename}

## Posts

**GET** /api/posts

**GET** /api/posts/recent

**POST** /api/posts

**GET** /api/posts/{userId}

**GET** /api/posts/paginated

**POST** /api/posts/like
