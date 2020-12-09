package movida.borghicremona;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.*;
import java.util.*;
import movida.commons.*;
import movida.borghicremona.hashmap.HashMap;
import movida.borghicremona.bstree.BinarySearchTree;
import movida.borghicremona.graph.NonOrientedGraph;
import movida.borghicremona.sort.Vector;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {

	private enum KeyType {
		TITLE,
		YEAR,
		DIRECTOR,
		CAST,
		VOTES;
	}

	private enum DictionaryOperation {
		INSERT,
		SEARCH,
		DELETE;
	}

	private MapImplementation dictionary;
	private SortingAlgorithm algorithm;
	private HashMap[] table;
	private BinarySearchTree[] tree;
	private NonOrientedGraph graph;

	public MovidaCore() {
		this.table = null;
		this.tree = null;
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
				this.dictionary = map;
				this.tree = new BinarySearchTree[5];
				break;

			case HashIndirizzamentoAperto:
				this.dictionary = map;
				this.table = new HashMap[5];
				break;

			default:
				return false;
		}

		return true;
	}

	public MovidaCore(SortingAlgorithm algorithm, MapImplementation map) {
		try {
			if (!__setSort(algorithm) || !__setMap(map))
				throw new IllegalArgumentException("Impossible to allocate a MovidaCore instance: aborting");
		} catch(IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

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

	private Object doOn(DictionaryOperation op, KeyType type, Comparable key, Object data) {
		KeyValueElement element = new KeyValueElement(key, data);

		switch (this.dictionary) {
			case ABR:
				switch (op) {
					case INSERT:
						switch (type) {
							case TITLE:
								this.tree[0].insert(element);
								break;

							case YEAR:
								this.tree[1].insert(element);
								break;

							case DIRECTOR:
								this.tree[2].insert(element);
								break;

							case CAST:
								this.tree[3].insert(element);
								break;

							case VOTES:
								this.tree[4].insert(element);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case SEARCH:
						switch (type) {
							case TITLE:
								return this.tree[0].search(key);
								break;

							case YEAR:
								return this.tree[1].search(key);
								break;

							case DIRECTOR:
								return this.tree[2].search(key);
								break;

							case CAST:
								return this.tree[3].search(key);
								break;

							case VOTES:
								return this.tree[4].search(key);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case DELETE:
						switch (type) {
							case TITLE:
								this.tree[0].delete(key);
								break;

							case YEAR:
								this.tree[1].delete(key);
								break;

							case DIRECTOR:
								this.tree[2].delete(key);
								break;

							case CAST:
								this.tree[3].delete(key);
								break;

							case VOTES:
								this.tree[4].delete(key);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

				}
				break;

			case HashIndirizzamentoAperto: 
				switch (op) {
					case INSERT:
						switch (type) {
							case TITLE:
								this.table[0].insert(element);
								break;

							case YEAR:
								this.table[1].insert(element);
								break;

							case DIRECTOR:
								this.table[2].insert(element);
								break;

							case CAST:
								this.table[3].insert(element);
								break;

							case VOTES:
								this.table[4].insert(element);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case SEARCH:
						switch (type) {
							case TITLE:
								return this.table[0].search(key);
								break;

							case YEAR:
								return this.table[1].search(key);
								break;

							case DIRECTOR:
								return this.table[2].search(key);
								break;

							case CAST:
								return this.table[3].search(key);
								break;

							case VOTES:
								return this.table[4].search(key);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case DELETE:
						switch (type) {
							case TITLE:
								this.table[0].delete(key);
								break;

							case YEAR:
								this.table[1].delete(key);
								break;

							case DIRECTOR:
								this.table[2].delete(key);
								break;

							case CAST:
								this.table[3].delete(key);
								break;

							case VOTES:
								this.table[4].delete(key);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

				}
				break;

			default:
				System.err.println("doOn() default case: aborting");	
				System.exit(-1);
				break;
		}

		return null;
	}

	public void loadFromFile(File f) {
		if (null == f) {
			System.err.println("Invalid File object: aborting");
			System.exit(-1);
		}

		Path path = f.toPath();
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Iterator<String> iter = lines.iterator();
		String tmpTitle;
		String tmpYear;
		String tmpVotes;
		String tmpCast;
		String tmpDirector;

		while (iter.hasNext()) {
			if (iter.next().contains(": ")) {
				String[] keys = iter.next().split(": ");

				switch (keys[0]) {
					case "Title":
						tmpTitle = keys[2];
						break;

					case "Year":
						tmpYear = keys[2];
						break;

					case "Director":
						tmpDirector = keys[2];
						break;

					case "Cast":
						tmpCast = keys[2];
						break;

					case "Votes":
						tmpVotes = keys[2];
						//TODO: last case, so create Movie obj and insert every key
						break;

					default:
						System.err.println("Unable to parse the file: aborting");
						System.exit(-1);
						break;
				}
			}
		}
	}

	public void saveToFile(File f) {
		
	}

	public void clear() {

	}

	public int countMovies() {
		return 1;
	}

	public int countPeople() {
		return 1;

	}

	public boolean deleteMovieByTitle(String title) {
		return true;

	}

	public Movie getMovieByTitle(String title) {
		return null;

	}

	public Person getPersonByName(String name) {

		return null;
	}

	public Movie[] getAllMovies() {

		return null;
	}

	public Person[] getAllPeople() {

		return null;
	}

	public Movie[] searchMoviesByTitle(String title) {

		return null;
	}

	public Movie[] searchMoviesInYear(Integer year) {

		return null;
	}

	public Movie[] searchMoviesDirectedBy(String name) {

		return null;
	}

	public Movie[] searchMoviesStarredBy(String name) {

		return null;
	}

	public Movie[] searchMostVotedMovies(Integer N) {

		return null;
	}

	public Movie[] searchMostRecentMovies(Integer N) {

		return null;
	}

	public Person[] searchMostActiveActors(Integer N) {

		return null;
	}

	public Person[] getDirectCollaboratorsOf(Person actor) {

		return null;
	}

	public Person[] getTeamOf(Person actor) {

		return null;
	}

	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {

		return null;
	}
}
