package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Board {

	private Square[][] matSquare;
	private ArrayList<Color> allColors;
	private int sizeBoard; // NXN
	private int score;
	private Point2D hintCell;
	private ArrayList<Square> listPressedSquare;

	public Board(int numOfColors, int sizeBoard) {
		this.sizeBoard = sizeBoard;
		matSquare = new Square[sizeBoard][sizeBoard];
		allColors = new ArrayList<Color>();
		listPressedSquare = new ArrayList<Square>();
		score = 0;

		initColors(numOfColors);

		//TRYING TO CREATE A RANDOM BOARD
		int triesCounter = 0;
		do {
			initMatrix(numOfColors, sizeBoard);
			if(triesCounter > 25) {
				numOfColors--;
				triesCounter=0;
			}
		}while(check4NextStep() == false);
	}

	private void initMatrix(int numOfColors, int sizeBoard) {
		Random rnd = new Random();
		for (int i = 0; i < sizeBoard; i++) {
			for (int j = 0; j < sizeBoard; j++) {
				//Choosing a random color from the allColors group (via choosing a random index in 'allColor' array).
				int index = rnd.nextInt(numOfColors);
				Color newRndColor = allColors.get(index);
				//Creating a square. 
				Square newSquare = new Square(newRndColor, i, j);
				matSquare[i][j] = newSquare;
			}
		}
	}

	private void initColors(int numOfColors) {
		allColors.clear();
		// Create equal distribution  of colors. 
		int devSize = numOfColors/3 ; 
		if(numOfColors%3!=0)
			devSize++;
		int intervalSizePerColor = 255/(devSize-1);
		for(int r=devSize-1; r>=0 ; r--)
			for(int g=devSize-1; g>=0 ; g--)
				for(int b=devSize-1; b>=0 && allColors.size()<numOfColors; b--)
					allColors.add(Color.rgb(
							r*intervalSizePerColor,
							g*intervalSizePerColor,
							b*intervalSizePerColor));
		
	}

	//########## GETTERS AND SETTERS ##########
	public Square[][] getMatSquare() {
		return matSquare;
	}

	public int getSize() {
		return sizeBoard;
	}

	public int getScore() {
		return score;
	}

	public void changeSquarePressedState(int row, int col) {
		if(listPressedSquare.size()>=4) { // don't do anything
			return;
		}

		Square sqaure = this.matSquare[row][col];
		sqaure.changeIsTheButtonPressed();

		// update  
		if(sqaure.getIsPressed()) 
			listPressedSquare.add(sqaure);
		else 
			listPressedSquare.remove(sqaure);

	}

	public int getCounterPressed() {
		return listPressedSquare.size();
	}

	//########## MATIN FUNCTIONALITY ##########

	public boolean check4Squares() {
		if(listPressedSquare.size()<4)
			return false;

		if (haveSameColor(listPressedSquare) == false)
			return false;

		//In this Point2D all squares have the same color. 
		//Check now the inner colors of all squares.
		if(isInnerRecWithDifferentColor(listPressedSquare) == false)
			return false;

		ArrayList<Point2D> squaresLocations = createPoint2Ds(listPressedSquare);
		return checkIfRectangle(squaresLocations);
	}

	public boolean check4NextStep() {
		for (int topLeftRow=0; topLeftRow<matSquare.length-1;topLeftRow++) {
			for (int topLeftCol=0; topLeftCol<matSquare[0].length-1;topLeftCol++) {
				for(int hight=1; hight < matSquare.length - topLeftRow; hight++) {
					for(int width=1; width < matSquare[0].length - topLeftCol; width++) {
						ArrayList<Square> listSquares = new ArrayList<Square>(Arrays.asList(
								matSquare[topLeftRow][topLeftCol],
								matSquare[topLeftRow][topLeftCol+width],
								matSquare[topLeftRow+hight][topLeftCol],
								matSquare[topLeftRow+hight][topLeftCol+width]
								));
						if(haveSameColor(listSquares) && isInnerRecWithDifferentColor(listSquares)) {
							hintCell=new Point2D(topLeftRow, topLeftCol);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void clearAllPressedSquared() {
		for (int i = 0; i < listPressedSquare.size(); i++) {
			listPressedSquare.get(i).changeIsTheButtonPressed();
		}
		listPressedSquare.clear();
	}
	public void paintAllPressedSquared() {
		if (listPressedSquare.size()<4)
			return;
		ArrayList<Point2D> rectLocations = createPoint2Ds(listPressedSquare);

		//calculate score
		int addedScore =  calculateScore(rectLocations);
		score = score + addedScore;


		//paint 
		Color c = listPressedSquare.get(0).getColor();
		for (int i = 0; i < matSquare.length; i++) {
			for (int j = 0; j < matSquare[0].length; j++) {
				Point2D location = getSquareLocation(matSquare[i][j]);
				if(checkIfLocationIsInRectangle(location, rectLocations))
					matSquare[i][j].setColor(c);
			}
		}


	}


	//########## HELPERS ##########
	private boolean haveSameColor(ArrayList<Square> listSquares) {
		Color c = listSquares.get(0).getColor();
		for (int i = 1; i < listSquares.size(); i++) {
			if(c.equals(listSquares.get(i).getColor()) == false)
				return false;
		}
		return true;
	}
	private boolean isInnerRecWithDifferentColor(ArrayList<Square> listSquares) {
		if(listSquares.size()<4)
			return false;
		Color c = listSquares.get(0).getColor();
		ArrayList<Point2D> rectLocations = createPoint2Ds(listSquares);
		Point2D p1 = rectLocations.get(0);
		Point2D p4 = rectLocations.get(3);

		for(int i=0;i<matSquare.length;i++) {
			for(int j=0; j<matSquare[0].length;j++) {
				if(p1.getX()<=i && i<=p4.getX() && p1.getY()<=j && j<=p4.getY())
					if(matSquare[i][j].getColor().equals(c)==false)
						return true;
			}
		}

		return false;
	}
	private boolean checkIfLocationIsInRectangle(Point2D location, ArrayList<Point2D> rectLocations) {
		if(checkIfRectangle(rectLocations) == false)
			return false;

		boolean bX = rectLocations.get(0).getX() <= location.getX() && 
				location.getX() <= rectLocations.get(2).getX();
		boolean bY = rectLocations.get(0).getY() <= location.getY() && 
				location.getY() <= rectLocations.get(1).getY();
		return bX && bY;
	}
	private boolean checkIfRectangle(ArrayList<Point2D> Point2Ds) {
		Point2D p1 = Point2Ds.get(0);
		Point2D p2 = Point2Ds.get(1);
		Point2D p3 = Point2Ds.get(2);
		Point2D p4 = Point2Ds.get(3);

		boolean b1 = (p1.getX() == p2.getX()) && (p3.getX() == p4.getX());
		boolean b2 = (p1.getY() == p3.getY()) && (p2.getY() == p4.getY());

		return b1 && b2; 
	}

	private int calculateScore(ArrayList<Point2D> rectLocations) {
		Point2D p1 = rectLocations.get(0);
		Point2D p3 = rectLocations.get(2);
		Point2D p4 = rectLocations.get(3);

		int hight= (int) ( p3.getX() -p1.getX() +1) ;
		int width = (int) (p4.getY() - p1.getY()+1);

		return hight*width;
	}
	private Point2D getSquareLocation (Square square) {
		return new Point2D(square.getRow(), square.getColumn());
	}	
	private ArrayList<Point2D> createPoint2Ds(ArrayList<Square> squares) {
		ArrayList<Point2D> Point2Ds = new ArrayList<Point2D>();
		for (int i = 0; i <  squares.size(); i++) {
			Point2Ds.add(getSquareLocation(squares.get(i)));
		}
		return sortPoint2Ds(Point2Ds);
	}
	private ArrayList<Point2D> sortPoint2Ds(ArrayList<Point2D> Point2Ds) {
		Collections.sort( Point2Ds, new Comparator<Point2D>() {
			public int compare(Point2D x1, Point2D x2) {
				int result = Double.compare(x1.getX(), x2.getX());
				if ( result == 0 ) {
					// both X are equal -> compare Y too
					result = Double.compare(x1.getY(), x2.getY());
				} 
				return result;
			}
		});
		return Point2Ds;
	}

	public  Point2D getHint() {
		return this.hintCell;
	}


}
