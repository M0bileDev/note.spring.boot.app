# 📝 Note Spring Boot App

A RESTful note-taking backend built with **Spring Boot** and **Kotlin**, backed by **MongoDB**.  
Supports full CRUD operations on notes, user authentication, and note search/filtering.

---

## 📋 Table of Contents

- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [API Reference](#-api-reference)
- [License](#-license)

---

## 🛠️ Tech Stack

| Technology      | Description                  |
|-----------------|------------------------------|
| Kotlin          | Primary language             |
| Spring Boot     | Application framework        |
| Spring Security | Authentication & authorization |
| MongoDB         | NoSQL database               |
| Gradle (Kotlin) | Build system                 |

---

## 🚀 Getting Started

### Prerequisites

- JDK 17+
- MongoDB running locally or a [MongoDB Atlas](https://www.mongodb.com/atlas) connection string
- Gradle (or use the included `./gradlew` wrapper)

### Clone the Repository

```bash
git clone https://github.com/M0bileDev/note.spring.boot.app.git
cd note.spring.boot.app
```

### Configure the Application

Update `src/main/resources/application.properties` (or `application.yml`) with your MongoDB connection details:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/notes_db
spring.data.mongodb.database=notes_db
```

### Run the Application

```bash
./gradlew bootRun
```

The app will start on `http://localhost:8080` by default.

### Build

```bash
./gradlew build
```

### Run Tests

```bash
./gradlew test
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/m0biledev/note/
│   │       ├── controller/      # REST controllers
│   │       ├── service/         # Business logic
│   │       ├── repository/      # MongoDB repositories
│   │       ├── model/           # Data models / documents
│   │       ├── dto/             # Request & response DTOs
│   │       └── security/        # Authentication & security config
│   └── resources/
│       └── application.properties  # App configuration
└── test/
    └── kotlin/                  # Unit and integration tests
```

---

## 📡 API Reference

All endpoints (except auth) require a valid **Bearer token** in the `Authorization` header:

```
Authorization: Bearer <your_token>
```

---

### 🔐 Authentication

#### Register

```http
POST /api/auth/register
```

| Body Field  | Type     | Description         |
|-------------|----------|---------------------|
| `username`  | `String` | Unique username     |
| `password`  | `String` | User password       |

**Response** `201 Created`

```json
{
  "message": "User registered successfully"
}
```

---

#### Login

```http
POST /api/auth/login
```

| Body Field  | Type     | Description     |
|-------------|----------|-----------------|
| `username`  | `String` | Your username   |
| `password`  | `String` | Your password   |

**Response** `200 OK`

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 📝 Notes

#### Get All Notes

```http
GET /api/notes
```

**Response** `200 OK`

```json
[
  {
    "id": "64f1a2b3c4d5e6f7a8b9c0d1",
    "title": "My Note",
    "content": "Note content here",
    "createdAt": "2024-06-15T10:30:00Z",
    "updatedAt": "2024-06-15T10:30:00Z"
  }
]
```

---

#### Get Note by ID

```http
GET /api/notes/{id}
```

| Path Param | Type     | Description  |
|------------|----------|--------------|
| `id`       | `String` | MongoDB note ID |

**Response** `200 OK` — returns the note object.

---

#### Create Note

```http
POST /api/notes
```

| Body Field | Type     | Description       |
|------------|----------|-------------------|
| `title`    | `String` | Title of the note |
| `content`  | `String` | Note body content |

**Response** `201 Created` — returns the created note object.

---

#### Update Note

```http
PUT /api/notes/{id}
```

| Path Param | Type     | Description     |
|------------|----------|-----------------|
| `id`       | `String` | MongoDB note ID |

| Body Field | Type     | Description            |
|------------|----------|------------------------|
| `title`    | `String` | Updated title          |
| `content`  | `String` | Updated body content   |

**Response** `200 OK` — returns the updated note object.

---

#### Delete Note

```http
DELETE /api/notes/{id}
```

| Path Param | Type     | Description     |
|------------|----------|-----------------|
| `id`       | `String` | MongoDB note ID |

**Response** `204 No Content`

---

#### Search / Filter Notes

```http
GET /api/notes/search?q={keyword}
```

| Query Param | Type     | Description              |
|-------------|----------|--------------------------|
| `q`         | `String` | Keyword to search for    |

**Response** `200 OK` — returns a list of matching note objects.

---

## 📄 License

This project is licensed under the [Apache 2.0 License](LICENSE).
