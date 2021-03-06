package view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import utils.BaseDeDonnee;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;

public class user_connexion {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user_connexion window = new user_connexion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public user_connexion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../assets/logo_apps.jpg")));
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 925, 443);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("CAR WASH | FEN�TRE DE CONNEXION");;
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Calibri", Font.BOLD, 18));
		lblMotDePasse.setBounds(551, 312, 120, 20);
		frame.getContentPane().add(lblMotDePasse);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/image_login_page.jpg")));
		lblNewLabel_1.setBounds(-44, 11, 562, 384);
		frame.getContentPane().add(lblNewLabel_1);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(528, 11, 371, 384);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(73, 5, 225, 225);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setIcon(new ImageIcon(this.getClass().getResource("../assets/image_login_lock.png")));
		
		JButton validatConnexion = new JButton("CONNEXION");
		validatConnexion.setBounds(123, 333, 138, 40);
		panel.add(validatConnexion);
		validatConnexion.setFont(new Font("Calibri", Font.PLAIN, 20));
		validatConnexion.setForeground(new Color(255, 255, 255));
		validatConnexion.setBackground(new Color(70, 130, 180));
		
		JLabel lblNewLabel = new JLabel("Nom d'utilisateur :");
		lblNewLabel.setBounds(10, 251, 159, 24);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		
		
		usernameField = new JTextField();
		usernameField.setForeground(new Color(255, 255, 255));
		usernameField.setBounds(159, 247, 202, 31);
		panel.add(usernameField);
		usernameField.setBackground(new Color(70, 130, 180));
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBounds(159, 291, 202, 31);
		panel.add(passwordField);
		passwordField.setBackground(new Color(70, 130, 180));
		validatConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				try {
					Connection connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
					PreparedStatement st = (PreparedStatement) connection.prepareStatement(
							"select * from user where username=? and password=?");
					st.setString(1, username);
					st.setString(2, password);
					ResultSet rs = st.executeQuery();
					if(rs.first()) {
						int id = rs.getInt("id");
						String fullname = rs.getString("fullname");
						if(rs.getBoolean("isactive")) {
							Main_page page_principale = new Main_page(new User(id,username,fullname, rs.getBoolean("isadmin"), rs.getString("password")));
							page_principale.setVisible(true);
							frame.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Votre compte est desactiver veuillez contacter l'administrateur", "Erreur", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(validatConnexion, "Information incorrecte");
					}
					
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		});
	}
	
	
}
