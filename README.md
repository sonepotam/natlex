**Тестовое задание для Natlex**

Выведенные наружу rest-сервисы документированы с помощью swagger
они доступны по адресу http://localhost:8082/swagger-ui.html

**Настройки минимальные**
В файле application.yml указан порт 8082 на котором поднято приложение
Настройка app.delay сделана для моделирования задержки при обработке файлов

Для доступа к rest-сервисам необходимо авторизоваться

имя пользователя    **user**

пароль           **password**

Правила доступа приведены ниже:

                .antMatchers( "/rest/**").permitAll()
                .antMatchers( "/import/**").authenticated()
                .antMatchers( "/export/**").authenticated()

Для хранения данных используется PostgreSQL
Для работы программы нужно создать базу данных 
**CREATE DATABASE natlex WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;**

Учетные данны для коннекта к базе указаны в application.yml в секции datasource
Таблицы в базе пересоздаются всегда

    url: jdbc:postgresql://localhost:5432/natlex
    username: postgres
    password: 12345

В docker просили не упаковывать, сл-но отдаю как есть