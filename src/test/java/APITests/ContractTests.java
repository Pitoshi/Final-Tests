package APITests;

import DBConnection.EmployeeEntity;
import APIDataClasses.AuthResponse;
import APIDataClasses.CreateEmployeeRequest;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static APIHelpers.DBQuery.*;
import static APIHelpers.DBQuery.fullFieldsRussianDB;
import static APIHelpers.RestAssuredRequests.*;
import static io.restassured.RestAssured.given;
import static APIVariables.VariablesForEmployeeTests.*;
import static APIVariables.VariablesOfResponses.*;
import static properties.GetProperties.getProperty;


public class ContractTests {

    AuthResponse info = auth();

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = getProperty("url");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @AfterAll
    public static void tearDown() {
        deleteTestsCreatedData();
    }

    @Test
    @DisplayName("Получение списка сотрудников")
    @Tag("Позитивный")
    public void getListOfEmployees() {

        given()
                .queryParam("company", NEW_COMPANY)
                .basePath("employee")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_OK)
                .header("Content-Type", JSON);
    }

    @Test
    @DisplayName("Получение списка сотрудников для несуществующей компании")
    @Tag("Негативный")
    public void getListOfEmployeesOfNotExistedCompany() {

        given()
                .basePath("employee")
                .when()
                .get("{company}", NEGATIVE_ID)
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_OK);
    }

    @Test
    @DisplayName("Получение списка сотрудников id компании строка")
    @Tag("Негативный")
    public void getListOfEmployeesIdCompanyString() {

        given()
                .basePath("employee")
                .when()
                .get("{company}", "NEW")
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание сотрудника, данные русские")
    @Tag("Позитивный")
    public void createEmployee() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), russian))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_CREATED)
                .header("Content-Type", JSON);
    }

    @Test
    @DisplayName("Создание сотрудника в несуществующей компании")
    @Tag("Негативный")
    public void createEmployeeNotExistedCompany() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), employeeNoCompany))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание сотрудника значения больше граничных")
    @Tag("Негативный")
    public void createEmployeeMoreBoundaryValues() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), fieldsMoreThanMax))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание сотрудника, неверный токен")
    @Tag("Негативный")
    public void createEmployeeWrongToken() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), russian))
                .header(TOKEN_TYPE, WRONG_USER_TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED);
    }

    @Test
    @DisplayName("Создание сотрудника, нет токена")
    @Tag("Негативный")
    public void createEmployeeNoToken() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), russian))
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED);
    }

    @Test
    @DisplayName("Создание сотрудника, пустой токен")
    @Tag("Негативный")
    public void createEmployeeEmptyToken() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), russian))
                .header(TOKEN_TYPE, "")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED);
    }

    @Test
    @DisplayName("Создание сотрудника, неверный формат email")
    @Tag("Негативный")
    public void createEmployeeIncorrectEmail() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), incorrectEmail))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    @Tag("Позитивный")
    public void getEmployeeById() {

        given()
                .basePath("employee")
                .when()
                .get("{id}", createEmployeeDB(new EmployeeEntity(), russianDB).getId())
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_OK)
                .header("Content-Type", JSON);
    }

    @Test
    @DisplayName("Получение сотрудника по несуществующему id, должен быть статус-код 404")
    @Disabled
    @Issue("в свагере сказано, что Сотрудник не найден - 404, а выходит 500")
    @Tag("Негативный")
    public void getEmployeeByNotExistedId() {

        given()
                .basePath("employee")
                .when()
                .get("{id}", NEGATIVE_ID)
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_NOT_FOUND);
    }

    @Test
    @DisplayName("Изменение информации о сотруднике")
    @Tag("Позитивный")
    public void changeEmployeeData() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), employeeChange))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .patch("{id}", createEmployeeDB(new EmployeeEntity(), fullFieldsRussianDB).getId())
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_OK)
                .header("Content-Type", JSON);
    }


    @Test
    @DisplayName("Изменение информации о сотруднике с несуществующим id, должен быть статус-код 404")
    @Disabled
    @Issue("в свагере сказано, что Сотрудник не найден - 404, а выходит 500")
    @Tag("Негативный")
    public void changeEmployeeDataWithNotExistedId() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), employeeChange))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .patch("{id}", NEGATIVE_ID)
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_NOT_FOUND);
    }

    @Test
    @DisplayName("Изменение всей информации о сотруднике")
    @Tag("Негативный")
    public void changeAllEmployeeData() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), latin))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .patch("{id}", createEmployeeDB(new EmployeeEntity(), fullFieldsRussianDB).getId())
                .then()
                .assertThat()
                .statusCode(STATUS_CODE_OK);
    }

    @Test
    @DisplayName("Изменение информации о сотруднике, некорректный емейл")
    @Tag("Негативный")
    public void changeEmployeeDataIncorrectEmail() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), incorrectEmail))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .patch("{id}", createEmployeeDB(new EmployeeEntity(), fullFieldsRussianDB).getId())
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST);

    }

}



