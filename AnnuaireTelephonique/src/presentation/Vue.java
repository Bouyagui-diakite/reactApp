package presentation;
import domaine.*;
import dao.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Vue  extends JFrame implements ActionListener
{
  private Acces bd = new Acces();
  private JPanel p1,p2,p3;
  private JLabel lnom,lprenom,ladresse,ltel;
  private JTextField chnom,chprenom,chadresse,chtel;
  
  private JTable table;
  private int numeropersonne;
  private JScrollPane sc; // barre de défilements
  private DefaultTableModel modele;// exemple de modele de gestion de données dans un JTable
  
  private JButton bajout,bmodif,bsup,bquitter;
  
  public Vue()
  {
	  lnom = new JLabel("Nom");
	  lprenom = new JLabel("Prénom");
	  ladresse = new JLabel("Adresse");
	  ltel = new JLabel("Téléphone");
	  chnom = new JTextField(40);
	  chprenom = new JTextField(40);
	  chadresse = new JTextField(40);
	  chtel = new JTextField(40);
	  
	  p1 = new JPanel();
	  p1.setLayout(new GridLayout(4,2));
	  p1.add(lnom);
	  p1.add(chnom);
	  p1.add(lprenom);
	  p1.add(chprenom);
	  p1.add(ladresse);
	  p1.add(chadresse);
	  p1.add(ltel);
	  p1.add(chtel);
      add(p1,BorderLayout.NORTH);
      
      table = new JTable();
      sc = new JScrollPane();
      sc.setViewportView(table);
      modele=(DefaultTableModel) table.getModel();
      modele.addColumn("Numero");
      modele.addColumn("Nom");
      modele.addColumn("Prénom");
      modele.addColumn("Adresse");
      modele.addColumn("Télephone");
      p2 = new JPanel();
      p2.add(sc);
      add(p2,BorderLayout.CENTER);
      chargerContacts();
      
      // gestion des lignes :rendu d'une ligne dans le formulaire MouseListener
      table.addMouseListener(new MouseAdapter()
    		  {
    	      public void mouseClicked(MouseEvent e)
    	      {
    	    	  int indexligne= table.getSelectedRow();
    	    	  String snumero= modele.getValueAt(indexligne, 0).toString();
    	    	  numeropersonne = Integer.parseInt(snumero);
    	    	  chnom.setText(modele.getValueAt(indexligne, 1).toString());
    	    	  chprenom.setText(modele.getValueAt(indexligne, 2).toString());
    	    	  chadresse.setText(modele.getValueAt(indexligne, 3).toString());
    	    	  chtel.setText(modele.getValueAt(indexligne, 4).toString());
    	    	  bmodif.setEnabled(true);
    	    	  bsup.setEnabled(true);
    	      }
    		  });
    		    
      // gestion du panneau p3 boutons
      p3 = new JPanel();
      bajout = new JButton("Ajouter");
      bmodif = new JButton("Modifier");
      bsup = new JButton("Supprimer");
      bquitter = new JButton("Quitter");
      bajout.addActionListener(this);
      bmodif.addActionListener(this);
      bsup.addActionListener(this);
      bmodif.setEnabled(false);
      bsup.setEnabled(false);
      bquitter.addActionListener(this);
      p3.add(bajout);
      p3.add(bmodif);
      p3.add(bsup);
      p3.add(bquitter);
      add(p3,BorderLayout.SOUTH);
      
      setTitle("Gestion des Contacts");
      setSize(700,500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
  }
  
  // charger les contacts au démarrage
  public void chargerContacts()
  {
	  ArrayList<Personne> liste = bd.listerPersonnes();
	  int ligne=0;
	  for (Personne x : liste)
	  {
		   modele.addRow(new Object[0]);
		   modele.setValueAt(String.valueOf(x.getNumero()), ligne, 0);
		   modele.setValueAt(x.getNom(), ligne, 1);
		   modele.setValueAt(x.getPrenom(), ligne, 2);
		   modele.setValueAt(x.getAdresse(), ligne, 3);
		   modele.setValueAt(x.getTelephone(), ligne, 4);
		   ligne++;
	  }
  }
  public void actionPerformed(ActionEvent e)
  {
	 if (e.getSource()==bajout)
	 {
		 String nom =chnom.getText();
		 String prenom =chprenom.getText();
		 String adresse =chadresse.getText();
		 String telephone =chtel.getText();
		 Personne p  = new Personne();
		 p.setNom(nom);
		 p.setPrenom(prenom);
		 p.setAdresse(adresse);
		 p.setTelephone(telephone);
		 bd.ajouterPersonne(p);
		 JOptionPane.showMessageDialog(null, "Personne ajoutée dans la base !!!!");
		 new Vue();
	 }
	 else
	 if (e.getSource()==bsup)
	 {
		 bd.supprimerPersonne(numeropersonne);
		 JOptionPane.showMessageDialog(null, "Contact supprimé !!!!");
		 new Vue();
	 }
	 else
		 if (e.getSource()==bquitter)
		 {
			 dispose();
			 System.exit(0);
		 }
		 else
			 if(e.getSource()==bmodif)
			 {
				String nom =chnom.getText();
				String prenom =chprenom.getText();
				String adresse =chadresse.getText();
				String telephone =chtel.getText();
				Personne p = new Personne();
				p.setNumero(numeropersonne);// !!!!!!!! numero de personne
				p.setNom(nom);
				p.setPrenom(prenom);
				p.setAdresse(adresse);
				p.setTelephone(telephone);
				bd.modifierPersonne(p);
				JOptionPane.showMessageDialog(null, "Contact modifié !!!!");
				new Vue();
			 }
  }
	public static void main(String[] args)
	{
	
      new Vue();
	}

}
