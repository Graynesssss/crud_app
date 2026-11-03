# CRUD Application (Spring Boot + PostgreSQL + Liquibase)

Инструкция по быстрому запуску проекта на любом компьютере с помощью Docker.

---

## 📌 Предварительные требования

Для запуска приложения вам понадобится только установленный **Docker Desktop** (включает Docker Compose):
* Скачать: https://www.docker.com/products/docker-desktop/

> ⚠️ Устанавливать Java, Maven или PostgreSQL на ваш локальный компьютер **не требуется** — всё запустится внутри изолированных Docker-контейнеров.

---

## 🚀 Быстрый запуск

### Шаг 1. Клонируйте репозиторий
Откройте терминал (Command Prompt / PowerShell / Terminal) и выполните команды:

git clone https://github.com/Graynesssss/crud_app.git
cd crud_app

### Шаг 2. Запустите проект через Docker Compose
Находясь в папке проекта, выполните:

docker compose up --build

> **Что произойдет далее:**
> 1. Docker автоматически соберет JAR-файл приложения из исходного кода.
> 2. Загрузит и развернет контейнер с СУБД **PostgreSQL**.
> 3. Применит все миграции базы данных через **Liquibase**.
> 4. Запустит Spring Boot приложение на порту `8080`.

---

## 🌐 Проверка работы

После успешного запуска (в логах появится надпись `Started CrudapplicationApplication`):

* **REST API / Приложение:** http://localhost:8080
* **База данных PostgreSQL:** доступна по адресу `localhost:5432`
  * **Database:** `mydatabase`
  * **Username:** `myuser`
  * **Password:** `secret`

---

## 🛑 Остановка приложения

Чтобы остановить работу контейнеров, нажмите `Ctrl + C` в окне терминала, где запущен проект, или выполните команду:

docker compose down

Если нужно полностью сбросить данные в базе (удалить volumes):

docker compose down -v