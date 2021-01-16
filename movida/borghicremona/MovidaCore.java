package movida.borghicremona;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.*;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.lang.RuntimeException;
import movida.commons.*;
import movida.borghicremona.hashmap.HashMap;
import movida.borghicremona.bstree.BinarySearchTree;
import movida.borghicremona.graph.*;
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

	// Variables to index arrays of arrayData (see below, arrayData is an attribute).
	private final static int YEARS = 0;
	private final static int VOTES = 1;
	private final static int DIRECTORS = 2;
	private final static int ACTORS = 3;

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

	/* Dictionaries for Movie and Person, only one for type (Movie and Person)
	   has to be active */
	private HashMap tableMovie;
	private HashMap tablePerson;
	private BinarySearchTree treeMovie;
	private BinarySearchTree treePerson;

	// Arrays to keep track of data about movies
	private Vector[] arrayData;

	// It keeps track of actors that attended in movies where they acted alone.
	private HashMap aloneActors;

	// Graph to mantain the collaborations between actors in same movies.
	private NonOrientedGraph collaborations;

	public MovidaCore() {
		this.tableMovie = null;
		this.tablePerson = null;
		this.treeMovie = null;
		this.treePerson = null;
		this.arrayData = null;
		this.aloneActors = new HashMap();
		this.collaborations = new NonOrientedGraph();
		this.dictionary = null;
		this.algorithm = null;
	}

	public boolean setSort(SortingAlgorithm algorithm) {
		if (null == algorithm) {
			System.err.println("Invalid map value: aborting");
			System.exit(-1);
		}

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

	public boolean setMap(MapImplementation map) {
		if (null == map) {
			System.err.println("Invalid map value: aborting");
			System.exit(-1);
		}

		switch (map) {
			case ABR:
				this.dictionary = map;
				break;

			case HashIndirizzamentoAperto:
				this.dictionary = map;
				break;

			default:
				return false;
		}

		return true;
	}

	public MovidaCore(SortingAlgorithm algorithm, MapImplementation map) {
		if (!this.setSort(algorithm) || !this.setMap(map)) {
			System.err.println("Impossible to allocate a MovidaCore instance: aborting");
			System.exit(-1);
		}

		this.tableMovie = null;
		this.tablePerson = null;
		this.treeMovie = null;
		this.treePerson = null;
		this.arrayData = null;
		this.aloneActors = new HashMap();
		this.collaborations = new NonOrientedGraph();
	}

	/**
	 * It performs one of the dictionary operations, characterizing the type of key (Title,
	 * Year, Director, Cast or Votes), on a specific type of map.
	 *
	 * @param op The operation to carry out (insert, search or delete).
	 * @param type The type of the key according to Movida file format (Title, Year, Director,
	 * Cast or Votes).
	 * @param map The map where performing the operation.
	 * @param key The key to pass as argument to dictionary operation.
	 * @param data Data to eventually map with key param in case of insert operation.
	 *
	 * @return The object returned by search operation or delete operation, if op is equal to
	 * SEARCH or DELETE, null otherwise.
	 */
	private Object doOn(DictionaryOperation op, KeyType type, MapImplementation map, Comparable key, Object data) {
		KeyValueElement element = new KeyValueElement(key, data);

		switch (map) {
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

	/**
	 * It performs toArray() function of the active dictionary implementation, according
	 * to the requested type of the key (Movie or Person).
	 *
	 * @param type The KeyType that characterizes the type of the array to return.
	 *
	 * @return The array returned by toArray() function.
	 *
	 * @attention The existence of an active dictionary istance is unchecked runtime error.
	 *
	 * @throws RuntimeException In case of there's no active dictionary or the type of
	 * the key is invalid.
	 */
	private Object[] getArray(KeyType type) throws RuntimeException {
		switch (this.dictionary) {
			case HashIndirizzamentoAperto:
				switch (type) {
					case MOVIE:
						return this.tableMovie.toArray();

					case PERSON:
						return this.tablePerson.toArray();

					default:
						throw new RuntimeException();
				}

			case ABR:
				switch (type) {
					case MOVIE:
						return this.treeMovie.toArray();

					case PERSON:
						return this.treePerson.toArray();

					default:
						throw new RuntimeException();
				}

			default:
				throw new RuntimeException();
		}
	}

	/**
	 * It turns a string representing the set of movie cast according to Movida file
	 * format into an array of Person.
	 *
	 * @param tmpCast The string representing the cast.
	 *
	 * @return An array of Person containing all the people inside tmpCast parameter.
	 */
	private static Person[] parseCast(String tmpCast) {
		// Parsing Cast keys because of many Person (people).
		String[] splitting = tmpCast.split(",");
		Person[] cast = new Person[splitting.length];

		for (int i = 0; splitting.length > i; ++i)
			cast[i] = new Person(splitting[i].trim());

		return cast;
	}

	/**
	 * It inserts nodes corresponding to the people of a cast and archs between these people in the Graph
	 * instance.
	 *
	 * @param cast Elements to insert.
	 */
	private void insertCollaboration(Person[] cast) {
		// First, insertion of every actors.
		for (int i = 0; cast.length > i; ++i)
			this.collaborations.addNode(cast[i].getName());

		// Second, insertion of collaborations between every actor.
		for (int i = 0; cast.length - 1 > i; ++i) {
			for (int j = i + 1; cast.length > j; ++j)
				this.collaborations.addArch(cast[i].getName(), cast[j].getName());
		}
	}

	/**
	 * It removes from the Graph instance the archs between the people in a cast and if a node has no more
	 * incident archs after the previous operation, then the node is removed as well.
	 *
	 * @param cast The people from which removing the archs.
	 *
	 * @return The nodes that have been removed.
	 */
	private String[] deleteCollaboration(Person[] cast) {
		String[] names = new String[cast.length];
		LinkedList<String> removedNodes = new LinkedList<String>();

		for (int i = 0; cast.length > i; ++i)
			names[i] = cast[i].getName();

		if (1 == names.length) {
			Integer occurrences = (Integer) this.aloneActors.search(names[0]);
			occurrences -= 1;
		}

		for (int i = 0; names.length - 1 > i; ++i) {
			for (int j = i + 1; names.length > j; ++j) {
				Arch arch = new Arch(names[i], names[j]);

				this.collaborations.removeArch(arch);
			}

			Object occurrences = (Object) this.aloneActors.search(names[i]);

			/* Removal of the node: it can be done only if the node representing the i-th actor has no adjacent nodes and the actor
			   did not act in movie as alone actor. */
			if (0 == this.collaborations.grade(names[i]) && null == occurrences) {
				this.collaborations.removeNode(names[i]);
				removedNodes.add(names[i]);
			}
		}

		// Removal of the last node: in the previous loop, the last node is not checked.
		String last = names[names.length - 1];
		Object occurrences = this.aloneActors.search(last);

		if (0 == this.collaborations.grade(last) && null == occurrences) {
			this.collaborations.removeNode(last);
			removedNodes.add(last);
		}

		String[] arrayType = new String[1];
		return (0 == removedNodes.size()) ? null : removedNodes.toArray(arrayType);
	}

	public void loadFromFile(File f) throws MovidaFileException {
		if (null == f)
			throw new MovidaFileException();

		this.tableMovie = new HashMap();
		this.treeMovie = new BinarySearchTree();
		this.tablePerson = new HashMap();
		this.treePerson = new BinarySearchTree();

		// Instantiation of arrayData, namely the arrays containing data about movies.
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
						if (boolmap[0] || boolmap[1] || boolmap[2] || boolmap[3])
							throw new MovidaFileException();
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
						this.doOn(DictionaryOperation.DELETE, KeyType.MOVIE, MapImplementation.HashIndirizzamentoAperto, title, null);
						this.doOn(DictionaryOperation.INSERT, KeyType.MOVIE, MapImplementation.HashIndirizzamentoAperto, title, movie);
						this.doOn(DictionaryOperation.DELETE, KeyType.MOVIE, MapImplementation.ABR, title, null);
						this.doOn(DictionaryOperation.INSERT, KeyType.MOVIE, MapImplementation.ABR, title, movie);

						break;

					default:
						throw new MovidaFileException();
				}
			}
		}

		Object[] array;

		try {
			array = this.getArray(KeyType.MOVIE);
		} catch (RuntimeException exception) {
			throw new MovidaFileException();
		}

		if (null == array)
			return;

		Movie[] movies = new Movie[array.length];

		for (int i = 0; array.length > i; ++i)
			movies[i] = (Movie) array[i];

		// List to populate arrays of arrayData, in order to keep track of data about movies.
		LinkedList<PairIntMovie> listYears = new LinkedList<PairIntMovie>();
		LinkedList<PairIntMovie> listVotes = new LinkedList<PairIntMovie>();
		LinkedList<PairPersonMovie> listDirectors = new LinkedList<PairPersonMovie>();
		LinkedList<PairPersonMovie> listActors = new LinkedList<PairPersonMovie>();

		/* Inside this 'for' loop, the dictionary for Person is populated and the arrayData
		   arrays are filled with data about every Movie in the dictionary as well. */
		for (int i = 0; movies.length > i; ++i) {
			Person[] people = movies[i].getCast();

			// If the actor acted alone.
			if (1 == people.length) {
				String name = people[0].getName();
				Object res = this.aloneActors.search(name);

				// If the actor never acted alone before.
				if (null == res) {
					KeyValueElement item = new KeyValueElement(name, new Integer(1));
					this.aloneActors.insert(item);
				}

				else {
					Integer occurrences = (Integer) res;
					occurrences += 1;
				}
			}

			// Cast arranging.
			for (int j = 0; people.length > j; ++j) {
				String actorName = people[j].getName();

				if (null == this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, actorName, null)) {
					this.doOn(DictionaryOperation.INSERT, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, actorName, people[j]);
					this.doOn(DictionaryOperation.INSERT, KeyType.PERSON, MapImplementation.ABR, actorName, people[j]);
				}

				PairPersonMovie actorMovie = new PairPersonMovie(people[j], movies[i]);
				listActors.add(actorMovie);
			}
			// Insertion of collaborations between every actor in the cast.
			this.insertCollaboration(people);

			// Director arranging.
			Person director = movies[i].getDirector();
			String directorName = director.getName();
			if (null == this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, directorName, null)) {
				this.doOn(DictionaryOperation.INSERT, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, directorName, director);
				this.doOn(DictionaryOperation.INSERT, KeyType.PERSON, MapImplementation.ABR, directorName, director);
			}

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
		this.arrayData[YEARS] = new Vector<PairIntMovie>(listYears.toArray(typeArr));
		this.arrayData[VOTES] = new Vector<PairIntMovie>(listVotes.toArray(typeArr));
		this.arrayData[DIRECTORS] = new Vector<PairPersonMovie>(listDirectors.toArray(typeArr1));
		this.arrayData[ACTORS] = new Vector<PairPersonMovie>(listActors.toArray(typeArr1));
	}

	/**
	 * It checks the existence of a dictionary istance for Movie or for Person.
	 *
	 * @param type The key type of the dictionary to check (Movie or Person).
	 *
	 * @return true If there is an active dictionary istance, false otherwise.
	 *
	 * @attention The type of the active dictionary is a checked runtime error.
	 */
	private boolean movieDictIstanceExist(KeyType type) {
		if (null == this.dictionary)
			return false;
		else {
			switch (type) {
				case MOVIE:
					/* It can check only with a dictionary, because if a dictionary is active also the other one
					   is active. */
					return !(null == this.tableMovie);

				case PERSON:
					return !(null == this.tablePerson);
			}
		}

		// Unreachable: to quiet the compiler.
		return false;
	}

	/**
	 * It turns the data of a Movie into an array of bytes according to Movida file format.
	 *
	 * @param movie The Movie object to convert.
	 *
	 * @return An array of bytes containing all the data about the movie parameter.
	 */
	private static byte[] movieToBytes(Movie movie) {
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

	public void saveToFile(File f) throws MovidaFileException {
		if (!this.movieDictIstanceExist(KeyType.MOVIE))
			throw new MovidaFileException();

		Object[] array;

		try {
			array = this.getArray(KeyType.MOVIE);
		} catch(RuntimeException exception) {
			throw new MovidaFileException();
		}

		if (null == f)
			throw new MovidaFileException();

		Path file = f.toPath();

		// I need to write to the file.
		if (!Files.isWritable(file))
			throw new MovidaFileException();

		// If there's no Movie, then write simply a newline, cancelling all the previous lines.
		if (null == array) {
			try {
				Files.write(file, "\n".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
				return;
			} catch (IOException | UnsupportedOperationException exception) {
				throw new MovidaFileException();
			}
		}

		// Lines are overwrited.
		else {
			Movie[] movies = new Movie[array.length];

			for (int i = 0; array.length > i; ++i)
				movies[i] = (Movie) array[i];

			try {
				for (int i = 0; movies.length < i; ++i) 
					Files.write(file, movieToBytes(movies[i]), StandardOpenOption.TRUNCATE_EXISTING);
			} catch (IOException | UnsupportedOperationException exception) {
				throw new MovidaFileException();
			}
		}
	}

	public void clear() {
		this.tableMovie = null;
		this.treeMovie = null;
		this.tablePerson = null;
		this.treePerson = null;
		this.arrayData = null;
		this.aloneActors = new HashMap();
		this.collaborations = new NonOrientedGraph();
	}

	public int countMovies() {
		if (!this.movieDictIstanceExist(KeyType.MOVIE)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		try {
			return this.getArray(KeyType.MOVIE).length;
		} catch (RuntimeException exception) {
			System.err.println("Invalid set dictionary: aborting");
			System.exit(-1);
		}

		// Unreachable: to quiet the compiler.
		return -1;
	}

	public int countPeople() {
		if (!this.movieDictIstanceExist(KeyType.PERSON)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		try {
			return this.getArray(KeyType.PERSON).length;
		} catch (RuntimeException exception) {
			System.err.println("Invalid set dictionary: aborting");
			System.exit(-1);
		}

		// Unreachable.
		return -1;
	}

	/**
	 * It deletes from arrayData the elements that keep a certain movie with them.
	 *
	 * @param movie The movie from which deleting elements.
	 *
	 * @attention The movie has to be a just deleted movie, otherwise a call to this function may lead
	 * to an inconsistent state of MovidaCore.
	 */
	private void updateDeletedData(Movie movie) {
		LinkedList<PairIntMovie> listYears = new LinkedList<PairIntMovie>();
		LinkedList<PairIntMovie> listVotes = new LinkedList<PairIntMovie>();
		LinkedList<PairPersonMovie> listActors = new LinkedList<PairPersonMovie>();
		LinkedList<PairPersonMovie> listDirectors = new LinkedList<PairPersonMovie>();

		PairIntMovie[] arrayYears = (PairIntMovie[]) this.arrayData[YEARS].getArray();
		PairIntMovie[] arrayVotes = (PairIntMovie[]) this.arrayData[VOTES].getArray();
		PairPersonMovie[] arrayActors = (PairPersonMovie[]) this.arrayData[ACTORS].getArray();
		PairPersonMovie[] arrayDirectors = (PairPersonMovie[]) this.arrayData[DIRECTORS].getArray();

		// Discarding elements that have movie as associated data.
		for (int i = 0; arrayYears.length > i; ++i) {
			if (movie != arrayYears[i].getMovie())
				listYears.add(arrayYears[i]);
		}

		for (int i = 0; arrayVotes.length > i; ++i) {
			if (movie != arrayVotes[i].getMovie())
				listVotes.add(arrayVotes[i]);
		}

		for (int i = 0; arrayActors.length > i; ++i) {
			if (movie != arrayActors[i].getMovie())
				listActors.add(arrayActors[i]);
		}

		for (int i = 0; arrayDirectors.length > i; ++i) {
			if (movie != arrayDirectors[i].getMovie())
				listDirectors.add(arrayDirectors[i]);
		}

		// Assigning vectors in arrayData.
		PairIntMovie[] arrayTypeInt = new PairIntMovie[1];
		this.arrayData[YEARS] = (0 == listYears.size()) ? null : new Vector<PairIntMovie>(listYears.toArray(arrayTypeInt));

		this.arrayData[VOTES] = (0 == listVotes.size()) ? null : new Vector<PairIntMovie>(listVotes.toArray(arrayTypeInt));

		PairPersonMovie[] arrayTypePerson = new PairPersonMovie[1];
		this.arrayData[ACTORS] = (0 == listActors.size()) ? null : new Vector<PairPersonMovie>(listActors.toArray(arrayTypePerson));

		this.arrayData[DIRECTORS] = (0 == listDirectors.size()) ? null : new Vector<PairPersonMovie>(listDirectors.toArray(arrayTypePerson));
	}

	/**
	 * It deletes the director from the dictionaries, if the director does not stand in arrayData.
	 *
	 * @param director The Person object to delete.
	 */
	private void deleteDirector(Person director) {
		// If the vector of directors in arrayData is null, then the director has to be removed.
		if (null == this.arrayData[DIRECTORS]) {
			this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, director.getName(), null);
			this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.ABR, director.getName(), null);
		}

		PairPersonMovie[] directors = (PairPersonMovie[]) this.arrayData[DIRECTORS].getArray();

		boolean found = false;
		for (int i = 0; directors.length > i; ++i) {
			if (director == directors[i].getPerson()) {
				found = true;
				break;
			}
		}

		if (!found) {
			this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, director.getName(), null);
			this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.ABR, director.getName(), null);
		}
	}

	public boolean deleteMovieByTitle(String title) {
		if (!this.movieDictIstanceExist(KeyType.MOVIE)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == title)
			return false;

		// An operation of edit on a dictionary has to be performed also on the other dictionary.
		Movie movie = (Movie) this.doOn(DictionaryOperation.DELETE, KeyType.MOVIE, MapImplementation.HashIndirizzamentoAperto, title, null);
		this.doOn(DictionaryOperation.DELETE, KeyType.MOVIE, MapImplementation.ABR, title, null);

		if (null == movie)
			return false;
		else {
			KeyValueElement item = new KeyValueElement(title, null);

			// Removal of actors that have no more collaborations with other actors.
			String[] removedNodes = this.deleteCollaboration(movie.getCast());

			if (null != removedNodes) {
				for (int i = 0; removedNodes.length > i; ++i) {
					this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.HashIndirizzamentoAperto, removedNodes[i], null);
					this.doOn(DictionaryOperation.DELETE, KeyType.PERSON, MapImplementation.ABR, removedNodes[i], null);
				}
			}

			// Updating data in arrayData.
			this.updateDeletedData(movie);
			// If the director did not direct another movie, he has to be removed.
			this.deleteDirector(movie.getDirector());

			return true;
		}
	}

	public Movie getMovieByTitle(String title) {
		if (!this.movieDictIstanceExist(KeyType.MOVIE)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == title)
			return null;

		return (Movie) this.doOn(DictionaryOperation.SEARCH, KeyType.MOVIE, this.dictionary, title, null);
	}

	public Person getPersonByName(String name) {
		if (!this.movieDictIstanceExist(KeyType.PERSON)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == name)
			return null;

		return (Person) this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, this.dictionary, name, null);
	}

	public Movie[] getAllMovies() {
		if (!this.movieDictIstanceExist(KeyType.MOVIE)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		Object[] array = null;

		try {
			array = this.getArray(KeyType.MOVIE);
		} catch (RuntimeException exception) {
			System.err.println("Invalid set dictionary: aborting");
			System.exit(-1);
		}

		if (null == array)
			return null;

		Movie[] res = new Movie[array.length];

		for (int i = 0; array.length > i; ++i)
			res[i] = (Movie) array[i];

		return res;
	}

	public Person[] getAllPeople() {
		if (!this.movieDictIstanceExist(KeyType.PERSON)) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		Object[] array = null;

		try {
			array = this.getArray(KeyType.PERSON);
		} catch (RuntimeException exception) {
			System.err.println("Invalid set dictionary: aborting");
			System.exit(-1);
		}

		if (null == array)
			return null;

		Person[] res = new Person[array.length];

		for (int i = 0; array.length > i; ++i)
			res[i] = (Person) array[i];

		return res;
	}

	public Movie[] searchMoviesByTitle(String title) {
		if (null == title)
			return null;

		// No duplicate is allowed, so the array length is 1.
		Movie[] movies = new Movie[1];

		movies[0] = this.getMovieByTitle(title);
		return movies;
	}

	/**
	 * It sorts one of the arrays in arrayData, according to a given type of attribute
	 * of a Movie (year, votes, director, cast).
	 *
	 * @param arrayIndex It tells the array to sort.
	 *
	 * @attention The correctness of arrayIndex is a checked runtime error.
	 */
	private void sort(Integer arrayIndex) {
		if (YEARS != arrayIndex && VOTES != arrayIndex && DIRECTORS != arrayIndex && ACTORS != arrayIndex) {
			System.err.println("sort() default case: aborting");
			System.exit(-1);
		}

		switch (this.algorithm) {
			case SelectionSort:
				this.arrayData[arrayIndex].selectionSort();
				break;

			case QuickSort:
				this.arrayData[arrayIndex].quickSort();
				break;

			default:
				System.err.println("sort() default case: aborting");
				System.exit(-1);
		}
	}

	public Movie[] searchMoviesInYear(Integer year) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == year || null == this.arrayData[YEARS])
			return null;

		LinkedList<Movie> list = new LinkedList<Movie>();
		PairIntMovie[] years = (PairIntMovie[]) this.arrayData[YEARS].getArray();

		/* Insertion of elements with the year equal to the parameter from the array of
		   arrayData to the list. */
		for (int i = 0; years.length > i; ++i) {
			if (0 == years[i].getIndex().compareTo(year))
				list.add(years[i].getMovie());
		}

		// Conversion of the list in an array.
		Movie[] arrayType = new Movie[1];
		return (0 == list.size()) ? null : list.toArray(arrayType);
	}

	public Movie[] searchMoviesDirectedBy(String name) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == name || null == this.arrayData[DIRECTORS])
			return null;

		LinkedList<Movie> list = new LinkedList<Movie>();
		PairPersonMovie[] directors = (PairPersonMovie[]) this.arrayData[DIRECTORS].getArray();

		/* Insertion of elements with the name equal to the parameter from the array of
		   arrayData to the list. */
		for (int i = 0; directors.length > i; ++i) {
			if (0 == directors[i].getPerson().getName().compareTo(name))
				list.add(directors[i].getMovie());
		}

		// Conversion of the list in an array.
		Movie[] arrayType = new Movie[1];
		return (0 == list.size()) ? null : list.toArray(arrayType);
	}

	public Movie[] searchMoviesStarredBy(String name) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == name || null == this.arrayData[ACTORS])
			return null;

		LinkedList<Movie> list = new LinkedList<Movie>();
		PairPersonMovie[] actors = (PairPersonMovie[]) this.arrayData[ACTORS].getArray();

		/* Insertion of elements with the name equal to the parameter from the array of
		   arrayData to the list. */
		for (int i = 0; actors.length > i; ++i) {
			if (0 == actors[i].getPerson().getName().compareTo(name))
				list.add(actors[i].getMovie());
		}

		// Conversion of the list in an array.
		Movie[] arrayType = new Movie[1];
		return (0 == list.size()) ? null : list.toArray(arrayType);
	}

	public Movie[] searchMostVotedMovies(Integer N) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == N || 0 >= N || null == this.arrayData[VOTES])
			return null;

		// Sorting the array containing data about votes of the movies.
		this.sort(VOTES);

		/* If N is greater than the number of movies, then length of the returned array must be
		   equal to the number of movies. */
		PairIntMovie[] votes = (PairIntMovie[]) this.arrayData[VOTES].getArray();
		Integer length = (N <= votes.length) ? N : votes.length;
		Movie[] mostVotedMovies = new Movie[length];

		// Taking the last N movies, because of the hypothesis of the sorted array.
		for (Integer i = length - 1; 0 < i; --i)
			mostVotedMovies[i] = votes[i].getMovie();

		return mostVotedMovies;
	}

	public Movie[] searchMostRecentMovies(Integer N) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == N || 0 >= N || null == this.arrayData[YEARS])
			return null;

		// Sorting the array containing data about years of the movies.
		this.sort(YEARS);

		/* If N is greater than the number of movies, then length of the returned array must be
		   equal to the number of movies. */
		PairIntMovie[] years = (PairIntMovie[]) this.arrayData[YEARS].getArray();
		Integer length = (N <= years.length) ? N : years.length;
		Movie[] mostRecentMovies = new Movie[length];

		// Taking the first N movies, because of the hypothesis of the sorted array.
		for (Integer i = 0; length > i; ++i)
			mostRecentMovies[i] = years[i].getMovie();

		return mostRecentMovies;
	}

	// To keep track the number of occurrences of a Person in a particular context.
	private class PairIntPerson implements Comparable<PairIntPerson> {
		private Integer index;
		private Person person;

		public PairIntPerson() {
			this.index = null;
			this.person = null;
		}

		public PairIntPerson(Integer i, Person p) {
			this.index = i;
			this.person = p;
		}

		public int compareTo(PairIntPerson w) {
			return this.index.compareTo(w.index);
		}

		public Integer getIndex() {
			return this.index;
		}

		public Person getPerson() {
			return this.person;
		}
	}

	public Person[] searchMostActiveActors(Integer N) {
		if (null == this.arrayData) {
			System.err.println("Dictionary not set: aborting");
			System.exit(-1);
		}

		if (null == N || 0 >= N || null == this.arrayData[ACTORS])
			return null;

		this.sort(ACTORS);

		PairPersonMovie[] actors = (PairPersonMovie[]) this.arrayData[ACTORS].getArray();
		LinkedList<PairIntPerson> list = new LinkedList<PairIntPerson>();

		/* Insertion of people in a list in order to avoid duplicates and keeping track of
		   all the occurrences of every person. */
		for (int i = 0; actors.length > i; ++i) {
			Person actor = actors[i].getPerson();

			int j = 1;
			while (actors.length > i + 1 && 0 == actors[i + 1].getPerson().getName().compareTo(actor.getName())) {
				++j;
				++i;
			}

			PairIntPerson entry = new PairIntPerson(j, actor);
			list.add(entry);
		}

		// Construction and sorting of vector with every person and all its occurrences (of the given person).
		PairIntPerson[] arrayType = new PairIntPerson[1];
		PairIntPerson[] occurrences = list.toArray(arrayType);
		Vector<PairIntPerson> v = new Vector<PairIntPerson>(occurrences);

		switch (this.algorithm) {
			case SelectionSort:
				v.selectionSort();
				break;

			case QuickSort:
				v.quickSort();
				break;

			default:
				System.err.println("Unavailable or invalid algorithm: aborting");
				System.exit(-1);
		}

		/* If N is greater than the number of people, then length of the returned array must be
		   equal to the number of people. */
		int length = (N <= occurrences.length) ? N : occurrences.length;
		Person[] mostActiveActors = new Person[length];

		// Taking the last N elements, because of the hypothesis of the sorted array.
		for (int i = 0; length > i; ++i)
			mostActiveActors[i] = occurrences[i].getPerson();

		return mostActiveActors;
	}

	public Person[] getDirectCollaboratorsOf(Person actor) {
		if (null == actor)
			return null;

		String name = actor.getName();

		// The actor taken as parameter must exist.
		if (null == this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, this.dictionary, name, null))
			return null;

		Arch[] directCollaborations = this.collaborations.incidentArchs(name);

		if (null == directCollaborations)
			return null;

		Person[] collaborators = new Person[directCollaborations.length];

		for (int i = 0; directCollaborations.length > i; ++i) {
			/* The Person instantiated from the direct collaborator of the actor taken as parameter is
			   granted to exist because each arch of directCollaborations exists. */
			String actorName = (String) this.collaborations.opposite(name, directCollaborations[i]);

			Person coactor = new Person(actorName);
			collaborators[i] = coactor;
		}

		return collaborators;
	}

	public Person[] getTeamOf(Person actor) {
		if (null == actor)
			return null;

		// The actor taken as parameter must exist.
		if (null == this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, this.dictionary, actor.getName(), null))
			return null;

		/* At this point, there's no need to do a specific operation on visited nodes, so the function
		   passed to the visit algorithm does nothing. */
		NodeOperation op = n -> {};
		Comparable[] array = this.collaborations.breadthFirstVisit(op, actor.getName());

		if (null == array)
			return null;

		Person[] team = new Person[array.length];

		for (int i = 0; array.length > i; ++i)
			team[i] = new Person((String) array[i]);

		return team;
	}

	/**
	 * It builds a collaboration from two actors by looking at the data about actors in arrayData.
	 *
	 * @param nameA The name of the first actor.
	 * @param nameB The name of the second actor.
	 *
	 * @return A collaboration between the two actors.
	 */
	private Collaboration getCollaboration(Comparable nameA, Comparable nameB) {
		this.sort(ACTORS);

		Person actorA = new Person((String) nameA);
		Person actorB = new Person((String) nameB);

		PairPersonMovie[] actors = (PairPersonMovie[]) this.arrayData[ACTORS].getArray();
		Collaboration collab = new Collaboration(actorA, actorB);

		/* Looking at the first actor having as name the value of nameA or nameB and from there calculating
		   the collaboration: the array of actors is already sorted, so the actors with nameA or nameB as
		   their name, are all positioned sequentially. */
		for (int i = 0; actors.length > i; ++i) {
			String name = actors[i].getPerson().getName();
			if (0 != actorA.getName().compareTo(name) && 0 != actorB.getName().compareTo(name))
				continue;

			else if (0 == actorA.getName().compareTo(name)) {
				// Found the actors with nameA.
				while (actors.length > i && actors[i].getPerson().getName() == name) {
					Movie movie = actors[i].getMovie();
					Person[] cast = movie.getCast();

					// Adding the movies with the actors that have nameB as their name.
					for (int j = 0; cast.length > j; ++j) {
						if (0 == actorB.getName().compareTo(cast[j].getName()))
							collab.addMovie(movie);
							break;
					}

					++i;
				}

				break;
			}

			else {
				while (actors.length > i && actors[i].getPerson().getName() == name) {
					Movie movie = actors[i].getMovie();
					Person[] cast = movie.getCast();

					for (int j = 0; cast.length > j; ++j) {
						if (0 == actorA.getName().compareTo(cast[j].getName()))
							collab.addMovie(movie);
							break;
					}

					++i;
				}

				break;
			}
		}

		return collab;
	}

	/**
	 * It gets the score of the collaboration between two actors.
	 *
	 * @param nameA The name of first actor.
	 * @param nameB The name of the second actor.
	 *
	 * @return The score of the collaboration between the two actors.
	 */
	private Double countScore(Comparable nameA, Comparable nameB) {
		return this.getCollaboration(nameA, nameB).getScore();
	}

	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
		if (null == actor)
			return null;

		// The actor taken as parameter must exist.
		if (null == this.doOn(DictionaryOperation.SEARCH, KeyType.PERSON, this.dictionary, actor.getName(), null))
			return null;

		Weight weight = (nodeA, nodeB) -> countScore(nodeA, nodeB);
		Arch[] archs = this.collaborations.spanningTree(actor.getName(), NodeFind.MAX, weight);

		if (null == archs)
			return null;

		Collaboration[] collaborations = new Collaboration[archs.length];

		for (int i = 0; archs.length > i; ++i) {
			Comparable[] edges = archs[i].getArchNodes();

			collaborations[i] = this.getCollaboration(edges[0], edges[1]);
		}

		return collaborations;
	}
}
