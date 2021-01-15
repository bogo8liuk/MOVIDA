package movida.borghicremona;

import java.io.File;
import movida.commons.*;

public class MovidaCoreTest {
	public static void main(String[] args) {
		MovidaCore base = new MovidaCore();

		if (!base.setSort(SortingAlgorithm.SelectionSort)) {
			System.err.println("setSort() returning false");
			System.exit(-1);
		}

		if (!base.setMap(MapImplementation.ABR)) {
			System.err.println("setMap() returning false");
			System.exit(-1);
		}

		try {
			base.loadFromFile(new File("movies.txt"));
		} catch (MovidaFileException exception) {
			System.err.println("loadFromFile() throwing MovidaFileException");
			System.exit(-1);
		}

		// TODO: saveToFile()
		// TODO: clear()

		if (10 != base.countMovies()) {
			System.err.println("countMovies() returning a wrong number of movies");
			System.exit(-1);
		}

		if (33 != base.countPeople()) {
			System.err.println("countPeople() returning a wrong number of people");
			System.exit(-1);
		}

		base.deleteMovieByTitle("Die Hard");

		if (9 != base.countMovies()) {
			System.err.println("countMovies() or deleteMovieByTitle() error: a movie should have been deleted");
			System.exit(-1);
		}

		base.deleteMovieByTitle("The Avengers");
		int peopleNo = base.countPeople();

		if (9 != base.countMovies()) {
			System.err.println("countMovies() or deleteMovieByTitle() error: no movie should have been deleted");
			System.exit(-1);
		}

		if (peopleNo != base.countPeople()) {
			System.err.println("countPeople() returning a different number of people after no movie has been deleted");
			System.exit(-1);
		}

		if (null != base.getMovieByTitle("Die Hard")) {
			System.err.println("getMovieByTitle() returning a non-null Movie object after that the searched movie was already deleted");
			System.exit(-1);
		}

		if (null == base.getMovieByTitle("Scarface")) {
			System.err.println("getMovieByTitle() returning null after searching an existent movie");
			System.exit(-1);
		}

		base.deleteMovieByTitle("Scarface");

		if (null != base.getMovieByTitle("Scarface")) {
			System.err.println("loadFromFile() error: no duplicates allowed");
			System.exit(-1);
		}

		if (null == base.getPersonByName("Robert De Niro")) {
			System.err.println("getPersonByName() error: the searched person is existent");
			System.exit(-1);
		}

		if (null != base.getPersonByName("Bonnie Bedelia")) {
			System.err.println("getPersonByName() returning a non-null Person object after that the searched person was already deleted");
			System.exit(-1);
		}

		Movie[] movies = base.getAllMovies();

		if (null == movies) {
			System.err.println("getAllMovies() returning null");
			System.exit(-1);
		}

		if (base.countMovies() != movies.length) {
			System.err.println("The length of the array of Movie returned by getAllMovies() is different from the value of countMovies()");
			System.exit(-1);
		}

		if (null == base.getAllPeople()) {
			System.err.println("getAllPeople() returning null");
			System.exit(-1);
		}

		if (base.getMovieByTitle("Cape Fear") != base.searchMoviesByTitle("Cape Fear")[0]) {
			System.err.println("getMovieByTitle() and searchMovieByTitle() should return the same");
			System.exit(-1);
		}

		if (null != base.searchMoviesInYear(2025)) {
			System.err.println("searchMoviesInYear() returning a non-null array of Movie while there's no movie in that year");
			System.exit(-1);
		}

		Movie[] movies_in_1997 = base.searchMoviesInYear(1997);

		if (null == movies_in_1997 || 2 != movies_in_1997.length) {
			System.err.println("searchMoviesInYear() error: there are two movies in 1997");
			System.exit(-1);
		}

		Movie[] movies_by_scorsese = base.searchMoviesDirectedBy("Martin Scorsese");

		if (null == movies_by_scorsese || 2 != movies_by_scorsese.length) {
			System.err.println("searchMoviesDirectedBy() error: Scorsese directed two movies");
			System.exit(-1);
		}

		Movie[] movies_with_willis = base.searchMoviesStarredBy("Bruce Willis");

		if (null == movies_with_willis || 1 != movies_with_willis.length) {
			System.err.println("searchMoviesDirectedBy() error: Willis attended one movie");
			System.exit(-1);
		}

		if (8 != base.searchMostVotedMovies(8).length) {
			System.err.println("searchMostVotedMovies() error: there are 8 movies");
			System.exit(-1);
		}

		if (8 != base.searchMostVotedMovies(11).length) {
			System.err.println("searchMostVotedMovies() error: there are less than 11 movies");
			System.exit(-1);
		}

		if (8 != base.searchMostRecentMovies(8).length) {
			System.err.println("searchMostRecentMovies() error: there are 8 movies");
			System.exit(-1);
		}

		if (8 != base.searchMostRecentMovies(11).length) {
			System.err.println("searchMostRecentMovies() error: there are less than 11 movies");
			System.exit(-1);
		}

		if (8 != base.searchMostActiveActors(8).length) {
			System.err.println("searchMostActiveActors() error: the query requires 8 actors");
			System.exit(-1);
		}

		if (21 != base.searchMostActiveActors(50).length) {
			System.err.println("searchMostActiveActors() error: there are less than 50 actors");
			System.exit(-1);
		}

		Person[] direct_collab_ford = base.getDirectCollaboratorsOf(new Person("Harrison Ford"));

		if (null == direct_collab_ford) {
			System.err.println("getDirectCollaboratorsOf() error: Harrison Ford exists and has collaborations");
			System.exit(-1);
		}

		boolean found = false;

		for (int i = 0; direct_collab_ford.length > i; ++i) {
			if (0 == direct_collab_ford[i].getName().compareTo("John Travolta")) {
				System.err.println("getDirectCollaboratorsOf() error: Harrison Ford has not John Travolta as direct collaborator");
				System.exit(-1);
			}

			if (0 == direct_collab_ford[i].getName().compareTo("Katharine Towne"))
				found = true;
		}

		if (!found) {
			System.err.println("getDirectCollaboratorsOf() error: Katharine Towne is a direct collaborator of Harrison Ford");
			System.exit(-1);
		}

		found = false;
		Person[] team_skerritt = base.getTeamOf(new Person("Tom Skerritt"));

		for (int i = 0; team_skerritt.length > i; ++i) {
			if (0 == team_skerritt[i].getName().compareTo("Sela Ward")) {
				System.err.println("getTeamOf() error: Sela Ward is not part of the team of Tom Skerritt");
				System.exit(-1);
			}

			if (0 == team_skerritt[i].getName().compareTo("Juliette Lewis"))
				found = true;
		}

		if (!found) {
			System.err.println("getTeamOf() error: Juliette Lewis is part of Tom Skerritt");
			System.exit(-1);
		}

		System.out.println("MovidaCore test terminated successfully!");
	}
}
