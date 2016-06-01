package iHear_final;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexis
 */
public class Chat_admin extends javax.swing.JFrame {

    /**
     * Creates new form Chat_admin
     */
    public Chat_admin() {
        initComponents();
    }
    
    private boolean startConnection(){
        /* Code relatif à la connexion à la base de données */
        /* Chargement du driver JDBC pour MySQL */
        String urlBDD = "jdbc:mysql://localhost:3306/projet_java";
        String user = "root";
        String password = "";
        c = null;

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
            //System.exit(0); /* Penser à l'affichage d'une erreur */
            System.out.println("ERREUR SGBD");
            return false;
        }

        try{
            c = DriverManager.getConnection(urlBDD, user, password);
            /* requete ici*/

            System.out.println("Connection OK.");
        } catch ( SQLException e ) {
            /* Gérer les éventuelles erreurs ici */
            System.out.println(e);
        } 
        
        return true;
    }
    
    private void endConnection(){
        if ( c != null )
            try {
            /* Fermeture de la connexion */
                System.out.println("Connection KO.");
                c.close();
                c = null;
            } catch ( SQLException ignore ) {
            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */

            }
        
    }
    
    private boolean cmpString(String cmp, String cmp2){
        boolean retour = true;
        
        if(cmp.length() != cmp2.length())
            retour = false;

        if(retour)
            for(int i = 0; i < cmp.length(); i++){
                if(cmp.charAt(i) != cmp2.charAt(i)){
                    retour = false;
                }
            }
        
        return retour;
    }
    
    /* parcourt le string et remplace le caractère demandé */ 
    private String remplaceThisIn(char toProtect, String data){
        String retour = "";
        
        for(int i = 0; i < data.length(); i++){
            if(data.charAt(i) == toProtect){
                retour = retour + '\\' + data.charAt(i);
            }
            else{
                retour = retour + data.charAt(i);
            }
                
        }
        return retour;
    }
    
    private boolean getPseudoByDatabase(String pseudo){
        Statement s = null;
        ResultSet r = null;
        boolean retour = false;
        
        try{
            s = c.createStatement();
            r = s.executeQuery("SELECT username FROM UTILISATEURS WHERE username like '" + pseudo + "'");
            
            if(r.next()){
                if(cmpString(pseudo, r.getString("username"))){
                    /*System.out.println(r.getString("username"));*/
                    retour = true;
                }
            }
            
        } catch ( SQLException e ) {
            System.out.println("funtion getPseudoByDatabase() " + e);
        } finally {
            if ( r != null ) { /* fermeture resultset */
                try {
                    r.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( s != null ) { /* fermeture statement */
                try {
                    s.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        
        return retour;
    }
    
    private int getIdUser(String username){
        Statement s = null;
        ResultSet r = null;
        int retour = 0;
        
        try{
            s = c.createStatement();
            r = s.executeQuery("SELECT idUser FROM UTILISATEURS WHERE username like '" + username + "'");
            
            if(r.next()){
                retour = r.getInt("idUser");
            }
            
        } catch ( SQLException e ) {
            System.out.println("funtion getIdUser() " + e);
        } finally {
            if ( r != null ) { /* fermeture resultset */
                try {
                    r.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( s != null ) { /* fermeture statement */
                try {
                    s.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        
        return retour;
    }
    
    private boolean getSaloonByDatabase(String intitule){
        Statement s = null;
        ResultSet r = null;
        boolean retour = false;
        
        try{
            s = c.createStatement();
            r = s.executeQuery("SELECT intitule FROM SALONS WHERE intitule like '" + intitule + "'");
            
            if(r.next()){
                if(cmpString(intitule, r.getString("intitule"))){
                    /*System.out.println(r.getString("username"));*/
                    retour = true;
                }
            }
            
        } catch ( SQLException e ) {
            System.out.println("funtion getSaloonByDatabase() " + e);
        } finally {
            if ( r != null ) { /* fermeture resultset */
                try {
                    r.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( s != null ) { /* fermeture statement */
                try {
                    s.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        
        return retour;
    }
    
    private int getIdSaloon(String intitule){
        Statement s = null;
        ResultSet r = null;
        int retour = 0;
        
        try{
            s = c.createStatement();
            r = s.executeQuery("SELECT idSalon FROM SALONS WHERE intitule like '" + intitule + "'");
            
            if(r.next()){
                retour = r.getInt("idSalon");
            }
            
        } catch ( SQLException e ) {
            System.out.println("funtion getIdSaloon() " + e);
        } finally {
            if ( r != null ) { /* fermeture resultset */
                try {
                    r.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( s != null ) { /* fermeture statement */
                try {
                    s.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        
        return retour;
    }
    
    private boolean isRelationBetween(String salon, String user){
        Statement s = null;
        ResultSet r = null;
        boolean retour = false;
        
        try{
            s = c.createStatement();
            r = s.executeQuery("SELECT idSalon FROM UTILISER WHERE idSalon like '" + getIdSaloon(salon) + "' and idUser like '" + getIdUser(user) + "'");
            
            if(r.next()){
                retour = true;
            }
            
        } catch ( SQLException e ) {
            System.out.println("funtion getIdSaloon() " + e);
        } finally {
            if ( r != null ) { /* fermeture resultset */
                try {
                    r.close();
                } catch ( SQLException ignore ) {
                }
            }
            if ( s != null ) { /* fermeture statement */
                try {
                    s.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        
        return retour;
    }
    
    private void creerUtilisateur(String pseudo, String pwd, String nom, String prenom, Boolean admin, String description){
        Statement s = null;
        int r = 0;
        String typeProfil = "admin";
        
        /* Vérifier la présence d'un utilisateur ayant le pseudo du pseudo */
        System.out.println("ECHO");
        
        /* Préparation des variables */
        if(!admin)
            typeProfil = "user";        
        
        try {
            s = c.createStatement();
            r = s.executeUpdate("INSERT INTO UTILISATEURS (idUser, username, password, prenom, nom, typeprofil, description) VALUES (DEFAULT, '" + pseudo + "', '" + pwd +"', '" + prenom + "', '" + nom + "', '" + typeProfil + "', '" + description + "')");
            
        } catch ( SQLException e ) {
            System.out.println("funtion creerUtilisateur() " + e);
        } 
    }
    
    private void modifierUtilisateur(String pseudo, String pwd, String nom, String prenom, String description){
        Statement s = null;
        int r = 0;
        
        /* Préparer les strings à ajouter à la requête !! */
        String virgule = " ";
        String requete = "UPDATE UTILISATEURS SET";
        
        if(!pwd.isEmpty()){
            requete = requete + virgule + "password = '" + pwd +"'";
            virgule = ", ";
        }
        
        if(!nom.isEmpty()){
            requete = requete + virgule + "nom =  '" + nom +"'";
            virgule = ", ";
        }
        
        if(!prenom.isEmpty()){
            requete = requete + virgule + "prenom =  '" + prenom +"'";
            virgule = ", ";
        }
        
        if(!description.isEmpty()){
            requete = requete + virgule + "description =  '" + description +"'";
            virgule = ", ";
        }
        
        try {
            s = c.createStatement();
            r = s.executeUpdate(requete + " WHERE username = '" + pseudo + "'");
            
        } catch ( SQLException e ) {
            System.out.println("funtion modifierUtilisateur() " + e);
        }
    }

    private void creerSalon(String intitule, String couleur, String description){
        Statement s = null;
        int r = 0;
        
        /* Préparation des variables */
        String requete = "INSERT INTO SALONS (idSalon, intitule, couleur";
        String finRequete = "VALUES (DEFAULT, '" + intitule + "'";
        
        if(!couleur.isEmpty()){
            finRequete = finRequete + ", '" + couleur + "'";
        }
        else
            finRequete = finRequete + ", DEFAULT";
        
        if(!description.isEmpty()){
            requete = requete + ", description";
            finRequete = finRequete + ", '" + description + "'";
        }
        
        requete = requete + ") ";
        finRequete = finRequete + ")";
        
        try {
            s = c.createStatement();
            r = s.executeUpdate(requete + finRequete);
            
        } catch ( SQLException e ) {
            System.out.println("funtion creerSalon() " + e);
        } 
    }
    
    private void modifierSalon(String intitule, String couleur, String description){
        Statement s = null;
        int r = 0;
        
        /* Préparer les strings à ajouter à la requête !! */
        String virgule = " ";
        String requete = "UPDATE SALONS SET";
        
        if(!couleur.isEmpty()){
            requete = requete + virgule + "couleur = '" + couleur +"'";
            virgule = ", ";
        }
        
        if(!description.isEmpty()){
            requete = requete + virgule + "description =  '" + description +"'";
            virgule = ", ";
        }
        
        try {
            s = c.createStatement();
            r = s.executeUpdate(requete + " WHERE intitule = '" + intitule + "'");
            
        } catch ( SQLException e ) {
            System.out.println("funtion modifierSalon() " + e);
        }
    }
    
    private void ajouterRelation(String salon, String utilisateur, int role){
        Statement s = null;
        int r = 0;
        
        /* Préparation des variables */
        String requete = "INSERT INTO UTILISER (idUser, idSalon, role) VALUES ('" + getIdUser(utilisateur) + "', '" + getIdSaloon(salon) + "', '" + role + "')";
                
        try {
            s = c.createStatement();
            r = s.executeUpdate(requete);
            
        } catch ( SQLException e ) {
            System.out.println("funtion creerRelation() " + e);
        } 
    }
    
    private void retirerRelation(String salon, String utilisateur){
        Statement s = null;
        int r = 0;
        
        /* Préparation des variables */
        String requete = "DELETE FROM UTILISER WHERE idUser = '" + getIdUser(utilisateur) + "' and idSalon = '" + getIdSaloon(salon) + "';";
                
        try {
            s = c.createStatement();
            r = s.executeUpdate(requete);
            
        } catch ( SQLException e ) {
            System.out.println("funtion retirerRelation() " + e);
        } 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField25 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jTextField33 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("iHear - Administration");
        setResizable(false);

        startConnection();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Créer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Pseudonyme :");

        jLabel2.setText("Mot de passe :");

        jLabel3.setText("Confirmation MdP :");

        jLabel4.setText("Nom :");

        jLabel5.setText("Prénom :");

        jLabel6.setText("Administrateur :");

        jLabel7.setText("Description :");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton1.setText("Créer");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(44, 44, 44)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(32, 32, 32)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField6)))
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jCheckBox1))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(68, 68, 68))
        );

        jTextField1.getAccessibleContext().setAccessibleName("utCreerPseudo");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modifier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jLabel8.setText("Pseudonyme :");

        jLabel9.setText("Mot de passe :");

        jLabel10.setText("Confirmation MdP :");

        jLabel11.setText("Nom :");

        jLabel12.setText("Prénom :");

        jLabel14.setText("Description :");

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jButton2.setText("Modifier");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(170, 0, 0));
        jLabel15.setText("Si un champ reste vide, son attribut ne sera pas changé.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField12))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(44, 44, 44)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addGap(32, 32, 32)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, Short.MAX_VALUE)
                .addGap(93, 93, 93))
        );

        jTabbedPane1.addTab("Utilisateurs", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Créer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jLabel19.setText("Nom :");

        jLabel21.setText("Couleur");

        jLabel22.setText("Description :");

        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jButton3.setText("Créer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "white", "blue", "red", "pink", "green", "yellow" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(543, 543, 543)
                        .addComponent(jButton3))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(44, 44, 44)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(53, 53, 53)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(63, 63, 63))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modifier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jLabel23.setText("Nom :");

        jLabel25.setText("Couleur");

        jLabel26.setText("Description :");

        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });

        jButton4.setText("Modifier");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel39.setForeground(new java.awt.Color(170, 0, 0));
        jLabel39.setText("Si un champ reste vide, son attribut ne sera pas changé.");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "white", "blue", "red", "pink", "green", "yellow" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(44, 44, 44)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(208, 208, 208)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Salons", jPanel2);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajouter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jLabel16.setText("Nom du salon :");

        jButton5.setText("Ajouter");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });

        jLabel17.setText("Nom de l'utilisateur :");

        jLabel18.setText("Spectateur :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel17)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(jCheckBox3))
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Retirer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 0, 0)));

        jLabel36.setText("Nom du salon :");

        jButton9.setText("Retirer");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });

        jLabel37.setText("Nom de l'utilisateur :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(jLabel37)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton9))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ajouter/Retirer", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    /* Créer un utilisateur ! */
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        //Créer un utilisateur !!
        boolean creer = true;
        
        String  pseudo = jTextField1.getText(),
                pwd = jTextField2.getText(),
                pwd2 = jTextField3.getText(),
                nom = jTextField4.getText(),
                prenom = jTextField5.getText(),
                desc = jTextField6.getText();
        
        Boolean admin = jCheckBox1.isSelected();
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField1, 2, 3, 4, 5 et 6 */
        if(pseudo.isEmpty()){
            jTextField1.setBackground(Color.red);
            creer = false;
        } else
            jTextField1.setBackground(Color.white);
        
        if(pwd.isEmpty()){
            jTextField2.setBackground(Color.red);
            creer = false;
        } else
            jTextField2.setBackground(Color.white);
        
        if(pwd2.isEmpty()){
            jTextField3.setBackground(Color.red);
            creer = false;
        } else
            jTextField3.setBackground(Color.white);
        
        if(nom.isEmpty()){
            jTextField4.setBackground(Color.red);
            creer = false;
        } else
            jTextField4.setBackground(Color.white);
        
        if(prenom.isEmpty()){
            jTextField5.setBackground(Color.red);
            creer = false;
        }
        else
            jTextField5.setBackground(Color.white);
        
        if(pwd2.isEmpty()){
            jTextField6.setBackground(Color.red);
            creer = false;
        }
        else
            jTextField6.setBackground(Color.white);
           
        if(!cmpString(pwd, pwd2)){ /* Si les mots de passe sont différents */
            jTextField2.setForeground(Color.red);
            jTextField3.setForeground(Color.red);
            creer = false;
        }
        else{
            jTextField2.setForeground(Color.black);
            jTextField3.setForeground(Color.black);
        }
        
        /* Si le pseudo est déjà pris : erreur */
        if(getPseudoByDatabase(pseudo)){
            jTextField1.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField1.setBackground(Color.white);
        //System.out.println(pseudo + " " + pwd + " " + nom + " " + prenom + " " + desc);
        
        if(creer){
            creerUtilisateur(pseudo, pwd, nom, prenom, admin, desc);
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
    }//GEN-LAST:event_jTextField3ActionPerformed

    /* Modifier les données utilisateurs */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // On modifie les données d'un utilisateur !!
        
        boolean creer = true;
        
        String  pseudo = jTextField7.getText(),
                pwd = jTextField8.getText(),
                pwd2 = jTextField9.getText(),
                nom = jTextField10.getText(),
                prenom = jTextField11.getText(),
                desc = jTextField12.getText();
        
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField1, 2, 3, 4, 5 et 6 */
        if(pseudo.isEmpty()){
            jTextField7.setBackground(Color.red);
            creer = false;
        } else
            jTextField7.setBackground(Color.white);
        
        if((!pwd.isEmpty() || !pwd2.isEmpty()) && !cmpString(pwd, pwd2)){ /* Si les mots de passe sont différents */
            jTextField8.setForeground(Color.red);
            jTextField9.setForeground(Color.red);
            creer = false;
        }
        else{
            jTextField8.setForeground(Color.black);
            jTextField9.setForeground(Color.black);
        }
        
         /* Si aucun autre variable que le pseudo n'est remplie */
        if(pwd.isEmpty() && pwd2.isEmpty() && nom.isEmpty() && prenom.isEmpty() && desc.isEmpty()){
            jTextField8.setBackground(Color.red);
            jTextField9.setBackground(Color.red);
            jTextField10.setBackground(Color.red);
            jTextField11.setBackground(Color.red);
            jTextField12.setBackground(Color.red);
            creer = false;
        }
        else{
            jTextField8.setBackground(Color.white);
            jTextField9.setBackground(Color.white);
            jTextField10.setBackground(Color.white);
            jTextField11.setBackground(Color.white);
            jTextField12.setBackground(Color.white);
        }
        
        /* Si le pseudo n'est pas pris : erreur */
        if(!getPseudoByDatabase(pseudo)){
            jTextField7.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField7.setBackground(Color.white);
        
        //System.out.println(pseudo + " " + pwd + " " + nom + " " + prenom + " " + desc);
        
        if(creer){
            jTextField7.setText("");
            jTextField8.setText("");
            jTextField9.setText("");
            jTextField10.setText("");
            jTextField11.setText("");
            jTextField12.setText("");
            
            modifierUtilisateur(pseudo, pwd, nom, prenom, desc);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    
    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    /* Création d'un salon */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Créer un utilisateur !!
        boolean creer = true;
        
        String  nom = jTextField16.getText(),
                couleur = (String) jComboBox2.getSelectedItem(),
                desc = jTextField18.getText();
        
        desc = remplaceThisIn('\'', desc);
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField16 et 18 */
        if(nom.isEmpty()){
            jTextField16.setBackground(Color.red);
            creer = false;
        } else
            jTextField16.setBackground(Color.white);
        
        /* Si le nom de salon est déjà pris : erreur */
        if(getSaloonByDatabase(nom)){
            jTextField16.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField16.setBackground(Color.white);
        
        if(creer){
            creerSalon(nom, couleur, desc);
            jTextField16.setText("");
            jTextField18.setText("");
        }
               
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /* Modifier un salon ! */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // On modifie les données d'un utilisateur !!
        boolean creer = true;
        
        String  nom = jTextField19.getText(),
                couleur = (String) jComboBox3.getSelectedItem(),
                desc = jTextField22.getText();
        
        desc = remplaceThisIn('\'', desc);
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField19 et 22*/
        if(nom.isEmpty()){
            jTextField19.setBackground(Color.red);
            creer = false;
        } else
            jTextField19.setBackground(Color.white);
        
        /* Si le pseudo n'est pas pris : erreur */
        if(!getSaloonByDatabase(nom)){
            jTextField19.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField19.setBackground(Color.white);
        
        //System.out.println(pseudo + " " + pwd + " " + nom + " " + prenom + " " + desc);
        
        if(creer){
            jTextField19.setText("");
            jTextField22.setText("");
            
            modifierSalon(nom, couleur, desc);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /* Ajouter relation */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        boolean creer = true;
        
        String  salon = jTextField25.getText(),
                user = jTextField26.getText();
        
        int role = 1;
       
        if(jCheckBox3.isSelected())
            role = 0;
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField25 et 26 */
        if(salon.isEmpty()){
            jTextField25.setBackground(Color.red);
            creer = false;
        } 
        else if(!getSaloonByDatabase(salon)){
            jTextField25.setBackground(Color.yellow);
            creer = false;
        }        
        else
            jTextField25.setBackground(Color.white);
        
        
        if(user.isEmpty()){
            jTextField26.setBackground(Color.red);
            creer = false;
        } 
        else if(!getPseudoByDatabase(user)){
            jTextField26.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField26.setBackground(Color.white);
        
        
        /* Si une relation n'existe déjà entre eux */
        if(creer && isRelationBetween(salon, user)){
            creer = false;
            jTextField25.setBackground(Color.yellow);
            jTextField26.setBackground(Color.yellow);
        }
        else {
            jTextField25.setBackground(Color.white);
            jTextField26.setBackground(Color.white);
        }
            
        
        if(creer){
            ajouterRelation(salon, user, role);
            jTextField25.setText("");
            jTextField26.setText("");
            jCheckBox3.setSelected(false);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /* Retirer Relation */
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        boolean creer = true;
        
        String  salon = jTextField33.getText(),
                user = jTextField34.getText();
        
        /* La créer ne s'opèrera pas si : les champs ne sont pas remplie. */
        /* Vérifier jTextField25 et 26 */
        if(salon.isEmpty()){
            jTextField33.setBackground(Color.red);
            creer = false;
        } 
        else if(!getSaloonByDatabase(salon)){
            jTextField33.setBackground(Color.yellow);
            creer = false;
        }        
        else
            jTextField33.setBackground(Color.white);
        
        
        if(user.isEmpty()){
            jTextField34.setBackground(Color.red);
            creer = false;
        } 
        else if(!getPseudoByDatabase(user)){
            jTextField34.setBackground(Color.yellow);
            creer = false;
        }
        else
            jTextField34.setBackground(Color.white);
        
        
        /* Si une relation existe déjà entre eux */
        if(creer && !isRelationBetween(salon, user)){
            creer = false;
            jTextField33.setBackground(Color.yellow);
            jTextField34.setBackground(Color.yellow);
        }
        else {
            jTextField33.setBackground(Color.white);
            jTextField34.setBackground(Color.white);
        }
            
        
        if(creer){
            retirerRelation(salon, user);
            jTextField33.setText("");
            jTextField34.setText("");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Chat_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    private Connection c = null;
}
