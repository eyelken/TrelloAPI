package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class trello {
    String idBoard = "639ab84d3cdeb4001de6b030";
    String idList = "639ab84d3cdeb4001de6b039";
    String idCard = "639ac6007e9d0e0195b49d33";
    String idCard2 ="639accc49a559000606b33a1";
    String key = "8578dcc8bc3f7ed91378da2d64d017b4";
    String token = "ATTAad3d3aa9885c434a86132f709fdb6100564765e48e5af1389a008bb9902081249251E829";





    @BeforeClass
    public void beforeClass() {
      baseURI = ConfigurationReader.get("trello_url");

    }

    @Test
    public void createBoard() {

      Response response = given().accept(ContentType.JSON)
        .and().contentType(ContentType.JSON)
        .when()
        .queryParam("key", key)
        .queryParam("token", token)
        .queryParam("name", "Ege Board2")
        .and().post("/boards/");
      System.out.println(response);

      assertEquals(response.statusCode(), 200);
      assertEquals(response.contentType(), "application/json; charset=utf-8");


    }
    @Test(priority = 2)
    public void createCards(){

      Response response = given().log().all().accept(ContentType.JSON)
        .and().contentType(ContentType.JSON)
        .queryParam("key",key)
        .queryParam("token",token)
        .queryParam("desc","yeni")
        .queryParam("idList",idList)
        .queryParam("idBoard", idBoard)
        .when().post("/cards");
      response.prettyPrint();

      response = given().log().all().accept(ContentType.JSON)
        .and().contentType(ContentType.JSON)
        .queryParam("key",key)
        .queryParam("token",token)
        .queryParam("desc","yeni")
        .queryParam("idList",idList)
        .queryParam("idBoard", idBoard)
        .when().post("/cards");
      response.prettyPrint();

      assertEquals(response.statusCode(),200);



    }
    @Test(priority = 3)
    public void updateCard(){

      Response response = given().log().all().accept(ContentType.JSON)
        .and().contentType(ContentType.JSON)
        .queryParam("key",key)
        .queryParam("token",token)
        //.queryParam("id",idCard)
        .queryParam("name","UpdatedCardName")
        .when().put("/cards/639ac6007e9d0e0195b49d33");
      response.prettyPrint();
      assertEquals(response.statusCode(),200);

    }

    @Test(priority = 4)
    public void deleteCards(){

      Response response= given()
        .queryParam("key",key)
        .queryParam("token",token)
        .queryParam("id",idCard)
        .when().delete("/cards/");
      assertEquals(response.statusCode(),200);

      given()
        .queryParam("key",key)
        .queryParam("token",token)
        .queryParam("id",idCard2)
        .when().delete("/cards/");
      assertEquals(response.statusCode(),200);


    }
    @Test(priority = 5)
    public void deleteBoard(){

      Response response = given()
        .queryParam("key",key)
        .queryParam("token",token)
        .queryParam("id",idBoard)
        .when().delete("/boards/{id}");
      assertEquals(response.statusCode(),200);
      

    }
}
