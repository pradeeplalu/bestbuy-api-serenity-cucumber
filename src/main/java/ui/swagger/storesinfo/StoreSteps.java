package ui.swagger.storesinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Title;
import ui.swagger.constants.EndPoints;
import ui.swagger.model.StorePojo;

import java.util.HashMap;

public class StoreSteps {

    @Step("Create a new Store info with name : {0}, type : {1},address : {2}, address2 : {3}, city : {4}, state : {5}, zip : {6}, lat: {7},lng : {8},hours :{9},services : {10}")
    public ValidatableResponse createANewStore(String name, String type, String address, String address2, String city, String state,
                                               String zip, double lat, double lng, String hours, HashMap<Object,Object>services){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        storePojo.setServices(services);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post(EndPoints.CREATE_STORES)
                .then();
    }
    @Step("Get the Store information with ID :{0}")
    public HashMap<String, ?>getStoreInfoByName(int storeID){
        HashMap<String,?>storeMap = SerenityRest.given().log().all()
                .when()
                .pathParam("storeID",storeID)
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");
        return storeMap;
    }
    @Step("update store with store ID:{0}, name : {1}, type :{2},address:{3}")
    public ValidatableResponse updateStoreInfo(int storeID, String name, String type, String address){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .pathParam("storeID",storeID)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_STORE_BY_ID)
                .then();
    }
    @Step("Delete store with ID {0}")
    public ValidatableResponse deleteStore(int storeID){
        return SerenityRest.given().log().all()
                .pathParam("storeID",storeID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_STORE_BY_ID)
                .then();
    }
    @Step("Getting Store with ID {0}")
    public ValidatableResponse getStoreByID(int storeID){
        return SerenityRest.given().log().all()
                .pathParam("storeID",storeID)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }
    @Step("Getting All Stores")
    public ValidatableResponse getAllStores(){
        return SerenityRest.given().log().ifValidationFails()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().log().ifValidationFails();
    }

}
