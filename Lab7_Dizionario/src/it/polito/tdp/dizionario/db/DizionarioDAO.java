package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DizionarioDAO {
	
	public List<String> loadWords(int dim){
		List<String> parole = new LinkedList<String>();
		
		String sql = "select nome from parola where length(nome) = ?";
		
		Connection conn = DBConnect.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, dim);
			ResultSet res = st.executeQuery() ;
			
			while( res.next() )
				parole.add(res.getString("nome"));
			
			res.close();
			conn.close();
			return parole;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
	
	public List<String> getSimili(String word){
		List<String> simili = new LinkedList<String>();
		String sql = "select nome from parola where length(nome)=? and nome like ?";
		Connection conn = DBConnect.getConnection();
		PreparedStatement st;
		String search;
		
		for(int i=0; i<word.length(); i++){
			try {
				st = conn.prepareStatement(sql);
				st.setInt(1, word.length());
				
				search = word.substring(0,i)+"_"+word.substring(i+1);
				
				st.setString(2, search);
				ResultSet res = st.executeQuery();
				
				while(res.next()){
					if(!simili.contains(res.getString("nome")))
					simili.add(res.getString("nome"));
				}
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simili;
	}

}
