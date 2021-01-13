package movida.borghicremona;

import movida.commons.*;

public class MovidaCoreTest {
	public static void main(String[] args) {
		MovidaCore base = new MovidaCore();

		if (!base.setSort(SortingAlgorithm.SelectionSort)) {
			System.err.println("setSort() returning false: aborting");
			System.exit(-1);
		}

		if (!base.setMap(MapImplementation.ABR)) {
			System.err.println("setMap() returning false: aborting");
			System.exit(-1);
		}

		try {
			base.loadFromFile(new File("../commons/esempio-formato-dati.txt"));
		} catch (MovidaFileException exception) {
			System.err.println("loadFromFile() throwing MovidaFileException");
			System.exit(-1);
		}

		// TODO: saveToFile()
	}
}
