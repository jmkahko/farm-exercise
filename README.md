# Solita Dev Academy 2022 harjoitustehtävä
Tehtävän [Github-linkki](https://github.com/solita/dev-academy-2022-exercise)


## Edellytykset
Projekti vaatii, että Java ja PostgreSQL toimivat koneella. PostgreSQL:ään voidaan käyttää Dockeri [filen kautta](https://github.com/jmkahko/farm-exercise/blob/main/Docker/docker-compose.yml) 


## Projektin luonti
1. Clonaa GitHubista projekti itsellesi
```
git clone git@github.com:jmkahko/farm-exercise.git
```

2. Luo tietokanta esimerksi [pgAdmin 4](http://localhost:5050/login?next=%2F)-työkalulla, jos käytetään Dockerin kautta PostgreSQL tietokantaa
```
CREATE DATABASE farmExercise;
```

3. Asetetaan sekvenssit tietokantaan [Lisää aiheesta](https://ntsim.uk/posts/how-to-use-hibernate-identifier-sequence-generators-properly).
```
CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
```

## Projektin ajaminen
Projekti voidaan käynnistää tämän jälkeen normaalisti Debugging tilassa. 

## Testit
Testien tekoon hyödynnettyjä materiaaleja:
- [JUnit 5 tutorial - Learn how to write unit tests](https://www.vogella.com/tutorials/JUnit/article.html)
- [Exploring the Spring Boot TestRestTemplate](https://www.baeldung.com/spring-boot-testresttemplate)

Testien ajaminen onnistuu esimerkiksi [Visual Studio Codessa](https://code.visualstudio.com/) painamalla play nappia **FarmExerciseApplicationTests** kohdassa

![TestienKuva](https://user-images.githubusercontent.com/18402605/149666556-925ffa4b-5ce9-4835-a1d0-5a91706f8877.png)


## Teknologiat
Tehtävän pohja on luotu [Spring työkalulla](https://start.spring.io/). 

Tehtävässä käytettyjä teknologioita Java, Thymeleaf ja PostgreSQL.

## Tehtävä lista
- [ ] Käyttäjien hallinta
- [ ] Jos tietokanta on tyhjä, ohjataan suoraan farmin luontiin / mittaustietojen tallennukseen
- [ ] Käyttäjien hallinnan lisäyksen jälkeen, farmien hallinta käyttäjille. Käyttäjä joka kuuluu tiettyyn farmiin, hän pystyy tallentamaan ainoastaan farmintietoja.
