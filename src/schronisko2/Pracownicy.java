package schronisko2;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Macius-laptop
 */
public class Pracownicy {
    private Integer idPracownika,nrTelefonu,idSchroniska,idStanowiska;
    private String imie, nazwisko,plec,nazwaStanowiska,pesel,dataZatrudnienia;

    private ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
    
    public Integer getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(Integer idPracownika) {
        this.idPracownika = idPracownika;
    }

    public Integer getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(Integer nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public Integer getIdSchroniska() {
        return idSchroniska;
    }

    public void setIdSchroniska(Integer idSchroniska) {
        this.idSchroniska = idSchroniska;
    }

    public Integer getIdStanowiska() {
        return idStanowiska;
    }

    public void setIdStanowiska(Integer idStanowiska) {
        this.idStanowiska = idStanowiska;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getNazwaStanowiska() {
        return nazwaStanowiska;
    }

    public void setNazwaStanowiska(String nazwaStanowiska) {
        this.nazwaStanowiska = nazwaStanowiska;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(String dataZatrudnienia) {
        this.dataZatrudnienia = dataZatrudnienia;
    }
    
    
    public ObservableList<Pracownicy> getAll(Connection conn) {
        ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT Id_Pracownika, Imie, Nazwisko, Plec, PESEL, Nr_Telefonu, Data_Zatrudnienia, Id_schroniska, Id_stanowiska from Pracownicy order by Id_Pracownika";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Pracownicy pracownik = new Pracownicy();
                pracownik.idPracownika = rs.getInt(1);
                pracownik.imie = rs.getString(2);
                pracownik.nazwisko = rs.getString(3);
                pracownik.plec = rs.getString(4);
                pracownik.pesel = rs.getString(5);
                pracownik.nrTelefonu = rs.getInt(6);
                pracownik.dataZatrudnienia = rs.getString(7);
                pracownik.idSchroniska = rs.getInt(8);
                pracownik.idStanowiska = rs.getInt(9);
                System.out.println("Pracownik: " +pracownik.idPracownika+pracownik.imie+pracownik.nazwisko +pracownik.plec+pracownik.pesel+pracownik.nrTelefonu
                +pracownik.dataZatrudnienia+" "+pracownik.idSchroniska+" "+pracownik.idStanowiska);
                listaPracownikow.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaPracownikow;
    }
    
    public ObservableList<Pracownicy> getRestricted(Connection conn, String nazwisko) {
        ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT Id_Pracownika, Imie, Nazwisko, Plec, PESEL, Nr_Telefonu, Data_Zatrudnienia, Id_schroniska, Id_stanowiska FROM Pracownicy where upper(Nazwisko) like ? ";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1,nazwisko.toUpperCase()+"%");
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pracownicy pracownik = new Pracownicy();
                pracownik.idPracownika = rs.getInt(1);
                pracownik.imie = rs.getString(2);
                pracownik.nazwisko = rs.getString(3);
                pracownik.plec = rs.getString(4);
                pracownik.pesel = rs.getString(5);
                pracownik.nrTelefonu = rs.getInt(6);
                pracownik.dataZatrudnienia = rs.getString(7);
                pracownik.idSchroniska = rs.getInt(8);
                pracownik.idStanowiska = rs.getInt(9);
                System.out.println("Pracownik: " +pracownik.idPracownika+pracownik.imie+pracownik.nazwisko +pracownik.plec+pracownik.pesel+pracownik.nrTelefonu
                +pracownik.dataZatrudnienia+" "+pracownik.idSchroniska+" "+pracownik.idStanowiska);
                listaPracownikow.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaPracownikow;
    }
     public ObservableList<Pracownicy> getRestricted(Connection conn, String Pesel,Integer temp) {
        ObservableList<Pracownicy> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT Id_Pracownika, Imie, Nazwisko, Plec, PESEL, Nr_Telefonu, Data_Zatrudnienia, Id_schroniska, Id_stanowiska FROM Pracownicy where upper(PESEL) like ? ";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1,Pesel.toUpperCase()+"%");
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pracownicy pracownik = new Pracownicy();
                pracownik.idPracownika = rs.getInt(1);
                pracownik.imie = rs.getString(2);
                pracownik.nazwisko = rs.getString(3);
                pracownik.plec = rs.getString(4);
                pracownik.pesel = rs.getString(5);
                pracownik.nrTelefonu = rs.getInt(6);
                pracownik.dataZatrudnienia = rs.getString(7);
                pracownik.idSchroniska = rs.getInt(8);
                pracownik.idStanowiska = rs.getInt(9);
                System.out.println("Pracownik: " +pracownik.idPracownika+pracownik.imie+pracownik.nazwisko +pracownik.plec+pracownik.pesel+pracownik.nrTelefonu
                +pracownik.dataZatrudnienia+" "+pracownik.idSchroniska+" "+pracownik.idStanowiska);
                listaPracownikow.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaPracownikow;
    }
    
    public int insertPracownik(Connection conn, String imie,String nazwisko, String Plec, String Pesel, Integer nrTelefonu,Integer idSchroniska, Integer idStanowiska ){
        String sql = "INSERT into Pracownicy(Id_Pracownika,Imie, Nazwisko, Plec, PESEL, Nr_Telefonu, Data_Zatrudnienia, Id_schroniska, Id_stanowiska) values ('' ,?,?,?,?,?,?,?,?)";
        //String sql = "INSERT into Pracownicy(Id_Pracownika,Imie, Nazwisko, Plec, PESEL, Nr_Telefonu, Data_Zatrudnienia, Id_schroniska, Id_stanowiska) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        Integer res=1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,imie);
            stmt.setString(2,nazwisko);
            stmt.setString(3,Plec);
            stmt.setString(4,Pesel);
            stmt.setInt(5,nrTelefonu);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String data = formatter.format(date);
            System.out.println(data);
            stmt.setString(6,data);
            stmt.setInt(7,idSchroniska);
            stmt.setInt(8,idStanowiska);

            
            res = stmt.executeUpdate();
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }

        return res;
    }
    public int modifyPracownik(Connection conn, String imie,String nazwisko, String Plec, String Pesel, Integer nrTelefonu,Integer idSchroniska, Integer idStanowiska, String givenPesel ){
        String sql = "UPDATE Pracownicy SET Imie=?, Nazwisko=?, Plec=?, Nr_Telefonu=?, Id_schroniska=?, Id_stanowiska=? WHERE PESEL=?";
        PreparedStatement stmt;
        Integer res=1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,imie);
            stmt.setString(2,nazwisko);
            stmt.setString(3,Plec);
            stmt.setInt(4,nrTelefonu);
            stmt.setInt(5,idSchroniska);
            stmt.setInt(6,idStanowiska);
            stmt.setString(7,givenPesel);
            System.out.println(stmt.toString());

            
            res = stmt.executeUpdate();
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }

        return res;
    }
    
    public int usunPracownika(Connection conn, Integer idPracownika){
        String sql = "DELETE from Pracownicy where Id_Pracownika =?";
        PreparedStatement stmt;
        Integer res =9;
        
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idPracownika);
            
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return res;
    }
    
    
}
