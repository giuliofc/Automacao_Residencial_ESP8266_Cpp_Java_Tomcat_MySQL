
package pacoteJava;
import java.util.*;
/**
 * @author Giulio
 */
public class CorAtual {
    private int idDisp, red, green, blue, status; 
    private String mensagem;

    public CorAtual() {
        this.idDisp = 0;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.status = 0;
    }
    
    public CorAtual(int red, int green, int blue, int status) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.status = status;
    }
    
    public CorAtual(int idDisp, int red, int green, int blue, int status) {
        this.idDisp = idDisp;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.status = status;
    }

    
    public int getIdDisp() {return idDisp;}
    public int getRed() {return red;}
    public int getGreen() {return green;}
    public int getBlue() {return blue;}
    public int getStatus() {return status;}
    
    
    public void setIdDisp(int idDisp) {       
        if(idDisp >0){
          this.idDisp=idDisp;
        }else{
          mensagem="ID do dispositivo incorreto";
        }      
    }
    public void setRed(int red) {this.red = red;}
    public void setGreen(int green) {this.green = green;}
    public void setBlue(int blue) {this.blue = blue;}
    public void setStatus(int status) {this.status = status;}  
    
    
    /**Método retorna um vetor com os nomes dos campos da tabela CorAtual
     * @return Vector - nome dos campos
     */
    public Vector getCampos(){
        Vector Campos=new Vector();
        Campos.add("idDisp");
        Campos.add("red");
        Campos.add("green");
        Campos.add("blue");
        Campos.add("status");
        return Campos;
    }
    
}
