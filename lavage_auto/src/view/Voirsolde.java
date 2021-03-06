package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.User;
import utils.BaseDeDonnee;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowStateListener;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Voirsolde extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Voirsolde frame = new Voirsolde(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private int solde=0;
	private JTextField jourField;
	private JTextField moisField;
	private JTextField anneeField;
	private int nombreLavage =0;
	private JLabel nombres = new JLabel("0");
	private String columns[] = { "Vehicule", "Type de lavage", "Date", "Montant" };
    private String data[]= new String[4];
    private DefaultTableModel model = new DefaultTableModel(null, columns);
	public void update() {
		Connection connection;
		try {
			connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			String query = "SELECT * FROM prestation";
		    Statement stm = connection.createStatement();
		    ResultSet res = stm.executeQuery(query);
			model.setRowCount(0);
			while (res.next()) {
		    	
		        String vehicule = res.getString("vehicule");
		        String typeLavage = res.getString("typeprestation");
		        Date date = res.getDate("dateprestation", null);
		        int montant = res.getInt("prixprestation");
		        data[0] = vehicule;
		        data[1] = typeLavage;
		        data[2] = ""+date;
		        data[3] = montant+" CFA";
		        solde+=montant;
		        nombreLavage+=1;
		        model.addRow(data);
		      }
		      }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	public Voirsolde(User user) {
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../assets/logo_apps.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 589);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setTitle("CAR WASH | PLUS DE DETAILS");
		
		 try 
		  {
		     
			 Connection connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
		    
		      String query = "SELECT * FROM prestation";
		      Statement stm = connection.createStatement();
		      ResultSet res = stm.executeQuery(query);
		      String columns[] = { "Vehicule", "Type de lavage", "Date", "Montant" };
		      String data[]= new String[4];
		      DefaultTableModel model = new DefaultTableModel(null, columns);
		    
		      
		      while (res.next()) {
		    	
		        String vehicule = res.getString("vehicule");
		        String typeLavage = res.getString("typeprestation");
		        Date date = res.getDate("dateprestation", null);
		        int montant = res.getInt("prixprestation");
		        data[0] = vehicule;
		        data[1] = typeLavage;
		        data[2] = ""+date;
		        data[3] = montant+" CFA";
		        solde+=montant;
		        nombreLavage+=1;
		      
		        model.addRow(data);
		      }
		      nombres.setText(""+nombreLavage);
		      
		      JTable table = new JTable(model);
		      table.setShowGrid(true);
		      table.setShowVerticalLines(true);
		      JScrollPane pane = new JScrollPane(table);
		      pane.setViewportBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 139, 139), new Color(0, 128, 128), new Color(0, 128, 128), new Color(0, 128, 128)), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(47, 79, 79)));
		      pane.setBounds(10, 247, 856, 284);
		     
		      JPanel panel = new JPanel();
		      panel.setBackground(SystemColor.menu);
		      panel.setLayout(null);
		      panel.add(pane);
		      
		      getContentPane().add(panel);
		      
		      JPanel panel_1 = new JPanel();
		      panel_1.setBackground(new Color(255, 255, 255));
		      panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 139, 139), new Color(224, 255, 255), new Color(0, 128, 128), new Color(224, 255, 255)));
		      panel_1.setBounds(15, 28, 274, 131);
		      panel.add(panel_1);
		      panel_1.setLayout(null);
		      
		      JLabel soldeAffiche = new JLabel("SOLDE");
		      soldeAffiche.setFont(new Font("Tahoma", Font.PLAIN, 30));
		      soldeAffiche.setBounds(10, 0, 168, 51);
		      panel_1.add(soldeAffiche);
		      
		      JLabel sommes = new JLabel("0 CFA");
		      sommes.setText(solde+" CFA");
		      sommes.setFont(new Font("Tahoma", Font.PLAIN, 29));
		      sommes.setBounds(10, 69, 254, 51);
		      panel_1.add(sommes);
		      
		      jourField = new JTextField();
		      jourField.setBounds(53, 216, 28, 20);
		      panel.add(jourField);
		      jourField.setColumns(10);
		    
		      
		      JLabel lblNewLabel = new JLabel("JOUR");
		      lblNewLabel.setBounds(15, 219, 46, 14);
		      panel.add(lblNewLabel);
		      
		      moisField = new JTextField();
		      moisField.setColumns(10);
		      moisField.setBounds(130, 216, 28, 20);
		      panel.add(moisField);
		      
		      JLabel lblNewLabel_1_1 = new JLabel("/ MOIS");
		      lblNewLabel_1_1.setBounds(85, 219, 46, 14);
		      panel.add(lblNewLabel_1_1);
		      
		      JLabel lblNewLabel_1_1_1 = new JLabel("/ ANN\u00C9E");
		      lblNewLabel_1_1_1.setBounds(166, 219, 54, 14);
		      panel.add(lblNewLabel_1_1_1);
		      
		      anneeField = new JTextField();
		      anneeField.setColumns(10);
		      anneeField.setBounds(223, 216, 46, 20);
		      panel.add(anneeField);
		      
		      JButton recherche = new JButton("Rechercher");
		      recherche.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		try{
		                int jour = Integer.parseInt(jourField.getText());
		                int mois = Integer.parseInt(moisField.getText());
		                int annee = Integer.parseInt(anneeField.getText());
		                if(jourField.getText().length()>2 || moisField.getText().length()>2 || anneeField.getText().length()!=4) {
		                	jourField.setText("");
		                	moisField.setText("");
		                	anneeField.setText("");  
		                	throw new NumberFormatException("");
		                	
		                	
		                }
		                
		                if(jour<0 || mois<0 || annee<0) {
		                	jourField.setText("");
		                	moisField.setText("");
		                	anneeField.setText("");
		                	throw new NumberFormatException("");
		                }
		                
		                
		                String datePrestation = ""+annee+"/"+mois+"/"+jour; 
		                PreparedStatement st = (PreparedStatement) connection.prepareStatement(
							"select * from prestation where dateprestation=?");
		                st.setString(1, datePrestation);
		                 ResultSet rs = st.executeQuery();
		               	
		  		      	
		  		      	nombreLavage=0;
		  		      	solde=0;
		  		      model.setRowCount(0);
		  			while (rs.next()) {
		  		    	
		  		        String vehicule = rs.getString("vehicule");
		  		        String typeLavage = rs.getString("typeprestation");
		  		        Date date = rs.getDate("dateprestation", null);
		  		        int montant = rs.getInt("prixprestation");
		  		        data[0] = vehicule;
		  		        data[1] = typeLavage;
		  		        data[2] = ""+date;
		  		        data[3] = montant+" CFA";
		  		        solde+=montant;
		  		        nombreLavage+=1;
		  		 
		  		        model.addRow(data);
		  		      }
		  		 
		  		  nombres.setText(""+nombreLavage);
		  		  sommes.setText(solde+" CFA");
//		  		  JTable table = new JTable(model); 
		  		  
		  		  
//		  		  table.setShowGrid(true);
//			      table.setShowVerticalLines(true);
//			      JScrollPane pane = new JScrollPane(table);
//			      pane.setViewportBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 139, 139), new Color(0, 128, 128), new Color(0, 128, 128), new Color(0, 128, 128)), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(47, 79, 79)));
//			      pane.setBounds(10, 247, 856, 284);
//			      panel.setBackground(SystemColor.menu);
//			      panel.setLayout(null);
//			      panel.add(pane);
//			      getContentPane().add(panel);
		           }catch (NumberFormatException ex) {
		        	   JOptionPane.showMessageDialog(recherche, "Veuillez saisir une date correcte SVP");
		           } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		      	}
		      });
		      recherche.setBackground(new Color(224, 255, 255));
		      recherche.setBounds(296, 213, 109, 23);
		      panel.add(recherche);
		      
		      JPanel panel_1_1 = new JPanel();
		      panel_1_1.setLayout(null);
		      panel_1_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(250, 128, 114), new Color(224, 255, 255), new Color(250, 128, 114), new Color(224, 255, 255)));
		      panel_1_1.setBackground(Color.WHITE);
		      panel_1_1.setBounds(592, 28, 274, 131);
		      panel.add(panel_1_1);
		      
		      JLabel prestationAffiche = new JLabel("LAVAGE");
		      prestationAffiche.setFont(new Font("Tahoma", Font.PLAIN, 30));
		      prestationAffiche.setBounds(10, 0, 168, 51);
		      panel_1_1.add(prestationAffiche);
		      
		      
		      nombres.setFont(new Font("Tahoma", Font.PLAIN, 29));
		      nombres.setBounds(10, 69, 254, 51);
		      panel_1_1.add(nombres);
		      
		      JLabel lblNewLabel_1 = new JLabel("");
		      lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/lavage.jpg")));
		      lblNewLabel_1.setBounds(130, 11, 569, 225);
		      panel.add(lblNewLabel_1);
		      
		      JButton btnAfficherTout = new JButton("TOUT");
		      btnAfficherTout.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		 String query = "SELECT * FROM prestation";
				      Statement stm;
					try {
						stm = connection.createStatement();
						ResultSet res = stm.executeQuery(query);
		               	
		  		      	
		  		      	nombreLavage=0;
		  		      	solde=0;
		  		      model.setRowCount(0);
		  			while (res.next()) {
		  		    	
		  		        String vehicule = res.getString("vehicule");
		  		        String typeLavage = res.getString("typeprestation");
		  		        Date date = res.getDate("dateprestation", null);
		  		        int montant = res.getInt("prixprestation");
		  		        data[0] = vehicule;
		  		        data[1] = typeLavage;
		  		        data[2] = ""+date;
		  		        data[3] = montant+" CFA";
		  		        solde+=montant;
		  		        nombreLavage+=1;
		  		    
		  		        model.addRow(data);
		  		      }
		  			jourField.setText("");
		  			moisField.setText("");
		  			anneeField.setText("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      
			  		 
			  		  nombres.setText(""+nombreLavage);
			  		  sommes.setText(solde+" CFA");
		      	}
		      });
		      btnAfficherTout.setBackground(new Color(224, 255, 255));
		      btnAfficherTout.setBounds(757, 215, 109, 23);
		      panel.add(btnAfficherTout);
		      
		      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		      this.setVisible(true);
		    
		    } catch(SQLException e) {
		      e.printStackTrace();
		    }
	}
}
