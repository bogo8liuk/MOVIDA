/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;

import java.io.File;

/**
 * 
 * Interfaccia usata per descrivere le operazioni di 
 * caricamento dei dati nell'applicazione Movida
 * 
 */
public interface IMovidaDB {
	
	/**
	 * Carica i dati da un file, organizzato secondo il formato MOVIDA (vedi esempio-formato-dati.txt)
	 * 
	 * Un film è identificato in modo univoco dal titolo (case-insensitive), una persona dal nome (case-insensitive). 
	 * Semplificazione: non sono gestiti omonimi e film con lo stesso titolo.
	 * 
	 * I nuovi dati sono aggiunti a quelli già caricati.
	 * 
	 * Se esiste un film con lo stesso titolo il record viene sovrascritto.
	 * Se esiste una persona con lo stesso nome non ne viene creata un'altra.
	 * 
	 * Se il file non rispetta il formato, i dati non sono caricati e 
	 * viene sollevata un'eccezione.
	 * 
	 * @param f il file da cui caricare i dati
	 * 
	 * @throws MovidaFileException in caso di errore di caricamento
	 */
	public void loadFromFile(File f);

	/**
	 * Salva tutti i dati su un file. 
	 * 
	 * Il file è sovrascritto.
	 * Se non è possibile salvare, ad esempio per un problema di permessi o percorsi,
	 * viene sollevata un'eccezione.
	 * 
	 * @param f il file su cui salvare i dati
	 * 
	 * @throws MovidaFileException in caso di errore di salvataggio
	 */
	public void saveToFile(File f);
	
	/**
	 * Cancella tutti i dati.
	 * 
	 * Sarà quindi necessario caricarne altri per proseguire.
	 */
	public void clear();
	
	/**
	 * Restituisce il numero di film
	 * 
	 * @return numero di film totali
	 */
	public int countMovies();

	/**
	 * Restituisce il numero di persone 
	 * 
	 * @return numero di persone totali
	 */
	public int countPeople();
	
	/**
	 * Cancella il film con un dato titolo, se esiste.
	 * 
	 * @param title titolo del film
	 * @return <code>true</code> se il film è stato trovato e cancellato, 
	 * 		   <code>false</code> in caso contrario
	 */
	public boolean deleteMovieByTitle(String title);
	
	/**
	 * Restituisce il record associato ad un film
	 *  
	 * @param title il titolo del film
	 * @return record associato ad un film
	 */
	public Movie getMovieByTitle(String title);

	/**
	 * Restituisce il record associato ad una persona, attore o regista
	 * 
	 * @param name il nome della persona
	 * @return record associato ad una persona
	 */
	public Person getPersonByName(String name);
	
	
	/**
	 * Restituisce il vettore di tutti i film
	 * 
	 * @return array di film
	 */
	public Movie[] getAllMovies();

	/**
	 * Restituisce il vettore di tutte le persone
	 *  
	 * @return array di persone
	 */
	public Person[] getAllPeople();

}