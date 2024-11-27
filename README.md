## Тестовое задание Nexign
В рамках тестового задания предстоит спроектировать и реализовать сервис распределенного выполнения задач. 
Задача содержит как минимум название и длительность исполнения воркером (в воркере может быть просто Thread.sleep(duration))".

Система обеспечивает:
1. Регистрацию задачи в системе (чтение сообщений из топика Kafka и сохранение задачи в БД);
2. Просмотр результатов выполнения задачи по идентификатору REST API;
3. Асинхронное выполнение пулом воркеров (кол-во воркеров настраивается в конфигурации сервиса);
4. Сохранение промежуточных и финальных результатов выполнения задачи в БД (статус задачи может опрашиваться по REST API - п.2)
5. Горизонтальное масштабирование (несколько экземпляров сервиса могут работать с одной БД)

Стек: Spring Boot, PostgreSQL, REST API, Kafka, Spring Data JPA, Docker
Доп.задания:
1. Использовать DTO для входных и выходных данных
2. Валидация входных данных
3. Swagger для документирования API
4. Реализовать обработку ошибок и возвращать соответствующие HTTP статусы сообщений об ошибках
5. Написать docker-compose для разворота БД, кафки и запуска сервиса