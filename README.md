# Курсовой проект "Справочник контрагентов"
Справочник контрагентов содержит следующие поля + (уникальный идентификатор в качестве первичного ключа):
1. Наименование
2. ИНН
3. КПП
4. Номер счёта
5. БИК банка

## Технологии, использованные в курсовом проекте:
- Spring (Spring Boot, Spring Data)
- Lombok
- СУБД PostgeSQL
- swagger doc
- Gradle
- Junit
- Orika
- Sjf4l
- Jasypt

## Работа со справочником реализована с помощью следующих сервисов:
### CRUD-сервис
Сервис, реализующий Crud операции над БД PostgreSQL. Позволяет добавлять, удалять, редактировать записи базы данных.

### Поисковой сервис
Сервис, позволяющий находить записи в БД PostgeSQL с определёнными параметрами. Возможен поиск по наименованию контрагента и по паре БИК + номер счёта. Также позволяет достать все записи из БД.

## Обработка запросов со стороны клиента реализована с помощью следующих контроллеров:
### CounteragentsController
Контроллер для обработки запросов, связанных с Crud операциями над таблицей контрагентов.

### CounteragentsSearchController
Контроллер для обработки запросов, связанных с поисковыми операциями над таблицей контрагентов.

### ExceptionController
Контроллер для обработки исключительных ситуаций, например, переход по несуществующей ссылке. Обрабатывает запросы, которые не обрабатывают CounteragentsController и CounteragentsSearchController

## Настройка базы данных
Приложение работает с СУБД PostgreSQL. Для настройки базы данных нужно в файле свойств приложения, находящегося по следующему пути:
> /src/main/resources/application.properties 

Задать следующие параметры БД, согласно своим данным сервера PostgreSQL:
> spring.datasource.username
> 
> spring.datasource.password
>
> spring.datasource.url

## Запуск приложения
Команды для запуска приложения:
> /gradlew bootrun (Windows)
>
>./gradlew bootrun (Mac OS)

Команда для запуска тестов:
> /gradlew test (Windows)
>
>./gradlew test (Mac OS)

## Swagger Doc
После запуска прилодения документация доступна по следующей ссылке ссылке: 
>http://localhost:8080/swagger-ui/
