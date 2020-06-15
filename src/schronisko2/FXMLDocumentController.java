/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schronisko2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Macius-laptop
 */
public class FXMLDocumentController implements Initializable {
  private Connection conn;  
  private ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
  private ObservableList<Wynagrodzenia> listaWynagrodzen = FXCollections.observableArrayList();
  
  
    
    @FXML
    private Label label;

    @FXML
    private TableView<Pracownicy> tableColumnPracownicy;

    @FXML
    private TableColumn<Pracownicy, Integer> tableColumnIdPracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnImiePracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnNazwiskoPracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnPlecPracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnPeselPracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnTelefonPracownika;

    @FXML
    private TableColumn<Pracownicy, String> tableColumnDataPracownika;

    @FXML
    private TableColumn<Pracownicy, Integer> tableColumnIdSchroniska;

    @FXML
    private TableColumn<Pracownicy, Integer> tableColumnIdStanowiska;
    
    @FXML
    private Button buttonSearch;
    
    @FXML
    private TextField textPracownikNazwisko;
    
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
    private Button buttonAdd;
    
    @FXML
    private Button buttonUsun;

    @FXML
    private Button buttonPotwierdzModyfikacje;
    
    @FXML
    private Button buttonAnulujModyfikacje;
    
    @FXML
    private Button buttonModyfikuj;
    
    @FXML
    private Button buttonWyloguj;
    
    @FXML
    private TextField textFieldWynagrodzenie;
    
    @FXML
    public void buttonUsunPracownikaOnAction(ActionEvent event){
        Integer rowIndex = tableColumnPracownicy.getSelectionModel().getSelectedIndex();
        
        if (rowIndex <1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie podano rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer pracownikId = tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdPracownika();
        
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setContentText("Czy napewno chcesz usunac ?");
        Optional<ButtonType> res1 = alert1.showAndWait();
        
        if(res1.get() == ButtonType.OK){
        Integer result = new Pracownicy().usunPracownika(conn,pracownikId);
            if(result >0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Usunieto pracownika");
                alert.showAndWait();
            }
        }
        setTableViewPracownicy(listaPracownikow);
        tempFunction();
    }
    
    @FXML
    public void buttonModyfikujPracownikaOnAction(ActionEvent event){
        Integer rowIndex = tableColumnPracownicy.getSelectionModel().getSelectedIndex();
        
        if (rowIndex <1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie podano rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer pracownikId = tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdPracownika();
        //System.out.println(pracownikId);
        textFieldImie.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getImie());
        //System.out.println(imie);
        textFieldNazwisko.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getNazwisko());
        textFieldPlec.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getPlec());
        textFieldPesel.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getPesel());
        textFieldTelefon.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getNrTelefonu().toString());
        //wyswietlanie nazwy schroniska
        textFieldSchronisko.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdSchroniska().toString());
        //wyswietlanie nazwy stanowiska
        if(tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdStanowiska()== 1){
            textFieldStanowisko.setText("Weterynarz"); 
        }else if(tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdStanowiska() == 2){
            textFieldStanowisko.setText("Hycel"); 
        }else if(tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdStanowiska() == 3){
            textFieldStanowisko.setText("Opiekun"); 
        }
        
        
       // textFieldStanowisko.setText(tableColumnPracownicy.getSelectionModel().getSelectedItem().getIdStanowiska().toString());
        buttonPotwierdzModyfikacje.setDisable(false);
        buttonPotwierdzModyfikacje.setVisible(true);
        buttonAnulujModyfikacje.setDisable(false);
        buttonAnulujModyfikacje.setVisible(true);  
        tempFunction();
    }
    
    @FXML
    public void buttonPotwierdzModyfikacjeOnAction(ActionEvent event){
        Integer idPracownika=0,nrTelefonu=0,idSchroniska=0,idStanowiska=0;
        String imie="", nazwisko="",plec="",nazwaStanowiska="",pesel="",dataZatrudnienia="",tempStanowisko="";
        Integer result;
        idStanowiska=1;
        
        
        
        try{
            imie = textFieldImie.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne imie");
            alert.showAndWait();
            return;
        }
        try{
            nazwisko = textFieldNazwisko.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne nazwisko");
            alert.showAndWait();
            return;
        }
        try{
            plec = textFieldPlec.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawna plec");
            alert.showAndWait();
            return;
        }
        try{
            pesel = textFieldPesel.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawny numer pesel");
            alert.showAndWait();
            return;
        }
        try{
            nrTelefonu = Integer.parseInt(textFieldTelefon.getText().trim());
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne numer telefonu");
            alert.showAndWait();
            return;
        }
        try{
            idSchroniska = Integer.parseInt(textFieldSchronisko.getText().trim());
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne numer Id schroniska");
            alert.showAndWait();
            return;
        }
        tempStanowisko="";
        try{
            tempStanowisko = textFieldStanowisko.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawna nazwe stanowiska");
            alert.showAndWait();
            return;
        }
        System.out.println(tempStanowisko);
        if(tempStanowisko.equals("Weterynarz")){
            System.out.println("Weterynarz");
            idStanowiska=1;
        }else if(tempStanowisko.equals("Hycel")){
            idStanowiska=2;
            System.out.println("Hycel");
        }else if(tempStanowisko.equals("Opiekun")){
            idStanowiska=3;
            System.out.println("Opiekun");
        }
         try{
        conn = DBConnection.getConnection();
        result = new Pracownicy().modifyPracownik(conn, imie, nazwisko, plec, pesel, nrTelefonu, idSchroniska, idStanowiska,pesel);
        //xd
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Dodano prawidlowo");
        alert.showAndWait();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Niepoprawne dane ;(");
            alert.showAndWait();
        
        }
         setTextFieldEmpty();
         buttonPotwierdzModyfikacje.setDisable(true);
         buttonPotwierdzModyfikacje.setVisible(false);
         buttonAnulujModyfikacje.setDisable(true);
         buttonAnulujModyfikacje.setVisible(false);
         tempFunction();
    }
    
    
    @FXML
    public void butonAnulujModyfikacjePracownika(ActionEvent event){
           setTextFieldEmpty();
         buttonPotwierdzModyfikacje.setDisable(true);
         buttonPotwierdzModyfikacje.setVisible(false);
         buttonAnulujModyfikacje.setDisable(true);
         buttonAnulujModyfikacje.setVisible(false); 
    }


    private void setTextFieldEmpty(){
            textFieldImie.setText("");
        textFieldNazwisko.setText("");
        textFieldPlec.setText("");
        textFieldPesel.setText("");
        textFieldTelefon.setText("");
        textFieldSchronisko.setText("");
        textFieldStanowisko.setText("");
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void buttonAddPracownikOnAction(ActionEvent event) {
        Integer idPracownika=0,nrTelefonu=0,idSchroniska=0,idStanowiska=0;
        String imie="", nazwisko="",plec="",nazwaStanowiska="",pesel="",dataZatrudnienia="",tempStanowisko="";
        Integer result,result1;
        
        try{
            imie = textFieldImie.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne imie");
            alert.showAndWait();
            return;
        }
        try{
            nazwisko = textFieldNazwisko.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne nazwisko");
            alert.showAndWait();
            return;
        }
        try{
            plec = textFieldPlec.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawna plec");
            alert.showAndWait();
            return;
        }
        try{
            pesel = textFieldPesel.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawny numer pesel");
            alert.showAndWait();
            return;
        }
        try{
            nrTelefonu = Integer.parseInt(textFieldTelefon.getText().trim());
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne numer telefonu");
            alert.showAndWait();
            return;
        }
        try{
            idSchroniska = Integer.parseInt(textFieldSchronisko.getText().trim());
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawne numer Id schroniska");
            alert.showAndWait();
            return;
        }
        tempStanowisko="";
        try{
            tempStanowisko = textFieldStanowisko.getText().trim();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Podano niepoprawna nazwe stanowiska");
            alert.showAndWait();
            return;
        }
        System.out.println(tempStanowisko);
        if(tempStanowisko.equals("Weterynarz")){
            System.out.println("Weterynarz");
            idStanowiska=1;
        }else if(tempStanowisko.equals("Hycel")){
            idStanowiska=2;
            System.out.println("Hycel");
        }else if(tempStanowisko.equals("Opiekun")){
            idStanowiska=3;
            System.out.println("Opiekun");
        }
        
        try{
        conn = DBConnection.getConnection();
        result = new Pracownicy().insertPracownik(conn, imie, nazwisko, plec, pesel, nrTelefonu, idSchroniska, idStanowiska);
        
        
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Dodano prawidlowo");
        alert.showAndWait();
        }catch(NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Niepoprawne dane ;(");
        alert.showAndWait();
        
        }
                
       tempFunction(); 
    }
    
    @FXML
    private void buttonSearchOnAction(ActionEvent action){

        tempFunction();
    }
    
    public void tempFunction(){
    conn = DBConnection.getConnection();
        listaPracownikow = new Pracownicy().getRestricted(conn, textPracownikNazwisko.getText().trim());
        System.out.println("essa: "+textPracownikNazwisko.getText().trim());
        conn = DBConnection.getConnection();
        listaPracownikow = new Pracownicy().getRestricted(conn, textPracownikNazwisko.getText().trim());
        System.out.println("essa: "+textPracownikNazwisko.getText().trim());
        setTableViewPracownicy(listaPracownikow);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
        Pracownicy pracownicy = new Pracownicy();
        listaPracownikow = pracownicy.getAll(conn);
        Wynagrodzenia wynagrodzenia = new Wynagrodzenia();
        listaWynagrodzen = wynagrodzenia.getAll(conn);
        
        
        //przyciski
        buttonPotwierdzModyfikacje.setDisable(true);
        buttonPotwierdzModyfikacje.setVisible(false);
        buttonAnulujModyfikacje.setDisable(true);
        buttonAnulujModyfikacje.setVisible(false); 
        setTableViewPracownicy(listaPracownikow);
    }    
    
        private void setTableViewPracownicy(ObservableList<Pracownicy> listaPracownikow){
        
        tableColumnIdPracownika.setCellValueFactory(new PropertyValueFactory<>("idPracownika"));
        tableColumnImiePracownika.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        tableColumnNazwiskoPracownika.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        tableColumnPlecPracownika.setCellValueFactory(new PropertyValueFactory<>("Plec"));
        tableColumnPeselPracownika.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        tableColumnTelefonPracownika.setCellValueFactory(new PropertyValueFactory<>("nrTelefonu"));
        tableColumnDataPracownika.setCellValueFactory(new PropertyValueFactory<>("dataZatrudnienia"));
        tableColumnIdSchroniska.setCellValueFactory(new PropertyValueFactory<>("idSchroniska"));
        //wyswietlanie w tabeli
        tableColumnIdStanowiska.setCellValueFactory(new PropertyValueFactory<>("idStanowiska"));
          
                
        tableColumnPracownicy.setItems(listaPracownikow);
    }
    
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
}
