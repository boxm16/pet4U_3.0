package Endo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;

public class EndoLablePrinter implements Printable {

    private int labelsCount;
    private String storeName;

    public void setLabelsCount(int labelsCount) {
        this.labelsCount = labelsCount;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    

    public PrintService findPrintService(String printerName) {
        for (PrintService service : PrinterJob.lookupPrintServices()) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page < this.labelsCount) {
            /* User (0,0) is typically outside the imageable area, so we must
        * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            /* Now we perform our rendering */

            g.setFont(new Font("Roman", 0, 30));
            g.drawString(this.storeName, 0, 40);//here0, is horizontla coordinat, but of imageable, 20 is vertical coordinat

            g.drawString(page+1+"/"+labelsCount, 100, 80);

            return PAGE_EXISTS;
        }

        /* We have only one page, and 'page' is zero-based */
        return NO_SUCH_PAGE;

    }

    public void printSomething(String printerName) {
        try {
            //find the printService of name printerName
            PrintService ps = findPrintService(printerName);
            //create a printerJob
            PrinterJob job = PrinterJob.getPrinterJob();
            //set the printService found (should be tested)
            job.setPrintService(ps);
            //set the printable (an object with the print method that can be called by "job.print")

            PageFormat pf = job.defaultPage();
            Paper copy = pf.getPaper();
            copy.setSize(pf.getWidth(), pf.getHeight());
            double cmPx300 = 300.0 / 2.54;
            copy.setImageableArea(0, 0, 7.0 * cmPx300, 3.0 * cmPx300);
            pf.setPaper(copy);

            job.setPrintable(this, pf);
            //call je print method of the Printable object
            job.print();
        } catch (PrinterException ex) {
            Logger.getLogger(EndoLablePrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
