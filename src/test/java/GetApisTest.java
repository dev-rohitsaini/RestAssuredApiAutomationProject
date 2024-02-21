import  io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetApisTest {

    GetApisTest(){
        RestAssured.baseURI = "http://127.0.0.1/RestApis/Products";
    }
    @Test
    public void getProducts(){

        String endPoint = "/GetProducts.php";
        var response = RestAssured.given().
                                    when().get(endPoint).
                                    then();
    }
    @Test
    public void getProductDetails(){
        String endPoint = "/GetProductDetails.php";
        int productId = 2;
        var response = RestAssured.given()
                                            .queryParams("id",productId).
                                        when().get(endPoint).
                                        then();

        System.out.println(response.log().body());
    }
    @Test
    public void addProduct(){
        String endPoint = "/AddProduct.php";

        String data = """
                            {
                            "name": "Water Product",
                            "description": "This is a sample Water bole product description.",
                            "price": 21.99,
                            "category_id": 1
                            }
                        """;

        var response = RestAssured.given().
                                                        body(data).
                                                    when().post(endPoint).
                                                    then();

        System.out.println(response.log().body());
    }
    @Test
    public void updateProduct(){

    }
}
