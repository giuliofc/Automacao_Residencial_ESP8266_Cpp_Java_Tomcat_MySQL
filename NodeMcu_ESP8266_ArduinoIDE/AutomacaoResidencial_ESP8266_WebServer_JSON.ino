
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>

ESP8266WebServer server(80);

//Editar nome e senha do wifi de acordo com suas rede sem fio
#ifndef STASSID
#define STASSID "nome_do_wifi"
#define STAPSK  "senha_do_wifi"
#endif

#define pinAzulLed 13      //entrada D7
#define pinVerdeLed 12     //entrada D6
#define pinVermelhoLed 14  //entrada D5

const char* ssid = STASSID;
const char* password = STAPSK;

unsigned long millisTarefa1 = millis();
unsigned long millisTarefa2 = millis();

void setup() {
  Serial.begin(115200);

  //led imbutido na placa 
  //pinMode(LED_BUILTIN, OUTPUT);
  //digitalWrite(LED_BUILTIN, 0);

  pinMode(pinVermelhoLed, OUTPUT);
  pinMode(pinAzulLed, OUTPUT);
  pinMode(pinVerdeLed, OUTPUT);

  analogWrite(pinVermelhoLed, 0);
  analogWrite(pinAzulLed, 0);
  analogWrite(pinVerdeLed, 0);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  
  //Para ip fixo
  //IPAddress subnet(255,255,255,0);
  //WiFi.config(IPAdress(192,168,0,125), 
  //            IPAdress(192,168,0,1),
  //            subnet );    
                
  // Aguardando conectar na rede 
  Serial.println("");
  Serial.print(F("Conectando a "));
  Serial.println(ssid);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(F("."));
  }
  Serial.println("");
  Serial.println(F("WiFi connected"));
  // Print the IP address
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  
  //Atribuindo urls com metodo GET a função handleRoot
  //server.on("/index", HTTP_GET, handleRoot);

  server.on("/",[](){server.send(200,"text/plain","Hello World!");});

//  server.on("/consultarCorAtual", RequestCorAtual);

  //teste
  server.on("/recerberCorAtual", ReceberCorAtual);
  //teste
  server.on("/enviarCorAtual", EnviarCorAtual);
  //teste
  server.on("/postRequest" ,PostRequest);
  
  //teste
  //server.on("/pantilt",setPanTilt);

  //Aqui definimos qual função será executada caso o caminho que o cliente 
  //requisitou não tenha sido registrado
//VERIFICAR DEPOIS  server.onNotFound(onNotFound);
  
  // Iniciando Servidor
  server.begin();
  Serial.println(F("HTTP server started"));

}  //fim do setup


void loop() {

 //a função server.handleClient() fica eternamente lidando com a
 //conexão do cliente.
 server.handleClient();

  // Verifica se já passou 3000 milisegundos
  if((millis() - millisTarefa1) > 3000){
    millisTarefa1 = millis();
    RequestCorAtual();
  }

  if((millis() - millisTarefa2) > 10000){
    millisTarefa2 = millis();
    RequestEnviarNivel();
  }
   
} //fim do loop


//Função que definimos para ser chamada quando o caminho requisitado não foi registrado 
void onNotFound() 
{
  server.send(404, "text/plain", "Not Found" );
}

//Editar IP_do_Server_na_Nuvem de acordo com o ip do seu servidor
void RequestCorAtual(){
  //logica para realizar uma Request
  WiFiClient client;
  HTTPClient http;
  String url = "http://IP_do_Server_na_Nuvem:8080/Automacao_Residencial/ProdutoServlet?acao=consultarCorAtual";
  
  if (http.begin(client, url)) {   //tenta conexão com o destino
    Serial.println("http.begin ok");
  }

  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  int httpCode = http.POST("idDisp=1");  //envia request parametro idDisp=1

  String payload = http.getString(); //get the response payload

  Serial.println("Retorno do RequestCorAtual:");
  Serial.println(httpCode);  //Print HTTP return code
  Serial.println(payload);   //Print request response payload

  //logica para tratar JSON que chegou
  StaticJsonDocument<80> filter; //este filtro serve para desconsiderar o restante do Json alem destes parametros
  filter["idDisp"] = true;
  filter["red"] = true;
  filter["green"] = true;
  filter["blue"] = true;
  filter["status"] = true;
  
  StaticJsonDocument<192> doc;
  
  DeserializationError error = deserializeJson(doc, payload, DeserializationOption::Filter(filter));
  
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  
  int idDisp = doc["idDisp"]; // "1"
  int red = doc["red"]; // "150"
  int green = doc["green"]; // "90"
  int blue = doc["blue"]; // "163"
  int status_ = doc["status"]; // "1"
  
  server.send(200, "text/plain", "JSON consultaCorAtual recebido com sucesso." );

  if(status_==1){                      //ligado led
    analogWrite(pinVermelhoLed, red);
    analogWrite(pinAzulLed, green);
    analogWrite(pinVerdeLed, blue);
  }else{                              //desliga led
    analogWrite(pinVermelhoLed, 0);
    analogWrite(pinAzulLed, 0);
    analogWrite(pinVerdeLed, 0);
  }
  http.end();  //close connection
    
} //fim do ResquestCorAtual



void RequestEnviarNivel(){
  
  float nivel=(1.5/1023)*analogRead(A0);
  //float percentualNivel=(nivel*100)/1.5;
  Serial.print("Nivel: ");
  Serial.println(nivel);
  //Serial.println(percentualNivel);
  
  //site para gerar o filtor do Json https://arduinojson.org/
  StaticJsonDocument<64> doc;

  doc["idNivel"] = NULL;
  doc["idDisp"] = 1;
  doc["dataNivel"] = NULL;
  doc["nivel"] = nivel;

  String output;
  serializeJson(doc, output);
  
  //Editar IP_do_Server_na_Nuvem de acordo com o ip do seu servidor
  WiFiClient client;
  HTTPClient http;
  String url = "http://IP_do_Server_na_Nuvem:8080/Automacao_Residencial/ProdutoServlet?acao=receberNivel";
  
  if (http.begin(client, url)) {
    Serial.println("http.begin ok - RequestEnviarNivel");
  }

   http.addHeader("Content-Type", "application/json");
   int httpCode = http.POST(output);
        
   String payload = http.getString(); //get the response payload
   Serial.print("Retorno do RequestEnviarNivel: httpCode: ");  
   Serial.print(httpCode);  //Print HTTP return code
   Serial.print(" payload: ");
   Serial.println(payload);   //Print request response payload
  
   http.end();  //close connection  
}




//teste
void ReceberCorAtual(){
  //Stream& input = server.arg("plain");
  //String input = server.arg("plain");
  const char* input = server.arg("plain").c_str();
  
  StaticJsonDocument<80> filter;
  filter["idDisp"] = true;
  filter["red"] = true;
  filter["blue"] = true;
  filter["green"] = true;
  filter["status"] = true;
  
  StaticJsonDocument<192> doc;
  
  DeserializationError error = deserializeJson(doc, input, DeserializationOption::Filter(filter));
  
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  
  const char* idDisp = doc["idDisp"]; // "1"
  const char* red = doc["red"]; // "150"
  const char* blue = doc["blue"]; // "90"
  const char* green = doc["green"]; // "163"
  const char* status_ = doc["status"]; // "1"

  //return root["sensor"].as<char*>(); //para quando uma função tem retorno
 
  //String valores="Teste aqui";
  //String valores= (String)idDisp+" "+(String)red+" "+(String)blue+" "+(String)green+" "+(String)status_;
  server.send(200, "text/plain", red );
}

//teste
void EnviarCorAtual(){

  StaticJsonDocument<128> doc;

  doc["idDisp"] = "1";
  doc["red"] = "139";
  doc["blue"] = "67";
  doc["green"] = "163";
  doc["status"] = "1";

  String output;
  serializeJson(doc, output);
  
  server.send(200, "text/plain", output );
}


//teste
void PostRequest(){
  WiFiClient client;
  HTTPClient http;
  String url = "http://192.168.1.114:8080/Automacao_Residencial/ProdutoServlet?acao=recerberJson";
  //Serial.println(url);
  
  if (http.begin(client, url)) {
    Serial.println("http.begin ok");
  }
  //delay(100);

  StaticJsonDocument<128> doc;

  doc["idDisp"] = "1";
  doc["red"] = "139";
  doc["blue"] = "67";
  doc["green"] = "163";
  doc["status"] = "1";

  String output;
  serializeJson(doc, output);


   http.addHeader("Content-Type", "application/json");
   int httpCode = http.POST(output);

   String payload = http.getString(); //get the response payload

   Serial.println(httpCode);  //Print HTTP return code
   Serial.println(payload);   //Print request response payload

   http.end();  //close connection
  
  //http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  //int httpCode = http.POST("var1=2");
}



/*
void setPanTilt()
{
  String data = server.arg("plain");
  StaticJsonBuffer<200> jBuffer;
  JsonObject& jObject = jBuffer.parseObject(data);
  String pan = jObject["pan"];
  String tilt = jObject["tilt"];
  //servo_pan.write(pan.toInt());
  //servo_tilt.write(tilt.toInt());
  //server.send(204,"application/json",jObject["pan"]);
  jObject.printTo(Serial);
}
*/

/*
void handleRoot(){
  //HTML da pagina principal
  String html="<html><head><title>Exemplo</title>";
  html+="<style> body {background-color:#8F8FE3; ";
  html+="font-family:Arial;";
  html+="color:#000088; }</style>";
  html+="</head></body>";
  html+="<h1>Exemplo NodeMcu Web Server</h1>";
  
  html+="<p>";
    if(server.hasArg("red")) {
      //String arg(String name) retorna o valor pelo nome do parametro
      //String arg(int i) retorna o valor pelo indice do parametro
      html+="Valor da Cor Vermelha=";
      html+=server.arg("red");
      analogWrite(pinVermelhoLed, atoi( server.arg("red").c_str() ) );
    }
  html+="</p>";
  html+="<p>";
    if(server.hasArg("blue")) {
      html+="Valor da Cor Azul=";
      html+=server.arg("blue");
      analogWrite(pinAzulLed, atoi( server.arg("blue").c_str() ) );
    }
  html+="</p>";
  html+="<p>";
    if(server.hasArg("green")) {
      html+="Valor da Cor Verde=";
      html+=server.arg("green");
      analogWrite(pinVerdeLed, atoi( server.arg("green").c_str() ) );
    }
  html+="</p>";
    
  html+="<p><a href=/ ></a></p>";
  html+="</body></html>";
  //Enviando HTML para o servidor
  server.send(200, "text/html", html);
} */
