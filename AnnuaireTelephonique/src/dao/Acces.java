package dao;

import domaine.*;
import java.util.*;
import java.sql.*;

public class Acces
{
  private Connection con=null;
  private PreparedStatement st =null;
  private ResultSet rs =null;
  
  // Acces bd = new Acces(); connexion à la base de données
  // injection de dépendances à réaliser dans la vue
  public Acces()
  {
	try
	{
		 Class.forName("com.mysql.jdbc.Driver"); 
		 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/annuaire", "ecole","ecole");
	}
	catch(Exception ex)
    {
		 System.out.println("impossible de se connecter");
		  System.out.println(ex.getMessage());
	}
  }
	public void ajouterPersonne(Personne p)
	{
		try
		{
		 st = con.prepareStatement(" insert into contacts(nom,prenom,telephone,adresse) "+ 
		 " values (?,?,?,?) ");
		 st.setString(1,p.getNom());
		 st.setString(2,p.getPrenom());
		 st.setString(3,p.getTelephone());
		 st.setString(4,p.getAdresse());
		 st.executeUpdate();
		}
		catch(Exception ex)
	    {
			  System.out.println(ex.getMessage());
		}
	}
		public void modifierPersonne(Personne p)
		{
			try
			{
			 st = con.prepareStatement(" update contacts set nom=?, prenom=?, telephone=?, adresse=? where numero=?");
			 st.setString(1,p.getNom());
			 st.setString(2,p.getPrenom());
			 st.setString(3,p.getTelephone());
			 st.setString(4,p.getAdresse());
			 st.setInt(5,p.getNumero());
			 st.executeUpdate();
			}
			catch(Exception ex)
		    {
				  System.out.println(ex.getMessage());
			}	
		}	
			public void supprimerPersonne(int numero)
			{
				try
				{
									
				 st = con.prepareStatement(" delete from contacts where numero=?");
				 st.setInt(1,numero);
				 st.executeUpdate();
			    }
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			}
    public ArrayList<Personne> listerPersonnes()
    {
    	ArrayList<Personne> liste = null;
    	try
    	{
    	  liste 	= new ArrayList<Personne>();
    	  st=con.prepareStatement("select * from contacts");
    	  rs=st.executeQuery();
    	  while(rs.next())
    	  {
    		  Personne p = new Personne();
    		  p.setNumero(rs.getInt("numero"));
    		  p.setNom(rs.getString("nom"));
    		  p.setPrenom(rs.getString("prenom"));
    		  p.setAdresse(rs.getString("adresse"));
    		  p.setTelephone(rs.getString("telephone"));
    		  liste.add(p);
    		  
    	  }
    	  
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	return liste;
    }
		}
	
  
  

