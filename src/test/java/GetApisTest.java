import ProductModel.Product;
import com.fasterxml.jackson.databind.util.BeanUtil;
import  io.restassured.RestAssured;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.testng.annotations.Test;

import java.util.List;

public class GetApisTest {

    GetApisTest(){
        RestAssured.baseURI = "http://127.0.0.1/RestApis/Products";
    }
    @Test
    public void getProducts(){

        String endPoint = "/GetProducts.php";
        var response = RestAssured.given().
                                                    when().
                                                        get(endPoint).
                                                    then().
                                                        statusCode(200);
        System.out.println(response.log().body());
    }
    @Test
    public void getProductDetails(){
        String endPoint = "/GetProductDetails.php";
        int productId = 1010;
        var response = RestAssured.given().
                                                        queryParams("id",productId).
                                                    when().
                                                        get(endPoint).
                                                    then().
                                                        statusCode(200).
                                                        body("id",equalTo("1010")).
                                                        body("name",equalTo("SweatBand")).
                                                        body("description",equalTo("The percentage of swaetbamd is 10%")).
                                                        body("price",equalTo("9.00")).
                                                        body("category_id",equalTo("2")).
                                                        header("Content-Type", equalTo("application/json"));


    }
    @Test
    public void addProduct(){
        String endPoint = "/AddProduct.php";

        String data = """
                            {
                            "name": "Hot Water Product",
                            "description": "This is a sample Hot Water bole product description.",
                            "price": 13.99,
                            "category_id": 5
                            }
                        """;

        var response = RestAssured.given().
                                                        body(data).
                                                    when().post(endPoint).
                                                    then().
                                                        statusCode(200);


    }
    @Test
    public void updateProduct(){
        String endPoint = "/UpdateProduct.php";

        String data = """
                            {
                            "id":1006,
                            "name": "Water Product - put",
                            "description": "This is a old Water bole product description.",
                            "price": 96.99,
                            "category_id": 2
                            }
                        """;

        var response = RestAssured.given().
                                                        body(data).
                                                    when().
                                                        put(endPoint).
                                                    then().
                                                        statusCode(200);

    }
    @Test
    public void deleteProduct(){
        String endPoint = "/DeleteProduct.php";
        String data = """
                           {"id":1011}
                     """;
        var response = RestAssured.given().
                                                            body(data).
                                                    when().
                                                            delete(endPoint).
                                                    then().
                                                        statusCode(200);
        System.out.println(response.log().status());
    }
    @Test
    public void createProductUsingModel(){
        String endPoint = "/AddProduct.php";
        Product newProduct = new Product(
          "SweatBand",
          "The percentage of swaetbamd is 5%",
          6,
          4
        );

        var response = RestAssured.given().
                body(newProduct).
                when().
                    post(endPoint).
                then().
                    statusCode(200);
    }
    @Test
    public void updateProductUsingModel(){
        String endPoint = "/UpdateProduct.php";
        Product exitedProduct = new Product(
                1010,
                "SweatBand",
                "The percentage of swaetbamd is 10%",
                9,
                2
        );

        var response = RestAssured.given().
                                                        body(exitedProduct).
                                                    when().
                                                        put(endPoint).
                                                    then().
                                                        statusCode(200);
    }
    @Test
    public void getProductDetailsAndValidateSchema(){
        String endPoint = "/GetProductDetails.php";
        int productId = 1010;
        Product expectedProduct = new Product(
                1010,
                "SweatBand",
                "The percentage of swaetbamd is 10%",
                9,
                2
                );
        Product actualProduct = RestAssured.given().
                                                        queryParams("id",productId).
                                                    when().
                                                        get(endPoint).
                                                            as(Product.class);

        assertThat(expectedProduct,samePropertyValuesAs(actualProduct));
    }
}
