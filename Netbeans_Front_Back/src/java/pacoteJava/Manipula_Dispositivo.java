/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacoteJava;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giulio
 */
public class Manipula_Dispositivo {
    
    private Conexao minhaConexao;
    private Dispositivo meuDispositivo;
    private String mensagem="";
    
    public Manipula_Dispositivo(Conexao con, Dispositivo disp){
        setConexao(con);
        setDispositivo(disp);
    }
    
    public void setConexao(Conexao con){minhaConexao = con; }
    public void setDispositivo(Dispositivo disp){ meuDispositivo = disp; }
   
    
    public Conexao getConexao(){return minhaConexao; }
    public Dispositivo getDispositivo(){ return meuDispositivo; }
    
    //coloca string entre aspas simples
    private String QuotedStr(String Item){return "\'"+Item+"\'";}
    
    //colocar double entre aspas simples
    private String QuotedStr(double Item){return "\'"+Item+"\'";}
    
    public String inserirDados(){
        String mensagem="";
        
        String colunas="("+meuDispositivo.getCampos().get(0)+","
                          +meuDispositivo.getCampos().get(1)+","
                          +meuDispositivo.getCampos().get(2)+","
                          +meuDispositivo.getCampos().get(3)+","
                          +meuDispositivo.getCampos().get(4)+","
                          +meuDispositivo.getCampos().get(5)+","
                          +meuDispositivo.getCampos().get(6)+")";
        
        
        String valores="("+QuotedStr(meuDispositivo.getDispID())+","
                          +QuotedStr(meuDispositivo.getUserID())+","
                          +QuotedStr(meuDispositivo.getRed())+","
                          +QuotedStr(meuDispositivo.getBlue())+","
                          +QuotedStr(meuDispositivo.getGreen())+","
                          +QuotedStr(meuDispositivo.getNomeDisp())+","
                          +QuotedStr(meuDispositivo.getNomeUser())+")"; 
        
        //JOptionPane.showMessageDialog(null, valores);
        String Comando = "INSERT INTO Historico "+colunas+" VALUES "+valores;
        mensagem=getConexao().ExpressaoSQL(Comando);
        getConexao().FecharConexao();
        return mensagem;
        
    }
    
    
    
} //fim da classe manipula_dispositivo
