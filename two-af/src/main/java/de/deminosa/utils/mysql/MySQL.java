package de.deminosa.utils.mysql;

import java.io.File;
import java.io.IOException;

import de.deminosa.utils.JIniFile;


/*
*	Class Create by Deminosa
*	YouTube: 	Deminosa
* 	Web:	 	deminosa.de
*	Create at: 	12:04:37 # 24.05.2021
*
*/

public class MySQL{
	
	private AsyncMySQL mysql;

	public MySQL(String dataFolder) {
		conMySQL(dataFolder);
	}

	private void conMySQL(String datafolder) {
		File dir = new File(datafolder);
		File file = new File(datafolder+"/MySQL.ini");
		JIniFile config = new JIniFile(file);

		if(!file.exists()){
			dir.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			config.setString("connection", "host", "localhost");
			config.setString("connection", "user", "user");
			config.setString("connection", "pass", "password");
			config.setString("connection", "data", "database");
			config.UpdateFile();
		}
		
		mysql = new AsyncMySQL( 
			config.ReadString("connection", "host", "database"),
			config.ReadString("connection", "user", "database"),
			config.ReadString("connection", "pass", "database"),
			config.ReadString("connection", "data", "database"));
	}
	
	public AsyncMySQL getAsyncMySQL() {
		return mysql;
	}
}