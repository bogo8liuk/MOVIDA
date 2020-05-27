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
 * Interfaccia usata per descrivere le operazioni sulle
 * collaborazioni nell'applicazione Movida
 * 
 */
public interface IMovidaCollaborations {
	
	/**
	 * Identificazione delle collaborazioni 
	 * dirette di un attore  
	 * 
	 * Restituisce gli attori che hanno partecipato 
	 * ad almeno un film con l'attore 
	 * <code>actor</code> passato come parametro.
	 *  
	 * @param actor attore di cui cercare i collaboratori diretti
	 * @return array di persone
	 */
	public Person[] getDirectCollaboratorsOf(Person actor);
	
	/**
	 * Identificazione del team di un attore  
	 * 
	 * Restituisce gli attori che hanno
	 * collaborazioni dirette o indirette
	 * con l'attore <code>actor</code> passato come parametro.
	 * 
	 * Vedi slide per maggiori informazioni su collaborazioni e team.
	 *  
	 * @param actor attore di cui individuare il team
	 * @return array di persone
	 */
	public Person[] getTeamOf(Person actor);

	/**
	 * Identificazione dell'insieme di collaborazioni 
	 * caratteristiche (ICC) del team di cui un attore fa parte
	 * e che ha lo score complessivo più alto
	 * 
	 * Vedi slide per maggiori informazioni su score e ICC.
	 *  
	 * Si noti che questo metodo richiede l'invocazione 
	 * del metodo precedente <code>getTeamOf(Person actor)</code>
	 *  
	 * @param actor attore di cui individuare il team
	 * @return array di collaborazioni
	 */
	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor);

}
