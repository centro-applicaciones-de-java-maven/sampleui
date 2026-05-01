
import javafx.application.Application;
import org.guanzon.appdriver.base.GRiderCAS;
import ph.com.guanzongroup.login.Login;

public class testLogin {
    public static void main(String[] args) throws Exception {
        Login login = new Login();
        login.setProductId("IntegSys");
        
        Application.launch(login.getClass());
        
        GRiderCAS gRider = login.getGRider();
        
        System.out.println(gRider.getProductID());
        System.out.println(gRider.getUserID());
    }
}
