package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//CONFIGURAÇÕES DA TELA
	final int originalTileSize = 16; // tile 16x16
	final int scale = 3; //Escala para os tiles
	
	public final int tileSize = originalTileSize * scale; // tile fica 48x48
	public final int maxScreenCol = 16; //Colunas máximas na tela
	public final int maxScreenRow = 12; //Linhas máximas na tela
	public final int screenWidth = tileSize * maxScreenCol; //Largura da tela = 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //Altura da tela = 576 pixels
	
	// CONFIGURAÇÕES DO MUNDO
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FPS
	int FPS = 60;
	
	// SISTEMA
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(); //Lê as teclas
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this); //Checa as colisões
	public AssetSetter aSetter = new AssetSetter(this);
	Thread gameThread; //Faz o programa rodar
	
	// ENTIDADE E OBJETO
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //Tamanho do GamePanel
		this.setBackground(Color.black); //Cor de fundo
		this.setDoubleBuffered(true); // Se for true, todos os desenhos do componente serão feitos em um buffer de pintura fora da tela
		this.addKeyListener(keyH); //Reconhece a entrada de teclas
		this.setFocusable(true); //Foca em receber as teclas
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); //Instanciando um Thread
		gameThread.start(); //Chama o método run
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //0.0166666 segundos
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) { //Enquanto o gameThread existir:	
			
			currentTime = System.nanoTime(); //O tempo atual é verificado
			
			delta += (currentTime - lastTime) / drawInterval; //É calculado quanto tempo passou e adicionado ao delta
			
			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update(); //atualiza informação assim como movimentos dos bonecos
				repaint(); //Desenha a tela com informação atualizada
				delta--; //Delta zerado
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) { //Graphics é uma classe que tem muitas funções para desenhar objetos na tela
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//TILE
		tileM.draw(g2);
		
		//OBJETO
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		//PLAYER
		player.draw(g2);
		
		g2.dispose(); //Elimina esse contexto gráfico e libera todos os recursos do sistema que ele estiver usando
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
