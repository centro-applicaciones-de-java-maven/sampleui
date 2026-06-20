
import javafx.application.Application;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.GRiderCAS;
import ph.com.guanzongroup.login.Login;
import ph.com.guanzongroup.login.LoginOLD;

public class testLogin {
    public static void main(String[] args) throws Exception {
        Login login = new Login();
        login.setProductId("IntegSys");
        
        Application.launch(login.getClass());
        
        GRiderCAS gRider = login.getGRider();
        
        System.out.println(login.getProductId());
        System.out.println(login.getUserId());
        
//        LoginOLD login = new LoginOLD();
//        login.setProductId("gRider");
//        
//        Application.launch(login.getClass());
//        
//        GRider gRider = login.getGRider();
//        
//        System.out.println(login.getProductId());
//        System.out.println(login.getUserId());
    }
}
