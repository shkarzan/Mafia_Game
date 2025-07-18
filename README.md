# 🕵️ Mafia Game - Multiplayer Role-Based Web Game

A real-time multiplayer Mafia Game where players are assigned secret roles (Mafia, Doctor, Police, or Citizen) and must use deduction, discussion, and strategy to win. Built with **React** (frontend) and **Spring Boot** (backend), using **WebSockets** for real-time updates.

---

## 🎮 Features

- 🔒 Host-controlled role assignment
- 👥 Live player lobby with real-time status
- 💬 Public chat for all, private Mafia chat for mafia members
- ⚡ Real-time actions (Kill, Investigate, Save) using WebSocket
- 📋 Game phases: Night (actions) & Day (discussions and voting)
- 🧠 Win condition logic: Mafia eliminated or Mafia takes over

---

## 🧪 Roles

| Role    | Description                                                                 |
|---------|-----------------------------------------------------------------------------|
| Mafia   | Secretly choose a player to kill at night                                   |
| Doctor  | Secretly save a player from being killed                                    |
| Police  | Secretly investigate if a player is Mafia                                   |
| Citizen | No special powers — rely on communication and deduction                     |
| Host    | Manages the lobby, starts the game, and assigns roles                       |

---

## 🛠 Tech Stack

### Frontend: React + WebSocket
- React + Tailwind (or Native CSS)
- WebSocket client for real-time updates
- Clean UI with role-based view control

### Backend: Spring Boot
- REST API for user & room management
- WebSocket for real-time messaging
- Game state management & role logic
- In-memory or DB-based session management

---

## ⚙️ Setup Instructions

### Backend (Spring Boot)

```bash
# Clone the repo
git clone https://github.com/yourusername/mafia-game-backend.git
cd mafia-game-backend

# Build and run
./mvnw spring-boot:run
```
Also add src/main/resources and inside resources add application.properties with this

spring.application.name=MafiaGame

server.address=0.0.0.0

server.port = 8080

#Local DB

## MySQL Database connection settings

spring.datasource.url=jdbc:mysql://localhost:3306/mafiadb

spring.datasource.username=your_db_username

spring.datasource.password=your_db_password

# JPA / Hibernate settings

spring.jpa.hibernate.ddl-auto=update

#spring.jpa.show-sql=true

# Optional: Connection pool settings

spring.datasource.hikari.maximum-pool-size=10

#spring.web.resources.static-locations=classpath:/static/

#spring.mvc.pathmatch.matching-strategy=ant_path_matcher

#spring.thymeleaf.check-template-location=false


#server.compression.enabled=true

#server.compression.mime-types=application/json,application/xml,text/html,text/xml,text/plain,text/css,text/javascript,application/javascript

#server.compression.min-response-size=1024


