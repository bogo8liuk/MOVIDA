package movida.borghicremona;

import java.io.File;
import java.io.IOException;
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
	}

	private MapImplementation dictionary;
	private SortingAlgorithm algorithm;
	// Arrays of dictionaries to differentiate the types of keys.
	private HashMap tableMovie;
	private HashMap tablePerson;
	private BinarySearchTree treeMovie;
	private BinarySearchTree treePerson;
	private Vector[] arrayData;
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
		Person[] cast = new Person[(splitting.length / 2) + 1];

		int j = 0;
		for (int i = 0; splitting.length > i; ++i) {
			if (", " == splitting[i])
				continue;

			cast[j++] = new Person(splitting[i]);
		}

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

		// List to populate arrays of arrayData, in order to keep track of data about movies.
		LinkedList<PairIntMovie> listYears = new LinkedList<PairIntMovie>();
		LinkedList<PairIntMovie> listVotes = new LinkedList<PairIntMovie>();
		LinkedList<PairPersonMovie> listDirectors = new LinkedList<PairPersonMovie>();
		LinkedList<PairPersonMovie> listActors = new LinkedList<PairPersonMovie>();

		// Parsing every line.
		while (iter.hasNext()) {
			if (iter.next().contains(": ")) {
				// Splitting the line into three parts, discarding colon character.
				String[] keys = iter.next().split(": ");

				if (3 != keys.length)
					throw new MovidaFileException();

				switch (keys[0]) {
					case "Title":
						boolmap[0] = true;

						title = keys[2];
						break;

					case "Year":
						if (!boolmap[0])
							throw new MovidaFileException();
						boolmap[1] = true;

						tmpYear = keys[2];
						break;

					case "Director":
						if (!boolmap[1])
							throw new MovidaFileException();
						boolmap[2] = true;

						tmpDirector = keys[2];
						break;

					case "Cast":
						if (!boolmap[2])
							throw new MovidaFileException();
						boolmap[3] = true;

						tmpCast = keys[2];
						break;

					/* Last case (see Movida file format): inserting a new element in the active
					   dictionary. */
					case "Votes":
						if (!boolmap[3])
							throw new MovidaFileException();
						for (int i = 0; boolmap.length > i; ++i)
							boolmap[i] = false;

						Integer year = Integer.decode(tmpYear);
						Integer votes = Integer.decode(keys[2]);
						Person[] cast = parseCast(tmpCast);
						Person director = new Person(tmpDirector);
						Movie movie = new Movie(title, year, votes, cast, director);

						Movie tmpMovie = (Movie) doOn(DictionaryOperation.DELETE, KeyType.MOVIE, title, null);
						doOn(DictionaryOperation.INSERT, KeyType.MOVIE, title, movie);
						doOn(DictionaryOperation.INSERT, KeyType.PERSON, director.getName(), director);
						for (int i = 0; cast.length > i; ++i)
							doOn(DictionaryOperation.INSERT, KeyType.PERSON, cast[i].getName(), cast[i]);

						if (null != tmpMovie) {
							listYears.remove(new PairIntMovie(tmpMovie.getYear(), tmpMovie));
							listVotes.remove(new PairIntMovie(tmpMovie.getVotes(), tmpMovie));
							listDirectors.remove(new PairPersonMovie(tmpMovie.getDirector(), tmpMovie));
							Person[] deletedCast = tmpMovie.getCast();
							for (int i = 0; deletedCast.length > i; ++i)
								listActors.remove(new PairPersonMovie(deletedCast[i], tmpMovie));
						}

						PairIntMovie yearMovie = new PairIntMovie(year, movie);
						listYears.add(yearMovie);
						PairIntMovie votesMovie = new PairIntMovie(votes, movie);
						listVotes.add(votesMovie);
						PairPersonMovie directorMovie = new PairPersonMovie(director, movie);
						listDirectors.add(directorMovie);
						for (int i = 0; cast.length > i; ++i) {
							PairPersonMovie actorMovie = new PairPersonMovie(cast[i], movie);
							listActors.add(actorMovie);
						}
						break;

					default:
						throw new MovidaFileException();
				}
			}
		}

		this.arrayData[0] = new Vector<PairIntMovie>((PairIntMovie[]) listYears.toArray());
		this.arrayData[1] = new Vector<PairIntMovie>((PairIntMovie[]) listVotes.toArray());
		this.arrayData[2] = new Vector<PairPersonMovie>((PairPersonMovie[]) listDirectors.toArray());
		this.arrayData[3] = new Vector<PairPersonMovie>((PairPersonMovie[]) listActors.toArray());
	}

	private Byte[] movieToBytes(Movie movie) {
		String data = "Title: " + movie.getTitle() + "\n";
		data += "Year: " + movie.getYear().toString() + "\n";
		data += "Director: " + movie.getDirector().getName() + "\n";
		//TODO: finish
		return null;
	}

	public void saveToFile(File f) {
		switch (this.dictionary) {
			case ABR:
				if (null == this.tableMovie)
					throw new MovidaFileException();
				break;

			case HashIndirizzamentoAperto:
				if (null == this.treeMovie)
					throw new MovidaFileException();
				break;
		}

		if (null == f)
			throw new MovidaFileException();

		Path file = f.toPath();

		if (!Files.isReadable(file) || !Files.isWritable(file))
			throw new MovidaFileException();
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
