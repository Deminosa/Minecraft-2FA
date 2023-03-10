package de.deminosa.utils.mysql.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.deminosa.utils.mysql.MySQL;


/*
*	Class Create by Deminosa
*	YouTube: 	Deminosa
* 	Web:	 	deminosa.de
*	Create at: 	13:19:18 # 21.04.2022
*
*/

public class Table {

	private final String tableName;
	private final MySQL mysql;
	
	public Table(String name, String dataFolder) {
		this.tableName = name;
		mysql = new MySQL(dataFolder);
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void create(Colum colum) {
		if(!checkConnection()) return;
		String s = "";
		for(String colums : colum.getMap().keySet()) {
			s = s + colums + " " + colum.getMap().get(colums).getArguments() + ",";
		}
		if(s.endsWith(",")) {
			s = s.substring(0, s.length()-1);
		}
		
		mysql.getAsyncMySQL().getMySQL().queryUpdate("CREATE TABLE IF NOT EXISTS " + getTableName() 
			+ "("+s+")");
	}
	
	public void setFirstColum(ColumValue value) {
		if(!checkConnection()) return;
		
		mysql.getAsyncMySQL().getMySQL().queryUpdate("INSERT INTO "+getTableName()+" ("
				+value.getColumName()+") VALUES ('"+value.getColumValue()+"')");
	}
	
	public void updateColum(Update update) {
		if(!checkConnection()) return;
		
		mysql.getAsyncMySQL().getMySQL().queryUpdate("UPDATE "+getTableName()
				+" SET "+update.getColum().getColumName()+"='"+update.getColum().getColumValue()
				+"' WHERE "+update.getSearch().getWhere()+"='"+update.getSearch().getMatch()+"'");
	}
	
	public boolean exsistValue(Update update) {
		if(!checkConnection()) return false;
		ResultSet rs = null;
		try{
			rs = mysql.getAsyncMySQL().getMySQL().query("SELECT * FROM "
					+getTableName()+" WHERE "+update.getSearch().getWhere()+"= '"
					+update.getSearch().getMatch()+"'");
			if (rs.next()){
				String s = rs.getString(update.getColum().getColumName());
				rs.close();
				return s != null;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public Object getValue(Update update) {
		if(!checkConnection()) return null;
		ResultSet rs = null;
		try{
			rs = mysql.getAsyncMySQL().getMySQL().query("SELECT * FROM "
					+getTableName()+" WHERE "+update.getSearch().getWhere()+"= '"
					+update.getSearch().getMatch()+"'");
			if (rs.next()){
				Object s = rs.getObject(update.getColum().getColumName());
				rs.close();
				return s;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T getValue(Update update, Class<? extends T> clazz, T t) {
		if(!checkConnection()) return null;
		ResultSet rs = null;
		try{
			rs = mysql.getAsyncMySQL().getMySQL().query("SELECT * FROM "
					+getTableName()+" WHERE "+update.getSearch().getWhere()+"= '"
					+update.getSearch().getMatch()+"'");
			if (rs.next()){
				T s = rs.getObject(update.getColum().getColumName(), clazz);
				rs.close();
				return clazz.cast(s);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return clazz.cast(t);
	}
	
	public void deletRow(Search search) {
		if(!checkConnection()) return;
		mysql.getAsyncMySQL().getMySQL().
			queryUpdate("DELETE FROM " + getTableName() + " WHERE " + search.getWhere() 
				+ " = '"+search.getMatch()+"'");
	}
	
	public ArrayList<String> getArrayList(Update update){
		if(!checkConnection()) return null;
		
		ArrayList<String> list = new ArrayList<>();

		ResultSet rs = null;
		try {
			rs = mysql.getAsyncMySQL().getMySQL().query("SELECT * FROM "+getTableName()+
					" WHERE "+update.getSearch().getWhere()+"= '"
					+update.getSearch().getMatch()+"'");
			while (rs.next()) {
				list.add(rs.getString(update.getColum().getColumName()));
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getArrayList(ColumValue value){
		if(!checkConnection()) return null;
		
		ArrayList<String> list = new ArrayList<>();

		ResultSet rs = null;
		try {
			rs = mysql.getAsyncMySQL().getMySQL().query("SELECT * FROM "+getTableName());
			while (rs.next()) {
				list.add(rs.getString(value.getColumName()));
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkConnection() {
		if(mysql.getAsyncMySQL().getMySQL() == null) {
			return false;
		}
		return true;
	}
}
