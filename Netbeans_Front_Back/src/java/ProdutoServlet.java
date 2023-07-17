
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

import pacoteJava.Conexao;
import pacoteJava.Dispositivo;
import pacoteJava.Manipula_Dispositivo;
import pacoteJava.CorAtual;
import pacoteJava.Manipula_CorAtual;
import pacoteJava.Nivel;
import pacoteJava.Manipula_Nivel;


/**
 *
 * @author giulio
 */
public class ProdutoServlet extends HttpServlet {
    
      // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        return "Servlet para controle de Cadastro de Produto";
    }// </editor-fold>

    //editar USER e SENHA de acordo com suas credenciais do banco
    String SERVER="localhost", DB="automacao", USER="nome_do_usuario", SENHA="senha_do_banco";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String acao=request.getParameter("acao");
        
        if(acao==null || acao.equals("")){
            acao="vazio";
        }
        
        switch(acao){

            //ok
            case "enviarCor":
                int idDisp=-1;
                String st=request.getParameter("inpIdDisp");
                
                if(!st.equals("")){
                    try{
                        idDisp=Integer.parseInt(st);
                    
                    }catch(Exception e){
                       System.out.println("Não foi possivel converter o idDisp em inteiro."); 
                       request.setAttribute("mensagemFormCores", "Não foi possivel converter o idDisp em inteiro.");
                       request.getRequestDispatcher("/index.jsp?divAceso=divProduto").forward(request,response); 
                    }
                }
                
                if(idDisp<1){
                    CadastrarCorAtual(request,response);
                }else{
                    AtualizarCorAtual(request,response);
                }
                
                break;

            //ok
            case "consultarCorAtual":
                int idDisp2=-1;
                String s=request.getParameter("idDisp");

                if(!s.equals("")){
                        try{
                            idDisp2=Integer.parseInt(s);
                        }catch(Exception e){
                           System.out.println("Não foi possivel converter o idDisp em inteiro."); 
                           request.setAttribute("mensagemFormCores", "Não foi possivel converter o idDisp em inteiro.");
                           request.getRequestDispatcher("/index.jsp?divAceso=divProduto").forward(request,response); 
                        }
                }
                
                if(idDisp2>0){
                    ConsultarCorAtual(request,response,idDisp2);
                }
                                
                break;
            
            case "recerberJson":  //teste
                    
                ReceberJson(request,response);               
                break;
                
            case "listarCorAtual":
                
                ListarCorAtual(request,response);
                break;    
                
            case "editarCorAtual":
                
                EditarCorAtual(request,response);
                break;
                
            case "excluirCorAtual":
                
                ExcluirCorAtual(request,response);
                break;
                
            case "receberNivel":
                
                ReceberNivel(request,response);
                break;
            
            case "listarNivel":
                
                ListarNivel(request,response);
                break;                
                           
        } //fim do switch case
        
    } //fim do processRequest


    
       
    //ok  
    private void CadastrarCorAtual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int red, green, blue, statusLigDesl; 
        String mensagem="";
                
        try{
            //idDisp=Integer.parseInt(request.getParameter("inpIdDisp")); //não tem pq é uma cor q nao esta sendo editada
            red  =Integer.parseInt(request.getParameter("inpVermelho"));
            green=Integer.parseInt(request.getParameter("inpVerde"));
            blue =Integer.parseInt(request.getParameter("inpAzul"));
            statusLigDesl=Integer.parseInt(request.getParameter("inpStatusLigDesl"));
                        
            CorAtual ca=new CorAtual(red,green,blue,statusLigDesl);
            
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            
            Manipula_CorAtual mca=new Manipula_CorAtual(con, ca);
            mensagem=mca.inserirDados();
            
            request.setAttribute("mensagemFormCores", mensagem);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divProduto");
            rd.forward(request, response);
            
        }catch(Exception e){
            request.setAttribute("mensagemFormCores", "Não foi possível converter algum campo do formulário - CadastrarCorAtual");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divProduto");
            rd.forward(request, response);
        }
                
        
    }
    
    //ok
    private void AtualizarCorAtual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idDisp, red, green, blue, statusLigDesl; 
        String mensagem="";
        
        try{
            idDisp = Integer.parseInt(request.getParameter("inpIdDisp"));
            red = Integer.parseInt(request.getParameter("inpVermelho"));
            green = Integer.parseInt(request.getParameter("inpVerde"));
            blue = Integer.parseInt(request.getParameter("inpAzul"));
            statusLigDesl = Integer.parseInt(request.getParameter("inpStatusLigDesl"));
            
            CorAtual CA = new CorAtual();
            CA.setIdDisp(idDisp);
            CA.setRed(red);
            CA.setGreen(green);
            CA.setBlue(blue);
            CA.setStatus(statusLigDesl);
            
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            
            Manipula_CorAtual mca = new Manipula_CorAtual(con, CA);
            String mensagem1 = mca.updateDados(idDisp);
            
            request.setAttribute("mensagemFormCores", mensagem1);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divProduto");
            rd.forward(request, response);
        }
        catch(Exception e){
            request.setAttribute("mensagemFormCores", "Não foi possivel atualizar cor atual!");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divProduto");
            rd.forward(request, response);
        }
                     
    }
    
    //ok
    private void ConsultarCorAtual(HttpServletRequest request, HttpServletResponse response, int idDisp)
            throws ServletException, IOException {
        
        try{
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            CorAtual ca=new CorAtual();
            Manipula_CorAtual mca=new Manipula_CorAtual(con, ca);
            //String[] corAtual=new String[5];
            ca=mca.consultarCorAtualObjeto(idDisp);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(ca);           
            PrintWriter out = response.getWriter();
            out.println(json);
            
            //teste 1
            //request.setAttribute("idProduto" , idDisp); //para caso seja precionado o botao de cadastrar novamente após editar o produto
            //request.setAttribute("corAtualStr" , corAtual);
            //request.getRequestDispatcher("/index.jsp?divAceso=divProduto").forward(request,response);
            
            //teste 2
            //String url = request.getRequestURL().toString();
            //String referrer = request.getHeader("referer");
            //PrintWriter out = response.getWriter();
            //out.println(referrer);
                        
        /*    out.println("<html>");
            out.println("<body>");
            out.println("<p>ID Disp: "+corAtual[0]+"</P>");
            out.println("<p>Red: "+corAtual[1]+"</P>");
            out.println("<p>Blue: "+corAtual[2]+"</P>");
            out.println("<p>Green: "+corAtual[3]+"</P>");
            out.println("<p>Status: "+corAtual[4]+"</P>");
            out.println("</body>");
            out.println("</html>");
        */
                                          
        }catch(Exception e){
            PrintWriter out = response.getWriter();
            out.println("Erro ao tentar executar a função ConsultarCorAtual "+e);            
        }          
    }
    
    //ok
    private void ListarCorAtual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            CorAtual ca=new CorAtual();
            Manipula_CorAtual mca=new Manipula_CorAtual(con, ca);
            request.setAttribute("coresAtuais" , mca.getListaCorAtual());
            request.getRequestDispatcher("/index.jsp?divAceso=divListar").forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("mensagemTelaListarCorAtual", "Não foi possivel listar Cores Atuais!");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divListar");
            rd.forward(request, response);
        }
    }
    
    //ok
    private void EditarCorAtual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String idDisp=request.getParameter("idDisp");
            int id=-1;

            if(idDisp !=null){
                try{
                    id=Integer.parseInt(idDisp);
                }catch(Exception e){
                    System.out.println("O numero digitado não é um inteiro.");
                    request.setAttribute("mensagemTelaListarCorAtual", "O número digitado não é um inteiro.");
                    request.getRequestDispatcher("/index.jsp?divAceso=divListar").forward(request,response);
                }
                
                if(id>=1){
                    Conexao con=new Conexao(SERVER, DB, USER, SENHA);;
                    con.Conectar();
                    CorAtual ca=new CorAtual();

                    Manipula_CorAtual mca=new Manipula_CorAtual(con, ca);
                    String[] corAtual=new String[5];
                    corAtual=mca.consultarCorAtualArray(id); //vem 5 elementos idDisp,red,green,blue,status

                    request.setAttribute("idDisp" , id); //para caso seja precionado o botao de cadastrar novamente após editar a cor atual
                    request.setAttribute("corArrayStr" , corAtual);
                    request.getRequestDispatcher("/index.jsp?divAceso=divProduto").forward(request,response);
                }
            }
        }
        catch(Exception e){
            request.setAttribute("mensagemTelaListarCorAtual", "Não foi possivel editar cor atual!");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divListar");
            rd.forward(request, response);
        }
    }

    //ok
    private void ExcluirCorAtual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            String idDisp=request.getParameter("idDisp");
            int id=-1;

            if(idDisp !=null){
                try{
                    id=Integer.parseInt(idDisp);
                }catch(Exception e){
                    System.out.println("O numero digitado não é um inteiro.");
                    request.setAttribute("mensagemTelaListarCorAtual", "O número digitado não é um inteiro.");
                    request.getRequestDispatcher("/index.jsp?divAceso=divListar").forward(request,response);
                }
                
                if(id>=1){
                    Conexao con=new Conexao(SERVER, DB, USER, SENHA);;
                    con.Conectar();
                    CorAtual ca=new CorAtual();
                    Manipula_CorAtual mca=new Manipula_CorAtual(con, ca);                    
                    String mensagem = mca.excluirCorAtual(id); 
                    
                    request.setAttribute("mensagemTelaListarCorAtual", mensagem);
                    request.getRequestDispatcher("/index.jsp?divAceso=divListar").forward(request,response);                                     
                }
            }
        }
        catch(Exception e){
            request.setAttribute("mensagemTelaListarCorAtual", "Não foi possivel editar cor atual!");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divListar");
            rd.forward(request, response);
        }
    }
    
    //ok
    private void ReceberNivel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        try{
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String linha;
            while( (linha = reader.readLine()) != null ){
                jsonBuilder.append(linha);
            }
            
            String json = jsonBuilder.toString();            
                       
            Nivel nivel=new Nivel();
            
        //    Date d= new Date();
            //nivel.setDataNivel(new java.sql.Timestamp(date.getTime()));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //    java.sql.Date dSql = new java.sql.Date(df.parse(d).getTime());
            
        /*    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH); // O mês vai de 0 a 11.
            int semana = calendar.get(Calendar.WEEK_OF_MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            int minuto = calendar.get(Calendar.MINUTE);
            int segundo = calendar.get(Calendar.SECOND);        
       
            String data=ano+"-"+mes+"-"+dia+" "+hora+":"+minuto+":"+segundo;
        */    
            
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
                    
            Gson gson = new Gson();
            nivel=gson.fromJson(json, Nivel.class);
            nivel.setDataNivel(currentTime);
            
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();

            Manipula_Nivel mnv = new Manipula_Nivel(con, nivel);
            String mensagem = mnv.inserirDados();
            
            PrintWriter out = response.getWriter(); 
            out.println(mensagem);        
        }
        catch(Exception e){
            PrintWriter out = response.getWriter();
            out.println("Erro ReceberJson do Nível "+e);
        }
        
    }

    //ok
    private void ListarNivel (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Conexao con=new Conexao(SERVER, DB, USER, SENHA);
            con.Conectar();
            
            String dataInicial=request.getParameter("dataIncial");
            String dataFinal=request.getParameter("dataFinal");
            
            Nivel nv=new Nivel();
            Manipula_Nivel mnv=new Manipula_Nivel(con, nv);
            request.setAttribute("listaDeNiveis" , mnv.getListaNivel(dataInicial, dataFinal));
            request.getRequestDispatcher("/index.jsp?divAceso=divListar").forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("mensagemTelaListarNivel", "Não foi possivel listar Niveis! Erro:"+e);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?divAceso=divListar");
            rd.forward(request, response);
        }
    }
    

    
    
    //teste
    private void ReceberJson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String linha;
            while( (linha = reader.readLine()) != null ){
                jsonBuilder.append(linha);
            }
            
            String json = jsonBuilder.toString();
            
            Gson gson = new Gson();
            CorAtual cor=gson.fromJson(json, CorAtual.class);
            
            //teste
            PrintWriter out = response.getWriter();
            out.println(cor.getIdDisp()+" "+cor.getRed()+" "+cor.getBlue()+" "+cor.getGreen()+" "+cor.getStatus());                       
            System.out.print("hello word");
            System.out.print(cor.getIdDisp()+" "+cor.getRed()+" "+cor.getBlue()+" "+cor.getGreen()+" "+cor.getStatus());
        }
        catch(Exception e){
            PrintWriter out = response.getWriter();
            out.println("Erro ReceberJson "+e);
        }
                
    }
    
    
    // Para imprimir como uma pagina nova:
    //        PrintWriter saida=response.getWriter();  
    //        saida.println("<script>alert(\"entrou\""+idProd+");</script>"); 
    
    
} //fim da classe ProdutoServlet
