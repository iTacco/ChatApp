package de.thm.project.hamster;

import java.util.ArrayList;

public class Field
{
	// Class Atrributes
	//g-cost = distance from start to current-position 
	//h-cost = distance to target
	//f-cost = sum of g and h
    private int row, column, g, h, f;
    
    // Constructors
	public Field(int row, int column)
    {
    	this.row = row;
    	this.column = column;
    }
    //Copy constructor
	public Field (Field f)
    {
    	this.row = f.getRow();
    	this.column = f.getColumn();
    	this.g = f.getG();
    	this.h = f.getH();
    	this.f = f.getF();
    }
    //Empty (default) constructor
	private Field()
    {}
    
    // Static-Methods
    //get Field with lowest f-score
	public static Field getNextField(ArrayList<Field> openList)
	{
		Field tempField = new Field();
		boolean uninit = true;
		for(Field f : openList)
		{
			if((f.getF() < tempField.getF()) || uninit)
			{
				tempField = f;
				uninit = false;
			}
		}
		return tempField;
	}
	//checks whether a Field is within the specified array list or not.
	public static boolean fieldInList(Field f1, ArrayList<Field> fieldList)
	{
		boolean retval = false;
		for (Field f2 : fieldList)
		{
			if((f1.getRow() == f2.getRow()) && (f1.getColumn() == f2.getColumn()))
			{
				retval = true;
				break;
			}
		}
		return retval;
	}
	//Method to get a specific Field from a List depending on the x- and y-coordinates
	public static Field getFieldFromList(Field f1, ArrayList<Field> fieldList)
	{
		Field retval = null;
		for (Field f2 : fieldList)
		{
			if((f1.getRow() == f2.getRow()) && (f1.getColumn() == f2.getColumn()))
			{
				retval = f2;
				break;
			}
		}
		return retval;
	}
	//returns the position of the corn or null if there is more or less than one corn in the territorium
	public static Field getCornField(Territorium t)
	{
		for(int row = 0; row < t.getRows(); row++)
		{
			for(int col = 0; col < t.getColumns(); col++)
			{
				if(t.getMapInfo(row, col).equals("!"))
				{
					return new Field(row, col);
				}
			}
		}
		return null;
	}
	
    // other methods
    //method to calculate all the scores needed for the A*-Algorithm
	public void compute(Field previous, Field corn)
	{
		this.h = calcH(corn);
		this.g = previous.getG() + 1;
		this.f = this.h + this.g;
	}
	
	//helper method to calculate the manhatten distance between the current position and the position of the corn
	public int calcH(Field corn)
	{
		int h = 0;
		h += Math.abs(this.getRow() - corn.getRow());
		h += Math.abs(this.getColumn() - corn.getColumn()); 
		return h;
	}
    
    //copy method to copy a field with all its attributes to the current field
	public void copyField (Field f)
    {
    	this.row = f.getRow();
    	this.column = f.getColumn();
    	this.g = f.getG();
    	this.h = f.getH();
    	this.f = f.getF();
    }
    
    // Getters and Setters
	public int getColumn()
    {
    	return column;
    }
	public int getRow()
    {
    	return row;
    }
	public int getG()
    {
    	return g;
    }
	public int getH()
    {
    	return h;
    }
	public int getF()
    {
    	return f;
    }
	public void setG(int g)
    {
    	this.g = g;
    }
	public void setH(int h)
    {
    	this.h = h;
    }
	public void setF(int f)
    {
    	this.f = f;
    }
	public void setColumn(int column)
    {
    	this.column = column;
    }
	public void setRow(int row)
    {
    	this.row = row;
    }
}