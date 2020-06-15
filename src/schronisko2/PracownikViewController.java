/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schronisko2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Macius-laptop
 */
public class PracownikViewController implements Initializable {
    
    Connection conn;
    private ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
    private ObservableList<Wynagrodzenia> listaWynagrodzen = FXCollections.observableArrayList();
    private String passedPesel;
    @FXML
    private TextField textFieldImie;

    @FXML
    private TextField textFieldNazwisko;

    @FXML
    private TextField textFieldPlec;

    @FXML
    private TextField textFieldPesel;

    @FXML
    private TextField textFieldTelefon;

    @FXML
    private TextField textFieldSchronisko;

    @FXML
    private TextField textFieldStanowisko;

    @FXML
    private Button buttonModyfikuj;
    
    @FXML
    private Button buttonShow;
    
    @FXML
    private Label labelPesel;
    
    @FXML
    private Button buttonWyloguj;
    
    @FXML
    private Label labelWynagrodzenie;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    public void buttonWylogujOnAction(ActionEvent event) throws IOException{
            
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            Parent root = loader.load();
 
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
            Stage stage1 = new Stage();
            stage1 = (Stage) buttonWyloguj.getScene().getWindow();
            stage1.close();
       
    }
    
    
    @FXML
    public void show(ObservableList<Pracownicy> listaPracownikow,ObservableList<Wynagrodzenia> listaWynagrodzen){
        
        
        

        
        Pracownicy pracownik = listaPracownikow.get(0);
        System.out.println(pracownik.getImie().toString());
        textFieldImie.setText(pracownik.getImie());
        textFieldNazwisko.setText(pracownik.getNazwisko());
        textFieldPlec.setText(pracownik.getPlec());
        textFieldPesel.setText(pracownik.getPesel());
        textFieldTelefon.setText(pracownik.getNrTelefonu().toString());
        textFieldSchronisko.setText(pracownik.getIdSchroniska().toString());
        textFieldStanowisko.setText(pracownik.getIdStanowiska().toString());
        
        Integer idP = pracownik.getIdPracownika();
        try{
            Wynagrodzenia wynagrodzenia = new Wynagrodzenia();
            listaWynagrodzen = wynagrodzenia.getRestricted(conn,idP);
            Wynagrodzenia w1 = listaWynagrodzen.get(0);
            labelWynagrodzenie.setText(w1.getKwota().toString());
        }catch(RuntimeException exc){
            System.out.println("niepoprawne dzialanie programu");
        }
        
    }
    @FXML
    private void showButtonOnAction(ActionEvent event){
        Pracownicy pracownicy = new Pracownicy();
        listaPracownikow = pracownicy.getAll(conn);
        String Pesel="98092600112";
        Pesel = labelPesel.getText().trim();
        listaPracownikow = pracownicy.getRestricted(conn,Pesel,1);
        System.out.println(listaPracownikow.toString());
        Wynagrodzenia wynagrodzenia = new Wynagrodzenia();
        listaWynagrodzen = wynagrodzenia.getAll(conn);
        show(listaPracownikow,listaWynagrodzen);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
        conn = DBConnection.getConnection();
        textFieldPesel.setDisable(true);

        /*Pracownicy pracownicy = new Pracownicy();
        listaPracownikow = pracownicy.getAll(conn);
        String Pesel="98092600112";
        labelPesel.getText().trim();
        listaPracownikow = pracownicy.getRestricted(conn,Pesel,1);
        System.out.println(listaPracownikow.toString());
        show(listaPracownikow);*/
        

        /*System.out.println("CHUJ");
        
        Pesel = labelPesel.getText().trim();
        System.out.println(Pesel);
        listaPracownikow = pracownicy.getRestricted(conn,Pesel,1);
        show(listaPracownikow);*/
    }    
    

    
    public void transferMessage(String pesel){
        System.out.println("transerf info");
        System.out.println(pesel);
        labelPesel.setText(pesel);
        
        
    }
    
    @FXML
    public void buttonPotwierdzModyfikacjeOnAction(ActionEvent event){
        Integer idPracownika,nrTelefonu,idSchroniska,idStanowiska;
        String imie, nazwisko,plec,nazwaStanowiska,pesel,dataZatrudnienia;
        Integer result;
        
        imie = textFieldImie.getText().trim();
        nazwisko = textFieldNazwisko.getText().trim();
        plec = textFieldPlec.getText().trim();
        pesel = textFieldPesel.getText().trim();
        nrTelefonu = Integer.parseInt(textFieldTelefon.getText().trim());
        idSchroniska = Integer.parseInt(textFieldSchronisko.getText().trim());
        idStanowiska = Integer.parseInt(textFieldStanowisko.getText().trim());
         try{
        conn = DBConnection.getConnection();
        result = new Pracownicy().modifyPracownik(conn, imie, nazwisko, plec, pesel, nrTelefonu, idSchroniska, idStanowiska,pesel);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Dodano prawidlowo");
        alert.showAndWait();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Niepoprawne dane ;(");
            alert.showAndWait();
        
        }
  
    }
}
