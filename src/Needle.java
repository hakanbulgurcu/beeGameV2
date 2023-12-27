import java.awt.*;

public class Needle extends BaseObj implements IMove {
	private Direction direction;
	public Needle(BaseObj hero, Direction direction) {
		setX(hero.getX() + hero.getImageWidth()/2);
		setY(hero.getY() + hero.getImageHeight()/2);
		switch (direction){
			case UP: assignImage("NeedleUp.png"); break;
			case RIGHT: assignImage("NeedleRight.png"); break;
			case DOWN: assignImage("NeedleDown.png"); break;
			case LEFT: assignImage("NeedleLeft.png"); break;
		}
		speed = 40;
		setPoint(100);
		setHealth(100);
		this.direction = direction;
	}
	
	public void move(MainPanel panel) {
		int height = panel.getHeight();
		int width = panel.getWidth();

		if (direction == Direction.RIGHT) //RIGHT
		{
			if (getX() + speed + getImageWidth() <= width)
	    	{
	    		setX(getX() + speed);
	    	}
	    	else
	    		setX(width - getImageWidth());
		}
		
		if(direction == Direction.DOWN) //DOWN
		{
	    	if (getY() + speed + getImageHeight() <= height)
	    	{
	    		setY(getY() + speed);
	    	}
	    	else
	    		setY(height - getImageHeight());
		}
		
		if(direction == Direction.LEFT) //LEFT
		{
	    	if (getX() - speed >= 0)
	    	{
	    		setX(getX() - speed);
	    	}
	    	else
	    		setX(0);

		}
		
		if(direction == Direction.UP) //UP
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
