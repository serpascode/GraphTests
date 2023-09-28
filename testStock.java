import java.util.*;

public class testStock
{   
    static int gridDimension = 3;
    //9 Tiles iN a tic tac toe field
    static int numVertices = (gridDimension*gridDimension);
    //Create Main Grid
    static int[] grid = new int[numVertices];
    //Create Main List
    //static List<Integer> stockList = new ArrayList<Integer>();
     //Create Sorted List
    static List<Integer> sortedStockList = new ArrayList<Integer>();
    //Create an Adjacency Matrix
    static int[][] adjMatrix = new int[numVertices][numVertices];

    //Create an Adjacency List //cannot do it anyother way without warning or errors
    static ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>(numVertices);


    //Create A Stack to store all elements visited in order to backtrack
    static Deque<Integer> visted = new ArrayDeque<>();
    //Also a stack to store all the elements that should be checked next
    static Deque<Integer> toVisit= new ArrayDeque<>();

    static boolean reRoll = true;
    static void genAdjMatrix(){
         for(int i=0; i<grid.length; i++){
            //Using Math to calculate the position UP, Down, Left, and Right
            //In a 2Dimensional Array that has been flattend to be 1Dimensional

            //UP is i-3
            int up = i-gridDimension;
            //DOWN is i+3
            int down = i+gridDimension;
            //Left is i-1
            int left = i-1;
            //Right is i+1
            int right = i+1;

            //System.out.println((i+1)%3 +" :: "+(i+1)/3+"--");//Used to determine columns
            //displayGridItem(i); 

            if(up>=0){ //UP
                //System.out.print("-up"); displayGridItem(up);
                addEdge(grid[i], grid[up]);
            }
            if(down<grid.length){//Down
                //System.out.print("-down"); displayGridItem(down);
                addEdge(grid[i], grid[down]);
            }
            if(left>=0){//Left
                if(!((i+1)%gridDimension==1)){
                //System.out.print("-left"); displayGridItem(left);
                    addEdge(grid[i], grid[left]);
                }
            }
            if(right<grid.length){//Right
                if(!((i+1)%gridDimension==0)){
                //System.out.print("-right"); displayGridItem(right);
                    addEdge(grid[i], grid[right]);
                }
            }
            System.out.println();
         }
         System.out.println();
    }

    static void displayAdjMatrix(){
     
        displaySortedGridList();
        System.out.println();
        for(int i=0; i<adjMatrix.length; i++){
            displaySortedGridItem(i);
            for(int j=0; j<adjMatrix.length; j++){
                if(adjMatrix[i][j]==0)
                    System.out.print("[   ]");
                else
                    System.out.print("[ "+adjMatrix[i][j]+" ]");
            }
            System.out.println();
        }
    }

    static void addEdge(int Origin, int Destination){
        int x= 0;
        int y= 0; 
        for(int i=0; i<sortedStockList.size(); i++){
            if(sortedStockList.get(i)==Origin)
                x = i;
            if(sortedStockList.get(i)==Destination)
                y = i;
        }
        adjMatrix[x][y] = 1;
        adjMatrix[y][x] = 1;
    }

    static void genAdjList(){
        ArrayList<Integer> adjacantTemp;
        for(int i=0; i<numVertices; i++){
            adjacantTemp= new ArrayList<Integer>();
            for(int j=0; j<numVertices; j++){
                if(adjMatrix[i][j] == 1){
                    adjacantTemp.add(sortedStockList.get(j));
                }
            }
            adjList.add(i, adjacantTemp);
        }
    }

    //Maybe usless
    static void displayList(ArrayList<Integer> list){
        for(int i=0; i<list.size(); i++){
              System.out.println(list.get(i));
        }
    }

    static void displayAdjList(){
        System.out.println();
        for(int i=0; i<adjList.size(); i++){
            System.out.print((char)(sortedStockList.get(i)+64)+"| ");
            for(int j=0; j<adjList.get(i).size(); j++){
                System.out.print((char)((adjList.get(i).get(j))+64)+" -> ");
            }
            System.out.println();
        }

        System.out.println();
        //(char)(grid[i]+64)
    }

    static void genGrid(){
        int randomStockNumber = 0;
        for(int i=0; i<grid.length; i++){   //Select a number from 1 to 27 // which are the letters of the alphabet
            do{randomStockNumber = ((int)(Math.random()*26)+1);}
            while(duplicateFound(i, randomStockNumber));

            grid[i] = randomStockNumber;
            sortedStockList.add(randomStockNumber);
        }
        Collections.sort(sortedStockList);
        genAdjMatrix();
        genAdjList();
    }

    static boolean duplicateFound(int j, int value){
        for(int i=0; i<=j; i++){ //Check if the values to be added already exist in grid
            if(grid[i] == value)
                return true;
        }
        return false;
    }

    static void displayGrid(){
        for(int i=0; i<grid.length; i++) { 
            System.out.print("[ "+(char)(grid[i]+64)+" ]"); //Convert the number into a letter  // +64 Is capital // +96 is Minor Case
            if((i+1)%gridDimension==0)
                System.out.println();
        }
    }

    static void displayGridItem(int j){
        System.out.print("[ "+(char)(grid[j]+64)+" ]");
    }

    static void displaySortedGridItem(int j){
        if(j<0||j>=grid.length)
            return;
        int stockValue = sortedStockList.get(j);
        System.out.print("[ "+(char)(stockValue+64)+" ]"); //Convert the number into a letter 
    }
    
    static void displaySortedGridList(){
        System.out.println();
        System.out.print("[   ]");
        for(int i=0; i<sortedStockList.size(); i++){
            int stockValue = sortedStockList.get(i);
             System.out.print("[ "+(char)(stockValue+64)+" ]"); //Convert the number into a letter 
      
        
        }
    }

    static void breadthFirstSearch() 
    {

    }

    static void depthFirstSearch() 
    {
    
    }

    static class PathObject
    {
        static int value;
        static int cost;
        int[] costArray = new int[sortedStockList.size()];
        PathObject(int destination, int travelDistance){
            //System.out.println("Dest: "+destination+" Dist: "+travelDistance);
            value = destination;
            cost = travelDistance;
        }
    }

    static void printStockValue(int cost){
        //System.out.println(toVisit.toString());
        int[] costArray = new int[sortedStockList.size()];
        int totalCount = 0;
        Iterator<Integer> vistedList = toVisit.iterator();
        while(vistedList.hasNext()){
            int temp = vistedList.next().intValue();
            for(int i=0; i<sortedStockList.size(); i++){
                if(sortedStockList.get(i) == temp)
                {   
                    totalCount +=1;
                    costArray[i] +=1;
                }
            }
        }
        for(int i=0; i<sortedStockList.size(); i++){
            System.out.println((char)(sortedStockList.get(i)+64)+ " ||x" +costArray[i] +" = "+(costArray[i]*cost));
            //+" / "+(int)(((double)(costArray[i]*cost)/(totalCount*cost))*100)+"% ");//+totalCount);
        } 
        System.out.println();
    }

    static void printVistList(){
        Iterator<Integer> vistedList = toVisit.iterator();
        System.out.print("\n[");
        while(vistedList.hasNext()){
            System.out.print((char)(vistedList.next().intValue()+64)+", ");
        }System.out.print("]\n");
        System.out.println();
    }

    static int plotPath(int source, int destination){//Source determines i
        //PathObject newPath = new PathObject(destination, 0);
        
        if(!toVisit.isEmpty() )
        {
            if(toVisit.peekLast()== source)
            {
                toVisit.removeLast();
                //System.out.println("\n duplicate entered");
            }
        }

        visted.addFirst(source); //We are currently visting
        toVisit.addLast(source);// Used as a reverse stack
        
        
        //The Base case is that the destination is the source
        //so therefore you have already arrived and the cost would be zero
        //in that case return destination and the cost of zero
        if(destination == source)
        {  
            //System.out.println("\n"+(char)(source+64));
            //System.out.println(" Destination: "+(char)(destination+64)+" Source: "+(char)(source+64));//+"\n V:"+visted.toString()+"\n S:"+toVisit.toString());
            //toVisit.addLast(destination);
            return 0; //Base cost could be 1 per move or the distance between elements, for a more complex value system   
        }
      

        /**
        Iterator<Integer> vistedList = toVisit.iterator();
        System.out.print("\n[");
        while(vistedList.hasNext()){
            System.out.print((char)(vistedList.next().intValue()+64)+", ");
        }System.out.print("]\n");
        System.out.println();
        /**/

        /********************************************************************************************************/
        
        //The next base case would be that the destination is directly adjacent
        //in which case it would just tell you to go there at the cost of the edge
        //
        ArrayList<Integer> destinationAdjList = new ArrayList<>();

        //Step 1:
        for(int i=0; i<sortedStockList.size(); i++){
            if(sortedStockList.get(i) == source){ // Find the source
                destinationAdjList = adjList.get(i); // Get the list of its adjacent elements
                break; //Exit the loop
            }     
        }
        
        int[] distanceSortedAdjacant = new int[destinationAdjList.size()];
        //Step 2: //Check adjacent elements ?????????and plot a path to each
        for(int j=0; j<destinationAdjList.size(); j++){
            if(destinationAdjList.get(j) == destination){ //The destination is directly adjacent to the source
                //System.out.println(" Dest: "+(char)(toVisit.peekLast()+64)+ " /"+(char)(destinationAdjList.get(j)+64));
                //if(toVisit.peekLast()!=destinationAdjList.get(j))
                    //toVisit.addLast(destination);
                return 1+plotPath(destinationAdjList.get(j), destination);
            }
            //System.out.println(" Dest: "+(char)(toVisit.peekLast()+64)+ " /"+(char)(destinationAdjList.get(j)+64));
            distanceSortedAdjacant[j] = destinationAdjList.get(j);
            //toVisit.addFirst(destinationAdjList.get(j)); //add all adjacent elements to the stack //alphabetically
        }//Destination not found in adjacent elements


        //Select the closest letter
        //By first Sorting the letters based on distance
         int keyValue = 0;
      
        boolean equalAdj = false;
        for(int j=1; j<destinationAdjList.size(); j++){
            equalAdj = false;
            int distance = Math.abs(destinationAdjList.get(j) - source);
           
            int i = j-1;
            while(i>=0 && Math.abs(distanceSortedAdjacant[i] - source) >= distance){
                if(source!=destination && distanceSortedAdjacant[i]!=source && Math.abs(distanceSortedAdjacant[i] - source) == distance){ //Two values are equally distant
                    //System.out.println((char)(distanceSortedAdjacant[i]+64)+"="+i+"="+(char)(destinationAdjList.get(j)+64) +" Distance: "+distance);
                    //System.out.println(Math.abs(distanceSortedAdjacant[i]-destination)+"=="+Math.abs(destinationAdjList.get(j)-destination)+" Distance from: "+(char)(destination+64));

                    if(Math.abs(distanceSortedAdjacant[i]-destination)<Math.abs(destinationAdjList.get(j)-destination)){
                        keyValue = distanceSortedAdjacant[i];
                    }
                    else{
                        keyValue = destinationAdjList.get(j);
                    }
                   equalAdj = true;
                }
                distanceSortedAdjacant[i+1] = distanceSortedAdjacant[i] ;
                i--;
           }
           if(equalAdj){
            reRoll = false;
           // System.out.println((char)(keyValue+64)+"||Fork 1 Choice Next"+"| ");
           }
            distanceSortedAdjacant[i+1] = destinationAdjList.get(j);
        } //Exit with all adjacent values sorted in order of distance to source

        /**
        for(int i=0; i<distanceSortedAdjacant.length; i++){//Print Re-reAssorted Adj List
            System.out.print((char)(distanceSortedAdjacant[i]+64)+"', ");
        }System.out.println();
        System.out.println("Next "+(char)(distanceSortedAdjacant[0]+64)+"/ "+keyValue);//+"||Selected Next""+": "+key);
        /**/

        //Insert Fork
        boolean forkAdded = false;
        if(equalAdj){
            equalAdj = false;
            forkAdded = true;
            boolean keyFound = false;
            for(int i= distanceSortedAdjacant.length-1; i>0; i--){
                if(distanceSortedAdjacant[i] == keyValue) keyFound = true;

                if(keyFound)
                    distanceSortedAdjacant[i] = distanceSortedAdjacant[i-1];
            }
            distanceSortedAdjacant[0]=keyValue;
        }


        /**
        //System.out.println("Next -->  "+(char)(distanceSortedAdjacant[0]+64));//+" / "+keyValue);//+"||Selected Next""+": "+key);
        for(int i=0; i<distanceSortedAdjacant.length; i++){//Print Re-reAssorted Adj List
            System.out.print((char)(distanceSortedAdjacant[i]+64)+"', ");
        }System.out.println();
        /**/
     

        int v = 0;
        //System.out.println("V: "+v+" array: "+distanceSortedAdjacant.length);
        //v<distanceSortedAdjacant.length-1 && v<visted.size()-1 && 
        
        while(toVisit.contains(distanceSortedAdjacant[v])){
            v++;
            if(v>distanceSortedAdjacant.length-1){
                break;
            }
        }

        //System.out.print("V: "+v+" array: "+distanceSortedAdjacant.length);
        if(v<distanceSortedAdjacant.length)
            return 1+plotPath(distanceSortedAdjacant[v] , destination);
        else{
            v=0;
            //System.out.print("Vsdfdf: "+v+" array: "+distanceSortedAdjacant.length);
            visted.addLast(visted.pop());
            return 1+plotPath(visted.pollFirst(), destination);
        }
    }

    static void printPath(){

        int cost = 0;
        int start = 0;
        int end = 0; 
        int startValue = 64;
        int endValue = 64;

        int traversePoint = 0;

        int endPoint = sortedStockList.size()-1;
        for(int j = 0; j<endPoint; j++){
         
            for(int i=0; i<sortedStockList.size(); i++){
                if(grid[i] == sortedStockList.get(j)){
                    start = i;
                    startValue =grid[i]+64;
                }
                if(grid[i] == sortedStockList.get(j+1)){
                    end = i;
                    endValue =grid[i]+64;
                }      
            }
            //System.out.print("\nStarting Letter: "+(char)(startValue)+"\nEnding Letter: "+ (char)(endValue));
            cost += plotPath(grid[start], grid[end]);
            System.out.println("Start: "+(char)(startValue)+" --> End: "+ (char)(endValue)+" \nCost: "+cost);
            printVistList();
        }
        printStockValue(cost); 
        //}
    }
    public static void main(String[]args)
    {

        //while(reRoll){
        genGrid();
        displayGrid();
        displayAdjMatrix();
        displayAdjList();
        printPath();
       
        displayGrid();
        //}
    }
}