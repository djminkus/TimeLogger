/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

/**
 *
 * @author dg
 */
public class Person {
    
    String name;
    
    int MAX_SWIPES = 200;
    
    private String[] cardSwipeTimes = new String[MAX_SWIPES];
    private boolean[] exit = new boolean[MAX_SWIPES]; //true if swipe corresponds to an exit
    private int numSwipes = 0; //
    
    private Visit[] visits = new Visit[MAX_SWIPES];
    private int numVisits = 0;
    
    private float totalHours = 0;
    
    public Person(String name_string, String firstTime){
        name = name_string;
        cardSwipeTimes[0] = firstTime;
        numSwipes = 1;
        //analyze();
    }
    
    public void addSwipe(String time, int entryNum){
        cardSwipeTimes[numSwipes] = time;
        //check if this swipe is an exit swipe. (If previous swipe is an entry swipe
        //  and happened on the same day, this is an exit swipe.)
        if ( !(exit[numSwipes-1])){ //Execute if previous swipe was an entry swipe:
            if ( (time.charAt(3)== cardSwipeTimes[numSwipes-1].charAt(3) ) && (time.charAt(4)== cardSwipeTimes[numSwipes-1].charAt(4)) ){
                //Check if previous swipe happened on same day,
                // If so, create a new "visit" from last swipe and this swipe...
                visits[numVisits] = new Visit(cardSwipeTimes[numSwipes-1],cardSwipeTimes[numSwipes]);
                numVisits++;
                // ... and mark this as an exit swipe:
                exit[numSwipes] = true;
                System.out.println("Entry " + entryNum + "represents an exit swipe");
            } else {
                //If previous swipe did not happen on same day, create new visit from previous swipe,
                //  ending at midnight on that day.
                visits[numVisits] = new Visit(cardSwipeTimes[numSwipes-1], cardSwipeTimes[numSwipes-1].substring(0,11) + "11:59PM");
                numVisits++;
                System.out.println("Entry " + entryNum + "represents an entry swipe");
            }
        }
        //If previous swipe was an exit swipe, don't create any visits this time. 
        numSwipes++;
    }
    
    public float totalHrs(){
        //add up minutes from entries in string array "cardSwipeTimes"
        int i;
        float total = 0;
        for (i=0; i<numVisits; i++){
            total += visits[i].getLength(); 
        }
        return total;
    }
    
    public String summary(){
        totalHours = totalHrs();
        String result = "";
        
        if ( !(totalHours == 0) ){
            result += "\"" + this.name + "\", " + String.format("%.2f",totalHours) + "\n";
        }
        return result;
    }
    
    public String detail(){
        totalHours = totalHrs();
        
        String result = "";
        result += this.name + ", " + "Hours logged: " + totalHours + "\n";
        int i;
        for (i=0; i<numVisits; i++){
            result += visits[i].toString();
        }
        result += "\n";
        return result;
    }
}
