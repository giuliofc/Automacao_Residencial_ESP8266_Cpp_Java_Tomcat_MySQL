
package pacoteJava;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import pacoteJava.Nivel;
import java.lang.Math;
import java.text.DecimalFormat;

/**
 * @author Giulio
 */
public class Manipula_Nivel {
    private Conexao minhaConexao;
    private Nivel meuNivel;
    private String mensagem="";
    
    public Manipula_Nivel(Conexao minhaConexao, Nivel meuNivel) {
        this.minhaConexao = minhaConexao;
        this.meuNivel = meuNivel;
    }
    
    public void setMinhaConexao(Conexao minhaConexao) {this.minhaConexao = minhaConexao;}
    public void setMeuNivel(Nivel meuNivel) {this.meuNivel = meuNivel;}
    
    public Conexao getMinhaConexao() {return minhaConexao;}
    public Nivel getMeuNivel() {return meuNivel;}
    
    //coloca string entre aspas simples
    private String QuotedStr(String Item){return "\'"+Item+"\'";}    
    //colocar int entre aspas simples
    private String QuotedStr(int Item){return "\'"+Item+"\'";}
    //colocar double entre aspas simples
    private String QuotedStr(double Item){return "\'"+Item+"\'";}
    
   /* private String QuotedStr(Date Item){
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy/MM/dd");
        String dataFormatada = formatador.format(Item);       
        return "\'"+Item+"\'";
    } */
    
    public String inserirDados(){
        String mensagem="";
                                                             //o idNivel é criado automaticamente pelo DB
        String colunas="("+meuNivel.getCampos().get(1)+","   //idDisp
                          +meuNivel.getCampos().get(2)+","   //dataNivel
                          +meuNivel.getCampos().get(3)+")";  //nivel
                                 
        String valores="("+QuotedStr(meuNivel.getIdDisp())+","
                          +QuotedStr(meuNivel.getDataNivel())+","
                          +QuotedStr(meuNivel.getNivel())+")";
                           
        String Comando = "INSERT INTO historiconivel"+colunas+" VALUES"+valores;
        mensagem=getMinhaConexao().ExpressaoSQL(Comando);
        getMinhaConexao().FecharConexao();
        return mensagem;               
    }
    
    public Nivel consultarUltimoNivel(){
        //String Comando="SELECT MAX(idNivel) AS idNivel, idDisp,dataNivel,nivel FROM historiconivel;";
        String Comando="SELECT * FROM historiconivel WHERE idNivel=(SELECT max(idNivel) FROM historiconivel);";
        
        ResultSet rst=null;
        Nivel nivel=new Nivel();
        
        try{
            this.mensagem=minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            
            if( !rst.isBeforeFirst() ){
                System.out.println("Erro ao tentar encontrar ultimo nivel!");
            }
            else{
                while(rst.next()){
                    nivel.setIdNivel(rst.getInt("idNivel"));
                    nivel.setIdDisp(rst.getInt("idDisp"));
                    nivel.setDataNivel(rst.getString("dataNivel") );
                    nivel.setNivel(rst.getFloat("nivel"));                                          
                }
            }                           
        }catch(Exception e){
            System.out.println("Erro ao tentar encontrar ultimo nivel!");
            //return e+" "+mensagem;
        }
        
        minhaConexao.FecharConexao();
        //teste
        //return nivel.getIdNivel()+" "+mensagem;  
        return nivel;
    }
    
    public List getListaNivel(){ //todo o historico 
        String Comando="SELECT * FROM historiconivel;";

        ResultSet rst=null;
        Nivel nivel=null;
        ArrayList<Nivel> lista=new ArrayList<Nivel>();
        
        try{
            minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            
            while(rst.next()){
                nivel=new Nivel();
                nivel.setIdNivel(rst.getInt("idNivel"));
                nivel.setIdDisp(rst.getInt("idDisp"));
                nivel.setDataNivel(rst.getString("dataNivel"));
                nivel.setNivel(rst.getFloat("nivel"));
                lista.add(nivel);
            }
        }
        catch(Exception e){
            mensagem="Erro ao listar historico de nivel!";
        }
        
        minhaConexao.FecharConexao();
        System.out.println(mensagem);
        return lista;
    }
    
    
    public List getListaNivel(String dataInicial,String dataFinal){ //todo o historico 
        String Comando="SELECT * FROM historiconivel WHERE dataNivel BETWEEN \'"+dataInicial+"\' AND \'"+dataFinal+"\';";
    
        ResultSet rst=null;
        Nivel nivel=null;
        ArrayList<Nivel> lista=new ArrayList<Nivel>();
        
        try{
            minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            
            while(rst.next()){
                nivel=new Nivel();
                nivel.setIdNivel(rst.getInt("idNivel"));
                nivel.setIdDisp(rst.getInt("idDisp"));
                nivel.setDataNivel(rst.getString("dataNivel"));
                nivel.setNivel(rst.getFloat("nivel"));
                lista.add(nivel);
            }
        }
        catch(Exception e){
            mensagem="Erro ao listar historico de nivel!";
        }
        
        minhaConexao.FecharConexao();
        System.out.println(mensagem);
        return lista;
    }
    
    
    
    
} //fim da classe manipula_nivel
