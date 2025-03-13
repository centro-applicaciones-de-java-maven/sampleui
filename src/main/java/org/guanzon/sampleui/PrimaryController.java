package org.guanzon.sampleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javafx.fxml.FXML;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.cas.inv.Inventory;
import org.guanzon.cas.inv.services.InvControllers;
import org.guanzon.cas.parameter.Branch;
import org.guanzon.cas.parameter.Brand;
import org.guanzon.cas.parameter.Category;
import org.guanzon.cas.parameter.Color;
import org.guanzon.cas.parameter.Industry;
import org.guanzon.cas.parameter.InvType;
import org.guanzon.cas.parameter.Measure;
import org.guanzon.cas.parameter.Model;
import org.guanzon.cas.parameter.ModelVariant;
import org.guanzon.cas.parameter.services.ParamControllers;
import org.json.simple.JSONObject;

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
            System.setProperty("sys.default.path.metadata", System.getProperty("sys.default.path.config") + "/config/metadata/new/");

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
            
//            searchMearusurement(instance);
//            searchModelVariant(instance);
//            searchColor(instance);
//            searchModel(instance);
//            searchBrand(instance);
//            searchInventoryType(instance);
//            searchIndustry(instance);
//            searchCategory(instance);
//            searchInventory(instance);
            searchBranch(instance);
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }
        
        App.setRoot("secondary");
    }
    
    private void searchBranch(GRiderCAS instance) throws SQLException, GuanzonException{
        Branch record = new ParamControllers(instance, null).Branch();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getBranchCode());
            System.out.println(record.getModel().getBranchName());
        } else System.out.println("No record was selected.");
    }
    
    private void searchIndustry(GRiderCAS instance) throws SQLException, GuanzonException{
        Industry record = new ParamControllers(instance, null).Industry();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getIndustryId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchInventoryType(GRiderCAS instance) throws SQLException, GuanzonException{
        InvType record = new ParamControllers(instance, null).InventoryType();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getInventoryTypeId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchCategory(GRiderCAS instance) throws SQLException, GuanzonException{
        Category record = new ParamControllers(instance, null).Category();

        JSONObject loJSON = record.searchRecord("", false, "01", null);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getCategoryId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getInventoryTypeCode());
            System.out.println(record.getModel().Industry().getDescription());
            System.out.println(record.getModel().Inv_Type().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchBrand(GRiderCAS instance) throws SQLException, GuanzonException{
        Brand record = new ParamControllers(instance, null).Brand();

        JSONObject loJSON = record.searchRecord("", false, "02");        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getBrandId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().Industry().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchModel(GRiderCAS instance) throws SQLException, GuanzonException{
        Model record = new ParamControllers(instance, null).Model();

        JSONObject loJSON = record.searchRecord("", false, "00001");        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getModelId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getManufactureYear());
            System.out.println(record.getModel().getBrandId());
            System.out.println(record.getModel().Brand().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchColor(GRiderCAS instance) throws SQLException, GuanzonException{
        Color record = new ParamControllers(instance, null).Color();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getColorId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getColorCode());
        } else System.out.println("No record was selected.");
    }
    
    private void searchModelVariant(GRiderCAS instance) throws SQLException, GuanzonException{
        ModelVariant record = new ParamControllers(instance, null).ModelVariant();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getVariantId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getYearModel());
            System.out.println(record.getModel().Model().getDescription());
            System.out.println(record.getModel().Model().getModelCode());
            System.out.println(record.getModel().Color().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchMearusurement(GRiderCAS instance) throws SQLException, GuanzonException{
        Measure record = new ParamControllers(instance, null).Measurement();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getMeasureId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchInventory(GRiderCAS instance) throws SQLException, GuanzonException{
        Inventory record = new InvControllers(instance, null).Inventory();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getStockId());
            System.out.println(record.getModel().getBarCode());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().Brand().getDescription());
            System.out.println(record.getModel().Model().getDescription());
            System.out.println(record.getModel().Color().getDescription());
            System.out.println(record.getModel().Measure().getDescription());
            System.out.println(record.getModel().Category().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private boolean loadProperties() {
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/cas.properties"));

            System.setProperty("store.branch.code", po_props.getProperty("store.branch.code"));
            System.setProperty("store.inventory.industry", po_props.getProperty("store.inventory.category"));

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
