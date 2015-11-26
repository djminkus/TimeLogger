/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.io.IOException;

/**
 *
 * @author dg
 */
public class TimeLogger {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Assumes users will swipe no more than 200 times a month
        //Assumes no more than 500 distinct users per month
        
        int MAX_USERS = 500;
        
        String inputFileName;
        
        try {
            inputFileName = args[0];
        }
        catch (IndexOutOfBoundsException e){
            inputFileName = "KL_Entries.txt";
        }
        
        Person[] people = new Person[MAX_USERS];
        int peopleInd = 0;
        
        String summaryText = "NAME, HOURS\n";
        String detailText = "";
        
        try {
            ReadFile file = new ReadFile(inputFileName);
            String[] aryLines = file.OpenFile();
            LogEntry[] entries = new LogEntry[aryLines.length-3];
            
            int i;
            for ( i=1; i < aryLines.length - 2; i++ ) { //-2 because last two lines not entries
                //System.out.println( aryLines[ i ] ) ;
                entries[i-1] = new LogEntry(aryLines[i]);
                if (entries[i-1].what.charAt(0) == 'G'){ //If Granted Access, 
                    //check if entries[i-1] belongs to an existing person.
                    //  If not, create an existing person from this entry and increment peopleInd.
                    //  If so, add swipe to their Person object using "entries[i-1].when"
                    int marker = entries[i-1].personExists(peopleInd, people);
                    if (marker == -1 ){
                        people[peopleInd] = new Person(entries[i-1].who, entries[i-1].when);
                        peopleInd++;
                        System.out.println("Entry " + (i-1) + " represents a new person" );
                    } else {
                        people[marker].addSwipe(entries[i-1].when, i-1);
                        System.out.println("Entry " + (i-1) + " belongs to an existing person");
                    }
                    entries[i-1].printEntry();
                }
            }
            
            for(i=0; i<peopleInd; i++){
                summaryText += people[i].summary();
                detailText += people[i].detail();
            }
            WriteFile summary = new WriteFile("summary.csv");
            summary.writeToFile(summaryText);
            
            WriteFile detail = new WriteFile("detail.txt");
            detail.writeToFile(detailText);
        }
        
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
