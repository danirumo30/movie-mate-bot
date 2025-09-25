### 🔹 Flujo resumido del bot

1. **Usuario envía comando** → `BotListener` recibe el mensaje.
2. `BotListener` → identifica el comando (`!pelicula`, `!actor`, `!estrenos`) y lo **delegará a la clase Command correspondiente**.
3. **Command concreto** → llama al `Controller` correspondiente (por ejemplo, `MovieController`).
4. `Controller` → invoca al `Service`.
5. `Service` → llama al `DAO` para obtener datos de TMDb.
6. `DAO` → devuelve un `Model` (`MovieModel`, `ActorModel`).
7. `Service` → convierte el `Model` en un **embed** usando `Utils`.
8. `Controller` → devuelve el embed al **Command**.
9. `Command` → envía el embed al `BotListener`.
10. `BotListener` → envía el mensaje al canal de Discord.

