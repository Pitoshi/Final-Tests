package APITests;

import APIDataClasses.AuthResponse;
import APIDataClasses.CreateEmployeeRequest;
import APIDataClasses.CreateEmployeeResponse;
import DBConnection.EmployeeEntity;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static APIHelpers.DBQuery.createNewCompanyDB;
import static APIHelpers.DBQuery.getEmployeeByIdDB;
import static APIHelpers.RestAssuredRequests.auth;
import static APIHelpers.RestAssuredRequests.createNewCompanyWithEmployees;
import static APIVariables.VariablesForEmployeeTests.*;
import static APIVariables.VariablesOfResponses.TOKEN_TYPE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static properties.GetProperties.getProperty;

@DisplayName("Бизнес тесты")
public class BusinessTests {

    private final AuthResponse info = auth();

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = getProperty("url");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Получение списка сотрудников")
    @Disabled("тест не пройдет, баг емейл не сохраняется в базу")
    @Issue("ISSUE-EMAIL")
    @Tag("Позитивный")
    void getListOfEmployees() throws IOException {
        given()
                .queryParam("company", createNewCompanyWithEmployees())
                .basePath("employee")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("lastName", hasItems(RUSSIAN_LASTNAME, LATIN_LASTNAME))
                .body("firstName", hasItems(RUSSIAN_NAME, LATIN_NAME))
                .body("middleName", hasItems(RUSSIAN_MIDDLE_NAME, null))
                .body("birthdate", hasItems(EMPLOYEE_BIRTHDAY, null))
                .body("phone", hasItem(EMPLOYEE_PHONE))
                .body("isActive", hasItem(true))
                .body("avatar_url", hasItems(EMPLOYEE_URL, null))
                .body("email", hasItems(EMPLOYEE_EMAIL, null));
    }

    @Test
    @DisplayName("Получение пустого списка сотрудников")
    @Tag("Позитивный")
    void getEmptyListOfEmployees() throws IOException {
        given()
                .queryParam("company", createNewCompanyDB())
                .basePath("employee")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .header("Content-Length", (String) null);
    }

    @Test
    @DisplayName("Создание сотрудника, данные на русском")
    @Tag("Позитивный")
    void createEmployeeRussian() {
        // Подготовка запроса
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request = createEmployeeRequest(request, russian);

        // Выполнение запроса и получение ID созданного сотрудника
        int newEmployeeId = given()
                .basePath("employee")
                .body(request)
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(CreateEmployeeResponse.class)
                .id();

        // Проверка данных в БД
        EmployeeEntity employeeFromDB = getEmployeeByIdDB(newEmployeeId);
        verifyEmployee(employeeFromDB, RUSSIAN_NAME, RUSSIAN_LASTNAME);
    }

    private void verifyEmployee(EmployeeEntity employee, String expectedFirstName, String expectedLastName) {
        assertAll(
                () -> assertEquals(expectedFirstName, employee.getFirstName(), "Неверное имя"),
                () -> assertEquals(expectedLastName, employee.getLastName(), "Неверная фамилия"),
                () -> assertTrue(employee.isActive(), "Сотрудник не активен")
        );
    }
}