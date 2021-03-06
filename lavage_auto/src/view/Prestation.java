package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.User;
import utils.BaseDeDonnee;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ListSelectionModel;

public class Prestation extends JFrame {

	private JPanel contentPane;
	private JTextField matriculeField;
	private JTextField voitureField;
	private JTextField nomField;
	private JTextField numeroField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prestation frame = new Prestation(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void empty() {
		matriculeField.setText("");
		voitureField.setText("");
		nomField.setText("");
		numeroField.setText("");
		
	}
	public void update(User user) {
		Connection connection;
		try {
			String query;
			connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			if(user.isAdmin) {
				query = "SELECT * FROM `prestation` CROSS JOIN `user` WHERE prestation.iduser = user.id";
			}else {
				query = "SELECT * FROM `prestation` CROSS JOIN `user` WHERE prestation.iduser = user.id and prestation.iduser='"+user.id+"'";
			}
			
			Statement stm = connection.createStatement();
			ResultSet res = stm.executeQuery(query);
			 String data[]= new String[9];
			model.setRowCount(0);
		      while (res.next()) {
		    	int id = res.getInt("prestation.id");
		    	String matricule = res.getString("prestation.matvehicule");
		    	String nomGerant = res.getString("user.username");
		        String vehicule = res.getString("prestation.vehicule");
		        String proprietaire = res.getString("prestation.nomproprietaire");
		        String numeroproprietaire = res.getString("prestation.numeroproprietaire");
		        String typeLavage = res.getString("prestation.typeprestation");
		        
		        Date date = res.getDate("prestation.dateprestation", null);
		        int montant = res.getInt("prestation.prixprestation");
		        data[0] =id+"";
		        data[1] = matricule;
		        data[2] = nomGerant;
		        data[3] = vehicule;
		        data[4] = proprietaire;
		        data[5] = numeroproprietaire;
		        data[6] = typeLavage;
		        data[7] = date+"";
		        data[8] = montant+" CFA";
		        model.addRow(data);
		      }
		      empty();
		      }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	   
	}
	/**
	 * Create the frame.
	 */
	private JComboBox typeVehicule = new JComboBox();
	private String columns[] = {"ID","MATRICULE","NOM GERANT","VEHICULE", "PROPRIETAIRE","NUMERO","TYPE DE LAVAGE", "DATE", "MONTANT" };
	private DefaultTableModel model = new DefaultTableModel(null, columns);
	private int montantType =0;
	private boolean checkedInterieur= false;
	private boolean checkedExterieur = true;
	private JTextField searchField;
	public void export(JTable table, File file){
	    try
	    {
	      TableModel m = table.getModel();
	      FileWriter fw = new FileWriter(file);
	      for(int i = 0; i < m.getColumnCount(); i++){
	        fw.write(m.getColumnName(i) + "\t");
	      }
	      fw.write("\n");
	      for(int i=0; i < m.getRowCount(); i++) {
	        for(int j=0; j < m.getColumnCount(); j++) {
	          fw.write(m.getValueAt(i,j).toString()+"\t");
	        }
	        fw.write("\n");
	      }
	      fw.close();
	    }
	    catch(IOException e){ System.out.println(e); }
	  }
	public Prestation(User user) {
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../assets/logo_apps.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1078, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setTitle("CAR WASH | FEN???TRE DE PRESTATION");
		setContentPane(contentPane);
		 JLabel montantLabel = new JLabel("0 CFA");
		 Connection connection;
		try {
			connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			 String query;
			 if(user.isAdmin) {
					query = "SELECT * FROM `prestation` CROSS JOIN `user` WHERE prestation.iduser = user.id";
				}else {
					query = "SELECT * FROM `prestation` CROSS JOIN `user` WHERE prestation.iduser = user.id and prestation.iduser='"+user.id+"'";
				}
		      Statement stm = connection.createStatement();
		      ResultSet res = stm.executeQuery(query);
		      
		      String data[]= new String[9];
		      
		      model.setRowCount(0);
		      while (res.next()) {
		    	int id = res.getInt("prestation.id");
		    	String matricule = res.getString("prestation.matvehicule");
		    	String nomGerant = res.getString("user.username");
		        String vehicule = res.getString("prestation.vehicule");
		        String proprietaire = res.getString("prestation.nomproprietaire");
		        String numeroproprietaire = res.getString("prestation.numeroproprietaire");
		        String typeLavage = res.getString("prestation.typeprestation");
		        
		        Date date = res.getDate("prestation.dateprestation", null);
		        int montant = res.getInt("prestation.prixprestation");
		        data[0] =id+"";
		        data[1] = matricule;
		        data[2] = nomGerant;
		        data[3] = vehicule;
		        data[4] = proprietaire;
		        data[5] = numeroproprietaire;
		        data[6] = typeLavage;
		        data[7] = date+"";
		        data[8] = montant+" CFA";
		        model.addRow(data);
		      }
		     
		      
		      JTable table = new JTable(model);
		      table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		      table.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(70, 130, 180), new Color(224, 255, 255), new Color(70, 130, 180), new Color(224, 255, 255)));
		      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		      table.setBackground(new Color(224, 255, 255));
			  table.setShowGrid(true);
		      table.setShowVerticalLines(true);
		      JScrollPane pane = new JScrollPane(table);
		      table.addMouseListener(new MouseAdapter() {
		    	 @Override
		    	 public void mouseReleased(MouseEvent e) {
		    		int i = table.getSelectedRow();
			      	int id = Integer.parseInt(model.getValueAt(i, 0).toString());
			      	nomField.setText(model.getValueAt(i, 4).toString());
			      	matriculeField.setText(model.getValueAt(i, 1).toString());
			      	numeroField.setText(model.getValueAt(i, 5).toString());
			      	voitureField.setText(model.getValueAt(i, 3).toString());
		    	 }
		     });
		      
		      
		      pane.setBounds(10, 270, 1044, 248);
		      pane.setViewportBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 139, 139), new Color(0, 128, 128), new Color(0, 128, 128), new Color(0, 128, 128)), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(47, 79, 79)));
		      JPanel panel = new JPanel();
		      panel.setBackground(SystemColor.menu);
		      panel.setLayout(null);
		      panel.add(pane);
		      getContentPane().add(panel);
		      
		      JLabel lblNewLabel = new JLabel("NOM :");
		      lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblNewLabel.setBounds(10, 42, 71, 23);
		      panel.add(lblNewLabel);
		      
		      JLabel lblMatricule = new JLabel("MATRICULE :");
		      lblMatricule.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblMatricule.setBounds(10, 110, 126, 23);
		      panel.add(lblMatricule);
		      
		      JLabel lblVoiture = new JLabel("VOITURE :");
		      lblVoiture.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblVoiture.setBounds(10, 144, 126, 23);
		      panel.add(lblVoiture);
		      
		      JLabel lblTypeDeVehicule = new JLabel("TYPE DE VEHICULE :");
		      lblTypeDeVehicule.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblTypeDeVehicule.setBounds(10, 178, 185, 23);
		      panel.add(lblTypeDeVehicule);
		      
		      
		      typeVehicule.setToolTipText("");
		      typeVehicule.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		montantType=0;
		      		montantLabel.setText(montantType+" CFA");
		      		System.out.println(typeVehicule.getSelectedIndex());
		      		if(checkedExterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=3000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=5000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		if(checkedInterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=1500;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=1000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		
		      	}
		      });
		      typeVehicule.setBackground(new Color(224, 255, 255));
		      typeVehicule.setModel(new DefaultComboBoxModel(new String[] {"", "4X4\t\t -> 3000 CFA", "BERLINE       -> 2000 CFA", "LUXE\t\t -> 5000 CFA"}));
		      typeVehicule.setBounds(195, 178, 194, 31);
		      panel.add(typeVehicule);
		      
		      matriculeField = new JTextField();
		      matriculeField.setBackground(new Color(224, 255, 255));
		      matriculeField.setColumns(10);
		      matriculeField.setBounds(195, 110, 194, 31);
		      panel.add(matriculeField);
		      
		      voitureField = new JTextField();
		      voitureField.setBackground(new Color(224, 255, 255));
		      voitureField.setColumns(10);
		      voitureField.setBounds(195, 144, 194, 31);
		      panel.add(voitureField);
		      
		      JLabel lblMontant = new JLabel("MONTANT TOTAL:");
		      lblMontant.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblMontant.setBounds(405, 213, 162, 23);
		      panel.add(lblMontant);
		      
		     
		      montantLabel.setForeground(new Color(0, 100, 0));
		      montantLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		      montantLabel.setBounds(568, 213, 185, 23);
		      panel.add(montantLabel);
		      
		      nomField = new JTextField();
		      nomField.setColumns(10);
		      nomField.setBackground(new Color(224, 255, 255));
		      nomField.setBounds(195, 34, 194, 31);
		      panel.add(nomField);
		      
		      numeroField = new JTextField();
		      numeroField.setColumns(10);
		      numeroField.setBackground(new Color(224, 255, 255));
		      numeroField.setBounds(195, 68, 194, 31);
		      panel.add(numeroField);
		      
		      JLabel lblTelephone = new JLabel("TELEPHONE :");
		      lblTelephone.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblTelephone.setBounds(10, 76, 142, 23);
		      panel.add(lblTelephone);
		      
		      JLabel lblTypeDeLavage = new JLabel("TYPE DE LAVAGE ");
		      lblTypeDeLavage.setFont(new Font("Tahoma", Font.PLAIN, 19));
		      lblTypeDeLavage.setBounds(524, 34, 162, 23);
		      panel.add(lblTypeDeLavage);
		      
		      JCheckBox interieurCheck = new JCheckBox("INTERIEUR");
		      interieurCheck.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {

		      		checkedInterieur= (checkedInterieur==true)? false : true;
		      		montantType=0;
		      		montantLabel.setText(montantType+" CFA");
		      		System.out.println(typeVehicule.getSelectedIndex());
		      		if(checkedExterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=3000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=5000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		if(checkedInterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=1500;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=1000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		
		      		
		      	}
		      });
		      interieurCheck.setBounds(501, 74, 101, 23);
		      panel.add(interieurCheck);
		      
		      JCheckBox exterieurCheck = new JCheckBox("EXTERIEUR");
		      exterieurCheck.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		checkedExterieur= (checkedExterieur==true)? false : true;
		      		montantType=0;
		      		montantLabel.setText(montantType+" CFA");
		      		System.out.println(typeVehicule.getSelectedIndex());
		      		if(checkedExterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=3000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=5000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		if(checkedInterieur) {
		      			if(typeVehicule.getSelectedIndex()==1) {
			      			montantType+=1500;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==2) {
			      			montantType+=1000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
			      		if(typeVehicule.getSelectedIndex()==3) {
			      			montantType+=2000;
			      			montantLabel.setText(montantType+" CFA");
			      		}
		      		}
		      		
		      	}
		      });
		      exterieurCheck.setSelected(true);
		      exterieurCheck.setBounds(618, 74, 101, 23);
		      panel.add(exterieurCheck);
		      
		      JButton btnSave = new JButton("ENREGISTRER");
		      btnSave.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		if(nomField.getText()!="" && matriculeField.getText()!="" && numeroField.getText()!="" && typeVehicule.getSelectedIndex()>0 && (checkedInterieur || checkedExterieur)) {
		      			try {
							String tp =""; 
							if(typeVehicule.getSelectedIndex()==1) {
								tp="4X4";
						}
							if(typeVehicule.getSelectedIndex()==2) {
								tp="Berline";
							}
							if(typeVehicule.getSelectedIndex()==3) {
								tp="Luxe";
							}
							
							String typprest="";
							if(checkedInterieur && checkedExterieur) {
							typprest="TOTAL";
							}else {
								if(checkedInterieur) {

									typprest= "INTERIEUR";
								}else {

									typprest= "EXTERIEUR";
								}
							}
					
							LocalDate myDate = LocalDate.now();
							Connection connection;
							connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
							String query = "INSERT INTO prestation (iduser, matvehicule, typevehicule, vehicule, typeprestation, prixprestation, nomproprietaire, numeroproprietaire, dateprestation) VALUES ('"+user.id+"','"+matriculeField.getText()+"','"+tp+"','"+voitureField.getText()+"','"+typprest+"','"+montantType+"','"+nomField.getText()+"','"+numeroField.getText()+"','"+myDate+"')";
							Statement stmT = connection.createStatement();
							stmT.executeUpdate(query);
								update(user);
								
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		      		}else {
		      			
		      		}
		      		
					
				      
					
				      
				    
				      
				     
		      		
		      	}
		      });
		      btnSave.setForeground(new Color(224, 255, 255));
		      btnSave.setBackground(new Color(70, 130, 180));
		      btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		      btnSave.setBounds(862, 42, 142, 31);
		      panel.add(btnSave);
		      
		      JButton btnEdit = new JButton("MODIFIER");
		      btnEdit.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		int i = table.getSelectedRow();
		      		 if(i>=0) {
		      		int id = Integer.parseInt(model.getValueAt(i, 0).toString());
		      		Connection connection;
					try {
						connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
						String tp =""; 
						if(typeVehicule.getSelectedIndex()==1) {
							tp="4X4";
					}
						if(typeVehicule.getSelectedIndex()==2) {
							tp="Berline";
						}
						if(typeVehicule.getSelectedIndex()==3) {
							tp="Luxe";
						}
						
						String typprest="";
						if(checkedInterieur && checkedExterieur) {
						typprest="TOTAL";
						}else {
							if(checkedInterieur) {

								typprest= "INTERIEUR";
							}else {

								typprest= "EXTERIEUR";
							}
						}
						if(nomField.getText()!="" && matriculeField.getText()!="" && numeroField.getText()!="" && typeVehicule.getSelectedIndex()>0 && (checkedInterieur || checkedExterieur)) {
							 if(i>=0) { 
						        int option = JOptionPane.showConfirmDialog(null,"???tes vous sur de vouloir modifier ?","MODIFICATION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					            if(option== JOptionPane.YES_OPTION) {
					            	LocalDate myDate = LocalDate.now();
					            	PreparedStatement st = (PreparedStatement) connection.prepareStatement("UPDATE `prestation` SET `iduser`='"+user.id+"',`matvehicule`='"+matriculeField.getText()+"',`typevehicule`='"+tp+"',`vehicule`='"+voitureField.getText()+"',`typeprestation`='"+typprest+"',`prixprestation`='"+montantType+"',`nomproprietaire`='"+nomField.getText()+"',`numeroproprietaire`='"+numeroField.getText()+"',`dateprestation`='"+myDate+"' WHERE id=?");
					            	st.setInt(1, id);
					            	st.executeUpdate();
					            	update(user);
						}}
							 }
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}}
	    			
		      		
		      	}
		      });
		      btnEdit.setForeground(new Color(224, 255, 255));
		      btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		      btnEdit.setBackground(new Color(70, 130, 180));
		      btnEdit.setBounds(862, 94, 142, 31);
		      panel.add(btnEdit);
		      
		      JButton btnDelete = new JButton("SUPPRIMER");
		      btnDelete.addActionListener(new ActionListener() {
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
					    			PreparedStatement st = (PreparedStatement) connection.prepareStatement("DELETE FROM `prestation` WHERE id =?");
					    			st.setInt(1, id);
					    			st.executeUpdate();
					    			update(user);
					    					
					      		}catch (SQLException sqlException) {
					    				sqlException.printStackTrace();
					    			}
				            }
		            }
		           
		      	
		      	}
		      });
		      btnDelete.setForeground(new Color(224, 255, 255));
		      btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		      btnDelete.setBackground(new Color(70, 130, 180));
		      btnDelete.setBounds(862, 148, 142, 31);
		      panel.add(btnDelete);
		      
		      JLabel lblNewLabel_1 = new JLabel("");
		      lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/image_prestation_page.jpg")));
		      lblNewLabel_1.setBounds(824, 11, 230, 225);
		      panel.add(lblNewLabel_1);
		      
		      searchField = new JTextField();
		      searchField.setBounds(10, 529, 195, 25);
		      panel.add(searchField);
		      searchField.setColumns(10);
		      
		      JButton recherche_1 = new JButton("Rechercher");
		      recherche_1.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		PreparedStatement st;
					try {
						if(user.isAdmin) {
							st = (PreparedStatement) connection.prepareStatement(
									"SELECT * FROM `prestation` CROSS JOIN `user` where prestation.matvehicule=?  AND prestation.iduser= user.id");
						}else {
							st = (PreparedStatement) connection.prepareStatement(
									"SELECT * FROM `prestation` CROSS JOIN `user` where prestation.matvehicule=?  AND prestation.iduser= user.id AND prestation.iduser='"+user.id+"'");
						}
						st = (PreparedStatement) connection.prepareStatement(
								"SELECT * FROM `prestation` CROSS JOIN `user` where prestation.matvehicule=?  AND prestation.iduser= user.id");
						st.setString(1, searchField.getText());
						 ResultSet rest = st.executeQuery();
			               	
			  		   
			  		    model.setRowCount(0);
					      while (rest.next()) {
					    	int id = rest.getInt("prestation.id");
					    	String matricule = rest.getString("prestation.matvehicule");
					    	String nomGerant = rest.getString("user.username");
					        String vehicule = rest.getString("prestation.vehicule");
					        String proprietaire = rest.getString("prestation.nomproprietaire");
					        String numeroproprietaire = rest.getString("prestation.numeroproprietaire");
					        String typeLavage = rest.getString("prestation.typeprestation");
					        
					        Date date = rest.getDate("prestation.dateprestation", null);
					        int montant = rest.getInt("prestation.prixprestation");
					        data[0] =id+"";
					        data[1] = matricule;
					        data[2] = nomGerant;
					        data[3] = vehicule;
					        data[4] = proprietaire;
					        data[5] = numeroproprietaire;
					        data[6] = typeLavage;
					        data[7] = date+"";
					        data[8] = montant+" CFA";
					        model.addRow(data);
					      }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
		      	}
		      });
		      recherche_1.setBackground(new Color(224, 255, 255));
		      recherche_1.setBounds(225, 528, 114, 26);
		      panel.add(recherche_1);
		      
		      JButton recherche_1_1 = new JButton("Afficher tout");
		      recherche_1_1.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		update(user);
		      	}
		      });
		      recherche_1_1.setBackground(new Color(224, 255, 255));
		      recherche_1_1.setBounds(349, 528, 114, 26);
		      panel.add(recherche_1_1);
		      
		      JButton recherche_1_2 = new JButton("IMPORTER");
		      recherche_1_2.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		 if(e.getSource() == recherche_1_2){
		                 JFileChooser fchoose = new JFileChooser();
		                 int option = fchoose.showSaveDialog(null);
		                 if(option == JFileChooser.APPROVE_OPTION){
		                   String name = fchoose.getSelectedFile().getName(); 
		                   String path = fchoose.getSelectedFile().getParentFile().getPath();
		                   String file = path + "\\" + name + ".xls"; 
		                   export(table, new File(file));
		                 }
		      		 }}
		      });
		      recherche_1_2.setBackground(new Color(255, 255, 0));
		      recherche_1_2.setBounds(930, 528, 114, 26);
		      panel.add(recherche_1_2);
		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	     
	      
	      
	      
	      
	}
}
