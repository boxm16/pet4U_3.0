package CamelotItemsOfInterest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class ItemSnapshot extends CamelotItemOfInterest {

    String dateStamp;

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getReformatedDateStamp() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = format1.parse(this.dateStamp);
            return format2.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(ItemSnapshot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Couldnt parse date";
    }
}
