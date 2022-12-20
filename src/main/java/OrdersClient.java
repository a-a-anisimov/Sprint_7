import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersClient {
    @Step("Create new order (Send POST request)")
    public Response sendGetRequestUsersMe(){
        Response response =given().post();
        return response;
    }

    @Step("Check status code")
    public void checkStatusCode(Response response){
        response.then().assertThat().statusCode(201);
    }

    @Step("Check body contains track")
    public void checkBodyContainsTrack(Response response, String username){
        response.then().assertThat().body("track",notNullValue());
    }
}
