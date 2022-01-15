# Solita Dev Academy 2022 harjoitustehtävä
Tehtävän [Github-linkki](https://github.com/solita/dev-academy-2022-exercise)


## Edellytykset
Projekti vaatii, että Java ja PostgreSQL toimivat koneella. PostgreSQL:ään voidaan käyttää Dockerin kautta ##!!LAITA TÄHÄN TIEDOSTO LINKKI


## Projektin luonti
Clonaa GitHubista projekti itsellesi

´´´
git clone git@github.com:jmkahko/farm-exercise.git
´´´

Luo tietokanta esimerksi [pgAdmin 4](http://localhost:5050/login?next=%2F)-työkalulla, jos käytetään Dockerin kautta PostgreSQL tietokantaa
´´´
CREATE DATABASE farmExercise;
´´´

Asetetaan sekvenssit tietokantaan [Lisää aiheesta](https://ntsim.uk/posts/how-to-use-hibernate-identifier-sequence-generators-properly).
´´´
CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
´´´

## Projektin ajaminen
Projekti voidaan käynnistää tämän jälkeen normaalisti Debugging tilassa. 

## Testit

## Teknologiat
Tehtävän pohja on luotu Spring https://start.spring.io/ työkalulla. 
Tehtävässä käytettyjä teknologioita Java, Thymeleaf ja PostgreSQL.

## ToDo

