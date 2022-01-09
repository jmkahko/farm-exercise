package com.farmexercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class FarmExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmExerciseApplication.class, args);

	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	// Kun sovellus käynnistyy luodaan tietokannat aluksi
	@Autowired
	public void run() throws Exception {

		System.out.println("Aloitetaan tietokantojen luonti");

		// Tarkistetaan saadaanko selecti tehtyä fileobject tauluun, jos ei saada luodaan tietokannat
		try {
			jdbcTemplate.query(
                "SELECT tiedostonnimi FROM fileobject;",
                (rs, rowNum) -> rs.getString("tiedostonnimi")
        		).forEach(System.out::println);
			System.out.println("Tietokannat on jo luotu");
		} catch (Exception e) {
			System.out.println("Virhe: " + e);
			System.out.println("Tietokantoja ei ollut, luodaan tietokannat: ");

			// Luodaan fileobject taulu
			System.out.println("Tietokanta: fileobject luotu");
			jdbcTemplate.execute("CREATE TABLE fileobject (" +
			"id SERIAL PRIMARY KEY," +
			"content BIGINT NOT NULL," +
			"tiedostonnimi VARCHAR(150) NOT NULL," +
			"mediatyyppi VARCHAR(15) NOT NULL," +
			"tiedostonkoko BIGINT NOT NULL," +
			"tallennettu TIMESTAMP NOT NULL)");	
		
			// Luodaan measurement taulu
			System.out.println("Tietokanta: measurement luotu");
			jdbcTemplate.execute("CREATE TABLE measurement (" +
			"id SERIAL PRIMARY KEY," +
			"fileobjectid SERIAL NOT NULL," +
			"location VARCHAR(50) NOT NULL," +
			"datetime TIMESTAMP NOT NULL," +
			"sensortype VARCHAR(11) NOT NULL," +
			"value DECIMAL NOT NULL," +
			"FOREIGN KEY (fileobjectid) REFERENCES fileobject(id)" +
			// Komennolla saadaan "vyörytettyä" poisto. Eli kun tietopoistetaan fileobject taulusta, niin myös measurement taulusta poistuu kyseisen tiedoston rivit
			"ON DELETE CASCADE)");

			// Luodaan farm taulu
			System.out.println("Tietokanta: farm luotu");
			jdbcTemplate.execute("CREATE TABLE farm (" +
			"id SERIAL PRIMARY KEY," +
			"farmi VARCHAR(50) NOT NULL)");	
		}
	}
}
