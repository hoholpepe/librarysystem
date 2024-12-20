# Техническое задание
## Общие сведения
Программа создана для автоматизации и повышения эффективности работы библиотеки.

## Цели и назначение создания автоматизированной системы;
- **Цель работы** — разработать базу данных и настольное приложение для учета книг в библиотеке.
- **Назначение** — управление и автоматизация процессов библиотеки.

##  Характеристика объектов автоматизации;
### Основные функции:
- Разграничение прав доступа
- Добавление, редактирование, удаление книг и информации о них из каталога
- Учет количества книг в остатке при новой выдаче
- Просмотр всех взятых книг и сроков возврата по ним
- Учет количества взятых книг по отношению к срокам сдачи в виде диаграммы
- Разделение пользователей: Сотрудники библиотеки и читатели (гостевой просмотр картотеки)

## Требования к производительности
- Быстрое загрузка страниц
- Отсутствие долгих задержек при выполнении запросов

## Требования к безопасности
- Защита от несанкционированного доступа к системе
- Протокол HTTPS для безопасной передачи данных 
- Регулярное обновление системы безопасности

## Требования к тестированию
- Проведение нагрузочного тестирования системы
- Проверка работы системы на различных операционных системах

## Требования к автоматизированной системе;
1) Многопользовательский доступ
2) Разграничение функционала по ролям 
3) Сотрудники библиотеки могут осуществлять **следующие действия**:
- Получать оперативную информацию об остатках книг;
- Добавлять, удалять, редактировать книги и всю сопутствующую информацию в остаток;
- Просматривать инфографику по взятым книгам и читателям;
- Выдавать и возвращать книги с баланса и на него соответственно;
4) Читатель может осуществлять **следующие действия**:
- Просматривать текущее наличие книг в библиотеке;
- Пользоваться поиском для удобного отображения книг по названию, автору и жанрам;
- Пользоваться фильтрами для удобной навигации по картотеке;
- Просматривать всю доступную информацию о книгах.

#### Схема данных:
1) users (Сотрудники)
- Идентификатор пользователя (int, PK) 
- Логин пользователя (VARCHAR(100))
- Пароль пользователя (VARCHAR(50))
- ФИО пользоваетеля (VARCHAR(150))
- Роль пользователя в системе (INT, FK (roles.id_role))
- Электронная почта пользователя (VARCHAR(50))
- Телефон пользователя (VARCHAR(12))
2) roles (Роли)
- Идентификатор роли (int, PK)
- Название роли (VARCHAR(45))
3) books (Книги)
- Идентификатор книги (int, PK)
- Название книги (VARCHAR(45))
- Международный стандартный книжный номер (VARCHAR(13))
- Описание книги (VARCHAR(150))
- Идентификатор автора (INT	FK(autors.id_autor))
- Идентификатор издательства (INT, FK(publishings.id_publishing))
- Идентификатор возрастного ограничения (INT, FK(ages.id_age))
- Путь до фотографии книги (VARCHAR)
4) ages (Возрастные ограничения)
- Идентификатор возрастного ограничения (int, PK)
- Название возрастного ограничения (VARCHAR(3))
5) readers (Читатели)
- Идентификатор читателя (int, PK)
- ФИО читателя (VARCHAR(150))
- Номер телефона читателя (VARCHAR(11))
- Электронная почта читателя (VARCHAR(45))
- Адрес читателя (VARCHAR(100))
- Номер читательского билета (VARCHAR(15))
6) autors (Авторы)
- Идентификатор автора (int, PK)
- ФИО автора (VARCHAR(50))
- Описание автора (VARCHAR(200))
7) publishings (Издательства)
- Идентификатор издательства (int, PK)
- Название издательства (VARCHAR(50))
- Описание издательства (VARCHAR(200))
8) books_on_hands (Книги на руках)
- Идентификатор читателя (int, PK)
- Идентификатор книги (int, PK)
- Срок сдачи книги (DATE)

## Состав и содержание работ по созданию автоматизированной системы
1) Анализ требований
- Анализ методов получения информации о сотрудниках и читателях
- Изучение способов создания записей о выдаче/возвращении книг
2) Концептуальное проектирование
- Проектирование базы данных
- Проектирование интерфейса
3) Разработка системы
- Разработка базы данных
- Разработка приложения
4) Тестирование
- Тестирование отдельных компонентов приложения
- Тестирование полного процесса создания УЗ, добавления и редактирования книг/авторов/жанров/изображений/читателей, выдачи/возвращения, учёта
5) Документация
- Создание руководства для пользователей
- Создание тех документации

## Порядок разработки автоматизированной системы;
1) Анализ требований
2) Концептуальное проектирование
3) Разработка системы
4) Тестирование
5) Документация

## Сроки выполнения
- Дата выдачи задания: 08 ноября 2023 года
- Срок сдачи работы: 24 ноября 2023 года

## Заключение
Разработка базы данных и настольного приложения для библиотеки должна обеспечивать эффективное управление ею. Система должна быть безопасной, простой и удобной для сотрудников и читателей.