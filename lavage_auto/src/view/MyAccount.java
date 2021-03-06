package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.User;
import utils.BaseDeDonnee;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class MyAccount extends JFrame {

	private JPanel contentPane;
	private JTextField fullname;
	private JTextField username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyAccount frame = new MyAccount(null);
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
	private boolean isActiv = false;
	private JPasswordField pwd;
	private JPasswordField newPwd;
	public MyAccount(User user) {
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 574);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOM ET PRENOM :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 286, 168, 31);
		contentPane.add(lblNewLabel);
		
		fullname = new JTextField();
		fullname.setBackground(new Color(176, 224, 230));
		fullname.setBounds(252, 290, 253, 28);
		contentPane.add(fullname);
		fullname.setColumns(10);
		fullname.setText(user.fullName);
		JLabel lblMotDePasse = new JLabel("MOT DE PASSE :");
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMotDePasse.setBounds(10, 376, 168, 31);
		contentPane.add(lblMotDePasse);
		
		JLabel lblNomDutilisateur = new JLabel("NOM D'UTILISATEUR :");
		lblNomDutilisateur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNomDutilisateur.setBounds(10, 244, 196, 31);
		contentPane.add(lblNomDutilisateur);
		
		username = new JTextField();
		username.setEditable(false);
		username.setBackground(new Color(176, 224, 230));
		username.setColumns(10);
		username.setBounds(252, 244, 253, 28);
		contentPane.add(username);
		username.setText(user.userName);
		JLabel lblMotDePasse_1 = new JLabel("NOUVEAU MDP :");
		lblMotDePasse_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMotDePasse_1.setBounds(10, 421, 168, 31);
		contentPane.add(lblMotDePasse_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("CHANGER DE MOT DE PASSE");
		chckbxNewCheckBox.setBackground(new Color(255, 99, 71));
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isActiv = (isActiv) ? false : true;
				if(isActiv) {
					newPwd.setEditable(true);
					pwd.setEditable(true);
				}else {
					newPwd.setEditable(false);
					pwd.setEditable(false);
				}
				
			}
		});
		chckbxNewCheckBox.setBounds(149, 346, 203, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JButton btnNewButton = new JButton("ENREGISTRER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="UPDATE `user` SET `fullname`='"+fullname.getText()+"' WHERE id ="+user.id+"";
					if(isActiv) {
						System.out.println(pwd.getPassword());
						String pass =pwd.getText();
						
						if(user.password.equals(pass)) {
							user.password= newPwd.getText();
							query = "UPDATE `user` SET `password`='"+newPwd.getText()+"',`fullname`='"+fullname.getText()+"' WHERE id ='"+user.id+"'";
							Connection connection;
							connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
							
							Statement stmT;
							stmT = connection.createStatement();
							stmT.executeUpdate(query);	
							JOptionPane.showMessageDialog(null, "Informaton modifi???e avec succ???s, veuillez redemarrer l'application pour voir le changement", "Information", JOptionPane.INFORMATION_MESSAGE);
							
						}else {
							JOptionPane.showMessageDialog(null, "Veuillez rentrer votre ancienne mot passe correctement", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						 
					}else {
						Connection connection;
						connection = (Connection) DriverManager.getConnection(BaseDeDonnee.bdMainConfig,BaseDeDonnee.bdName,BaseDeDonnee.bdPassword);
						Statement stmT;
						stmT = connection.createStatement();
						stmT.executeUpdate(query);	
						JOptionPane.showMessageDialog(null, "Informaton modifi???e avec succ???s, veuillez redemarrer l'application pour voir le changement", "Information", JOptionPane.INFORMATION_MESSAGE);

					}
									
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(new Color(60, 179, 113));
		btnNewButton.setBounds(188, 489, 143, 31);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../assets/users.png")));
		lblNewLabel_1.setBounds(117, 0, 332, 256);
		contentPane.add(lblNewLabel_1);
		
		pwd = new JPasswordField();
		pwd.setEditable(false);
		pwd.setBackground(new Color(176, 224, 230));
		pwd.setBounds(252, 379, 253, 28);
		contentPane.add(pwd);
		
		newPwd = new JPasswordField();
		newPwd.setEditable(false);
		newPwd.setBackground(new Color(176, 224, 230));
		newPwd.setBounds(252, 421, 253, 28);
		contentPane.add(newPwd);
		
		
		
		
	}
}
