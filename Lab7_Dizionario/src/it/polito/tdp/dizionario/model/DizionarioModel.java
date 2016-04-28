package it.polito.tdp.dizionario.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.DizionarioDAO;

public class DizionarioModel {
	
	private List<String> words;
	private SimpleGraph<String, DefaultEdge> graph;
	
	public DizionarioModel() {
		this.words = new LinkedList<String>();
		this.graph = new SimpleGraph<String,DefaultEdge>(DefaultEdge.class);
	}

	DizionarioDAO dao = new DizionarioDAO();
	
	public List<String> getWords() {
		return words;
	}
	
	public SimpleGraph<String, DefaultEdge> getGraph() {
		return graph;
	}


	public void load(int dim){
		words.clear();
		words.addAll(dao.loadWords(dim));
	}
	
	public void buildGraph(){
		
		List<String> previous = new LinkedList<String>(this.graph.vertexSet());
		this.graph.removeAllVertices(previous);
		Graphs.addAllVertices(this.graph, this.words);
		List<String> simili = new LinkedList<String>();
		for(String s : graph.vertexSet() ) {
			simili.clear();
			simili.addAll(this.getSimili(s));
			
			for( String s2 : simili ) {
				if(!s.equals(s2))
					graph.addEdge(s, s2) ;
				
			}
		}
	
	
	}
	
	public List<String> getSimili(String word){
		List<String> simili = new LinkedList<String>();
		
		for(int i=0; i<word.length(); i++){
			String start = word.substring(0,i);
			String end = word.substring(i+1);
			
			for(String s : this.words){
				if(s.startsWith(start) && s.endsWith(end) && !s.contains(word))
					simili.add(s);
			}
		}
		return simili;
	
	}
	
	public List<String> getVicini(String vertice){
		return Graphs.neighborListOf(this.graph, vertice );
	}
	
	public List<String> getRaggiungibili(String start) {
		
		BreadthFirstIterator<String, DefaultEdge> visita =
				new BreadthFirstIterator<String, DefaultEdge>(this.graph, start) ;
		
		List<String> vicini = new LinkedList<String>() ;
		
		while( visita.hasNext() ) {
			String s = visita.next() ;
			vicini.add(s) ;
		}
				
		return vicini ;
	}
	

	public static void main(String[] args) {
		DizionarioModel m = new DizionarioModel();
		m.load(4);
		
		for(String s : m.getWords())
			System.out.println(s);
		
		m.buildGraph(); 

		System.out.format("Graph: %d vertices, %d edges\n",
				m.getGraph().vertexSet().size(),
				m.getGraph().edgeSet().size()) ;
		
		System.out.print("VICINI DI CASA\n");
		for(String s : m.getVicini("casa")){
			System.out.println(s);
		}
	
		System.out.println("CONNESSI A CASA\n");
		for(String s : m.getRaggiungibili("casa"))
			System.out.println(s);
	}


	

}
