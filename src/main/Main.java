package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fecha a janela ao apertar o X
		window.setResizable(false); //Impossível mudar tamanho da janela
		window.setTitle("Aventura 2D"); //Título
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //Faz a janela caber nos tamanhos do GamePanel
		
		window.setLocationRelativeTo(null); //Janela no centro da tela
		window.setVisible(true); //Possível ver janela
		
		gamePanel.startGameThread();
	}

}
