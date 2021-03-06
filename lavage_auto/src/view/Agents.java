package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.table.TableModel;

import utils.BaseDeDonnee;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Agents extends JFrame {

	private JPanel contentPane;
	private JTextField nomField=new JTextField();
	private JTextField prenomField= new JTextField();
	private JTextField usernameField=new JTextField();
	private JPasswordField passwordField=new JPasswordField();
	private int isAdminChecked = 0;
	private int isActiveChecked = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agents frame = new Agents();
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
	private JCheckBox isActifField = new JCheckBox("ACTIVER L'AGENT");
	private JCheckBox isAdminField = new JCheckBox("ACCORDER TOUT LES PRIVIL\u00C8GES");
	private ArrayList<String> ifUserExist = new ArrayList<String>();
	private String columns[] = {"ID","NOM D'UTILISATEUR","NOM COMPLET","ADMIN","ETAT","CAISSE"};
	private DefaultTableModel model = new DefaultTableModel(null, columns);
	public void empty() {
		usernameField.setText("");
		passwordField.setText("");
		nomField.setText("");
		prenomField.setText("");
		isAdminChecked=0;
		isActiveChecked=0;
		isActifField.setSelected(false);
		isAdminField.setSelected(false);
		
	}
	public void update() {
		Connection connection;
		try {
			connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			String query = "SELECT * FROM `user`";
			
			Statement stm = connection.createStatement();
			ResultSet res = stm.executeQuery(query);
			 String data[]= new String[6];
			 model.setRowCount(0);
			 ifUserExist.clear();
		      while (res.next()) {
		    	int id = res.getInt("id");
		    	String username= res.getString("username");
		    	String nomComplet = res.getString("fullname");
		    	boolean isAdmin = res.getBoolean("isadmin");
		        boolean isActif = res.getBoolean("isactive");
		        String etatAdmin= (isAdmin)? "ADMIN":"NO";
		        String etatActive= (isActif)? "Activ???":"Desactiv???";
		        data[0] =id+"";
		        data[1] = username;
		        data[2] = nomComplet;
		        data[3] = etatAdmin;
		        data[4] = etatActive;
		        ifUserExist.add(res.getString("username"));
		        model.addRow(data);
		      }
		      
		      empty();
		      }catch (SQLException e) {
		    	  e.printStackTrace();
		      }
		
		}

	public Agents() {
		isAdminField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isAdminChecked = (isAdminChecked==0)? 1:0;
				System.out.println(isAdminChecked);
				
			}
		});
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../assets/logo_apps.jpg")));
		this.setTitle("CAR WASH | FEN???TRE DE GESTION DES AGENTS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 701, 592);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 JTable table = new JTable(model);
		 table.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseReleased(MouseEvent e) {
		 		int i = table.getSelectedRow();
		      	int id = Integer.parseInt(model.getValueAt(i, 0).toString());
		      	usernameField.setText(model.getValueAt(i, 1).toString());
		 	}
		 });
		 table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      table.setShowGrid(true);
	      table.setShowVerticalLines(true);
	      JScrollPane pane = new JScrollPane(table);
	      pane.setViewportBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 139, 139), new Color(0, 128, 128), new Color(0, 128, 128), new Color(0, 128, 128)), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(47, 79, 79)));
	      pane.setBounds(10, 283, 677, 234);
	      
	      contentPane.add(pane);
	      
	    update();
		
		JLabel lblNewLabel = new JLabel("NOM :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 40, 47, 14);
		this.contentPane.add(lblNewLabel);
		
		nomField = new JTextField();
		nomField.setBackground(new Color(224, 255, 255));
		nomField.setBounds(175, 29, 278, 31);
		contentPane.add(nomField);
		nomField.setColumns(10);
		
		JLabel lblPrenom = new JLabel("PRENOM :");
		lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrenom.setBounds(10, 77, 67, 14);
		contentPane.add(lblPrenom);
		
		prenomField = new JTextField();
		prenomField.setBackground(new Color(224, 255, 255));
		prenomField.setColumns(10);
		prenomField.setBounds(175, 71, 278, 31);
		contentPane.add(prenomField);
		
		JLabel lblNomDutilisateur = new JLabel("NOM D'UTILISATEUR :");
		lblNomDutilisateur.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNomDutilisateur.setBounds(10, 127, 158, 14);
		contentPane.add(lblNomDutilisateur);
		
		usernameField = new JTextField();
		usernameField.setBackground(new Color(224, 255, 255));
		usernameField.setColumns(10);
		usernameField.setBounds(175, 121, 278, 31);
		contentPane.add(usernameField);
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(135, 206, 250));
		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/image_user_page.png")));
		lblNewLabel_1.setBounds(446, 0, 241, 272);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("ENREGISTRER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(!ifUserExist.contains(usernameField.getText())) {
							Connection connection;
							connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
							String query = "INSERT INTO `user`(`username`, `password`, `fullname`, `isadmin`, `isactive`) VALUES ('"+usernameField.getText()+"','admin','"+nomField.getText()+" "+prenomField.getText()+"','"+isAdminChecked+"','"+isActiveChecked+"')";
//							String query = "INSERT INTO `user`(`username`, `password`, `fullname`, `isadmin`, `isactive`) VALUES ('kedja','kedja','KEDJA KEDJA','0','0')";
							Statement stmT;
							stmT = connection.createStatement();
							stmT.executeUpdate(query);			
							update();						
					}else {
						
						JOptionPane.showMessageDialog(null, "Ce nom d'utilisateur existe d???ja veuillez choisir un autre", "Erreur", JOptionPane.ERROR_MESSAGE);
						usernameField.setText("");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBackground(new Color(135, 206, 235));
		btnNewButton.setBounds(20, 257, 128, 23);
		contentPane.add(btnNewButton);
		
		JButton btnModifier = new JButton("MODIFIER");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int i = table.getSelectedRow();
	      		int id = Integer.parseInt(model.getValueAt(i, 0).toString());
	      		
				if(nomField.getText()!="" && prenomField.getText()!="" && passwordField.getText()!="") {
					 if(i>=0) { 
				        int option = JOptionPane.showConfirmDialog(null,"???tes vous sur de vouloir modifier ?","MODIFICATION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			            if(option== JOptionPane.YES_OPTION) {
			    			Connection connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			            	PreparedStatement st = (PreparedStatement) connection.prepareStatement("UPDATE `user` SET `username`='"+usernameField.getText()+"',`password`='"+passwordField.getText()+"',`fullname`='"+nomField.getText()+" "+prenomField.getText()+"',`isadmin`='"+isAdminChecked+"',`isactive`='"+isActiveChecked+"' WHERE  id=?");
			            	st.setInt(1, id);
			            	st.executeUpdate();
			            	update();
				}}
					 }
			}catch (SQLException e1) {
		    	  e1.printStackTrace();
		      }}
		});
		btnModifier.setBackground(new Color(135, 206, 250));
		btnModifier.setBounds(290, 257, 139, 23);
		contentPane.add(btnModifier);
		
		JButton btnSupprimer = new JButton("SUPPRIMER");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
	      		
	            if(i>=0) {
	            	int id = Integer.parseInt(model.getValueAt(i, 0).toString());  
		            int option = JOptionPane.showConfirmDialog(null, 
		              "???tes vous sur de vouloir supprimer ?", 
		              "SUPPRESSION", 
		              JOptionPane.YES_NO_OPTION, 
		              JOptionPane.WARNING_MESSAGE);
	            	 if(option== JOptionPane.YES_OPTION) {
	            		 empty();
			            	try {
				    			Connection connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
				    			PreparedStatement st = (PreparedStatement) connection.prepareStatement("DELETE FROM `user` WHERE id =?");
				    			st.setInt(1, id);
				    			st.executeUpdate();
				    			update();
				    					
				      		}catch (SQLException sqlException) {
				    				sqlException.printStackTrace();
				    			}
			            }
	            }
			}
		});
		btnSupprimer.setBackground(new Color(135, 206, 250));
		btnSupprimer.setBounds(549, 257, 128, 23);
		contentPane.add(btnSupprimer);
		
		
		isAdminField.setForeground(new Color(255, 255, 255));
		isAdminField.setBackground(new Color(250, 128, 114));
		isAdminField.setBounds(10, 195, 241, 23);
		contentPane.add(isAdminField);
		
		
		isActifField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isActiveChecked = (isActiveChecked==0)? 1:0;
				System.out.println(isActiveChecked);
//				if(isActiveChecked==1) {
//					isActifField.setSelected(true);
//				}else {
//					isActifField.setSelected(false);
//				}
//				
			}
		});
		isActifField.setForeground(Color.WHITE);
		isActifField.setBackground(new Color(46, 139, 87));
		isActifField.setBounds(10, 225, 241, 23);
		contentPane.add(isActifField);
		
		JButton afficherCaisse = new JButton("AFFICHER CAISSE");
		afficherCaisse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
					String query = "SELECT *, SUM(prestation.prixprestation) AS total FROM `user` CROSS JOIN prestation where user.id=prestation.iduser GROUP BY (username)";
					
					Statement stm = connection.createStatement();
					ResultSet res = stm.executeQuery(query);
					 String data[]= new String[6];
					 model.setRowCount(0);
					 ifUserExist.clear();
				      while (res.next()) {
				    	int id = res.getInt("user.id");
				    	String username= res.getString("user.username");
				    	String nomComplet = res.getString("user.fullname");
				    	boolean isAdmin = res.getBoolean("user.isadmin");
				        boolean isActif = res.getBoolean("user.isactive");
				        String etatAdmin= (isAdmin)? "ADMIN":"NO";
				        String etatActive= (isActif)? "Activ???":"Desactiv???";
				        data[0] =id+"";
				        data[1] = username;
				        data[2] = nomComplet;
				        data[3] = etatAdmin;
				        data[4] = etatActive;
				        data[5] = res.getInt("total")+" CFA";
				        ifUserExist.add(res.getString("username"));
				        model.addRow(data);
				      }
				      
				      empty();
				      }catch (SQLException e1) {
				    	  e1.printStackTrace();
				      
				      }}
		});
		afficherCaisse.setBackground(new Color(135, 206, 235));
		afficherCaisse.setBounds(20, 528, 148, 23);
		contentPane.add(afficherCaisse);
		
		JButton btnAfficherTout = new JButton("AFFICHER TOUT");
		btnAfficherTout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnAfficherTout.setBackground(new Color(135, 206, 235));
		btnAfficherTout.setBounds(529, 528, 148, 23);
		contentPane.add(btnAfficherTout);
		

	     
	}
}
