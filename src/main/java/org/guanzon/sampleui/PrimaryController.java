package org.guanzon.sampleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javafx.fxml.FXML;
import org.guanzon.appdriver.agent.systables.SysTableContollers;
import org.guanzon.appdriver.agent.systables.TransactionAttachment;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.ClientGUI;
import org.guanzon.cas.client.services.ClientControllers;
import org.guanzon.cas.gl.AccountChart;
import org.guanzon.cas.gl.TransactionAccountChart;
import org.guanzon.cas.gl.services.GLControllers;
import org.guanzon.cas.inv.InvMaster;
import org.guanzon.cas.inv.InvSerial;
import org.guanzon.cas.inv.Inventory;
import org.guanzon.cas.inv.services.InvControllers;
import org.guanzon.cas.parameter.Branch;
import org.guanzon.cas.parameter.Brand;
import org.guanzon.cas.parameter.Category;
import org.guanzon.cas.parameter.Color;
import org.guanzon.cas.parameter.Company;
import org.guanzon.cas.parameter.Country;
import org.guanzon.cas.parameter.Department;
import org.guanzon.cas.parameter.Industry;
import org.guanzon.cas.parameter.InvLocation;
import org.guanzon.cas.parameter.InvType;
import org.guanzon.cas.parameter.Measure;
import org.guanzon.cas.parameter.Model;
import org.guanzon.cas.parameter.ModelVariant;
import org.guanzon.cas.parameter.Province;
import org.guanzon.cas.parameter.Region;
import org.guanzon.cas.parameter.Section;
import org.guanzon.cas.parameter.Term;
import org.guanzon.cas.parameter.TownCity;
import org.guanzon.cas.parameter.Warehouse;
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
            
            testNewClient(instance, wrapper);
//            searchMearusurement(instance);
//            searchModelVariant(instance);
//            searchColor(instance);
//            searchModel(instance);
//            searchBrand(instance);
//            searchInventoryType(instance);
//            searchIndustry(instance);
//            searchCategory(instance);
//            searchInventory(instance);
//            searchBranch(instance);
//            searchWarehouse(instance);
//            searchSection(instance);
//            searchInventoryLocation(instance);
//            searchTerm(instance);
//            searchInventoryMaster(instance);
//            searchInventorySerial(instance);
//            searchTownCity(instance);
//            searchCountry(instance);
//            searchRegion(instance);
//            searchProvince(instance);
//            searchCompany(instance);
//            searchDepartment(instance);
//            searchAccountChart(instance);
//            searchGeneralLedger(instance);
//            searchClient(instance);
//            searchTransactionAttachment(instance);
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }
        
        App.setRoot("secondary");
    }
    
    private void testNewClient(GRiderCAS instance, LogWrapper wrapper){
        try {
            ClientGUI client = new ClientGUI();
            client.setGRider(instance);
            client.setLogWrapper(wrapper);
            client.setClientId("");

            CommonUtils.showModal(client);
            
            if (!client.isCancelled()){
                System.out.println("Client Id: " + client.getClient().getModel().getClientId());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void searchTransactionAttachment(GRiderCAS instance) throws SQLException, GuanzonException{
        TransactionAttachment record = new SysTableContollers(instance, null).TransactionAttachment();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getTransactionNo());
            System.out.println(record.getModel().getSourceNo());
            System.out.println(record.getModel().getSourceCode());
            System.out.println(record.getModel().getFileName());
        } else System.out.println("No record was selected.");
        
        
        //load list of attachment per transaction
        List loList = record.getAttachments("PRec", "M00125000001");
        for (int lnCtr = 0; lnCtr <= loList.size() - 1; lnCtr++){
            System.out.println("File " + (lnCtr + 1) + " transaction no: " + loList.get(lnCtr));
            loJSON = record.openRecord((String) loList.get(lnCtr));
            
            if ("success".equals((String) loJSON.get("result"))){
                System.out.println(record.getModel().getTransactionNo());
                System.out.println(record.getModel().getSourceNo());
                System.out.println(record.getModel().getSourceCode());
                System.out.println(record.getModel().getFileName());
            }
        }
    }
    
    private void searchClient(GRiderCAS instance) throws SQLException, GuanzonException{
        ClientInfo record = new ClientControllers(instance, null).ClientInfo();

        JSONObject loJSON = record.searchRecord("Cuison", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getClientId());
        } else System.out.println("No record was selected.");
    }
    
    private void searchGeneralLedger(GRiderCAS instance) throws SQLException, GuanzonException{
        TransactionAccountChart record = new GLControllers(instance, null).TransactionAccountChart();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getGLCode());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchAccountChart(GRiderCAS instance) throws SQLException, GuanzonException{
        AccountChart record = new GLControllers(instance, null).AccountChart();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getAccountCode());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().General_Ledger().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchDepartment(GRiderCAS instance) throws SQLException, GuanzonException{
        Department record = new ParamControllers(instance, null).Department();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getDepartmentId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getDepartmentCode());
        } else System.out.println("No record was selected.");
    }
    
    private void searchCompany(GRiderCAS instance) throws SQLException, GuanzonException{
        Company record = new ParamControllers(instance, null).Company();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getCompanyId());
            System.out.println(record.getModel().getCompanyName());
            System.out.println(record.getModel().getCompanyCode());
        } else System.out.println("No record was selected.");
    }
    
    private void searchProvince(GRiderCAS instance) throws SQLException, GuanzonException{
        Province record = new ParamControllers(instance, null).Province();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getProvinceId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getRegionId());
            System.out.println(record.getModel().Region().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchRegion(GRiderCAS instance) throws SQLException, GuanzonException{
        Region record = new ParamControllers(instance, null).Region();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getRegionId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchCountry(GRiderCAS instance) throws SQLException, GuanzonException{
        Country record = new ParamControllers(instance, null).Country();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getCountryId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getNationality());
        } else System.out.println("No record was selected.");
    }
    
    private void searchTownCity(GRiderCAS instance) throws SQLException, GuanzonException{
        TownCity record = new ParamControllers(instance, null).TownCity();

        JSONObject loJSON = record.searchRecord("", false, "01");        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getTownId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getProvinceId());
        } else System.out.println("No record was selected.");
    }
    
    private void searchInventorySerial(GRiderCAS instance) throws SQLException, GuanzonException{
        InvSerial record = new InvControllers(instance, null).InventorySerial();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getSerialId());
            System.out.println(record.getModel().getSerial01());
            System.out.println(record.getModel().getSerial02());
            System.out.println(record.getModel().getStockId());
            System.out.println(record.getModel().Inventory().getDescription());       
            System.out.println(record.SerialRegistration().getFileNumber());       
        } else System.out.println("No record was selected.");
    }
    
    private void searchTerm(GRiderCAS instance) throws SQLException, GuanzonException{
        Term record = new ParamControllers(instance, null).Term();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getTermId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getTermValue());
        } else System.out.println("No record was selected.");
    }
    
    private void searchInventoryLocation(GRiderCAS instance) throws SQLException, GuanzonException{
        InvLocation record = new ParamControllers(instance, null).InventoryLocation();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getLocationId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().Warehouse().getDescription());
            System.out.println(record.getModel().Section().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchSection(GRiderCAS instance) throws SQLException, GuanzonException{
        Section record = new ParamControllers(instance, null).Section();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getSectionId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchWarehouse(GRiderCAS instance) throws SQLException, GuanzonException{
        Warehouse record = new ParamControllers(instance, null).Warehouse();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getWarehouseId());
            System.out.println(record.getModel().getDescription());
        } else System.out.println("No record was selected.");
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

        JSONObject loJSON = record.searchRecordByModel("tmx", false, "00001");        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getVariantId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().getYearModel());
            System.out.println(record.getModel().Model().getModelId());
            System.out.println(record.getModel().Model().getDescription());
            System.out.println(record.getModel().Model().getModelCode());
            System.out.println(record.getModel().Model().getBrandId());
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

        JSONObject loJSON = record.searchRecordOfVariants("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getStockId());
            System.out.println(record.getModel().getBarCode());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().Brand().getDescription());
            System.out.println(record.getModel().Model().getDescription());
            System.out.println(record.getModel().Color().getDescription());
            System.out.println(record.getModel().Measure().getDescription());
            System.out.println(record.getModel().Category().getDescription());
            System.out.println(record.getModel().Variant().getDescription());
        } else System.out.println("No record was selected.");
    }
    
    private void searchInventoryMaster(GRiderCAS instance) throws SQLException, GuanzonException{
        InvMaster record = new InvControllers(instance, null).InventoryMaster();

        JSONObject loJSON = record.searchRecord("", false);        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getStockId());
            System.out.println(record.getModel().getBranchCode());
            System.out.println(record.getModel().getQuantityOnHand());
        } else System.out.println("No record was selected.");
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
