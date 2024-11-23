package Service;

import CamelotItemsOfInterest.CamelotItemsOfInterestController;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import Pet4uItems.Pet4uItemsController;
import StockAnalysis.StockAnalysisController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    public void startScheduledTasks() {

        int delay = 5000; // delay for 5 sec.
        int period = 1000; // repeat every sec.
        period = period * 60 * 30;//half an hour period

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Basement basement = new Basement();
                String applicationHostName = basement.getApplicationHostName();
                System.out.println("Half an Hour 'Ship Bell':" + LocalDateTime.now());
                if (applicationHostName.equals("LAPTOP")) {
                    //do nothing
                } else {
                    int hour = LocalDateTime.now().getHour();
                    if (hour >= 22) {

                        //time for 
                        System.out.println("Time For Snapshot");
                        LocalDate nowDate = LocalDate.now();

                        if (StaticsDispatcher.getLastCamelotSnapshotDate() == null) {
                            System.out.println("Last Snapshot Time Not Available From 'RAM'. Going for searching database");
                            CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
                            String lastCamelotSnapshotDate = camelotItemsOfInterestDao.getLastCamelotSnapshotDate();
                            System.out.println("'Now Date': " + nowDate.toString());
                            System.out.println("LastTimeSnapshot Insertion Date: " + lastCamelotSnapshotDate);
                            if (lastCamelotSnapshotDate.equals(nowDate.toString())) {
                                System.out.println("Database Last Datestamp is same with today`s datestamp");

                            } else {
                                System.out.println("Database Last Datestamp is NOT  today`s datestamp");
                            }
                            StaticsDispatcher.setLastCamelotSnapshotDate(lastCamelotSnapshotDate);
                        }
                        System.out.println("----------------------CONCLUSION------------------------");

                        if (StaticsDispatcher.getLastCamelotSnapshotDate().equals(nowDate.toString())) {
                            //do nothing

                            System.out.println("Last Snapshot was taken  today. No need for insertion");

                        } else {
                            System.out.println("Going For Camelot Snapshοt Insertion");
                            CamelotItemsOfInterestController camelotItemsOfInteresController = new CamelotItemsOfInterestController();
                            camelotItemsOfInteresController.addSnapshot();

                            System.out.println("Going For Camelot Full Version Snapshοt Insertion");
                            camelotItemsOfInteresController.addSnapshotToFullVersion();

                            System.out.println("Going For Pet4U Varibobi Snapshοt Insertion");
                            Pet4uItemsController pet4uItemsController = new Pet4uItemsController();
                            pet4uItemsController.updateItemsState();
                            System.out.println("Going For Pet4U Varibobi Snapshοt Insertion");
                            pet4uItemsController.updateItemsStateFullVersion();

                            System.out.println("Going For Pet4U Total Stock Snapshοt Insertion");
                            StockAnalysisController stockAnalysisController = new StockAnalysisController();
                            stockAnalysisController.addStockSnapshot();

                            StaticsDispatcher.setLastCamelotSnapshotDate(nowDate.toString());
                        }

                    }
                }
            }
        }, delay, period);
    }

}
