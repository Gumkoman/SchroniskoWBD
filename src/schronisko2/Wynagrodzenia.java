/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schronisko2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Macius-laptop
 */
public class Wynagrodzenia {
        private Integer kwota, idWynagrodzenia, idPracownika;

    public Integer getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(Integer idPracownika) {
        this.idPracownika = idPracownika;
    }
        private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getKwota() {
        return kwota;
    }

    public void setKwota(Integer kwota) {
        this.kwota = kwota;
    }

    public Integer getIdWynagrodzenia() {
        return idWynagrodzenia;
    }

    public void setIdWynagrodzenia(Integer idWynagrodzenia) {
        this.idWynagrodzenia = idWynagrodzenia;
    }
    
        public ObservableList<Wynagrodzenia> getAll(Connection conn) {
        ObservableList<Wynagrodzenia> listaWynagrodzen = FXCollections.observableArrayList();
        String sql = "SELECT Id_Wynagrodzenia, Kwota, Data, Id_Pracownika from Wynagrodzenia order by Id_Wynagrodzenia";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Wynagrodzenia wynagrodzenie = new Wynagrodzenia();
                wynagrodzenie.idWynagrodzenia = rs.getInt(1);
                wynagrodzenie.kwota = rs.getInt(2);
                wynagrodzenie.data = rs.getString(3);
                wynagrodzenie.idPracownika = rs.getInt(4);
                System.out.println(wynagrodzenie.idPracownika+"wynagrodzenie"+wynagrodzenie.idWynagrodzenia+wynagrodzenie.kwota+wynagrodzenie.idPracownika );
                listaWynagrodzen.add(wynagrodzenie);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaWynagrodzen;
    }
        public ObservableList<Wynagrodzenia> getRestricted(Connection conn, Integer idPracownika) {
        ObservableList<Wynagrodzenia> listaWynagrodzen = FXCollections.observableArrayList();
        String sql = "SELECT Id_Wynagrodzenia, Kwota, Data, Id_Pracownika from Wynagrodzenia WHERE Id_Pracownika = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1,idPracownika);
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                Wynagrodzenia wynagrodzenie = new Wynagrodzenia();
                wynagrodzenie.idWynagrodzenia = rs.getInt(1);
                wynagrodzenie.kwota = rs.getInt(2);
                wynagrodzenie.data = rs.getString(3);
                wynagrodzenie.idPracownika = rs.getInt(4);
                System.out.println(wynagrodzenie.idPracownika+"wynagrodzenie"+wynagrodzenie.idWynagrodzenia+wynagrodzenie.kwota+wynagrodzenie.idPracownika );
                listaWynagrodzen.add(wynagrodzenie);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errots with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaWynagrodzen;
    }
public int insertWynagrodznie(Connection conn, Integer idPracownika, Integer kwota ){
        String sql = "INSERT into WYNAGRODZNIA (Id_Wynagrodznia,Data,Kwota,Premia,Id_Pracownika) values (0,TO_DATE('2020-02-01','YYYY-MM-DD'),?,null,?)";
        PreparedStatement stmt;
        Integer res=1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,kwota);
            stmt.setInt(2,idPracownika);
                       
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
