import java.awt.*;
import java.net.URL;

import javax.swing.ImageIcon;


public class BaseObj implements IDraw {
	/*
	 * Simple Object class to store the object's general properties.
	 */
	private int x;
	private int y;
	public int speed;
	private int point;
	private int health;

	Image image; // Reserve memory for Image heroImage; // get ImageIcon imageIcon and store it in Image Image

	BaseObj() {} // default constructor

	public Image getImage()
	{
		return image;
	}

	public int getImageHeight()
	{
		return image.getHeight(null);
	}
	
	public int getImageWidth()
	{
		return image.getWidth(null);
	}

	public int getX() { // accessor/GET method for data
		return x;
	}

	public void setX(int x) { // mutator/SET method for data
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics2D g2d, MainPanel panel) {
		g2d.drawImage(getImage(), getX(), getY(), panel);
	}
	public void assignImage(String fileName) {
		URL imagePath = getClass().getResource(fileName);
		ImageIcon imageIcon = new ImageIcon(imagePath);
		image = imageIcon.getImage();
	}

	public boolean collisionCheck(BaseObj obj)
	{
		if (((obj.getX() + obj.getImageWidth()) < getX()) || (obj.getX() > (getX() + getImageWidth())))
		{
			return false;
		}
		else if (((obj.getY() + obj.getImageHeight()) < getY()) || (obj.getY() > (getY() + getImageHeight())))
		{
			return false;
		}
		else return true;
	}

	public int alignmentCheck(BaseObj obj)
	{
		if (((obj.getX() + obj.getImageWidth()/2) < (getX()+getImageWidth())) && ((obj.getX() + obj.getImageWidth()/2) > getX()))
		{
			if (obj.getY()<getY())
				return 0;
			else
				return 2;
			
		}
		else if (((obj.getY() + obj.getImageHeight()/2) < (getY() + getImageWidth())) && ((obj.getY() + obj.getImageHeight()/2) > getY()))
		{
			if (obj.getX()<getX())
				return 3;
			else
				return 1;			
		}
		else return -1;
	}

}