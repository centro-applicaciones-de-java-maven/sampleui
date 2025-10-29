package org.guanzon.sampleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javafx.fxml.FXML;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.ClientType;
import ph.com.guanzongroup.cas.client.ClientGUI;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        try {
            String path;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = "D:/GGC_Maven_Systems";
            } else {
                path = "/srv/GGC_Maven_Systems";
            }

            System.setProperty("sys.default.path.config", path);

            if (!loadProperties()) {
                System.err.println("Unable to load config.");
                System.exit(1);
            } else {
                System.out.println("Config file loaded successfully.");
            }

            GRiderCAS instance = new GRiderCAS("gRider");

            if (!instance.logUser("gRider", "M001000001")) {
//                System.err.println(instance.getMessage());
                System.exit(1);
            }

            LogWrapper wrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");
            //testNewInstitution(instance, wrapper);
            //testLoadInstitution(instance, wrapper);
            testLoadClient(instance, wrapper);
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }

        App.setRoot("secondary");
    }

    private void testNewClient(GRiderCAS instance, LogWrapper wrapper) {
        try {
            ClientGUI client = new ClientGUI();
            client.setGRider(instance);
            client.setLogWrapper(wrapper);
            client.setClientId("");

            CommonUtils.showModal(client);

            if (!client.isCancelled()) {
                System.out.println("Client Id: " + client.getClient().getModel().getClientId());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void testLoadClient(GRiderCAS instance, LogWrapper wrapper) {
        try {
            ClientGUI client = new ClientGUI();
            client.setGRider(instance);
            client.setLogWrapper(wrapper);
            client.setClientType(ClientType.INDIVIDUAL);
            client.setClientId("");

            CommonUtils.showModal(client);

            if (!client.isCancelled()) {
                System.out.println("Client Id: " + client.getClient().getModel().getClientId());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void testNewInstitution(GRiderCAS instance, LogWrapper wrapper) {
        try {
            ClientGUI client = new ClientGUI();
            client.setGRider(instance);
            client.setLogWrapper(wrapper);
            client.setClientType(ClientType.INSTITUTION);
            client.setClientId("");

            CommonUtils.showModal(client);

            if (!client.isCancelled()) {
                System.out.println("Client Id: " + client.getClient().getModel().getClientId());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void testLoadInstitution(GRiderCAS instance, LogWrapper wrapper) {
        try {
            ClientGUI client = new ClientGUI();
            client.setGRider(instance);
            client.setLogWrapper(wrapper);
            client.setClientType(ClientType.INSTITUTION);
            client.setClientId("");

            CommonUtils.showModal(client);

            if (!client.isCancelled()) {
                System.out.println("Client Id: " + client.getClient().getModel().getClientId());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean loadProperties() {
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/cas.properties"));

            System.setProperty("sys.default.path.temp", System.getProperty("sys.default.path.config") + po_props.getProperty("sys.default.path.temp"));
            System.setProperty("sys.default.path.metadata", System.getProperty("sys.default.path.config") + po_props.getProperty("sys.default.path.metadata"));

            System.setProperty("app.global.company", po_props.getProperty("app.global.company"));
            System.setProperty("app.global.industry", po_props.getProperty("app.global.industry"));
            System.setProperty("app.global.category", po_props.getProperty("app.global.category"));

            System.setProperty("app.global.branch", po_props.getProperty("app.global.branch"));
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
