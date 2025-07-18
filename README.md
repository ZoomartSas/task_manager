Task Manager REST API
Описание проекта
Простой REST API сервис управления задачами, реализованный на Spring Boot с использованием встроенной базы данных H2. Проект покрывает основные CRUD операции для задач и содержит дополнительные улучшения для безопасности, производительности и тестирования.

Выполненные пункты тестового задания
№	Задание	Статус	Комментарии
1	Создание задачи	✅ Выполнено	Эндпоинт POST /api/tasks
2	Получение списка всех задач	✅ Выполнено	Эндпоинт GET /api/tasks/all
3	Обновление задачи	✅ Выполнено	Эндпоинт PUT /api/tasks/{id}
4	Удаление задачи	✅ Выполнено	Эндпоинт DELETE /api/tasks/{id}
5	Получение задачи по ID	✅ Выполнено	Эндпоинт GET /api/tasks/{id}
6	Подключение реляционной базы H2	✅ Выполнено	H2 встроенная БД, доступ через консоль
7	Логирование запросов и ответов	✅ Выполнено	Реализован фильтр логирования
8	HTTP GET запрос к https://api.restful-api.dev/objects + логирование	✅ Выполнено	Использован RestTemplate, результат в логах
9	Unit-тесты для CRUD методов	✅ Выполнено	JUnit + Mockito для сервисного слоя
10	Отправка email при создании задачи	✅ Выполнено	Spring Mail, конфигурация SMTP
11	Basic Authentication для API	✅ Выполнено	Spring Security с Basic Auth
12	Кэширование "Получение списка всех задач" с Redis	✅ Выполнено	Использован Spring Cache + Redis
13	Dockerfile для проекта	✅ Выполнено	Есть готовый Dockerfile

Технологии
Java 17

Spring Boot

Spring Data JPA

H2 Database

Spring Security (Basic Auth)

Spring Mail

Spring Cache + Redis

JUnit 5, Mockito

Docker

Запуск проекта
Локально
Клонировать репозиторий

Запустить приложение:

arduino
Копировать
Редактировать
./mvnw spring-boot:run
Консоль H2:

bash
Копировать
Редактировать
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:tasksdb

User: sa

Password: (пусто)

Через Docker
bash
Копировать
Редактировать
docker build -t task-manager .
docker run -p 8080:8080 task-manager
Примеры запросов API (Postman/cURL)
Создание задачи (POST /api/tasks)
json
Копировать
Редактировать
{
  "title": "Сделать домашнее задание",
  "description": "Закончить курсовую работу",
  "status": "TODO",
  "completed": false,
  "assignee": "Иван Иванов",
  "dueDate": "2025-07-23",
  "priority": "MEDIUM"
}
Получить все задачи (GET /api/tasks/all)
Заголовок авторизации: Basic Auth (username, password)

Получение задачи по ID (GET /api/tasks/{id})
Обновление задачи (PUT /api/tasks/{id})
В теле JSON с новыми данными задачи

Удаление задачи (DELETE /api/tasks/{id})
