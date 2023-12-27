import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class Enemies extends BaseObj implements IMove {
		
	public Enemies(int width, int height) {
		Random generator = new Random();
		setX(generator.nextInt(width));
		setY(generator.nextInt(height));
		assignImage("Spider.png");
		speed = 10;
		setPoint(200);
		setHealth(100);
	}

	public void move(MainPanel panel) {
		int height = panel.getHeight();
		int width = panel.getWidth();
		BaseObj myHero = panel.myHero;

		Random generator = new Random();
		int direction = generator.nextInt(7);
		
		switch(generator.nextInt(3)){
		case 0: 
		if(myHero.getX()<getX())
			direction = 3;
			else
				direction = 1;break;
		case 1:
			if(myHero.getY()<getY())	
				direction = 0;
			else 
				direction = 2;break;
		}
		
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
