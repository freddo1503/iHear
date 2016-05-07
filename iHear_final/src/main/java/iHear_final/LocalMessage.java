/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iHear_final;

/**
 *
 * @author Alexis
 */
public class LocalMessage {
    private int id;
    private String timestamp;
    private String contenu;
    private String auteur;
    
    public LocalMessage(){
        id = 0;
        timestamp = "";
        contenu = "";
        auteur = "";
    }
    
    public void setTime(String s){
        timestamp = s;
    }
    
    public void setAuthor(String s){
        auteur = s;
    }
    
    public void setText(String s){
        contenu = s;
    }
    
    public String getTime(){
        return timestamp;
    }
    
    public String getAuthor(){
        return auteur;
    }
    
    public String getText(){
        return contenu;
    }
}


