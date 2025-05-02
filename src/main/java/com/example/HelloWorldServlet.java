package com.example;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class HelloWorldServlet extends HttpServlet {


    ArrayList<Object> Retr(){
    
        ArrayList<Object> arr = new ArrayList<>();

        int a = 5;

        arr.add(a);
        arr.add(2);

        return arr;

    }



    String players = "";
    int playerCount = 6;
    @Override
    public void init(){
        players = "";
        for (int i = 0; i < 6; i++) {
            players += "+O+";
        }
    }



    int shots = 6;
    float sansa;
    String[] Rulet(int broj, String ab[]){
    
        // ako je pucanje
        if(broj < 7 && broj > 0){

            broj = broj - 1;
        
            // ako nije vec pucan
            if(ab[broj * 3].equals("+") && ab[broj * 3 + 1].equals("O")){

                shots--;
                if(shots < 1){ shots = 6; }


                ab[broj * 3] = "_";

                if((int)(Math.random() * shots) <= 1){
                    ab[broj * 3 + 1] = "X"; // pogoden
                    shots = 6;

                    ab = Victory(ab);
                }

                
                ab = CheckNewRound(ab);

                sansa = 1f / shots;
            }
        return ab;
        }
        // ako je reset
        else if(broj == 0){
           return Reset();
        }

        return ab;

    }


    String[] Reset(){
    
        players = "";
        for (int i = 0; i < playerCount; i++) {
            players += "+O+";
        }
        shots = 6; 
        sansa = 1f / shots;
        return players.split("");
    
    }


    String[] CheckNewRound(String ab[]){
    
         // ako su svi upucani i/ili mrtvi 

         int newRound = 0;
         int dead = 0;

         for (int i = 0; i < playerCount; i++) {
             if(ab[i * 3].equals("_")){ 
                 newRound++;
             }
         }

         // ako su svi ili pucani ili ubijeni
         if(6 == newRound + dead){
        
            for (int i = 0; i < playerCount; i++) {
                if(ab[i * 3 + 1].equals("O")){ 
                    ab[i * 3] = "+";
                }
            }
        
        }

        return ab;
    
    }


    String[] Victory(String ab[]){
    
        int victory = 0;
        int victorious = -1;
        for (int i = 0; i < playerCount; i++) {
            if(ab[i * 3 + 1].equals("X")){
                victory++;
            }
            else if(victorious == -1) { victorious = i; } 
            else { victorious = -1; break; }
        }

        if(victory == playerCount - 1){
            ab[0] = "V";
            ab[1] = String.valueOf(victorious + 1);
        }
    

        return ab;
    }




    String WriteResp(String ab[]){
        
        String fin = "";
        
        //fin += "<h1>" + ab[0] + "  " + ab[1] + "</h1>";

        if(!ab[0].equals("V")){
        fin += "<div class = 'pl'>";
        fin += "<h1>";
        for (int i = 0; i < 6; i++) {
            fin += " ";
            fin += " " + ab[i * 3] + " ";
            fin += " ";
        }
        fin += "</h1>";
        fin += "<h1>";
        for (int i = 0; i < 6; i++) {
            fin += "" + ab[1 + i * 3] + "";
        }
        fin += "</h1>";
        fin += "</div>";

        fin += "<h4> Sansa za metak je " + (int)(sansa * 100) + "%</h4>";
    }
    else{
        fin += "<h4> Pobjedio je igrač broj " + ab[1] + "</h4>";
    }


        return fin;
    
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        

        
        String brojStr = request.getParameter("input");
        String resetStr = request.getParameter("reset");

        int broj = 0;
        if (resetStr == null) {
            try {
                broj = Integer.parseInt(brojStr);
            } catch (NumberFormatException e) {
                response.getWriter().println("Neispravan unos.");
            }
        }

        
        int rezultat = broj;

        String ab[] = new String[18];
        ab = players.split("");
        

        ab = Rulet(rezultat, ab);
        
        response.getWriter().println("<html> <head> <style> *{text-align: center; } .pl {line-height: 0; } </style> </head> <body>");

        response.getWriter().println(WriteResp(ab));



        players = "";
        for (int i = 0; i < ab.length; i++) {
            players += ab[i];
        }


        response.getWriter().println("<form id='inputForm'>"
                + "<input type='number' name='input' id='input' required>"
                + "<button type='submit'>Pucaj</button>"
                + "</form>");


        response.getWriter().println(
                "<form id='resetForm' method='get' action='servlet'>" +
                "<input type='hidden' name='reset' value='0'>" +
                "<button type='submit'>Ponovo</button>" +
                "</form>" );
            
        response.getWriter().println("<a href=\"index.html\">Natrag</a>");
        

    }


}
