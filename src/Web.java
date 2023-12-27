import javax.swing.ImageIcon;
import java.awt.*;

public class Web extends BaseObj implements IMove {
	private int direction;
	
	public Web(int intX, int intY, int direction) {
		setX(intX);
		setY(intY);
		assignImage("Web.png");
		speed = 30;
		setPoint(100);
		setHealth(100);
		this.direction = direction;
	}
	
	public void move(MainPanel panel) {
		int height = panel.getHeight();
		int width = panel.getWidth();

		if (direction == 1) //RIGHT
		{
			if (getX() + speed + getImageWidth() <= width)
	    	{
	    		setX(getX() + speed);
	    	}
	    	else
	    		setX(width - getImageWidth());
		}
		
		if(direction == 2) //DOWN
		{
	    	if (getY() + speed + getImageHeight() <= height)
	    	{
	    		setY(getY() + speed);
	    	}
	    	else
	    		setY(height - getImageHeight());			
		}
		
		if(direction == 3) //LEFT
		{
	    	if (getX() - speed >= 0)
	    	{
	    		setX(getX() - speed);
	    	}
	    	else
	    		setX(0);			
		}
		
		if(direction == 0) //UP
		{
	    	if (getY() - speed >= 0)
	    	{
	    		setY(getY() - speed);
	    	}
	    	else
	    		setY(0);			
		}
	}

}
