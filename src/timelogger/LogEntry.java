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
public class LogEntry {
    
    private String entryString;
    
    String what, where, who, when, cardNum, facilityCustomerCode;
    
    private int whatBeginIndex, whatEndIndex, whereBeginIndex, whereEndIndex, whoBeginIndex;
    private int whoEndIndex, whenBeginIndex, whenEndIndex, cardNumBeginIndex, cardNumEndIndex;
    private int fccBeginIndex, fccEndIndex;
    
    public LogEntry(String entryText){
        entryString = entryText;
        analyze();
        //this.personExists(peopleIndParam) = personExists(peopleIndParam);
    }
    
    int personExists(int peopleIndParam, Person[] peopleArr){
        int i;
        for(i=0; i<peopleIndParam; i++){
            if(peopleArr[i].name.equals(who)) {
                return i; //return the index of the person if they exist
            }
        }
        return -1; //return -1 if person doesn't exist
    }
    
    private void analyze(){
        whatBeginIndex = 0;
      
        whatEndIndex = findTab(whatBeginIndex);
        whereBeginIndex = whatEndIndex + 1;
        
        whereEndIndex = findTab(whereBeginIndex);
        whoBeginIndex = whereEndIndex + 1;
        
        whoEndIndex = findTab(whoBeginIndex);
        whenBeginIndex = whoEndIndex + 1;
        
        whenEndIndex = findTab(whenBeginIndex);
        
        what = entryString.substring(whatBeginIndex, whatEndIndex + 1);
        where = entryString.substring(whereBeginIndex, whereEndIndex + 1);
        who = entryString.substring(whoBeginIndex, whoEndIndex + 1);
        when = entryString.substring(whenBeginIndex, whenEndIndex + 1);
    }
    
    private int findTab(int start){ 
        int foundTab = 0;
        while (foundTab < entryString.length()){
            if(entryString.charAt(foundTab+start) == (char)9){
                foundTab = foundTab + start;
                return foundTab;
            }
            foundTab++;
        }
        return 0;
    }
    
    void printEntry(){
        System.out.println(what.concat(", " + where).concat(", " + who ).concat(", " + when));
    }
}
