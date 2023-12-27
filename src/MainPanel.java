import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.*;

public class MainPanel extends JPanel implements KeyListener { // inherit JPanel
	/**
	 *
	 */
	private static final long serialVersionUID = 657370957399188201L;
	private int frameHeight = 0;
	private int frameWidth = 0;
	private int maxEnemies = 1;
	private int maxFlowers = 1;
	private int maxWebs = 10;
	public int maxNeedles = 10;

	Hero myHero = new Hero();
	List<Enemies> myEnemies = new ArrayList<Enemies>();
	List<Flowers> myFlowers = new ArrayList<Flowers>();
	List<Web> myWebs = new ArrayList<Web>();
	List<Needle> myNeedles = new ArrayList<Needle>();

	// Constructor:-----------------------------------------------------
	public MainPanel(int width, int height)
	{
		setFocusable(true); //Keylistener'�n �al��mas� i�in gerekli
        addKeyListener(this);
		frameWidth = width;
		frameHeight = height;
		createObjects();
		new Timer(250, enemyMoveTimer).start();
		new Timer(1000, enemyCreateTimer).start();
		new Timer(10, CollisionTimer).start();
		new Timer(200, weaponMoveTimer).start();
	}

	public void createObjects() {
		Random generator = new Random();
		if (myEnemies.size() < maxEnemies) {
			if(generator.nextInt(5) == 0)
				myEnemies.add(new Enemies(frameWidth, frameHeight));
		}

		if ((myFlowers.size() < maxFlowers) && frameHeight!=0) {
			myFlowers.add(new Flowers(generator.nextInt(frameWidth),generator.nextInt(frameHeight)));
		}

		for (int i = 0; i < myEnemies.size();i++) {
			if(myEnemies.get(i).alignmentCheck(myHero) != -1) {
				if (myWebs.size() < maxWebs) {
					if(generator.nextInt(3)==0)
						myWebs.add(new Web(myEnemies.get(i).getX(), myEnemies.get(i).getY(), myEnemies.get(i).alignmentCheck(myHero)));
				}
			}
		}

		prepareImages();
	}

	public void prepareImages() {
		prepareImage(myHero.getImage(), this);
		myEnemies.forEach(e -> prepareImage(e.getImage(), this));
		myFlowers.forEach(e -> prepareImage(e.getImage(), this));
		myWebs.forEach(e -> prepareImage(e.getImage(), this));
		myNeedles.forEach(e -> prepareImage(e.getImage(), this));
		setDoubleBuffered(true);
	}

	// ----------------------------------------------------------------

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		//g.clearRect(0, 0, getWidth(), getHeight()); //rengi s�f�rl�yor

		myHero.draw(g2d, this);
		myEnemies.forEach(e -> e.draw(g2d, this));
		myFlowers.forEach(e -> e.draw(g2d, this));
		myWebs.forEach(e -> e.draw(g2d, this));
		myNeedles.forEach(e -> e.draw(g2d, this));

		Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
		g.dispose();
		frameHeight = this.getHeight();
		frameWidth = this.getWidth();
		this.maxFlowers= (frameHeight*frameWidth/(myHero.getImageHeight()*myHero.getImageWidth()*100)*3);
		this.maxEnemies= (frameHeight*frameWidth/(myHero.getImageHeight()*myHero.getImageWidth()*100)*2);
		this.maxWebs= this.maxEnemies;
		this.maxNeedles = this.maxEnemies * 2;
	}

	Action enemyMoveTimer = new AbstractAction() {

		public void actionPerformed(ActionEvent en) {
			myEnemies.forEach(e -> e.move(MainPanel.this));
			prepareImages();
			repaint();
		}
	};

	Action weaponMoveTimer = new AbstractAction() {

		public void actionPerformed(ActionEvent ae) {
			Random generator = new Random();
			myWebs.forEach(e -> e.move(MainPanel.this));
			myNeedles.forEach(e -> e.move(MainPanel.this));

			for (int i = 0; i < myNeedles.size() ; i++) {
				if((myNeedles.get(i).getX()+myNeedles.get(i).getImageWidth()==frameWidth)||(myNeedles.get(i).getY()+myNeedles.get(i).getImageHeight()==frameHeight)||(myNeedles.get(i).getX()==0)||(myNeedles.get(i).getY()==0))
					if(generator.nextInt(7)==0)
						myNeedles.remove(i);
			}

			prepareImages();
			repaint();
		}
	};


	Action enemyCreateTimer = new AbstractAction() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			createObjects();
			repaint();
		}
	};

	Action CollisionTimer = new AbstractAction() {

		public void actionPerformed(ActionEvent e) {
			List<BaseObj> collisions = myFlowers.stream().filter(f -> f.collisionCheck(myHero)).collect(Collectors.toList());
			collisions.forEach(f -> {
				myHero.setPoint(myHero.getPoint() + f.getPoint());
				myHero.setHealth(myHero.getHealth() + f.getHealth());
			});

			myFlowers.removeAll(collisions);

			collisions = myEnemies.stream().filter(f -> f.collisionCheck(myHero)).collect(Collectors.toList());

			collisions.forEach(f -> {
				myHero.setPoint(myHero.getPoint() + f.getPoint());
				myHero.setHealth(myHero.getHealth() - f.getHealth());
			});

			myEnemies.removeAll(collisions);

			collisions = myWebs.stream().filter(f -> f.collisionCheck(myHero)).collect(Collectors.toList());

			collisions.forEach(f -> {
				myHero.setPoint(myHero.getPoint() + f.getPoint());
				myHero.setHealth(myHero.getHealth() - f.getHealth());
			});

			myWebs.removeAll(collisions);

			for (int i = 0; i < myNeedles.size() ; i++)
			{
				for (int j = 0; j < myEnemies.size(); j++)
				{
					if(myNeedles.get(i).collisionCheck(myEnemies.get(j)))
					{
						myHero.setPoint(myHero.getPoint() + myEnemies.get(j).getPoint());
						myEnemies.remove(j);
					}
				}
			}

			for (int i = 0; i < myNeedles.size() ; i++)
			{
				for (int j = 0; j < myWebs.size(); j++)
				{
					if(myNeedles.get(i).collisionCheck(myWebs.get(j)))
					{
						myHero.setPoint(myHero.getPoint() + myWebs.get(j).getPoint());
						myWebs.remove(j);
					}
				}
			}

			prepareImages();
			repaint();
		}
	};

	@Override
    public void keyPressed(KeyEvent ke) 
    {
//		System.out.println(mPanel.getHeight() + "-" + mPanel.getWidth()+ "-" + this.getHeight()+ "-" + this.getWidth() + "-" + myHero.getX()+ "-" + myHero.getY() + "-" + myHero.imagePath);
		switch (ke.getKeyCode()) {
			//if the right arrow in keyboard is pressed...
            case KeyEvent.VK_RIGHT: {
            	if (ke.isControlDown() && (maxNeedles > myNeedles.size()))
            		myNeedles.add(new Needle(myHero, Direction.RIGHT));
            	else
            		myHero.moveRight(getWidth());
            }
            break;
            //if the left arrow in keyboard is pressed...
            case KeyEvent.VK_LEFT: {
            	if (ke.isControlDown() && (maxNeedles > myNeedles.size()))
            		myNeedles.add(new Needle(myHero, Direction.LEFT));
            	else
            		myHero.moveLeft();
            }
            break;
            //if the down arrow in keyboard is pressed...
            case KeyEvent.VK_DOWN: {
            	if (ke.isControlDown() && (maxNeedles > myNeedles.size()))
            		myNeedles.add(new Needle(myHero, Direction.DOWN));
            	else
            		myHero.moveDown(getHeight());
            }
            break;
            //if the up arrow in keyboard is pressed...
            case KeyEvent.VK_UP: {
            	if (ke.isControlDown() && (maxNeedles > myNeedles.size()))
            		myNeedles.add(new Needle(myHero, Direction.UP));
            	else
            		myHero.moveUp();
            }
            break;
        }
		System.out.println(myHero.getHealth() + "-" + myHero.getPoint());
        repaint();
    }

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
	}

}

