package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.DizionarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGenera;

    @FXML
    private Button btnVicini;

    @FXML
    private Button btnConnessi;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Button btReset;

	private DizionarioModel model;

    @FXML
    void doConnessi(ActionEvent event) {
    	if(!model.getWords().contains(txtParola.getText()))
    	{
			txtOutput.setText("Parola non presente in dizionario e/o di dimensione non corretta\n");
			return;
		}
    	
    	for(String s : model.getRaggiungibili(txtParola.getText()))
    		txtOutput.appendText(s+"\n");
    }

    @FXML
    void doGenera(ActionEvent event) {
    	int dim = 0;
    	try{
    		dim = Integer.parseInt(txtNumero.getText());
    	} catch (NumberFormatException nfe){
    		txtOutput.setText("Inserire un valore intero > 0\n");
    		return;
    	}
    	model.load(dim);
    	model.buildGraph();
    	txtOutput.setText("Grafo generato con "+model.getGraph().vertexSet().size()+" vertici e "+model.getGraph().edgeSet().size()+" archi\n");
    	txtParola.setEditable(true);
    	btnConnessi.setDisable(false);
    	btnVicini.setDisable(false);
    	txtNumero.setDisable(true);
    	btnGenera.setDisable(true);
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNumero.clear();
    	txtParola.clear();
    	txtOutput.clear();
    	txtParola.setEditable(false);
    	txtNumero.setDisable(false);
    	btnGenera.setDisable(false);
    	btnVicini.setDisable(true);
    	btnConnessi.setDisable(true);
    }

    @FXML
    void doVicini(ActionEvent event) {
    	if(!model.getWords().contains(txtParola.getText()))
    	{
			txtOutput.setText("Parola non presente in dizionario e/o di dimensione non corretta\n");
			return;
		}
    	
    	for(String s : model.getVicini(txtParola.getText()))
    		txtOutput.appendText(s+"\n");
    }

    @FXML
    void initialize() {
        assert txtNumero != null : "fx:id=\"txtNumero\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnGenera != null : "fx:id=\"btnGenera\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnConnessi != null : "fx:id=\"btnConnessi\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btReset != null : "fx:id=\"btReset\" was not injected: check your FXML file 'Dizionario.fxml'.";
        txtOutput.autosize();
    }

	public void setModel(DizionarioModel model) {
		// TODO Auto-generated method stub
		this.model = model;
	}
}

