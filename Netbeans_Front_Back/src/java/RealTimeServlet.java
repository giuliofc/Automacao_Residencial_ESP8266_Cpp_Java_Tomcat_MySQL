/**
 * @author Giulio
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.util.*;
import com.google.gson.*;
import java.io.BufferedReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pacoteJava.Conexao;
import pacoteJava.Dispositivo;
import pacoteJava.Manipula_Dispositivo;
import pacoteJava.CorAtual;
import pacoteJava.Manipula_CorAtual;
import pacoteJava.Nivel;
import pacoteJava.Manipula_Nivel;


public class RealTimeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Servlet para controle de atualizações sem recarregar toda a pagina.";
    }// </editor-fold>
    
    //editar USER e SENHA de acordo com suas credenciais do banco
    String SERVER="localhost", DB="automacao", USER="nome_do_usuario", SENHA="senha_do_banco";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        String acao=request.getParameter("acao");
        
        if(acao==null || acao.equals("")){
            acao="vazio";
        }
        
        switch(acao){
           
            case "mostrarNivel":
                
                MostrarNivel(request,response);
                break;
                
        }        
                    
    } //fim do processRequest
    
    //ok
    private void MostrarNivel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            Nivel nv=new Nivel();
            Manipula_Nivel mpn=new Manipula_Nivel(con, nv);
            nv=mpn.consultarUltimoNivel(); //o q colocar de parametro?

            DecimalFormat formatador = new DecimalFormat("0.00"); //para deixar o nivel somente com 2 casas decimais
            
            String text =  formatador.format(nv.getNivel())+"m "+nv.getDataNivel();
              
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(text);       // Write response body.
        }
        catch(Exception e){
            response.setContentType("text/plain"); 
            response.setCharacterEncoding("UTF-8"); 
            response.getWriter().write(e+"");
        }
        
    }
    
} //fim da classe

