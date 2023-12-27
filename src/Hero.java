import java.awt.*;

public class Hero extends BaseObj {

	public Hero() {
		setX(215);
		setY(0);
		assignImage("Bee.png");
		speed = 10;
		setPoint(0);
		setHealth(1000);
	}
				
	public void moveRight(int width)
	{
    	if (getX() + speed + getImageWidth() <= width)
    	{
    		setX(getX() + speed);
    	}
    	else
    		setX(width - getImageWidth());
	}

	public void moveDown(int height)
	{
    	if (getY() + speed + getImageHeight() <= height)
    	{
    		setY(getY() + speed);
    	}
    	else
    		setY(height - getImageHeight());
	}

	public void moveUp()
	{
    	if (getY() - speed >= 0)
    	{
    		setY(getY() - speed);
    	}
    	else
    		setY(0);
	}

	public void moveLeft()
	{
    	if (getX() - speed >= 0)
    	{
    		setX(getX() - speed);
    	}
    	else
    		setX(0);
	}

}
