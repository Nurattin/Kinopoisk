# Кинопоиск

Пример тестового проекта кинопоиск

## Функции

- На главном экране отображается список популярных фильмов.
<p align="center">
  <img src="screenshots/list_films_light.jpg" width="45%" alt="Светлая тема" /> &nbsp; &nbsp;
  <img src="screenshots/list_films_dark.jpg" width="45%" alt="Темная тема" />
</p>
<p align="center">
  <em>Светлая тема</em> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <em>Темная тема</em>
</p>

- При клике на карточку открывается экран с постером фильма, описанием, жанром, страной
производства.
<p align="center">
  <img src="screenshots/film_detail_light.jpg" width="45%" alt="Светлая тема" /> &nbsp; &nbsp;
  <img src="screenshots/film_detail_dark.jpg" width="45%" alt="Темная тема" />
</p>
<p align="center">
  <em>Светлая тема</em> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <em>Светлая тема</em>
</p>

- При перевороте экрана список фильмов будет занимать только 50% экрана, во второй половине
будет отображаться экран с описанием фильма.
![Camera Light](screenshots/film_detail_landscape_light.jpg)  |  ![Camera Dark](screenshots/film_detail_landscape_dark.jpg)

- На главном экране присутствуют разделы «Популярное» и «Избранное». При длительном клике на
карточке, фильм помещается в избранное и хранится в базе данных. Фильмы из избранного
доступны в оффлайн режиме.

- При просмотре популярных выделяются фильмы, находящиеся в избранном.

- В разделах доступен поиск фильмов по наименованию.
<p align="center">
  <img src="screenshots/search_by_key_word_dark.jpg" width="50%" alt="Темная тема" />
</p>

- Лого
<p align="center">
  <img src="screenshots/logo_light.jpg" width="45%" alt="Светлая тема" /> &nbsp; &nbsp;
  <img src="screenshots/logo_dark.jpg" width="45%" alt="Темная тема" />
</p>
<p align="center">
  <em>Светлая тема</em> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <em>Темная тема</em>
</p>


## Технологический стек

- Kotlin
- Jetpack Compose
- Hilt (Dependency Injection)
- Flow (Asynchronous programming)
- Coroutine (Concurrent programming)
- Material Design
- Room (Local database)
- Retrofit (Network requests)
---
