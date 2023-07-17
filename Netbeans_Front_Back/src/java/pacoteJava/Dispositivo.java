/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacoteJava;
import java.util.*;
import java.text.*;
//import javax.swing.JOptionPane;
/**
 * @author giulio
 */
public class Dispositivo {
    
    private int iDispID, iUserID, iRed, iBlue, iGreen;
    private String sNomeDisp, sNomeUser;
    private boolean bConsistencia;
    private String mensagem="";
    
    public Dispositivo(){
        setDispID(0);
        setUserID(0);
        setRed(0);
        setBlue(0);
        setGreen(0);
        setNomeDisp("");
        setNomeUser("");
    }
    
    //verifica a consistencia em todos os metodos
    public void setConsistencia(boolean consistencia){
        bConsistencia =consistencia;
    }
    
    public String setDispID(int dispid){
        if(dispid >=0){ 
            this.iDispID = dispid;
            setConsistencia(true);
        }else{
            this.iDispID =-1;
            mensagem="Código do dispositivo incorreto.";
            setConsistencia(false);
        } 
        return mensagem;
    }
    
    public String setUserID(int userid){
        if(userid >=0){ 
            this.iUserID = userid;
            setConsistencia(true);
        }else{
            this.iUserID =-1;
            mensagem="Código do usuario incorreto.";
            setConsistencia(false);
        } 
        return mensagem;
    }
    
    public String setRed(int red){
        if(red>=0 && red<=255){ 
            this.iRed = red;
            setConsistencia(true);
        }else{
            this.iRed =-1;
            mensagem="Código da cor vermelha incorreto.";
            setConsistencia(false);
        } 
        return mensagem;
    }
    
    public String setBlue(int blue){
        if(blue>=0 && blue<=255){ 
            this.iBlue = blue;
            setConsistencia(true);
        }else{
            this.iBlue =-1;
            mensagem="Código da cor azul incorreto.";
            setConsistencia(false);
        } 
        return mensagem;
    }
    
    public String setGreen(int green){
        if(green>=0 && green<=255){ 
            this.iGreen = green;
            setConsistencia(true);
        }else{
            this.iGreen =-1;
            mensagem="Código da cor verde incorreto.";
            setConsistencia(false);
        } 
        return mensagem;
    }
    
    public String setNomeDisp(String nomedisp){
        if(nomedisp.length() >0){
            sNomeDisp = nomedisp;
            setConsistencia(true);
        }else{
            sNomeDisp="Nome_Dispositivo_Anonimo";
            mensagem="Nome do dispositivo anônimo.";
            setConsistencia(false);
        }
        return mensagem;
    }
    
    public String setNomeUser(String nomeuser){
        if(nomeuser.length() >0){
            sNomeUser = nomeuser;
            setConsistencia(true);
        }else{
            sNomeUser="Nome_Usuario_Anonimo";
            mensagem="Nome do usuario anônimo.";
            setConsistencia(false);
        }
        return mensagem;
    }
    
    
    public int getDispID(){return iDispID;}
    public int getUserID(){return iUserID;}
    public int getRed(){return iRed;}
    public int getBlue(){return iBlue;}
    public int getGreen(){return iGreen;}
    public String getNomeDisp(){return sNomeDisp;}
    public String getNomeUser(){return sNomeUser;}
    
    //retorna um vetor com os nomes dos campos da tabela Dispositivo
    public Vector getCampos(){
        Vector Campos=new Vector();
        Campos.add("dispID");
        Campos.add("userID");
        Campos.add("red");
        Campos.add("blue");
        Campos.add("green");
        Campos.add("nomeDisp");
        Campos.add("nomeUser");
        return Campos;
    }
    
} //fim da classe Dispositivo
