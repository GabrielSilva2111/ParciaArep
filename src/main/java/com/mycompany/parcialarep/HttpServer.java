package com.mycompany.parcialarep;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gabriel.silva
 */
import java.net.*;
import java.io.*;
import java.util.HashMap;

public class HttpServer {
    
    private static HttpConnectionExample httpConnection = new HttpConnectionExample();
    private static HashMap<String, String> cache = new HashMap<String, String>();
    
  public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   try { 
      serverSocket = new ServerSocket(36000);
   } catch (IOException e) {
      System.err.println("Could not listen on port: 36000.");
      System.exit(1);
   }

   Socket clientSocket = null;
   try {
       System.out.println("Listo para recibir ...");
       clientSocket = serverSocket.accept();
   } catch (IOException e) {
       System.err.println("Accept failed.");
       System.exit(1);
   }
   PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true);
   BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
   String inputLine, outputLine;
   boolean firstLine = true;
   String uriString = "";
   while ((inputLine = in.readLine()) != null) {
      System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
   }
    System.out.println("URI: " + uriString);
            if (uriString.split("=").length > 1) {
                outputLine = getHello(uriString.split("=")[1]);
            } else if (uriString.startsWith("/class")) {
                outputLine = "";
            } else {
                outputLine = getIndexResponse();
            }
    
   
    out.println(outputLine);
    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
  }

    private static String getIndexResponse() {
        
        String response = "HTTP/1.1 200 OK"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <title>Aplicación clase</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <h1>Nombre de la clase a buscar</h1>\n" +
"        <form action=\"/hello\">\n" +
"            <label for=\"name\">Name:</label><br>\n" +
"            <input type=\"text\" id=\"name\" name=\"name\" value=\"java.base\"><br><br>\n" +
"            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
"        </form> \n" +
"        <div id=\"getrespmsg\"></div>\n" +
"\n" +
"        <script>\n" +
"            function loadGetMsg() {\n" +
"                let nameVar = document.getElementById(\"name\").value;\n" +
"                const xhttp = new XMLHttpRequest();\n" +
"                xhttp.onload = function() {\n" +
"                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
"                    this.responseText;\n" +
"                }\n" +
"                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
"                xhttp.send();\n" +
"            }\n" +
"        </script>\n" +
"\n" +
"        <h1>Resultado del nombre de la clase </h1>\n" +
"        <form action=\"/hellopost\">\n" +
"            <label for=\"postname\">Name:</label><br>\n" +
"            <input type=\"text\" id=\"postname\" name=\"name\" value=\"java.io\"><br><br>\n" +
"            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
"        </form>\n" +
"        \n" +
"        <div id=\"postrespmsg\"></div>\n" +
"        \n" +
"        <script>\n" +
"            function loadPostMsg(name){\n" +
"                let url = \"/hellopost?name=\" + name.value;\n" +
"\n" +
"                fetch (url, {method: 'POST'})\n" +
"                    .then(x => x.text())\n" +
"                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>" ;
               return response ;
    }

    private static String getHello(String nameClass) throws IOException {
        
        if (cache.containsKey(nameClass)) {
            return "HTTP/1.1 200 OK"
                    + "Content-Type: text/html\r\n"
                    + "\r\n" + cache.get(nameClass);
        } else {
            String nameCLASS = httpConnection.getClass(nameClass);
            cache.put(nameClass,  nameCLASS );
            return "HTTP/1.1 200 OK"
                    + "Content-Type: text/html\r\n"
                    + "\r\n" ;
          
        }
        
    }
    
    
    
    
}
