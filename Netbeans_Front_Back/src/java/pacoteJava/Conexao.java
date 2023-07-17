/*
    Usuario - nome do usuario que se conectara ao mySQL
    Senha - senha de acesso ao mySQL
    Servidor - servidor de onde o mySQL sera acessado
    DataBase - banco de dados que será acessado
    Con - Classe do tipo Connection do proprio java que faz a conexao com 
          o banco de dados
    Dados - classe do tipo ResultSet, do java, que armazena os resultados de um 
          comando "Select"
    Conexao() - metodo que instancia a classe Conexao
    Conectar() - metodo que faz a conexao com o banco de dados
    FecharConexao() - metodo que fecha a conexao com o banco de dados
    ExpressaoSQL - metodo que executa um comando de SQL
*/

package pacoteJava;
import javax.swing.JOptionPane;
import java.sql.*; //pacote para manipulacao de banco de dados

public class Conexao {
    
    private String Usuario, Senha, Servidor, DataBase;
    private Connection Con;
    private boolean Conectado;
    private ResultSet Dados;
    private String mensagem; //mensagem retorno ao servlet para usuario
    
    public Conexao(){
        setSenha("");
        setUsuario("");
        setServidor("");
        setConectado(false);
        setCon(null);
        setDados(null);
        setDataBase("");
    }
    
    public Conexao(String SERV, String DB, String USU, String SENHA){
        setSenha(SENHA);
        setUsuario(USU);
        setServidor(SERV);
        setDataBase(DB);
        setConectado(false);
        setCon(null);
        setDados(null);
    }
    
    public void setCon(Connection con) {Con = con; }
    public void setConectado(boolean conectado) {Conectado=conectado; }
    public void setSenha(String senha) {Senha=senha; }
    public void setUsuario(String usuario) {Usuario=usuario; }
    public void setServidor(String servidor) {Servidor=servidor; }
    public void setDados(ResultSet dados) {Dados=dados; }
    public void setDataBase(String DB) {DataBase=DB; }
    
    public String getUsuario() {return Usuario; }
    public String getSenha() {return Senha; }
    public boolean getConectado() {return Conectado; }
    public Connection getCon() {return Con; }
    public String getServidor() {return Servidor; }
    public ResultSet getDados() {return Dados; }
    public String getDataBase() {return DataBase; }
    
    public String Conectar(){
        //toda conexao com banco de dados em java, deve ser protegida obrigatoriamente
        //com tratamento de exceção try..catch
        try{
            //carrega o driver que será para conexao com o java. Neste caso, 
            //será uma conexao via JDBC
            //forName retorna a classe passada como parametro dentro da string
            //Local do driver para MySQL 5.x e anterior:
            //Class.forName("com.mysql.jdbc.Driver"); 
            //Local do driver para MySQL 8.x e posterior:
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Para isso, invocamos o método estático getConnection com uma String que 
            //indica a qual banco desejamos nos conectar.
            //jdbc:mysql://ip:porta/nome_do_banco
            //URL para MySQL 5.x e anteriores:
            //String URL="jdbc:mysql://"+getServidor()+":3306"+"/"+getDataBase();
            //URL para MySQL 8.x e posteriores:
            String URL="jdbc:mysql://"+getServidor()+":3306"+"/"+getDataBase()+"?useTimezone=true&serverTimezone=UTC&useSSL=false";
            
            //tenta fazer a conexao com o banco a partir da URL, usuario e senha fornecidos
            setCon(DriverManager.getConnection(URL, getUsuario(), getSenha() ));
            setConectado(true);
            mensagem="Conexao realizada com Sucesso!"; 
        }
        catch(Exception e){
            mensagem="Conexao com banco de dados \nnão foi realizada! Erro: "+e.getMessage();
            setConectado(false);
        }
        System.out.println(mensagem);
        return mensagem;
    
    } //fim do metodo Conectar
    
    public String FecharConexao(){
        try{
            if(getConectado())
                //fecha o banco de dados
                getCon().close();
                mensagem="Conexão fechada com sucesso!";
        }
        catch(Exception e){
            mensagem="Conexao não foi fechada!";        
        }
        System.out.println(mensagem);
        return mensagem;
        
    } //fim do metodo FecharConexao
    
    //Executa um comando em SQL e guarda o resultado do comando no atributo 
    //Dados, do tipo ResultSet
    public String ExpressaoSQL(String Comando){
        //SELECT - comandos com recuperação de dados
        //INSERT, UPDATE, DELETE - comando sem recuperação de dados
        
        if(getConectado()){
            try{
                Statement st=getCon().createStatement();
                if(Comando.toUpperCase().indexOf("SELECT") != -1)
                    setDados(st.executeQuery(Comando)); //executar pergunta(Comando de pergunta)
                else{
                    setDados(null);
                    st.executeUpdate(Comando); //executar atualização (Comando de atualização)
                    if(Comando.toUpperCase().indexOf("UPDATE") != -1)
                        mensagem="Dados Atualizados!";
                    else{
                        if(Comando.toUpperCase().indexOf("DELETE") != -1)
                            mensagem="Dados Removidos!";
                        else
                            if(Comando.toUpperCase().indexOf("INSERT") != -1)
                                mensagem="Dados Inseridos!";
                    }    
                }
            }
            catch(SQLException sqle){
                mensagem="SQL invalido!";        
                mensagem+="Erro:"+sqle.getMessage();
            }
        }
        
        return mensagem;
    } //fim do metodo ExpressaoSQL
    
    
} //fim da classe 