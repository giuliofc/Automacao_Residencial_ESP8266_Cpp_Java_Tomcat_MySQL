
package pacoteJava;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Vector;

/**
 * @author Giulio
 */
public class Nivel {
    private int idNivel, idDisp; 
    private String dataNivel;
    private float nivel;
        
    public Nivel(int idNivel, int idDisp, String dataNivel, float nivel) {
        this.idNivel = idNivel;
        this.idDisp = idDisp;
        this.dataNivel = dataNivel;
        this.nivel = nivel;
    }     
            
    public Nivel() {
        this.idNivel = 0;
        this.idDisp = 0;
        this.dataNivel = "";
        this.nivel = 0 ;
    }
                
    public void setIdNivel(int idNivel) {this.idNivel = idNivel;}
    public void setIdDisp(int idDisp) {this.idDisp = idDisp;}
    public void setDataNivel(String dataNivel) {this.dataNivel = dataNivel;}
    public void setNivel(float nivel) {this.nivel = nivel;}
        
    public int getIdNivel() {return idNivel;}
    public int getIdDisp() {return idDisp;}
    public String getDataNivel() {return dataNivel;}
    public double getNivel() {return nivel;}
    
    public Vector getCampos(){
        Vector Campos=new Vector();
        Campos.add("idNivel");
        Campos.add("idDisp");
        Campos.add("dataNivel");
        Campos.add("nivel");        
        return Campos;
    }
      
}
