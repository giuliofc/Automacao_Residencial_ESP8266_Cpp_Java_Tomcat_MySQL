
package pacoteJava;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pacoteJava.CorAtual;
/**
 *
 * @author Giulio
 */
public class Manipula_CorAtual {
    private Conexao minhaConexao;
    private CorAtual minhaCorAtual;
    private String mensagem="";
    
    public Manipula_CorAtual(Conexao con, CorAtual cor){
        setConexao(con);
        setCorAtual(cor);
    }

    public Conexao getConexao() {return minhaConexao;}
    public CorAtual getCorAtual() {return minhaCorAtual;}

    public void setConexao(Conexao minhaConexao) {this.minhaConexao = minhaConexao;}
    public void setCorAtual(CorAtual minhaCorAtual) {this.minhaCorAtual = minhaCorAtual;}
    
    //coloca string entre aspas simples
    private String QuotedStr(String Item){return "\'"+Item+"\'";}    
    //colocar int entre aspas simples
    private String QuotedStr(int Item){return "\'"+Item+"\'";}
    //colocar boolean entre aspas simples
    private String QuotedStr(boolean myBoolean){
        int myInt = myBoolean ? 1 : 0;
        return "\'"+myInt+"\'";
    }
    
    public String inserirDados(){
        String mensagem="";
        
        //idDisp, red, green, blue, status
        String colunas="("+minhaCorAtual.getCampos().get(0)+","
                          +minhaCorAtual.getCampos().get(1)+","
                          +minhaCorAtual.getCampos().get(2)+","
                          +minhaCorAtual.getCampos().get(3)+","
                          +minhaCorAtual.getCampos().get(4)+")";
                                 
        String valores="("+QuotedStr(minhaCorAtual.getIdDisp())+","
                          +QuotedStr(minhaCorAtual.getRed())+","
                          +QuotedStr(minhaCorAtual.getGreen())+","
                          +QuotedStr(minhaCorAtual.getBlue())+","
                          +QuotedStr(minhaCorAtual.getStatus())+")";
                           
        String Comando = "INSERT INTO coratual"+colunas+" VALUES"+valores;
        mensagem=getConexao().ExpressaoSQL(Comando);
        getConexao().FecharConexao();
        return mensagem;               
    }
    
    
    public String[] consultarCorAtualArray(int idDisp){
        String Comando="SELECT idDisp, red, blue, green, status "
                + "FROM coratual WHERE idDisp="+idDisp+";"; 
    
        ResultSet rst=null;
        String[] corAtual=new String[5];
        
        try{
            String mensagem=minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            
            if( !rst.isBeforeFirst() ){
                System.out.println("Erro ao tentar encontrar corAtual!");
            }
            else{
                while(rst.next()){
                    corAtual[0]=rst.getString("idDisp");
                    corAtual[1]=rst.getString("red");
                    corAtual[2]=rst.getString("green");
                    corAtual[3]=rst.getString("blue");
                    corAtual[4]=rst.getString("status");
                }
            }                           
        }catch(Exception e){
            System.out.println("Erro ao tentar encontrar corAtual!");                   
        }
        
        minhaConexao.FecharConexao();
        return corAtual;  
    }
    
    
    public CorAtual consultarCorAtualObjeto(int idDisp){
        String Comando="SELECT idDisp, red, green, blue, status "
                + "FROM coratual WHERE idDisp="+idDisp+";"; 
    
        ResultSet rst=null;
        CorAtual corAtual=new CorAtual();
        
        try{
            String mensagem=minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            
            if( !rst.isBeforeFirst() ){
                System.out.println("Erro ao tentar encontrar corAtual!");
            }
            else{
                while(rst.next()){
                    corAtual.setIdDisp( Integer.parseInt( rst.getString("idDisp") ));
                    corAtual.setRed( Integer.parseInt( rst.getString("red") ));
                    corAtual.setGreen( Integer.parseInt( rst.getString("green") ));
                    corAtual.setBlue( Integer.parseInt( rst.getString("blue") ));
                    corAtual.setStatus( Integer.parseInt( rst.getString("status") ));                                      
                }
            }                           
        }catch(Exception e){
            System.out.println("Erro ao tentar encontrar corAtual!");                   
        }
        
        minhaConexao.FecharConexao();
        return corAtual;  
    }
    
    
    public String updateDados(int idDisp){
        String mensagem="";
        
        String colunas="("+minhaCorAtual.getCampos().get(1)+","    //red
                          +minhaCorAtual.getCampos().get(2)+","    //green
                          +minhaCorAtual.getCampos().get(3)+","    //blue
                          +minhaCorAtual.getCampos().get(4)+")";   //status
        
        String valores="("+QuotedStr(minhaCorAtual.getRed())+","
                          +QuotedStr(minhaCorAtual.getGreen())+","
                          +QuotedStr(minhaCorAtual.getBlue())+","
                          +QuotedStr(minhaCorAtual.getStatus())+")";
        
        String Comando = "UPDATE coratual SET "
        +minhaCorAtual.getCampos().get(1)+"="+QuotedStr(minhaCorAtual.getRed())+","
        +minhaCorAtual.getCampos().get(2)+"="+QuotedStr(minhaCorAtual.getGreen())+","
        +minhaCorAtual.getCampos().get(3)+"="+QuotedStr(minhaCorAtual.getBlue())+","
        +minhaCorAtual.getCampos().get(4)+"="+QuotedStr(minhaCorAtual.getStatus())+" "
        +"WHERE idDisp="+idDisp+";";
        
        mensagem=getConexao().ExpressaoSQL(Comando);
        getConexao().FecharConexao();
        return mensagem;
    }

    
    public List getListaCorAtual(){

        String Comando="SELECT * FROM coratual;";

        ResultSet rst=null;
        CorAtual corAtual=null;
        ArrayList<CorAtual> lista=new ArrayList<CorAtual>();

        try{
            minhaConexao.ExpressaoSQL(Comando);
            rst=minhaConexao.getDados();
            while(rst.next()){
                corAtual=new CorAtual();
                corAtual.setIdDisp(rst.getInt("idDisp"));
                corAtual.setRed(rst.getInt("red"));
                corAtual.setGreen(rst.getInt("green"));
                corAtual.setBlue(rst.getInt("blue"));
                corAtual.setStatus(rst.getInt("status"));
                lista.add(corAtual);
            }
        }
        catch(Exception e){
            mensagem="Erro ao listar cor atual!";
        }

        minhaConexao.FecharConexao();
        System.out.println(mensagem);

        return lista;
}


    public String excluirCorAtual(int idDisp){
        String Comando="DELETE FROM coratual WHERE idDisp="+idDisp+";";
        String mensagem="";
        
        try{
            mensagem=minhaConexao.ExpressaoSQL(Comando);
            minhaConexao.FecharConexao();
        }
        catch(Exception e){
            mensagem="Erro ao tentar excluir a cor atual!";
            System.out.println("Erro ao tentar excluir a cor atual!");
        }
        return mensagem;
    }


    
} //fim da classe CorAtual
