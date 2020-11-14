package movida.borghicremona;

import movida.commons.*;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {
	public boolean setSort(SortingAlgorithm algorithm) {

	}

	public boolean setMap(MapImplementation map) {

	}

	public void loadFromFile(File f) {

	}

	public void saveToFile() {
		
	}

	public void clear() {

	}

	public int countMovies() {

	}

	public int countPeople() {

	}

	public boolean deleteMovieByTitle(String title) {

	}

	public Movie getMovieByTitle(String title) {

	}

	public Person getPersonByName(String name) {

	}

	public Movie[] getAllMovies() {

	}

	public Person[] getAllPeople() {

	}

	public Movie[] searchMoviesByTitle(String title) {

	}

	public Movie[] searchMoviesInYear(Integer year) {

	}

	public Movie[] searchMoviesDirectedBy(String name) {

	}

	public Movie[] searchMoviesStarredBy(String name) {

	}

	public Movie[] searchMostVotedMovies(Integer N) {

	}

	public Movie[] searchMostRecentMovies(Integer N) {

	}

	public Person[] searchMostActiveActors(Integer N) {

	}

	public Person[] getDirectCollaboratorsOf(Person actor) {

	}

	public Person[] getTeamOf(Person actor) {

	}

	public Collaboration[] maximizeCollaborationInTheTeamOf(Person actor) {

	}
}
