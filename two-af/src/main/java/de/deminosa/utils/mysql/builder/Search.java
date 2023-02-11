package de.deminosa.utils.mysql.builder;

/*
*	Class Create by Deminosa
*	YouTube: 	Deminosa
* 	Web:	 	deminosa.de
*	Create at: 	13:46:43 # 21.04.2022
*
*/

public class Search {

	private final String where, match;
	
	public Search(String where, String match) {
		this.match = match;
		this.where = where;
	}

	public String getWhere() {
		return where;
	}

	public String getMatch() {
		return match;
	}
	
}