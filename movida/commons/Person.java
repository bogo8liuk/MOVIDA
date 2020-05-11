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
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona è identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: <code>name</code> è usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe può essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person {

	private String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
