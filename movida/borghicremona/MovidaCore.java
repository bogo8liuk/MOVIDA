package movida.borghicremona;

import movida.commons.*;
import movida.borghicremona.hashmap;
import movida.borghicremona.graph;
import movida.borghicremona.sort;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {
	private MapImplementation dictionary;
	private SortingAlgorithm algorithm;
	private HashMap hashmap;
	// TODO: abr
	private NonOrientedGraph graph;

	public MovidaCore() {
		this.hashmap = null;
		// TODO: abr
		this.graph = null; // TODO: (?)
		this.dictionary = null;
		this.algorithm = null;
	}

	/**
	 * It sets the value of algorithm member field to the value passed as parameter,
	 * if possible.
	 *
	 * @param algorithm Value to set.
	 *
	 * @return true If the setting is successful, false otherwise.
	 */
	private boolean __setSort(SortingAlgorithm algorithm) {
		switch (algorithm) {
			case SelectionSort:
			case QuickSort:
				this.algorithm = algorithm;
				break;

			default:
				return false;
		}

		return true;
	}

	/**
	 * It sets the value of dictionary member field to the value passed as parameter,
	 * if possibile.
	 *
	 * @param map Value to set.
	 *
	 * @return true If the setting is successful, false otherwise.
	 */
	private boolean __setMap(MapImplementation map) {
		switch (map) {
			case ABR:
			case HashIndirizzamentoAperto:
				this.dictionary = map;
				break;

			default:
				return false;
		}

		return true;
	}

	public MovidaCore(SortingAlgorithm algorithm, MapImplementation map) {
		try {
			if (!__setSort(algorithm) || !__setMap(map))
				throw new IllegalArgumentException("Impossible to allocate a MovidaCore instance");
		} catch(IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

		this.hashmap = null;
		// TODO: abr
		this.graph = null; // TODO: (?)
	}

	public boolean setSort(SortingAlgorithm algorithm) {
		if (null != this.algorithm)
			return false;

		return __setSort(algorithm);
	}

	public boolean setMap(MapImplementation map) {
		if (null != this.dictionary)
			return false;

		return __setMap(map);
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
