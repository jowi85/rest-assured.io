import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class HelloWorldRestAssured {

    private String basePath = "https://us.api.battle.net/wow";
    private String apiKey = "9jgj6gnfgr7wjgn6w5db8vpfpatam2uy";

    @Test
    public void HappyPathStatusCode200() {
        String characterEndpoint = "/character/dalaran/eeyr?fields=stats&locale=en_US&apikey=";
        Response characterEndpointResponse = RestAssured.get(basePath + characterEndpoint + apiKey);

        characterEndpointResponse.then()
                .statusCode(200);

//        System.out.println(characterEndpointResponse.asString());
    }

    @Test
    public void MissingAPIKeyStatusCode403() {
        String characterEndpoint = "/character/dalaran/eeyr?fields=stats&locale=en_US&apikey=";
        Response characterEndpointResponse = RestAssured.get(basePath + characterEndpoint);

        characterEndpointResponse.then()
                .statusCode(403)
                .body("detail", equalTo("Account Inactive"));

//        System.out.println(characterEndpointResponse.asString());
    }

    @Test
    public void InvalidCharacterNameStatusCode404() {
        String characterEndpoint = "/character/dalaran/notAValidName?fields=stats&locale=en_US&apikey=";
        Response characterEndpointResponse = RestAssured.get(basePath + characterEndpoint + apiKey);

        characterEndpointResponse.then()
                .statusCode(404)
                .body("status", equalTo("nok"))
                .body("reason", equalTo("Character not found."));

//        System.out.println(characterEndpointResponse.asString());
    }

    @Test
    public void InvalidRealmNameStatusCode404() {
        String characterEndpoint = "/character/notAValidName/eeyr?fields=stats&locale=en_US&apikey=";
        Response characterEndpointResponse = RestAssured.get(basePath + characterEndpoint + apiKey);

        characterEndpointResponse.then()
                .statusCode(404)
                .body("status", equalTo("nok"))
                .body("reason", equalTo("Realm not found."));

//        System.out.println(characterEndpointResponse.asString());
    }

    @Test
    public void InvalidEndpointStatusCode596() {
        String characterEndpoint = "https://us.api.battle.net/thisIsNotAValidEndpoint/wow/character/dalaran/eeyr?fields=stats&locale=en_US&apikey=";
        Response characterEndpointResponse = RestAssured.get(basePath + characterEndpoint + apiKey);

        characterEndpointResponse.then()
                .statusCode(596);

//        System.out.println(characterEndpointResponse.asString());
    }

}
