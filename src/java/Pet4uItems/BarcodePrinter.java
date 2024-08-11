package Pet4uItems;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.PrintService;

public class BarcodePrinter implements Printable {

    private int labelsCount;
    private String code;
    private String barcode;
    private String description;
    private String position;

    public void setLabelsCount(int labelsCount) {
        this.labelsCount = labelsCount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
            try {
                int x = 10;                                        //print start at 100 on x axies
                int y = 2;                                          //print start at 10 on y axies
                int imagewidth = 100;
                int imageheight = 30;
                BufferedImage read = ImageIO.read(new File("C:/Pet4U_3.0/barcode.png"));
                g2d.drawImage(read, x, y, imagewidth, imageheight, null);         //draw image
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawString(code, 130, 10);
            g.setFont(new Font("Roman", Font.BOLD, 15));
            g.drawString("*" + barcode, 150, 25);
            g.setFont(new Font("Roman", Font.PLAIN, 10));
            g.drawString(description, 10, 43);

            g.setFont(new Font("Roman", Font.BOLD, 50));
            g.drawString(position, 10, 90);

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
            Logger.getLogger(BarcodePrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
