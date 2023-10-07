import java.util.ArrayList;
import java.util.Locale;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {

	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();


	public static void main(String[] args) {

		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();

		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}

	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);

		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();
	}

	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}

	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}


	// TODO add additional methods as specified in the instructions PDF

	/**
	 * If the given actor is not already present in the given actorsInfo arraylist, this method
	 * should append the given actor along with their given movies to the end of actorsInfo.
	 * This method does not return anything. It will work by just modifying the given actorsInfo
	 * ArrayList passed to it
	 * @param actor is the actor name as a string
	 * @param movies is a String array of movie names that the actor has acted in
	 * @param actorsInfo is the ArrayList that is to be inserted into/updated
	 */

	public void insertActor(String actor, String [] movies, ArrayList<Actor> actorsInfo)
	{
		Actor newActor;
		boolean sign1 = true;
		String newMovie;
		//actor is case insensitive
		actor = actor.trim().toLowerCase();
		//iterate actorInfo array
		oo:
		for(int i = 0; i < actorsInfo.size(); i++)
		{
			//If the given actor is already present in the given actorsInfo arraylist, it should append
			//the given movies to the end of the actor object's movieCasted list.
			if(actor.equals(actorsInfo.get(i).getName()))
			{
				sign1 = false;
				//When a movie in the movies Array already exists in the actor’s list of movies, avoid adding a
				//duplicated one

				for (int m = 0; m < movies.length; m++)
				{
					newMovie = movies[m].trim().toLowerCase();
					int s;
					jj:
					for(s = 0; s < actorsInfo.get(i).getMoviesCast().size(); s++)
					{
						if(newMovie.equals(actorsInfo.get(i).getMoviesCast().get(s)))
						{
							break jj;
						}

					}
					if(s==actorsInfo.get(i).getMoviesCast().size())
					{
						actorsInfo.get(i).getMoviesCast().add(newMovie);
					}

				}
				break oo;
			}
		}
		//If the given actor is not already present in the given actorsInfo arraylist, this method
		//should append the given actor along with their given movies to the end of actorsInfo.
		if(sign1){
			newActor = new Actor (actor);
			for (int l = 0; l < movies.length; l++) {
				newActor.getMoviesCast().add(movies[l].trim().toLowerCase());
			}
			actorsInfo.add(newActor);
		}
	}


	public void insertRating (String movie, int [] ratings, ArrayList <Movie> moviesInfo) {
		// Check if the input ratings are valid
		if (ratings==null||ratings.length!=2||ratings[0]<0||ratings[0]>100||ratings[1]>100||ratings[1]<0) {
			return;
		}else {
			/**
			 * Critic's rating
			 */
			int criticRating=ratings[0];
			/**
			 * Audience's rating
			 */
			int audienceRatings=ratings[1];
			/**
			 * Change the movie name into proper form
			 */
			String newMov=movie.trim().toLowerCase();
			/**
			 * Count the time of the loop
			 */
			int i=0;
			//Iterate over every element in moviesInfo
			for(;i<moviesInfo.size();i++) {
				//Check if the i'th element in moviesInfo is the same name as the new movie
				if (moviesInfo.get(i).getName().equals(newMov)) {
					//Update the rating of the movie
					moviesInfo.get(i).setAudienceRating(audienceRatings);
					moviesInfo.get(i).setCriticRating(criticRating);
					break;
				}
			}
			//When the new movie is not in the moviesInfo
			if(i==moviesInfo.size()) {
				//Insert the new movie and its ratings into moviesInfo
				Movie newMovie= new Movie(newMov,criticRating,audienceRatings);
				moviesInfo.add(newMovie);
			}
		}
	}
	public  ArrayList<String> selectWhereActorIs(String actor, ArrayList<Actor> actorsInfo)
	{
		actor = actor.trim().toLowerCase();
		ArrayList<String> moviess = new ArrayList<>(0);
		for(int i = 0; i < actorsInfo.size(); i++)
		{
			if(actor.equals(actorsInfo.get(i).getName()))
			{
				moviess = actorsInfo.get(i).getMoviesCast();
			}

		}

		return moviess;
	}

	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList<Actor> actorsInfo)
	{
		//make the input movie without whitespace and to lowercase
		movie = movie.trim().toLowerCase();
		ArrayList<String> molist;
		String ac;
		//create an empty new list of actors in that movie;
		ArrayList<String> Actorofm = new ArrayList<>(0);
		for(int i = 0; i < actorsInfo.size(); i++ )
		{
			//molist is # i movie array;
			molist = actorsInfo.get(i).getMoviesCast();
			for(int m = 0; m < molist.size(); m++)
			{
				if(movie.equals(molist.get(m)))
				{
					//get actor name acted in this movie;
					ac = actorsInfo.get(i).getName();
					//store the actor name in the array created before;
					Actorofm.add(ac);
					continue;
				}
			}
		}

		return Actorofm;
	}
	/**
	 * This method returns a list of movies that satisfy an inequality or equality, based on the comparison argument
	 * and the targeted rating argument
	 * @param comparison is either ‘=’, ‘>’, or ‘< ‘ and is passed in as a char
	 * @param targetRating is an integer
	 * @param isCritic is a boolean that represents whether we are interested in the critics’ rating or the audience rating.
	 * true = critic ratings, false = audience ratings.
	 * @param moviesInfo
	 * @return a list of movies that satisfy an inequality or equality
	 */
	public ArrayList <String> selectWhereRatingIs (char comparison, int targetRating, boolean isCritic, ArrayList <Movie> moviesInfo){
		/**
		 * Create arraylist of movies that satisfy an inequality or equality
		 */
		ArrayList <String> satisfyingMovie=new ArrayList<String>();
		//Check if the targetRating is out of range
		if(targetRating<0||targetRating>100) {
			satisfyingMovie.add(null);
		}
		//Check if it ask for the critic's rating
		else if(isCritic==true) {
			//Check the equality&inequality
			if(comparison=='<') {
				//Iterate over the moviesInfo
				for(int i=0;i<moviesInfo.size();i++) {
					//Check if the critic's rating of certain movie is in the range
					if(moviesInfo.get(i).getCriticRating()<targetRating) {
						//Append it to the arraylist satisfyingMovie
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}else if (comparison=='=') {
				for(int i=0;i<moviesInfo.size();i++) {
					if(moviesInfo.get(i).getCriticRating()==targetRating) {
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}
			else if (comparison=='>') {
				for(int i=0;i<moviesInfo.size();i++) {
					if(moviesInfo.get(i).getCriticRating()>targetRating) {
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}
		}
		//Check if it ask for audience's rating
		else if(isCritic==false) {
			if(comparison=='<') {
				for(int i=0;i<moviesInfo.size();i++) {
					if(moviesInfo.get(i).getAudienceRating()<targetRating) {
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}else if (comparison=='=') {
				for(int i=0;i<moviesInfo.size();i++) {
					if(moviesInfo.get(i).getAudienceRating()==targetRating) {
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}
			else if (comparison=='>') {
				for(int i=0;i<moviesInfo.size();i++) {
					if(moviesInfo.get(i).getAudienceRating()>targetRating) {
						satisfyingMovie.add(moviesInfo.get(i).getName());
					}
				}
			}
		}
		return satisfyingMovie;
	}
	/**
	 * Returns a list of all actors that the given actor has ever worked with in any movie except the actor herself/himself
	 * @param actor is the name of an actor as a String
	 * @param actorsInfo is the ArrayList to search through
	 * @return a list of all actors that the given actor has ever worked with
	 */
	public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo){
		/**
		 * Create an ArrayList of actor that the given actor ever worked with
		 */
		ArrayList<String> actorWorkedWith=new ArrayList<String>();
		/**
		 * Create an ArrayList of movie that the given actor acted in
		 */
		ArrayList<String> movieActIn=selectWhereActorIs(actor,actorsInfo);

		//Iterate over all the movie that acted in

		for(int i=0;i<movieActIn.size();i++) {
			/**
			 * Create an ArrayList of actor in certain movie
			 */
			ArrayList<String> actorInMovie=selectWhereMovieIs(movieActIn.get(i),actorsInfo);
			//Iterate over the ArrayList of actor in certain movie
			for(int j=0;j<actorInMovie.size();j++) {
				//Check if certain actor is the given actor
				if(actor.equals(actorInMovie.get(j))){
					continue;
				}
				//If not, append it to ArrayList of actor that ever worked with
				else {
					actorWorkedWith.add(actorInMovie.get(j));
				}
			}
		}
		return actorWorkedWith;
	}
	/**
	 * Returns a list of movie names where both actors were cast.
	 * @param actor1 is actor names as Strings
	 * @param actor2 is actor names as Strings
	 * @param actorsInfo is the ArrayList to search through
	 * @return
	 */
	public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo)
	{
		/**
		 * Create an ArrayList of movies that the two actors worked in
		 */
		actor1 = actor1.trim().toLowerCase();
		actor2 = actor2.trim().toLowerCase();
		ArrayList <String> commonMovieList=new ArrayList <String> (0);
		ArrayList <String> oneMovieList;
		ArrayList <String> twoMovieList;
		oneMovieList = selectWhereActorIs(actor1,actorsInfo);
		twoMovieList = selectWhereActorIs(actor2,actorsInfo);
		if(actor1.equals(actor2)){
			for(int n = 0; n < oneMovieList.size(); n++)
			{
			     commonMovieList.add(oneMovieList.get(n));
			}
		}
		else
		{
			if(oneMovieList != null & twoMovieList != null){
				for(int i = 0; i < oneMovieList.size(); i++)
				{
					oo:
					for(int m = 0; m < twoMovieList.size(); m++)
					{
						if(oneMovieList.get(i).equals(twoMovieList.get(m)))
						{
							commonMovieList.add(oneMovieList.get(i));
							break oo;
						}
						else continue ;
					}
				}
			}

		}
		return commonMovieList;
	}

	public ArrayList<String> getCommonActors(String movie1,String movie2,ArrayList<Actor> actorsInfo)
	{
		movie1 = movie1.trim().toLowerCase();
		movie2 = movie2.trim().toLowerCase();
		ArrayList <String> commonActorList=new ArrayList <String> (0);
		ArrayList <String> oneActorList;
		ArrayList <String> twoActorList;
		oneActorList = selectWhereMovieIs(movie1,actorsInfo);
		twoActorList = selectWhereMovieIs(movie2,actorsInfo);
		if(movie1.equals(movie2)){
			for(int n = 0; n< oneActorList.size();n++)
			{
				commonActorList.add(oneActorList.get(n));
			}
		}
		else{
			oo:
			for(int i = 0; i < oneActorList.size(); i++)
			{
				for(int m = 0; m < twoActorList.size(); m++)
				{
					if(oneActorList.get(i).equals(twoActorList.get(m)))
					{
						commonActorList.add(oneActorList.get(i));
						break oo;
					}
				}
			}
		}

		return commonActorList;

	}

	/**
	 * Returns a list of movie names that both critics and the audience have rated above 85 (>= 85).
	 * @param moviesInfo
	 * @return
	 */
	public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo){
		/**
		 * Create an ArrayList of good movies
		 */
		ArrayList <String> goodMovieList=new ArrayList <String> ();
		//Iterate over the given ArrayList moviesInfo
		for(int i=0;i<moviesInfo.size();i++) {
			//Check if the ratings greater than or equal to 85
			if(moviesInfo.get(i).getAudienceRating()>=85 && moviesInfo.get(i).getCriticRating()>=85) {
				//Append it to goodMovieList
				goodMovieList.add(moviesInfo.get(i).getName());
			}
		}
		return goodMovieList;
	}


	public static double[] getMean(ArrayList<Movie> moviesInfo)
	{
		double meanofC = 0;
		double meanofA = 0;
		double[] mean = new double[2];
		for(int i=0;i<moviesInfo.size();i++) {
			meanofA += moviesInfo.get(i).getAudienceRating();
			meanofC += moviesInfo.get(i).getCriticRating();
		}
		meanofA = Double.parseDouble(String.format("%.2f", meanofA/moviesInfo.size()));
		meanofC = Double.parseDouble(String.format("%.2f", meanofC/moviesInfo.size()));
		mean[0] = meanofC;
		mean[1] = meanofA;

		return mean;
	}

}
