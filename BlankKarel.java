import stanford.karel.*;
import java.awt.*;

public class BlankKarel extends SuperKarel {
	int count =0;                       //steps counter

		public void run() {
		int width = 1;
		boolean doubleLine = false,squareCenter=false;

		while (frontIsClear()) {        //measure width
			move();
			System.out.println(++count);
			width++;
		}

		if (width % 2 == 0) {           //(decide single or doubleLines) and (squareCenter or single point center)
			doubleLine = true;
			if((width-2)/2%2 == 0)squareCenter=true;
		}
		else if((width-1)/2%2 == 0)squareCenter=true;

		final int halfWay = width / 2 + width % 2;    								//ceiling value for width/2
		setBeepersInBag(10000);

		turnAround();
		for (int i = 1; i < halfWay; i++) {	move(); System.out.println(++count);}  //move to start line
		turnRight();

		int repeat = 4;															   //repeat the following code 4 times
		boolean flag = true;

		while (repeat > 0) {	//single loop solves a single quarter

			if (flag) putBeeper();												//this is applied the first loop always & then if double line it will keep applying
			moveStraight(halfWay - 1, flag);								//walk halfway
			flag = doubleLine;
			turnRight();

			moveStraight(halfWay / 2, repeat!=1 || doubleLine);		//move half halfway
			turnRight();
			moveStraight(halfWay / 2, false);							//walk to center
			fillCenter(squareCenter);
			moveStraight(halfWay / 2, false);							//come back from center
			corner(doubleLine ,squareCenter,repeat==1);				//do corner

			repeat -= 1;		//a single quarter is done
		}
		turnLeft();
		moveStraight(halfWay - 1, false);
	}

	public void moveStraight(int steps, boolean flag) {		//move a number of given steps (place beepers while moving if flag is true)
		for (int i = 0; i < steps; i++) {
			move();
			System.out.println(++count);
			if (flag) putBeeper();
		}
	}

	public void corner(boolean doubleLine,boolean squareCenter,boolean lastCorner) {	//Corner case after filling the center
		turnRight();
		if(squareCenter && (!lastCorner || doubleLine))putBeeper();
		while (frontIsClear()) {
			move();
			System.out.println(++count);
			if(!lastCorner || doubleLine)putBeeper();
		}
		if (doubleLine && !lastCorner) {
			turnLeft();
			move();
			System.out.println(++count);
			turnLeft();
		}
		else turnAround();
	}

	public void fillCenter(boolean square) {	//fill center either in a single point or in a square shape
		if (square) {
			for (int i = 0; i < 3; i++) {
				paintCorner(Color.PINK);
				move();
				System.out.println(++count);
				if(i!=2)turnLeft();
			}
			paintCorner(Color.PINK);
		}
		else {
			paintCorner(Color.PINK);
			turnAround();
		}
	}
}
//mesk alhamaideh