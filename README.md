# JetBrains Internship - Поиск пользователей GitHub
Тестовое задание для [стажировки](http://jetbrains.ru/students/internship/) в JetBrains. Ссылка на [Stepic](https://stepik.org/lesson/Android-Разработка-приложения-для-создателей-учебных-материалов-45136)
# Комментарии
Я долго думал какую архитектуру выбрать и что от меня ждут. В итоге, сделал именно так, как я сделал бы это в продакшане, основываясь на том, что это маленькое приложение.
В приложении отсутсвует кеширование ответов с сервера, ибо не нужно. Так же хоть основная логика и вынесена в отдельные классы, все данные уничтожаются при уничтожении приложения (кроме истории запросов, конечно).
Думаю, в приложении для Степика потребуется сделать несколько иную архитектуру, основанную на сервисах, так как там мало тех запросов, которые мы дестроим. => это все дело должно выполнятся в Service.
Хотелось бы использовать Moxy, но как-то забыл про него.
# Библиотеки
- [Floating Search View](https://github.com/arimorty/floatingsearchview): Красивое окошко поиска.
- [View Model Binding](https://github.com/jakubkinst/Android-ViewModelBinding): Указываем куда что лепить в XML, а не в Fragment'е кучкой findViewById(). Удобно.
- [Retrofit 2](https://github.com/square/retrofit): HTTP-запросы. Классика :)
- [Picasso](https://github.com/square/picasso): Загрузка изображений по URL. В прошлом моем проекте плохо себя зарекомендовал. Тут вроде нормально. Был выбор между ним и Fresco. Решил для маленького проекта использовать Picasso, как очень простая библиотека с удобным интерфейсом.
- [Realm](https://news.realm.io/news/realm-for-android/): База данных. Боженька смилоствился над бедными Android-разработчиками и дал им Realm. Аминь. Возможно, после релиза [ObjectBox](https://github.com/greenrobot/ObjectBox) стоит перейти на него. Пока Realm.

