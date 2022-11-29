package Moviecollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import Moviecollection.domain.GenreRepository;
import Moviecollection.domain.MovieRepository;
import Moviecollection.domain.Movie;
import Moviecollection.domain.ApplicationUserRepository;
import Moviecollection.domain.ApplicationUser;
import Moviecollection.domain.Genre;

@SpringBootApplication
public class MoviecollectionApplication {
	private static final Logger log = LoggerFactory.getLogger(MoviecollectionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MoviecollectionApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner movieDemo(MovieRepository repository, GenreRepository grepository, ApplicationUserRepository urepository) {
		
		return (args) -> {
			
			log.info("Adding a couple of movies");
			grepository.save(new Genre("Fantasy"));
			grepository.save(new Genre("Drama"));
			grepository.save(new Genre("Scifi"));
			
			repository.save(new Movie("Forrest Gump", "Robert Zemeckis", "Forrest Gump is a 1994 American comedy-drama film directed by Robert Zemeckis and written by Eric Roth.", 1994, grepository.findByName("Drama").get(0)));
			repository.save(new Movie("Interstellar", "Christopher Nolan", "Interstellar is a 2014 epic science-fiction film co-written, directed, and produced by Christopher Nolan.", 2014, grepository.findByName("Scifi").get(0)));
			repository.save(new Movie("Harry Potter and the Philosopher's Stone", "Chris Columbus", "Harry Potter and the Philosopher's Stone is a 2001 fantasy film directed by Chris Columbus.", 2001, grepository.findByName("Fantasy").get(0)));
			
			ApplicationUser user1 = new ApplicationUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			ApplicationUser user2 = new ApplicationUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
			log.info("Fetch all movies");
			for (Movie movie: repository.findAll()) {
				log.info(movie.toString());
			}
		};
	}

}
