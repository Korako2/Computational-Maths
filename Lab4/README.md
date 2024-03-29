# Лабораторная работа 4. Интерполяция и аппроксимация
## Вариант
### Аппроксимация методом наименьших квадратов

Необходимо сгенерировать набор входных точек, на которых тестировать программу. Набор точек должен представлять собой некоторое количество пар
(x; y), полученных из какой-то существующей функции. Чтобы сгенерировать такой набор данных
рекомендуется использовать подготовленную функцию, поведение которой вам уже известно.
Предпочтительно иметь несколько наборов входных данных из нескольких функций. Также
желательно добавить шум (отклонения значений y от математического). Имеет смысл также
отобразить на графике функцию, на основе которой был получен набор данных.

Необходимо строить графики функций. На графике необходимо отобразить исходные точки и точки, полученные при
помощи разработанного численного метода, согласно варианту.

Необходимо применить метод аппроксимации дважды: второй раз
метод должен быть запущен на укороченном списке входных точек, при чём исключена должна
быть точка, имеющая наибольший квадрат отклонения после первого запуска. Иначе говоря,
алгоритм, следующий: 

1. Задается произвольный (полученный ранее) набор значений пар (x; y).
2. Задается аппроксимирующая функция.
3. Рассчитываются программой коэффициенты аппроксимирующей функции.
4. Производится поиск точки с наибольшим отклонением от значения, полученного при помощи
аппроксимирующего многочлена.
5. Найденная точка исключается и производится пересчёт коэффициентов аппроксимирующего
многочлена.
6. Строится график, содержащий в себе две функции (1 - до исключения, 2 - после исключения
и пересчёта) и набор заданных изначально точек (пар значений (x, y)).
7. Помимо этого, отдельно на экран выводятся полученные значения аппроксимирующих
коэффициентов.

В качестве аппроксимирующей функции следует выбрать несколько наиболее популярных
аппроксимирующих функций: линейная, квадратичная, логарифмическая, тригонометрическая и
т. п.
