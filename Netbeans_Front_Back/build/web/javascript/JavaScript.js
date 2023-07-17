
function qualDivAcender(div){
    
    if(div.length >4){ //a comparação com !=null nao funcionou, entao coloquei >4
        var telas=["divProduto","divListar","divListarLucro","divCores"];
        var span=["spanHome","spanListar","spanListarLucro","spanCores"];

        for( i=0; i<telas.length; i++){   
            if(telas[i]==div){
                document.getElementById(telas[i]).style.display="block";
                document.getElementById(span[i]).style.fontWeight="bold";
            } 
            else{
                document.getElementById(telas[i]).style.display="none";
                document.getElementById(span[i]).style.fontWeight="normal";   
            }   
        } 
    }
    else{
        document.getElementById("divProduto").style.display="block";
        document.getElementById("spanHome").style.fontWeight="bold";
    }
    
    //teste
    mostrarDataRealTimeJS("mostrarData");
}//fim qualDivAcender
    
//------- PIWEB -------
function setToSpan(){
    var red = document.getElementById("inpVermelho").value;
  document.getElementById("spVermelho").innerHTML = red;
  
    var green = document.getElementById("inpVerde").value;
  document.getElementById("spVerde").innerHTML = green;
  
    var blue = document.getElementById("inpAzul").value;
  document.getElementById("spAzul").innerHTML = blue;
  
    var box = document.getElementById("box_color");
    box.style.background = "rgb("+red+","+green+","+blue+")"; 
}

function validarCamposCores(form, acao, status){
    
    if(acao=="enviarCor"){
        document.querySelector("[name='acao']").value = "enviarCor";
        document.querySelector("[name='inpStatusLigDesl']").value = status;
        document.formCoresLampada.submit();
    }   
}

//teste
function mostrarDataRealTimeJS(acao){        
   var div = document.getElementById("hora");
   
    var interval = setInterval(function () {
        var date = new Date();
        div.innerHTML = date.toLocaleTimeString();

        //if (date.getSeconds() % 5 == 0) {
        //    div.innerHTML = "Intervalo cancelado :)";
        //    clearInterval(interval);
        //}
    }, 1000/* 1s */);
}









