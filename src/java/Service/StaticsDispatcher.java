/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import org.springframework.stereotype.Service;

@Service
public class StaticsDispatcher {

    private static boolean timerOn = false;
    private static String lastCamelotSnapshotDate = null;

    public static final String dbSchema = "PETCAMELOT_UAT2";
    // public static final String dbSchema = "TRAINING_PC";
    public static String COMPANY_DB = "PETCAMELOT_UAT2";
    // public static String COMPANY_DB = "TRAINING_PC";

    public static boolean isTimerOn() {
        return timerOn;
    }

    public static void setTimerOn() {
        timerOn = true;
    }

    public static String getLastCamelotSnapshotDate() {
        return lastCamelotSnapshotDate;
    }

    public static void setLastCamelotSnapshotDate(String lastCamelotSnapshotDate) {
        StaticsDispatcher.lastCamelotSnapshotDate = lastCamelotSnapshotDate;
    }

}
