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

	// Key type according to Movida file format.
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

	private class wrapPerson implements Comparable {
		private Person person;

		public wrapPerson() {
			this.person = null;
		}

		public wrapPerson(Person p) {
			this.person = p;
		}

		public Integer compareTo(wrapPerson w) {
			return this.person.getName().compareTo(w.person.getName());
		}
	}

	private class wrapMovie implements Comparable {
		private Movie movie;

		public wrapMovie() {
			this.movie = null;
		}

		public wrapMovie(Movie m) {
			this.movie = m;
		}

		public Integer compareTo(wrapMovie w) {
			return this.movie.getTitle().compareTo(w.movie.getTitle());
		}
	}

	private MapImplementation dictionary;
	private SortingAlgorithm algorithm;
	// Arrays of dictionaries to differentiate the types of keys.
	private HashMap[] table;
	private BinarySearchTree[] tree;
	private Vector<wrapPerson> arrayPerson;
	private Vector<wrapMovie> arrayMovie;
	private NonOrientedGraph graph;

	public MovidaCore() {
		this.table = null;
		this.tree = null;
		this.arrayPerson = null;
		this.arrayMovie = null;
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

		this.arrayPerson = null;
		this.arrayMovie = null;
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

	/**
	 * It performs one of the dictionary operations, characterizing the type of key (Title,
	 * Year, Director, Cast or Votes), on the active map implementation.
	 *
	 * @param op The operation to carry out (insert, search or delete).
	 * @param type The type of the key according to Movida file format (Title, Year, Director,
	 * Cast or Votes).
	 * @param key The key to pass as argument to dictionary operation.
	 * @param data Data to eventually map with key param in case of insert operation.
	 *
	 * @return The object returned by search operation, if op is equal to SEARCH, null otherwise.
	 */
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

	private Person[] parseCast(String tmpCast) {
		// Parsing Cast keys because of many Person (people).
		String[] splitting = tmpCast.split(", ");
		// Person (people) are one more than comma characters.
		Person[] cast = new Person[(splitting.length / 2) + 1];

		int j = 0;
		for (int i = 0; splitting.length > i; ++i) {
			if (", " == splitting[i])
				continue;

			cast[j++] = new Person(splitting[i]);
		}

		return cast;
	}

	// TODO: keys have to exist only for Movie(title) and Person(name)? Look at IMovidaDB.
	public void loadFromFile(File f) throws MovidaFileException {
		if (null == f)
			throw new MovidaFileException("Invalid object file");

		Path path = f.toPath();
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		Iterator<String> iter = lines.iterator();

		List<Person> people = new LinkedList<Person>();
		List<Movie> movies = new LinkedList<Movie>();

		String tmpTitle;
		String tmpYear;
		String tmpCast;
		String tmpDirector;

		// Parsing every line.
		while (iter.hasNext()) {
			if (iter.next().contains(": ")) {
				// Splitting the line into three parts, discarding colon character.
				String[] keys = iter.next().split(": ");

				if (3 != keys.length)
					throw new MovidaFileException("Invalid file format");

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

					/* Last case (see Movida file format): inserting a new element in the active
					   dictionary. */
					case "Votes":
						// Decoding strings (keys) into different types for Movie constructor.
						Integer votes = decode(keys[2]);
						Integer year = decode(tmpYear);
						Person director = new Person(tmpDirector);
						Person[] cast = this.parseCast(tmpCast);

						people.add(director);
						for (Person p: cast)
							people.add(p);

						// Movie object inserting.
						Movie movie = new Movie(tmpTitle, year, votes, cast, director);
						String[] tmpKey = {tmpTitle, tmpYear, tmpDirector, tmpCast, keys[2]};
						movies.add(movie);

						int i = 0;
						for (KeyType t: KeyType.values()) {
							// To avoid movies with the same title.
							if (TITLE == t)
								this.doOn(DELETE, t, tmpKey[i], null);

							this.doOn(INSERT, t, tmpKey[i++], movie);
						}
						break;

					default:
						throw new MovidaFileException("Unable to find keys");
						break;
				}
			}
		}

		Person[] arrayP = people.toArray();
		Movie[] arrayM = movies.toArray();
		this.arrayPerson = new Vector(arrayP);
		this.arrayMovie = new Vector(arrayM);
	}

	private Bytes[] movieToBytes(Movie movie) {
		String data = "Title: " + movie.getTitle() + "\n";
		data += "Year: " + movie.getYear().toString() + "\n";
		data += "Director: " + movie.getDirector().getName() + "\n";
		//TODO: finish
	}

	public void saveToFile(File f) {
		if (null == this.arrayMovie)
			throw new MovidaFileException("No already uploaded data");

		if (null == f)
			throw new MovidaFileException("null File object");

		Path file = f.toPath();

		if (!Files.isReadable(file) || !Files.isWritable(file))
			throw new MovidaFileException("Do not have permissions");
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
