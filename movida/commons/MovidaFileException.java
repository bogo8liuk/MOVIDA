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
 * Eccezione generata in caso di errore in fase di 
 * caricamento o salvataggio dei dati
 *  
 */
public class MovidaFileException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Errore caricamento o salvataggio dati.";
	}
}
