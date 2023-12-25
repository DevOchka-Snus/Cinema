# КДЗ-1

Данное консольное приложение на Kotlin может имеет 2 режима работы:
* Режим для обычного пользователя
* Режим для сотрудника кинотеатра

В режиме пользователя можно купить билет, вернуть билет и вывести все билеты, которые купил данный пользователь

В режиме сотрудника кинотеатра можно добавлять новые фильмы и киносессии в БД, редактировать их, а также проверять актуальность билета по его номеру.


### ВАЖНО:
- редактировать можно только те фильмы, для которых еще нет киносеансов
- редактировать можно только те киносеансы, на которые еще не продали ни одного билета и которые еще не начались
- вернуть можно билеты только на те киносеансы, которые еще не начались

Сами сеансы не являются регулярными (то есть проходят в определенный промежуток времени, указанный работником)
Формат ввода данных для начала/конца сеанса: yyyy MM dd HH:mm
Пример: 2023 12 24 12:48 - 12 часов 48 минут 24 декабря 2023 года

После выбора режима работы в начале программы пользователь должен авторизироваться (авторизация пользователя идет через введеные логин и пароль). Авторизация является обязательным элементом для обоих режимов работы. В случае, если пользователь не зарегистрирован, у него будет возможность это сделать. Хочется отметить, что после завершения регистрации пользователь так же придется совершить авторизацию

Все данные хранятся в соответствующих JSON-файлах. Сам формат JSON обладет некоторыми преимуществами над другими форматами хранения данных (XML, CSV), а именно:
- читаемость и простота
- объектная семантика
- легковесность
- поддержка массивов
- простота в разборе и генерации

Все диаграммы находятся в папке docs
