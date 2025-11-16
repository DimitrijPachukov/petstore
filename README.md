# Pet Store – Spring Boot Апликација

Тест задача користејќи го Spring Boot framework-от

## Карактеристики
- create-users - Креира 10 корисници со случајни параметри
- create-pets - Креира 20 миленичиња мачки и кучиња со случајни параметри
- list-users - Ги листа сите корисници кои ги има во in memory H2 базата
- list-pets - Ги листа сите миленичиња 
- buy - Корисниците со доволен буцет купуваат милениче, а миленичето добива совственик
- history-log - Време на извршување на buy командата бројот на успешни и бројот на не успешни купувања
- H2 ин-мемори база

## Технологии
- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Lombok
- JUnit 5

## За апликациата
- работи на порта 8080
- root на страната е /api/petshop
  POST	/create-users	
  POST	/create-pets	
  GET	/list-users	
  GET	/list-pets	
  POST	/buy	
  GET	/history-log	
-H2 датабазата може да се пристапи на /h2-console so url jdbc:h2:mem:petstore

