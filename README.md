# Привіт!

Для сереалізації JSON об'єктів використана бібліотека Jackson.
Кількість потоків я рахував з main потоком в якому запускається парсер тому [тут](https://github.com/vladRak/scrapingExample/blob/master/src/main/java/scraping/App.java) в Executors.newFixedThreadPool задано значення "2".
