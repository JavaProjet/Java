package fr.uvsq.poo.monprojet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Fenetre implements ActionListener{
	public boolean start = false;
	public JFrame  F  = new JFrame("Rogue Like");
	public JPanel P = new JPanel();
	public JButton jouer = new JButton("Jouer partie");
	public JButton charger = new JButton("Charger partie");
	public JLabel titre = new JLabel("Menu Principal", SwingConstants.CENTER);
	public JLabel fond = new JLabel(new ImageIcon("pic.jpg"));
	public ImageIcon icone = new ImageIcon("pic.jpg");
	
	public Fenetre(){
		fond.setSize(10,10);
		jouer.addActionListener(this);
		charger.addActionListener(this);
		P.setLayout(new BorderLayout());
		P.add(titre, BorderLayout.NORTH);
		P.add(jouer, BorderLayout.WEST);
		P.add(charger,BorderLayout.EAST);
		P.add(fond);
		
		F.setIconImage(icone.getImage());
		F.setSize(new Dimension(600,300));
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        F.setContentPane(P);
		F.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e){
		this.start = true;
	}
	
	public boolean getGo(){
		System.out.print("");
		if(this.start == true){
			return this.start;
		}
		else return false;
	}
}
