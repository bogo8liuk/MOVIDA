/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;

/**
 * 
 * Interfaccia usata per descrivere le operazioni di configurazione
 * dell'applicazione Movida.
 * 
 * Permette di selezionare l'implementazione del dizionario e l'algoritmo di 
 * ordinamento da usare per le successive operazioni di ricerca.
 * Le possibili opzioni sono nelle enumeration MapImplementation e 
 * SortingAlgorithm. 
 * 
 */
public interface IMovidaConfig {

	/**
	 * Seleziona l'algoritmo di ordinamento.
	 * Se l'algortimo scelto non è supportato dall'applicazione
	 * la configurazione non cambia 
	 * 
	 * @param a l'algoritmo da selezionare
	 * @return <code>true</code> se la configurazione è stata modificata, <code>false</code> in caso contrario
	 */
	public boolean setSort(SortingAlgorithm a);

	/**
	 * Seleziona l'implementazione del dizionario 
	 * 
	 * Se il dizionario scelto non è supportato dall'applicazione
	 * la configurazione non cambia 
	 *
	 * @param m l'implementazione da selezionare
	 * @return <code>true</code> se la configurazione è stata modificata, <code>false</code> in caso contrario
	 */
	public boolean setMap(MapImplementation m);
}
