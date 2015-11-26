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
public class Visit {
    
    private String entryTime, exitTime;
    private float length, hrs, mins;

    public Visit(String entryTime, String exitTime) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.length = calcLength();
        if (this.length>5){
            this.length = 5; //5 hour max
        }
    }
    
    private float calcLength(){
        //hours:
        int entryH, exitH;
        entryH = Integer.parseInt(entryTime.substring(11,13));
        exitH = Integer.parseInt(exitTime.substring(11,13));
        if (entryH == 12){
            entryH = 0;
        }
        if (exitH == 12){
            exitH = 0;
        }
        
        int hours =  exitH - entryH;
        if ( !(entryTime.substring(16,17).equals( exitTime.substring(16,17)) ) ){
            hours = hours + 12;
            System.out.println("AM/PM mismatch");
        }
        //minutes:
        int entryM, exitM;
        entryM = Integer.parseInt(entryTime.substring(14,16));
        exitM = Integer.parseInt(exitTime.substring(14,16));
        
        int minutes =  exitM - entryM;
        if (minutes < 0){
            minutes = minutes + 60;
        }
        
        //combine 'em:
        float minutesFloat = (float)minutes;
        float hoursFloat = (float)hours;
        mins = minutesFloat;
        hrs = hoursFloat;
        hoursFloat = hoursFloat + (minutesFloat/60);
        return hoursFloat;
    }

    public String getEntryTime() {
        return entryTime;
    }
    public String getExitTime() {
        return exitTime;
    }
    public float getLength() {
        return length;
    }

    public String toString() {
        return ("" + entryTime + ", " + exitTime + ", " + String.format("%.2f", length) + " hours\n");
    }
}
