package movida.borghicremona;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.*;
import java.nio.file.StandardOpenOption;
import java.util.*;
import movida.commons.*;
import movida.borghicremona.hashmap.HashMap;
import movida.borghicremona.bstree.BinarySearchTree;
import movida.borghicremona.graph.NonOrientedGraph;
import movida.borghicremona.sort.Vector;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {

	// Key type according to Movida file format.
	private enum KeyType {
		MOVIE,	//Identified uniquely by the title.
		PERSON;	//Identified uniquely by the name.
	}

	private enum DictionaryOperation {
		INSERT,
		SEARCH,
		DELETE;
	}

	// Association between a Person and a Movie.
	private class PairPersonMovie implements Comparable<PairPersonMovie> {
		private Person person;
		private Movie movie;

		public PairPersonMovie() {
			this.person = null;
			this.movie = null;
		}

		public PairPersonMovie(Person p, Movie m) {
			this.person = p;
			this.movie = m;
		}

		public int compareTo(PairPersonMovie w) {
			return this.person.getName().compareTo(w.person.getName());
		}

		public Person getPerson() {
			return this.person;
		}

		public Movie getMovie() {
			return this.movie;
		}

		public void remove(List<PairPersonMovie> list, Movie m) {
			Iterator<PairPersonMovie> iter = list.listIterator();

			while (iter.hasNext()) {
				if (iter.next().getMovie() == m)
					iter.remove();
			}
		}
	}

	// Association between an Integer and a Movie.
	private class PairIntMovie implements Comparable<PairIntMovie> {
		private Integer index;
		private Movie movie;

		public PairIntMovie() {
			this.index = null;
			this.movie = null;
		}

		public PairIntMovie(Integer i, Movie m) {
			this.index = i;
			this.movie = m;
		}

		public int compareTo(PairIntMovie w) {
			return this.index.compareTo(w.index);
		}

		public Integer getIndex() {
			return this.index;
		}

		public Movie getMovie() {
			return this.movie;
		}

		public void remove(List<PairIntMovie> list, Movie m) {
			Iterator<PairIntMovie> iter = list.listIterator();

			while (iter.hasNext()) {
				if (iter.next().getMovie() == m)
					iter.remove();
			}
		}
	}

	private MapImplementation dictionary;
	private SortingAlgorithm algorithm;

	/* Dictionaries for Movie and Person, only one for type (Movie and Person)
	   has to be active */
	private HashMap tableMovie;
	private HashMap tablePerson;
	private BinarySearchTree treeMovie;
	private BinarySearchTree treePerson;

	// Arrays to keep track of data about movies
	private Vector[] arrayData;

	/* Hashmap used to keep track of deleted movies, in order not to consider
	   data about already deleted movies. */
	private HashMap deletedMovies;

	private NonOrientedGraph graph;

	public MovidaCore() {
		this.tableMovie = null;
		this.tablePerson = null;
		this.treeMovie = null;
		this.treePerson = null;
		this.arrayData = null;
		this.deletedMovies = new HashMap();
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
				this.treeMovie = new BinarySearchTree();
				this.treePerson = new BinarySearchTree();
				break;

			case HashIndirizzamentoAperto:
				this.dictionary = map;
				this.tableMovie = new HashMap();
				this.tablePerson = new HashMap();
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

		this.arrayData = null;
		this.deletedMovies = new HashMap();
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
	 * @return The object returned by search operation or delete operation, if op is equal to
	 * SEARCH or DELETE, null otherwise.
	 */
	private Object doOn(DictionaryOperation op, KeyType type, Comparable key, Object data) {
		KeyValueElement element = new KeyValueElement(key, data);

		switch (this.dictionary) {
			case ABR:
				switch (op) {
					case INSERT:
						KeyValueElement entry = new KeyValueElement(key, data);
						
						switch (type) {
							case MOVIE:
								this.treeMovie.insert(entry);
								break;

							case PERSON:
								this.treePerson.insert(entry);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case SEARCH:
						switch (type) {
							case MOVIE:
								return this.treeMovie.search(key);

							case PERSON:
								return this.treePerson.search(key);

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case DELETE:
						switch (type) {
							case MOVIE:
								return this.treeMovie.delete(key);

							case PERSON:
								return this.treePerson.delete(key);

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
						KeyValueElement entry = new KeyValueElement(key, data);

						switch (type) {
							case MOVIE:
								this.tableMovie.insert(entry);
								break;

							case PERSON:
								this.tablePerson.insert(entry);
								break;

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case SEARCH:
						switch (type) {
							case MOVIE:
								return this.tableMovie.search(key);

							case PERSON:
								return this.tablePerson.search(key);

							default:
								System.err.println("doOn() default case: aborting");	
								System.exit(-1);
								break;
						}
						break;

					case DELETE:
						switch (type) {
							case MOVIE:
								return this.tableMovie.delete(key);

							case PERSON:
								return this.tablePerson.delete(key);

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

	private static Person[] parseCast(String tmpCast) {
		// Parsing Cast keys because of many Person (people).
		String[] splitting = tmpCast.split(", ");
		// Person (people) are one more than comma characters.
		Person[] cast = new Person[splitting.length];

		for (int i = 0; splitting.length > i; ++i)
			cast[i] = new Person(splitting[i]);

		return cast;
	}

	public void loadFromFile(File f) throws MovidaFileException {
		if (null == f)
			throw new MovidaFileException();

		this.arrayData = new Vector[4];

		List<String> lines;
		Path path = f.toPath();
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException exception) {
			throw new MovidaFileException();
		}
		Iterator<String> iter = lines.iterator();

		/* Temporary variables to save the strings representing data in the file, while
		   parsing it. */
		String title = "";
		String tmpYear = "";
		String tmpCast = "";
		String tmpDirector = "";

		// Map to check the right format of every line.
		boolean[] boolmap = { false, false, false, false };

		// Parsing every line.
		while (iter.hasNext()) {
			String next = iter.next();

			if (next.contains(":")) {
				// Splitting the line into two parts, discarding colon character.
				String[] keys = next.split(":");

				if (2 != keys.length)
					throw new MovidaFileException();

				switch (keys[0]) {
					case "Title":
						boolmap[0] = true;

						title = keys[1].trim();
						break;

					case "Year":
						if (!boolmap[0])
							throw new MovidaFileException();
						boolmap[1] = true;

						tmpYear = keys[1].trim();
						break;

					case "Director":
						if (!boolmap[1])
							throw new MovidaFileException();
						boolmap[2] = true;

						tmpDirector = keys[1].trim();
						break;

					case "Cast":
						if (!boolmap[2])
							throw new MovidaFileException();
						boolmap[3] = true;

						tmpCast = keys[1].trim();
						break;

					/* Last case (see Movida file format): inserting a new element in the active
					   dictionary. */
					case "Votes":
						if (!boolmap[3])
							throw new MovidaFileException();
						for (int i = 0; boolmap.length > i; ++i)
							boolmap[i] = false;

						// Decoding data from String to specific data types.
						Integer year = Integer.decode(tmpYear);
						Integer votes = Integer.decode(keys[1].trim());
						Person[] cast = parseCast(tmpCast);
						Person director = new Person(tmpDirector);
						Movie movie = new Movie(title, year, votes, cast, director);

						// Overwriting of Movie in the active dictionary.
						doOn(DictionaryOperation.DELETE, KeyType.MOVIE, title, null);
						doOn(DictionaryOperation.INSERT, KeyType.MOVIE, title, movie);
						break;

					default:
						throw new MovidaFileException();
				}
			}
		}

		Movie[] movies;

		switch (this.dictionary) {
			case HashIndirizzamentoAperto:
				movies = (Movie[]) this.tableMovie.toArray();
				break;

			case ABR:
				movies = (Movie[]) this.treeMovie.toArray();
				break;

			default:
				throw new MovidaFileException();
		}

		if (null == movies)
			return;

		// List to populate arrays of arrayData, in order to keep track of data about movies.
		LinkedList<PairIntMovie> listYears = new LinkedList<PairIntMovie>();
		LinkedList<PairIntMovie> listVotes = new LinkedList<PairIntMovie>();
		LinkedList<PairPersonMovie> listDirectors = new LinkedList<PairPersonMovie>();
		LinkedList<PairPersonMovie> listActors = new LinkedList<PairPersonMovie>();

		/* Inside this 'for' loop, the dictionary for Person is populated and the arrayData
		   arrays are filled with data about every Movie in the dictionary as well. */
		for (int i = 0; movies.length > i; ++i) {
			Person[] people = movies[i].getCast();

			for (int j = 0; people.length > j; ++j) {
				String actorName = people[j].getName();

				if (null == doOn(DictionaryOperation.SEARCH, KeyType.PERSON, actorName, null))
					doOn(DictionaryOperation.INSERT, KeyType.PERSON, actorName, null);

				PairPersonMovie actorMovie = new PairPersonMovie(people[j], movies[i]);
				listActors.add(actorMovie);
			}

			String directorName = movies[i].getDirector().getName();
			if (null == doOn(DictionaryOperation.SEARCH, KeyType.PERSON, directorName, null))
				doOn(DictionaryOperation.INSERT, KeyType.PERSON, directorName, null);

			PairPersonMovie directorMovie = new PairPersonMovie(movies[i].getDirector(), movies[i]);
			PairIntMovie yearMovie = new PairIntMovie(movies[i].getYear(), movies[i]);
			PairIntMovie votesMovie = new PairIntMovie(movies[i].getVotes(), movies[i]);
			listDirectors.add(directorMovie);
			listYears.add(yearMovie);
			listVotes.add(votesMovie);
		}

		/* These two arrays are temporary used to pass it to toArray(T[]) function, in order
		   to specify the type of the arrays in which the lists have to be converted */
		PairIntMovie[] typeArr = new PairIntMovie[1];
		PairPersonMovie[] typeArr1 = new PairPersonMovie[1];
		this.arrayData[0] = new Vector<PairIntMovie>(listYears.toArray(typeArr));
		this.arrayData[1] = new Vector<PairIntMovie>(listVotes.toArray(typeArr));
		this.arrayData[2] = new Vector<PairPersonMovie>(listDirectors.toArray(typeArr1));
		this.arrayData[3] = new Vector<PairPersonMovie>(listActors.toArray(typeArr1));
	}

	private byte[] movieToBytes(Movie movie) {
		Person[] cast = movie.getCast();

		String data = "Title: " + movie.getTitle() + "\n";
		data += "Year: " + movie.getYear().toString() + "\n";
		data += "Director: " + movie.getDirector().getName() + "\n";
		for (int i = 0; cast.length > 0; ++i) {
			if (0 != i)
				data += ", ";

			data += cast[i].getName();
		}
		data += "\n";
		data += "Votes: " + movie.getVotes().toString() + "\n";
		data += "\n";

		return data.getBytes();
	}

	public void saveToFile(File f) {
		Movie[] movies;

		switch (this.dictionary) {
			case ABR:
				if (null == this.tableMovie)
					throw new MovidaFileException();
				else
					movies = (Movie[]) this.tableMovie.toArray();
				break;

			case HashIndirizzamentoAperto:
				if (null == this.treeMovie)
					throw new MovidaFileException();
				else
					movies = (Movie[]) this.treeMovie.toArray();
				break;

			default:
				throw new MovidaFileException();
		}

		if (null == f)
			throw new MovidaFileException();

		Path file = f.toPath();

		if (!Files.isReadable(file) || !Files.isWritable(file))
			throw new MovidaFileException();

		if (null == movies)
			return;

		try {
			for (int i = 0; movies.length < i; ++i) 
				Files.write(file, movieToBytes(movies[i]), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException | UnsupportedOperationException exception) {
			throw new MovidaFileException();
		}
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
