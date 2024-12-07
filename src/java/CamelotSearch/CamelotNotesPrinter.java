package CamelotSearch;

import Inventory.InventoryItem;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;

public class CamelotNotesPrinter implements Printable {

    private ArrayList<InventoryItem> sortedNotesArrayList;

    public void setSortedNotesArrayList(ArrayList<InventoryItem> sortedNotesArrayList) {
        this.sortedNotesArrayList = sortedNotesArrayList;
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
        int pageIndex = 1;
        if (page < pageIndex) {
            /* User (0,0) is typically outside the imageable area, so we must
        * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            /* Now we perform our rendering */

            int h = 20;                                        //print start at 100 on x axies
            g.setFont(new Font("Roman", Font.BOLD, 12));
            g.drawString("Κωδικός", 0, h);

            g.setFont(new Font("Roman", Font.BOLD, 12));
            g.drawString("Θέση", 70, h);

            g.setFont(new Font("Roman", Font.BOLD, 12));
            g.drawString("Περιγραφή ", 120, h);

            g.setFont(new Font("Roman", Font.BOLD, 12));
            g.drawString("Παρατήρηση ", 355, h);

            g.setFont(new Font("Roman", Font.BOLD, 12));
            g.drawString("Θέσεις ", 500, h);
            h = h + 10;
            g.drawString("Στοκ ", 500, h);
            h += 2;
            g.drawLine(0, h, 550, h);
            h += 1;
            g.drawLine(0, h, 550, h);
            h = h + 15;
            for (InventoryItem note : this.sortedNotesArrayList) {

                g.setFont(new Font("Roman", Font.BOLD, 10));
                String code = note.getCode();
                if (code.length() > 10) {
                    code = code.substring(0, 10) + ">";;
                }
                g.drawString(code, 0, h);

                g.setFont(new Font("Roman", Font.PLAIN, 12));
                g.drawString(note.getPosition(), 70, h);

                String description = note.getDescription();
                if (description.length() > 30) {
                    description = description.substring(0, 30) + ">";
                }
                g.setFont(new Font("Roman", Font.PLAIN, 12));
                g.drawString(description, 120, h);

                g.setFont(new Font("Roman", Font.PLAIN, 12));
                g.drawString(note.getNote(), 355, h);

                LinkedHashMap<Integer, String> stockPositions = note.getStockPositions();
                if (stockPositions == null) {
                } else {
                    int stockPositionIndex = 0;
                    for (Map.Entry<Integer, String> stockPositionsEntry : stockPositions.entrySet()) {
                        g.setFont(new Font("Roman", Font.PLAIN, 10));
                        g.drawString(stockPositionsEntry.getValue(), 500, h);

                        stockPositionIndex++;
                        if (stockPositionIndex == stockPositions.size()) {
                            h = h + 3;
                            g.drawLine(0, h, 550, h);
                        } else {
                            h = h + 12;
                        }
                    }

                }
                h = h + 12;
                //-- delete after
                g.drawString("h= " + h, 450, h);
//--
                //    pageIndex = 2;
            }
            //  pageIndex = 1;
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
            copy.setImageableArea(0, 0, 7.0 * cmPx300, 50.0 * cmPx300);
            pf.setPaper(copy);

            job.setPrintable(this, pf);
            //call je print method of the Printable object
            job.print();
        } catch (PrinterException ex) {
            Logger.getLogger(CamelotNotesPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
