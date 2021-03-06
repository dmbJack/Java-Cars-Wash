package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.User;
import utils.BaseDeDonnee;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;


public class Main_page extends JFrame {
	/**
	 * Launch the application.
	 */
	private JFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_page frame = new Main_page(null);
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
	private int soldeCaisse =0;
	private int nombrePrestation=0;
	public Main_page(User user) {
		Connection connection;
		try {
			connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
			String query = "SELECT * FROM prestation where iduser='"+user.id+"'";
		    Statement stm = connection.createStatement();
		    ResultSet res = stm.executeQuery(query);
		    while(res.next()) {
		    	soldeCaisse+= res.getInt("prixprestation");
		    	nombrePrestation++;
		    }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	      
		getContentPane().setBackground(Color.WHITE);
		
			// TODO Auto-generated constructor stub
		

		/**
		 * Initialize the contents of the frame.
		 * @wbp.parser.entryPoint
		 */
		this.setResizable(false);
		this.setTitle("CARS WASH | FEN�TRE PRINCIPALE");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../assets/logo_apps.jpg")));
		this.setBounds(100, 100, 1165, 662);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel agent = new JLabel("AGENT"+" "+ user.fullName);
		agent.setFont(new Font("Calibri", Font.PLAIN, 16));
		agent.setBounds(10, 53, 195, 32);
		this.getContentPane().add(agent);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 191, 255), 1, true));
		panel.setForeground(new Color(240, 248, 255));
		panel.setBounds(72, 201, 381, 247);
		this.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SOLDE");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(152, 11, 97, 44);
		panel.add(lblNewLabel);
		
		JLabel spmmeCaisse = new JLabel("");
		spmmeCaisse.setText(soldeCaisse+" CFA");
		spmmeCaisse.setForeground(new Color(0, 128, 0));
		spmmeCaisse.setFont(new Font("Tahoma", Font.PLAIN, 25));
		spmmeCaisse.setBounds(10, 111, 239, 80);
		panel.add(spmmeCaisse);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new LineBorder(new Color(0, 191, 255), 1, true));
		panel_1.setForeground(new Color(255, 255, 255));
		panel_1.setBounds(695, 201, 381, 247);
		this.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblLavageEffectu = new JLabel("LAVAGE EFFECTU\u00C9");
		lblLavageEffectu.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblLavageEffectu.setBounds(53, 11, 277, 44);
		panel_1.add(lblLavageEffectu);
		
		JLabel nombreLavage = new JLabel("");
		nombreLavage.setText(nombrePrestation+"");
		nombreLavage.setForeground(new Color(0, 128, 0));
		nombreLavage.setFont(new Font("Tahoma", Font.PLAIN, 25));
		nombreLavage.setBounds(10, 113, 239, 80);
		panel_1.add(nombreLavage);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.setForeground(new Color(224, 255, 255));
		menuBar.setBackground(new Color(70, 130, 180));
		menuBar.setBounds(0, 0, 112, 32);
		this.getContentPane().add(menuBar);
		
		JMenu mnNewMenu_2 = new JMenu("PARAMETRE");
		mnNewMenu_2.setBackground(new Color(0, 191, 255));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("GESTION DES AGENTS");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agents agents = new Agents();
				agents.setVisible(true);
			}
		});
		if(user.isAdmin) {
			mnNewMenu_2.add(mntmNewMenuItem_3);
		}
		
		JMenuItem mntmNewMenuItem = new JMenuItem("SE DECONNECTER");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user_connexion.main(null);
				dispose();
			}
		});
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("MON COMPTE");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyAccount account = new MyAccount(user);
				account.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_1);
		mnNewMenu_2.add(mntmNewMenuItem);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/image_main_page.jpg")));
		lblNewLabel_1.setBounds(436, 11, 263, 192);
		getContentPane().add(lblNewLabel_1);
		
		JButton voirSolde = new JButton("PLUS DE DETAILS");
		voirSolde.setBounds(475, 298, 199, 44);
		if(user.isAdmin) {
			getContentPane().add(voirSolde);
		}
		voirSolde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Voirsolde(user);
			}
		});
		voirSolde.setForeground(new Color(30, 144, 255));
		voirSolde.setFont(new Font("Calibri", Font.PLAIN, 16));
		voirSolde.setBackground(Color.WHITE);
		
		JButton btnPrestation = new JButton("PRESTATION");
		btnPrestation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Prestation prestationPage= new Prestation(user);
				prestationPage.setVisible(true);
				
			}
		});
		btnPrestation.setForeground(new Color(30, 144, 255));
		btnPrestation.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnPrestation.setBackground(Color.WHITE);
		btnPrestation.setBounds(475, 548, 199, 44);
		getContentPane().add(btnPrestation);
		
		JButton btnNewButton = new JButton("Actualiser la caisse");
		btnNewButton.setForeground(new Color(224, 255, 255));
		btnNewButton.setBackground(new Color(0, 128, 128));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					soldeCaisse=0;
					nombrePrestation=0;
					connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
					String query = "SELECT * FROM prestation where iduser='"+user.id+"'";
				    Statement stm = connection.createStatement();
				    ResultSet res = stm.executeQuery(query);
				    while(res.next()) {
				    	soldeCaisse+= res.getInt("prixprestation");
				    	nombrePrestation++;
				    }
				    spmmeCaisse.setText(soldeCaisse+" CFA");
				    nombreLavage.setText(nombrePrestation+"");
				    
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(475, 425, 195, 23);
		getContentPane().add(btnNewButton);
		
		
			
	}
}
