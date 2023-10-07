import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;


class MovieTriviaTest {
	
	//instance of movie trivia object to test
	MovieTrivia mt;
	//instance of movieDB object
	MovieDB movieDB;
	
	@BeforeEach
	void setUp() throws Exception {
		//initialize movie trivia object
		mt = new MovieTrivia ();
		
		//set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		//get instance of movieDB object from movie trivia object 
		movieDB = mt.movieDB;
	}

	@Test
	void testSetUp() { 
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}
	
	@Test
	void testInsertActor () {
		
		//try to insert new actor with new movies
		mt.insertActor("test1", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));

		//try to insert existing actor with new movies
		mt.insertActor("   Meryl STReep      ", new String [] {"   DOUBT      ", "     Something New     "}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());

		//look up and inspect movies for existing actor
		//note, this requires the use of properly implemented selectWhereActorls method
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"));
		
		// TODO add additional test case scenarios
		//insert non exist
		mt.insertActor("test2",new String[] {"movie1","movie2","movie3"},movieDB.getActorsInfo());
		assertEquals(8,movieDB.getActorsInfo().size());
		assertEquals("test2", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(3, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("movie2", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(1));

		//try to insert existing actor with new movies
		mt.insertActor("   Robin WilliaMS     ", new String [] {"   PopeyE     ", "     Something New     "}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());
		//look up and inspect movies for existing actor
		//note, this requires the use of properly implemented selectWhereActorls method
		assertEquals(2, mt.selectWhereActorIs("Robin WilliaMS ", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs("Robin WilliaMS", movieDB.getActorsInfo()).contains("something new"));

		//try to insert existing actor with new movies
		mt.insertActor("  Tom Hanks    ", new String [] {"  Catch Me If You Can   ", "     Something New one    "}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());
		//look up and inspect movies for existing actor
		//note, this requires the use of properly implemented selectWhereActorls method
		assertEquals(4, mt.selectWhereActorIs(" Tom Hanks  ", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs(" Tom Hanks ", movieDB.getActorsInfo()).contains("something new one"));

		//insert non exist
		mt.insertActor("test3",new String[] {"mov1"},movieDB.getActorsInfo());
		assertEquals(9,movieDB.getActorsInfo().size());
		assertEquals("test3", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(1, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("mov1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
	}
	
	@Test
	void testInsertRating () {
		
		//try to insert new ratings for new movie 
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
		//try to insert new ratings for existing movie 
		mt.insertRating("doubt", new int [] {100, 100}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());
		
		//look up and inspect movies based on newly inserted ratings
		//note, this requires the use of properly implemented selectWhereRatingIs method
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size());
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"));
		
		// TODO add additional test case scenarios
	}
	
	@Test
	void testSelectWhereActorIs () {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		// TODO add additional test case scenarios
		//non exist actor
		assertEquals(0, mt.selectWhereActorIs("ac", movieDB.getActorsInfo()).size());
		//exist
		assertEquals("seven", mt.selectWhereActorIs("Brad Pitt", movieDB.getActorsInfo()).get(0));
		assertEquals(2, mt.selectWhereActorIs("Brad Pitt  ", movieDB.getActorsInfo()).size());
		//exist
		assertEquals("fight club", mt.selectWhereActorIs("Brad Pitt  ", movieDB.getActorsInfo()).get(1));
		//non exist
		assertEquals(0, mt.selectWhereActorIs("ac1", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.selectWhereActorIs("Brandon Krakowsky", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO add additional test case scenarios
		//exist
		assertEquals(1, mt.selectWhereMovieIs("Sophie's Choice", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("Sophie's Choice", movieDB.getActorsInfo()).contains("meryl streep"));
		//non exist movie
		assertEquals(0, mt.selectWhereMovieIs("mov", movieDB.getActorsInfo()).size());
		//exist
		assertEquals(1, mt.selectWhereMovieIs("Popeye ", movieDB.getActorsInfo()).size());
		//exist
		assertEquals(true, mt.selectWhereMovieIs("Arrival", movieDB.getActorsInfo()).contains("amy adams"));

	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		// TODO add additional test case scenarios
	}
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO add additional test case scenarios
	}
	
	@Test
	void testGetCommonMovie () {
		//no same movies
		assertEquals(0, mt.getCommonMovie("meryl streep", "Robin Williams", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		// TODO add additional test case scenarios
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertFalse(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("Sophie's Choice"));
		assertFalse(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("Doubt"));
		//non-exist actors
		assertEquals(0,mt.getCommonMovie("a", "b", movieDB.getActorsInfo()).size());
		//same two actors
		assertEquals(3, mt.getCommonMovie("tom hanks", "tom hanks", movieDB.getActorsInfo()).size());
		assertFalse(mt.getCommonMovie("tom hanks", "tom hanks", movieDB.getActorsInfo()).contains("Cast Away"));

		//non exist
		assertEquals(0,mt.getCommonMovie("tom hanks", "b", movieDB.getActorsInfo()).size());


	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		// TODO add additional test case scenarios
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		// TODO add additional test case scenarios
		//no same actors
		assertEquals(0, mt.getCommonActors("doubt", " Cast Away", movieDB.getActorsInfo()).size());
		//non exist movie
		assertEquals(0, mt.getCommonActors("  mov", "Popeye", movieDB.getActorsInfo()).size());
		//exist
		assertTrue(mt.getCommonActors("Cast Away", "the post", movieDB.getActorsInfo()).contains("tom hanks"));
		//same movie
		assertEquals(2, mt.getCommonActors(" Doubt", "Doubt", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors(" Doubt", "Doubt", movieDB.getActorsInfo()).contains("meryl streep"));
	}
	
	@Test
	void testGetMean () {

		// TODO add ALL test case scenarios!
		assertEquals(7, movieDB.getMoviesInfo().size());
		assertEquals(67.86,mt.getMean(movieDB.getMoviesInfo())[0]);
		assertEquals(65.71,mt.getMean(movieDB.getMoviesInfo())[1]);

		//insert
		mt.insertRating("Star", new int [] {88, 66},movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());
		assertEquals(70.38,mt.getMean(movieDB.getMoviesInfo())[0]);
		assertEquals(65.75,mt.getMean(movieDB.getMoviesInfo())[1]);

		//insert existing movie rating
		mt.insertRating("Popey  ", new int [] {80, 86},movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals(71.44,mt.getMean(movieDB.getMoviesInfo())[0]);
		assertEquals(68,mt.getMean(movieDB.getMoviesInfo())[1]);

		//insert
		mt.insertRating("mo", new int[] {0,0}, movieDB.getMoviesInfo());
		assertEquals(10, movieDB.getMoviesInfo().size());
		assertEquals(64.3,mt.getMean(movieDB.getMoviesInfo())[0]);
		assertEquals(61.2,mt.getMean(movieDB.getMoviesInfo())[1]);


	}
}
