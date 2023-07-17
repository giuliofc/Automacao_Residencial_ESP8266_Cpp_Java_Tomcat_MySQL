<%-- 
    Document   : index
    Created on : 09/08/2021, 09:59:47
    Author     : giulio
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
        <link rel="stylesheet" type="text/css" href="css/estilo.css">
        
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="javascript/JavaScript.js"> </script>
        <script>
            var interval = setInterval(function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
                $.get("RealTimeServlet?acao=mostrarNivel", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });}, 10000/* 10s */);
            
          /*  $(document).on("click", "#somebutton", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
                $.get("RealTimeServlet?acao=mostrarNivel", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
            });
           */
        </script>
        
        <% 
            String div=request.getParameter("divAceso");
            //System.out.println(div);             
        %>
    </head>
    
    <body id="bodyIndex" onload="qualDivAcender('<%=div%>');"> 
    <div id="divMain">    
        <!-- Inicio do header -->
        <div id="divHeader">
            <div id="divImg">
                <img src="imagens/logotipo.png">
            </div>

            <div id="divNomeDoPrograma">
                <p>Automação Residencial</p>
            </div> 
                
            <div id="hora">00:00:00</div> <!-- fica mostrando a hora atual -->
        </div>  <!-- Fim do header -->
        
        
        <!--Incio do Menu-->
        <div id="divLinksMenu">
                    <span id="spanHome"><a href="#" onclick="qualDivAcender('divProduto');">Home</a></span> | 
                    <span id="spanListar"><a href="#" onclick="qualDivAcender('divListar');"> Listar </a></span> <!-- | 
                    <span id="spanCadastrar"><a href="#" onclick="qualDivAcender('divCadastrar');"> Cadastrar </a></span> | 
                    <span id="spanCores"><a href="#" onclick="qualDivAcender('divCores');"> Cores </a></span> -->
        </div> <!--fim do Menu-->
        
        
        <!--    Inicio da tela Automação   --> 
        <div id="divProduto" style="display:block">
            <div id="divPrincipalProduto">
                <div id="divConteudoCores">
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cores da Lampada</span>
                <div id="divForm1">
                    <form id="formCoresLampada" name="formCoresLampada" action="ProdutoServlet" method="post">
                    <input type="hidden" name="acao" value=""> <!--a ação será adicionada aqui ao passar pelo javascript dependendo do botão precionado-->                   
                    <!--<input type="hidden" id="inpProdutoID" name="inpProdutoID" value='<c:out value="${idProduto}" default=""/>'> -->
                        <table id="tblCadastro">
                            <tr>
                                <td>
                                    <table id="tblCamposCadastro">                                        
                                     <!--   <tr>
                                            <td id="tdApelidoCor" class="textRight"><span>Apelido:</span></td>
                                            <td><input id="inpApelidoCor" type="text" name="inpApelidoCor" value='<c:out value=""/>' size="30" maxlength="50" onblur="validarApelidoCor(formCoresLampada.inpApelidoCor);" onfocus="apagaMensagem();"></td>
                                        </tr>
                                        <tr>
                                            <td id="tdIdCor" class="textRight"><span>ID Cor</span></td>
                                            <td><input id="inpIdCor" type="number" value='<c:out value=""/>' step="1.0" min="1.0" max="9999" name="inpIdCor" value="" onblur="validarIdCor(formCoresLampada.inpIdCor);" onfocus="apagaMensagem();" ></td>
                                        </tr> 
                                     -->
                                        <tr>
                                            <td id="tdVermelho" class="textRight"><span>Red</span></td>
                                            <td><input id="inpVermelho" type="range" class="barCores vermelho" oninput="setToSpan();" value='<c:out value="${corArrayStr[1]}"/>' step="1" min="1" max="255" name="inpVermelho" ></td>
                                            <td><span id="spVermelho"></span></td>
                                        </tr>
                                        <tr>
                                            <td id="tdVerde" class="textRight"><span>Green</span></td>
                                            <td><input id="inpVerde" type="range" class="barCores verde" oninput="setToSpan();" value='<c:out value="${corArrayStr[2]}"/>' step="1" min="1" max="255" name="inpVerde" value="" onfocus="apagaMensagem();" ></td>
                                            <td><span id="spVerde"></span></td>
                                        </tr>
                                        <tr>
                                            <td id="tdAzul" class="textRight"><span>Blue</span></td>
                                            <td><input id="inpAzul" type="range" class="barCores azul" oninput="setToSpan();" value='<c:out value="${corArrayStr[3]}"/>' step="1" min="1" max="255" name="inpAzul" value="" onfocus="apagaMensagem();" ></td>
                                            <td><span id="spAzul"></span></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><div id="box_color"></div></td>
                                        </tr>
                                        <tr>
                                            <td id="tdIdDisp" class="textRight"><span>ID Disp</span></td>
                                            <td><input id="inpIdDisp" type="number" value='<c:out value="${corArrayStr[0]}"/>' step="1" name="inpIdDisp" min="1" max="9999" value="" onfocus="apagaMensagem();"></td>                                            
                                        </tr>
                                <!--        <tr>
                                            <td id="tdApelidoDisp" class="textRight"><span>Apelido</span></td>
                                            <td><input id="inpApelidoDisp" type="text" value='<c:out value=""/>' name="inpApelidoDisp" value="" size="30" maxlength="25" onfocus="apagaMensagem();" readonly=""></td>
                                        </tr>
                                -->
                                        <input type="hidden" id="inpStatusLigDesl" name="inpStatusLigDesl" value='<c:out value="${corArrayStr[4]}"/>' >
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                
                <div id="divBotoesCor">
                    <table>
                        <tr>
                            <td><input id="inpEnviarCor" type="button" value="Enviar/Ligar" onclick="validarCamposCores(formCoresLampada, 'enviarCor', '1');"></td>                            
                            <td><input id="inpLigaDesCor" type="button" value="Desligar" onclick="validarCamposCores(formCoresLampada, 'enviarCor', '0');"></td>                           
                        <!--    <td><input id="inpSalvarCor" type="button" value="Salvar Cor" onclick="" ></td> -->
                        </tr>
                    </table>
                </div>
                                                                        
                <div style="margin:60px 0px 0px 60px;">
                    <h4 id="mensagemResultadoForm">${mensagemFormCores}</h4>              
                </div>
                </div> <!--fim do divConteudoCores-->
                
                <!-- Inicio mostrando ultima leitura de Nivel -->
                <div id="divNivelRealTime">
                    <span id="spNivelRealTime">Nível Atual</span>                    
                    <div id="somediv"></div>   
                </div>
            
            </div> <!<!-- fim do divPrincipalProduto -->
        
            
        </div> <!--   fim da tela produto   -->
        
                
        <!--    Inicio da Tela Listar    -->
        <div id="divListar" style="display:none;">
            <div id="divConteudoListarCor">
                <div id="divTituloListar"><h3>Listar Cor Atual</h3></div>

                <form id="formListarCorAtual" name="formListarCorAtual" action="ProdutoServlet" method="post">
                    <input type="hidden" name="acao" value="listarCorAtual">                   
                    <input id="btListarCorAtual" class="btListar" type="submit" value="Listar Cor Atual" />
                </form>

                <div id="divMensagemTelaListarCorAtual"><h4 id="mensagemResultadoListarCorAtual">${mensagemTelaListarCorAtual}</h4></div>

                <table class="comBordas">
                    <th class="simetricas1">IdDisp</th><th class="simetricas1">Red</th><th class="simetricas1">Green</th><th class="simetricas1">Blue</th>
                    <th class="simetricas1">status</th><th class="simetricas1">Editar</th><th class="simetricas1">Excluir</th>
                    <c:forEach items="${coresAtuais}" var="corAtual" varStatus="status">
                        <tr> <td><c:out value="${corAtual.idDisp}"/></td> <td><c:out value="${corAtual.red}"/></td> 
                            <td><c:out value="${corAtual.green}"/></td> <td><c:out value="${corAtual.blue}"/></td>
                            <td><c:out value="${corAtual.status}"/></td> 
                            <td><a href='ProdutoServlet?acao=editarCorAtual&idDisp=<c:out value="${corAtual.idDisp}"/>'>Editar</a></td>
                            <td><a href='ProdutoServlet?acao=excluirCorAtual&idDisp=<c:out value="${corAtual.idDisp}"/>'>Excluir</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            
            <div id="divConteudoListarNivel">
                <div id="divTituloNivel"><h3>Listar Nivel</h3></div>
                
                <form id="formListarNivel" name="formListarNivel" action="ProdutoServlet" method="post">
                    <table cellspacing="5px">
                        <input type="hidden" name="acao" value="listarNivel">
                        <tr>
                            <td><span>Data Inicial:</span></td>
                            <td><input id="dataIncial" type="datetime-local" name="dataIncial" min="2021-10-9T17:00" max=""></td>
                        </tr>
                        <tr>
                            <td><span>Data Final:</span></td>
                            <td><input id="dataFinal" type="datetime-local" name="dataFinal" min="2021-10-9T17:00" max=""></td>
                        <tr>
                            <td colspan="2"><input id="btListarNivel" class="btListar" type="submit" value="Listar Nivel" /></td>
                        </tr>
                    </table>
                </form>
                
                <div id="divMensagemTelaListarNivel"><h4 id="mensagemResultadoListarNivel">${mensagemTelaListarNivel}</h4></div>
                
                <table class="comBordas">
                    <th class="simetricas1">idNivel</th><th class="simetricas1">idDisp</th><th class="simetricas1" style="width:170px;">dataNivel</th><th class="simetricas1">nivel</th>
                    <c:forEach items="${listaDeNiveis}" var="nivel" varStatus="status">
                        <tr> <td><c:out value="${nivel.idNivel}"/></td> <td><c:out value="${nivel.idDisp}"/></td> 
                            <td><c:out value="${nivel.dataNivel}"/></td> <td><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${nivel.nivel}" /></td>                     
                        </tr>
                    </c:forEach>                
                </table>
                
            </div>
            
            
            
        </div> <!--   fim da tela listar   --> 
                     
    
    </div> <!--fim do div main-->
    </body>
</html>
