
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.cas.purchasing.controller.PurchaseOrder;
import org.guanzon.cas.purchasing.services.PurchaseOrderControllers;

public class testFile {
    public static void main (String [] args) throws Exception{
        
        try {
            String path;
            String lsTemp;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = "D:/GGC_Maven_Systems";
                lsTemp = "D:/temp";
            } else {
                path = "/srv/GGC_Maven_Systems";
                lsTemp = "/srv/temp";
            }
            System.setProperty("sys.default.path.config", path);
            System.setProperty("sys.default.path.metadata", System.getProperty("sys.default.path.config") + "/config/metadata/new/");
            System.setProperty("sys.default.path.temp", lsTemp);
            
            if (!loadProperties()) {
                System.err.println("Unable to load config.");
                System.exit(1);
            } else {
                System.out.println("Config file loaded successfully.");
            }
            
            GRiderCAS instance = new GRiderCAS("gRider");

            if (!instance.logUser("gRider", "M001000001")) {
                System.err.println(instance.getMessage());
                System.exit(1);
            }
            
            PurchaseOrder POTrans = new PurchaseOrderControllers(instance, null).PurchaseOrder();
            POTrans.InitTransaction();
            POTrans.OpenTransaction("GK0125000037");
            POTrans.ShowStatusHistory();
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean loadProperties() {
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/cas.properties"));
            
            //industry ids
            System.setProperty("sys.main.industry", po_props.getProperty("sys.main.industry"));
            System.setProperty("sys.general.industry", po_props.getProperty("sys.general.industry"));
            
            //department ids
            System.setProperty("sys.dept.finance", po_props.getProperty("sys.dept.finance"));
            System.setProperty("sys.dept.procurement", po_props.getProperty("sys.dept.procurement"));
            
            //property for selected industry/company/category
            System.setProperty("user.selected.industry", po_props.getProperty("user.selected.industry"));
            System.setProperty("user.selected.category", po_props.getProperty("user.selected.category"));
            System.setProperty("user.selected.company", po_props.getProperty("user.selected.company"));
            
            //properties for client token and attachments
            System.setProperty("sys.default.client.token", System.getProperty("sys.default.path.config") + "/client.token");
            System.setProperty("sys.default.access.token", System.getProperty("sys.default.path.config") + "/access.token");
            System.setProperty("sys.default.path.temp.attachments", po_props.getProperty("sys.default.path.temp.attachments"));
            
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
