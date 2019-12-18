package de.thm.project.hamster;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

// Move the hamster using the shortest path to the corn
public class AStarAlgorithm {

	private Territorium territorium;
	private Hamster hamster;
	private Field corn;

	public AStarAlgorithm(Territorium territorium, Hamster hamster) {
		this.territorium = territorium;
		this.hamster = hamster;
		this.corn = Field.getCornField(territorium);
	}

	private void goPath(ArrayList<Field> path)
	{
		for(Field f : path)
		{
			while(f.getRow() < hamster.getRow())
			{
				while(hamster.getDirection() != Hamster.NORD)
				{
					hamster.turnLeft();
				}
				hamster.goForward();
			}
			while(f.getRow() > hamster.getRow())
			{
				while(hamster.getDirection() != Hamster.SUED)
				{
					hamster.turnLeft();
				}
				hamster.goForward();
			}
			while(f.getColumn() < hamster.getCol())
			{
				while(hamster.getDirection() != Hamster.WEST)
				{
					hamster.turnLeft();
				}
				hamster.goForward();
			}
			while(f.getColumn() > hamster.getCol())
			{
				while(hamster.getDirection() != Hamster.OST)
				{
					hamster.turnLeft();
				}
				hamster.goForward();
			}
		}
	}

	// Calculate the neighbor with the given offset. If needed add it to the openList or refresh the parameters of the current neighbor
	private void calcNeighbor(Field currField, Field neighbor, Field corn, int rowOffset, int columnOffset, ArrayList<Field> openList, ArrayList<Field> closedList)
	{
		neighbor.setRow(currField.getRow() + rowOffset);
		neighbor.setColumn(currField.getColumn() + columnOffset);
		boolean mauerDa = territorium.isWall(neighbor.getRow(), neighbor.getColumn());
		//if there is a wall on the field: ignore it
		if(!mauerDa)
		{
			//if the neighbor is already in the closedList: ignore it
			if(!Field.fieldInList(neighbor, closedList))
			{
				//calculate all of its scores
				neighbor.compute(currField, corn);
				//if the neighbor is not in the openList: add ir
				if(!Field.fieldInList(neighbor, openList))
				{
					openList.add(new Field(neighbor));
				}
				//else: check if the f-score is lower by using the current path. If this is the case is: refresh the scores of the field
				else
				{
					Field alreadyInOpen = Field.getFieldFromList(neighbor, openList);
					if(alreadyInOpen.getF() > neighbor.getF())
					{
						openList.remove(alreadyInOpen);
						openList.add(new Field(neighbor));
					}
				}
			}
		}
	}

	// Method for handling the A*-Algorithm
	public void start()
	{
		if(corn != null) {
			//Initialize two ArrayLists for the Algorithm
			ArrayList <Field> openList = new ArrayList<Field>();	//squares that are being considered to find the shortest path
			ArrayList <Field> closedList = new ArrayList<Field>();	//squares that does not have to consider it again

			//Initialize three Field-variables which are used by the algorithm
			Field currField = new Field();
			Field neighbor = new Field();

			//Corn found?
			boolean found = false;

			//Initalize start-field
			currField.setRow(hamster.getRow());
			currField.setColumn(hamster.getCol());

			//Start-field: calc h and and set f to the same value because g-cost is 0! So for the start-field: h and f are identical
			currField.setH(currField.calcH(corn));
			currField.setF(currField.getH());
			openList.add(currField);		//add start-field to openList

			//Start of the actual A*-Algorithm
			do {
				currField = Field.getNextField(openList);		//get field with lowest f-score from openList
				openList.remove(currField);						//move it from the openList...
				closedList.add(currField);						//...to the closedList

				//CurrentField = CornField?
				if(Field.fieldInList(corn, closedList))
				{
					found = true;
					break;						//if target-field was added to closedList -> FINISH!
				}

				//Calculate up and down neighbors
				for(int rowOffset = -1; rowOffset <= 1; rowOffset += 2)
				{
					calcNeighbor(currField, neighbor, corn, rowOffset, 0, openList, closedList);
				}

				//Calculate left and right neighbors
				for(int columnOffset = -1; columnOffset <= 1; columnOffset += 2)
				{
					calcNeighbor(currField, neighbor, corn, 0, columnOffset, openList, closedList);
				}
			} while(!openList.isEmpty());		//Continue until openList is empty

			//If there is no valid path: throw an Error-Massage
			if(!found)
			{
				System.out.println("Openlist ist leer! => Kein Weg zum Korn!");
			}

			//If there is a path to the corn, create the shortest possible path
			else
			{
				ArrayList<Field> shortestPath = new ArrayList<>();
				ArrayList<Field> neighbors = new ArrayList<>();
				Field tempField = new Field();
				tempField = closedList.get(closedList.size() -1);
				shortestPath.add(new Field(tempField));
				closedList.remove(tempField);

				//Continue until current position of hamster was reached
				while((tempField.getRow() != hamster.getRow()) || (tempField.getColumn() != hamster.getCol()))
				{
					for(Field f : closedList)
					{
						if(f.getG() == (tempField.getG() -1))
						{
							//calculate distance delta (it must be one otherwise it's not a direct neighbor
							int distanceDelta = Math.abs(f.getRow() - tempField.getRow());
							distanceDelta += Math.abs(f.getColumn() - tempField.getColumn());
							//add all direct neighbors (no diagonal-neighbors) to the neighbot-List
							if(distanceDelta == 1)
							{
								neighbors.add(new Field(f));
							}
						}
					}
					//find the neighbor with the lowest f-score
					for(Field n : neighbors)
					{
						if(n.getF() <= tempField.getF())
						{
							tempField = n;
						}
					}
					shortestPath.add(new Field(tempField)); 	//and add it to the shortist possible path
					neighbors.clear();							//clear all neighbors
				}
				//The path needs to be reversed because it was created from the corn-field to the position of the hamster
				Collections.reverse(shortestPath);
				goPath(shortestPath);			//call method to move the hamster to the corn
			}
		}
		else {
			System.out.println("Corn is null!");
		}
	}
}