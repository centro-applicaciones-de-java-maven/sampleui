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
import org.guanzon.cas.client.ClientGUI;
import org.guanzon.cas.client.account.AP_Client_Master;
import org.guanzon.cas.client.account.Account_Accreditation;
import org.guanzon.cas.client.services.ClientControllers;
import org.guanzon.cas.parameter.Brand;
import org.guanzon.cas.parameter.services.ParamControllers;
import org.guanzon.cas.purchasing.controller.PurchaseOrder;
import org.guanzon.cas.purchasing.controller.PurchaseOrderReceiving;
import org.guanzon.cas.purchasing.services.PurchaseOrderControllers;
import org.guanzon.cas.purchasing.services.PurchaseOrderReceivingControllers;
import org.json.simple.JSONObject;
import ph.com.guanzongroup.cas.cashflow.APPaymentAdjustment;
import ph.com.guanzongroup.cas.cashflow.services.CashflowControllers;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException, CloneNotSupportedException, Exception {
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

            LogWrapper wrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");
            
//            APPaymentAdjustmentStatusHist(instance, wrapper);
            POTransApprovalHist(instance, wrapper);
            POTransStatusHist(instance, wrapper);
//            PORecTransStatusHist(instance, wrapper);
//            searchAccountAccreditation(instance, wrapper);
//            searchAPClient(instance, wrapper);
//            searchStockID(instance);
//            testNewClient(instance, wrapper);
//            testLoadClient(instance, wrapper);
//            testNewInstitution(instance, wrapper);
//            testLoadInstitution(instance, wrapper);
//            testGetSupplier(instance, wrapper);
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
//            searchTaxCode(instance);
//            searchBankAccount(instance);
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }

        App.setRoot("secondary");
    }
    
    private void POTransApprovalHist(GRiderCAS instance, LogWrapper wrapper) throws SQLException, GuanzonException, CloneNotSupportedException, Exception{
        PurchaseOrder POTrans = new PurchaseOrderControllers(instance, wrapper).PurchaseOrder();
        POTrans.InitTransaction();
        POTrans.OpenTransaction("GK0126000251");
        POTrans.ShowApprovalHistory();
    }
    
    private void POTransStatusHist(GRiderCAS instance, LogWrapper wrapper) throws SQLException, GuanzonException, CloneNotSupportedException, Exception{
        PurchaseOrder POTrans = new PurchaseOrderControllers(instance, wrapper).PurchaseOrder();
        POTrans.InitTransaction();
        POTrans.OpenTransaction("GK0126000251");
        POTrans.ShowStatusHistory();
    }
    
    private void PORecTransStatusHist(GRiderCAS instance, LogWrapper wrapper) throws SQLException, GuanzonException, CloneNotSupportedException, Exception{
        PurchaseOrderReceiving POTrans = new PurchaseOrderReceivingControllers(instance, wrapper).PurchaseOrderReceiving();
        POTrans.InitTransaction();
        POTrans.OpenTransaction("GK0125000015");
        POTrans.ShowStatusHistory();
    }
    
     private void APPaymentAdjustmentStatusHist(GRiderCAS instance, LogWrapper wrapper) throws SQLException, GuanzonException, CloneNotSupportedException, Exception{
        APPaymentAdjustment POTrans = new CashflowControllers(instance, wrapper).APPaymentAdjustment();
        JSONObject loJSON = POTrans.OpenTransaction("GCO126000022");
        
        if ("success".equals((String) loJSON.get("result"))){
            POTrans.ShowStatusHistory();
        }
    }

//    private void searchBankAccount(GRiderCAS instance) throws SQLException, GuanzonException{
//        BankAccountMaster record = new CashflowControllers(instance, null).BankAccountMaster();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getBankAccountId());
//            System.out.println(record.getModel().getAccountNo());
//            System.out.println(record.getModel().getAccountName());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchTaxCode(GRiderCAS instance) throws SQLException, GuanzonException{
//        TaxCode record = new ParamControllers(instance, null).TaxCode();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getTaxCode());
//            System.out.println(record.getModel().getRegularRate());
//            System.out.println(record.getModel().getGovernmentRate());
//        } else System.out.println("No record was selected.");
//    }
    private void searchAccountAccreditation(GRiderCAS instance, LogWrapper wrapper) {
        try {
            Account_Accreditation record = new ClientControllers(instance, wrapper).AccountAccreditation();

            JSONObject loJSON = record.searchRecord("", false);
            if ("success".equals((String) loJSON.get("result"))) {
                System.out.println(record.getModel().getClientId());
                System.out.println(record.getModel().getAddressId());
                System.out.println(record.getModel().getContactId());
            } else {
                System.out.println("No record was selected.");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void searchAPClient(GRiderCAS instance, LogWrapper wrapper) {
        try {
            AP_Client_Master record = new ClientControllers(instance, wrapper).APClientMaster();
            record.setRecordStatus("10");

            JSONObject loJSON = record.searchRecord("", false);
            if ("success".equals((String) loJSON.get("result"))) {
                System.out.println(record.getModel().getClientId());
                System.out.println(record.getModel().getAddressId());
                System.out.println(record.getModel().getContactId());
            } else {
                System.out.println("No record was selected.");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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
    
    private void testGetSupplier(GRiderCAS instance, LogWrapper wrapper) {
        try {
            AP_Client_Master client = new ClientControllers(instance, wrapper).APClientMaster();
            client.setRecordStatus("1");
            
            JSONObject loJSON = client.searchRecord("", false);
            
            if ("success".equals((String) loJSON.get("result"))){
                System.out.println("Client ID: " +client.getModel().getClientId());
                System.out.println("Client Address ID: " + client.getModel().ClientAddress().getAddressId());
                System.out.println("Client Contact P ID: " + client.getModel().ClientInstitutionContact().getContactPId());
            } else {
                System.err.println((String) loJSON.get("message"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

//    private void searchTransactionAttachment(GRiderCAS instance) throws SQLException, GuanzonException{
//        TransactionAttachment record = new SysTableContollers(instance, null).TransactionAttachment();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getTransactionNo());
//            System.out.println(record.getModel().getSourceNo());
//            System.out.println(record.getModel().getSourceCode());
//            System.out.println(record.getModel().getFileName());
//        } else System.out.println("No record was selected.");
//        
//        
//        //load list of attachment per transaction
//        List loList = record.getAttachments("PRec", "M00125000001");
//        for (int lnCtr = 0; lnCtr <= loList.size() - 1; lnCtr++){
//            System.out.println("File " + (lnCtr + 1) + " transaction no: " + loList.get(lnCtr));
//            loJSON = record.openRecord((String) loList.get(lnCtr));
//            
//            if ("success".equals((String) loJSON.get("result"))){
//                System.out.println(record.getModel().getTransactionNo());
//                System.out.println(record.getModel().getSourceNo());
//                System.out.println(record.getModel().getSourceCode());
//                System.out.println(record.getModel().getFileName());
//            }
//        }
//    }
//    
//    private void searchClient(GRiderCAS instance) throws SQLException, GuanzonException{
//        ClientInfo record = new ClientControllers(instance, null).ClientInfo();
//
//        JSONObject loJSON = record.searchRecord("Cuison", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getClientId());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchGeneralLedger(GRiderCAS instance) throws SQLException, GuanzonException{
//        TransactionAccountChart record = new GLControllers(instance, null).TransactionAccountChart();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getGLCode());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchAccountChart(GRiderCAS instance) throws SQLException, GuanzonException{
//        AccountChart record = new GLControllers(instance, null).AccountChart();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getAccountCode());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().General_Ledger().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchDepartment(GRiderCAS instance) throws SQLException, GuanzonException{
//        Department record = new ParamControllers(instance, null).Department();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getDepartmentId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getDepartmentCode());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchCompany(GRiderCAS instance) throws SQLException, GuanzonException{
//        Company record = new ParamControllers(instance, null).Company();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getCompanyId());
//            System.out.println(record.getModel().getCompanyName());
//            System.out.println(record.getModel().getCompanyCode());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchProvince(GRiderCAS instance) throws SQLException, GuanzonException{
//        Province record = new ParamControllers(instance, null).Province();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getProvinceId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getRegionId());
//            System.out.println(record.getModel().Region().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchRegion(GRiderCAS instance) throws SQLException, GuanzonException{
//        Region record = new ParamControllers(instance, null).Region();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getRegionId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchCountry(GRiderCAS instance) throws SQLException, GuanzonException{
//        Country record = new ParamControllers(instance, null).Country();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getCountryId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getNationality());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchTownCity(GRiderCAS instance) throws SQLException, GuanzonException{
//        TownCity record = new ParamControllers(instance, null).TownCity();
//
//        JSONObject loJSON = record.searchRecord("", false, "01");        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getTownId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getProvinceId());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchInventorySerial(GRiderCAS instance) throws SQLException, GuanzonException{
//        InvSerial record = new InvControllers(instance, null).InventorySerial();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getSerialId());
//            System.out.println(record.getModel().getSerial01());
//            System.out.println(record.getModel().getSerial02());
//            System.out.println(record.getModel().getStockId());
//            System.out.println(record.getModel().Inventory().getDescription());       
//            System.out.println(record.SerialRegistration().getFileNumber());       
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchTerm(GRiderCAS instance) throws SQLException, GuanzonException{
//        Term record = new ParamControllers(instance, null).Term();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getTermId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getTermValue());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchInventoryLocation(GRiderCAS instance) throws SQLException, GuanzonException{
//        InvLocation record = new ParamControllers(instance, null).InventoryLocation();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getLocationId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().Warehouse().getDescription());
//            System.out.println(record.getModel().Section().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchSection(GRiderCAS instance) throws SQLException, GuanzonException{
//        Section record = new ParamControllers(instance, null).Section();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getSectionId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchWarehouse(GRiderCAS instance) throws SQLException, GuanzonException{
//        Warehouse record = new ParamControllers(instance, null).Warehouse();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getWarehouseId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchBranch(GRiderCAS instance) throws SQLException, GuanzonException{
//        //get industry using branch parameter search
//        Branch record = new ParamControllers(instance, null).Branch();
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getBranchCode());
//            System.out.println(record.getModel().getBranchName());
//            System.out.println(record.getModel().getCompanyId());
//            System.out.println(record.getModel().getIndustryCode());
//        } else System.out.println("No record was selected.");
//        
//        //get industry through gRider
//        System.out.println(instance.getIndustry());
//    }
//    
//    private void searchIndustry(GRiderCAS instance) throws SQLException, GuanzonException{
//        Industry record = new ParamControllers(instance, null).Industry();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getIndustryId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchInventoryType(GRiderCAS instance) throws SQLException, GuanzonException{
//        InvType record = new ParamControllers(instance, null).InventoryType();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getInventoryTypeId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchCategory(GRiderCAS instance) throws SQLException, GuanzonException{
//        Category record = new ParamControllers(instance, null).Category();
//
//        JSONObject loJSON = record.searchRecord("", false, "01", null);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getCategoryId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getInventoryTypeCode());
//            System.out.println(record.getModel().Industry().getDescription());
//            System.out.println(record.getModel().Inv_Type().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
    private void searchBrand(GRiderCAS instance) throws SQLException, GuanzonException{
        Brand record = new ParamControllers(instance, null).Brand();

        JSONObject loJSON = record.searchRecord("", false, "09");        
        if ("success".equals((String) loJSON.get("result"))){
            System.out.println(record.getModel().getBrandId());
            System.out.println(record.getModel().getDescription());
            System.out.println(record.getModel().Industry().getDescription());
        } else System.out.println("No record was selected.");
    }
    
//    private void searchModel(GRiderCAS instance) throws SQLException, GuanzonException{
//        Model record = new ParamControllers(instance, null).Model();
//
//        JSONObject loJSON = record.searchRecord("", false, "00001");        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getModelId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getManufactureYear());
//            System.out.println(record.getModel().getBrandId());
//            System.out.println(record.getModel().Brand().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchColor(GRiderCAS instance) throws SQLException, GuanzonException{
//        Color record = new ParamControllers(instance, null).Color();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getColorId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getColorCode());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchModelVariant(GRiderCAS instance) throws SQLException, GuanzonException{
//        ModelVariant record = new ParamControllers(instance, null).ModelVariant();
//
//        JSONObject loJSON = record.searchRecordByModel("", false, "00001");        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getVariantId());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().getYearModel());
//            System.out.println(record.getModel().Model().getModelId());
//            System.out.println(record.getModel().Model().getDescription());
//            System.out.println(record.getModel().Model().getModelCode());
//            System.out.println(record.getModel().Model().getBrandId());
//            System.out.println(record.getModel().Color().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchMearusurement(GRiderCAS instance) throws SQLException, GuanzonException{
//        Measure record = new ParamControllers(instance, null).Measurement();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getMeasureId());
//            System.out.println(record.getModel().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchStockID(GRiderCAS instance) throws SQLException, GuanzonException{
//        Inventory record = new InvControllers(instance, null).Inventory();
//
//        JSONObject loJSON = record.searchRecord("0005", "00009", "V0002", "00004", "00004");        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getStockId());
//            System.out.println(record.getModel().getBarCode());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().Brand().getDescription());
//            System.out.println(record.getModel().Model().getDescription());
//            System.out.println(record.getModel().Color().getDescription());
//            System.out.println(record.getModel().Measure().getDescription());
//            System.out.println(record.getModel().Category().getDescription());
//            System.out.println(record.getModel().Variant().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchInventory(GRiderCAS instance) throws SQLException, GuanzonException{
//        Inventory record = new InvControllers(instance, null).Inventory();
//
//        JSONObject loJSON = record.searchRecordOfVariants("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getStockId());
//            System.out.println(record.getModel().getBarCode());
//            System.out.println(record.getModel().getDescription());
//            System.out.println(record.getModel().Brand().getDescription());
//            System.out.println(record.getModel().Model().getDescription());
//            System.out.println(record.getModel().Color().getDescription());
//            System.out.println(record.getModel().Measure().getDescription());
//            System.out.println(record.getModel().Category().getDescription());
//            System.out.println(record.getModel().Variant().getDescription());
//        } else System.out.println("No record was selected.");
//    }
//    
//    private void searchInventoryMaster(GRiderCAS instance) throws SQLException, GuanzonException{
//        InvMaster record = new InvControllers(instance, null).InventoryMaster();
//
//        JSONObject loJSON = record.searchRecord("", false);        
//        if ("success".equals((String) loJSON.get("result"))){
//            System.out.println(record.getModel().getStockId());
//            System.out.println(record.getModel().getBranchCode());
//            System.out.println(record.getModel().getQuantityOnHand());
//        } else System.out.println("No record was selected.");
//    }
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
