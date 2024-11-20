package APITests;

import APIDataClasses.AuthResponse;
import APIDataClasses.CreateEmployeeRequest;
import APIDataClasses.CreateEmployeeResponse;
import DBConnection.EmployeeEntity;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.io.IOException;

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

    @Test
    @DisplayName("Создание сотрудника, данные на латинице")
    @Tag("Позитивный")
    void createEmployeeLatin() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request = createEmployeeRequest(request, latin);

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

        EmployeeEntity employeeFromDB = getEmployeeByIdDB(newEmployeeId);
        verifyEmployee(employeeFromDB, LATIN_NAME, LATIN_LASTNAME);
    }

    @Test
    @DisplayName("Создание неактивного сотрудника")
    @Tag("Позитивный")
    void createInactiveEmployee() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request = createEmployeeRequest(request, russian);
        request.setIsActive();

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

        EmployeeEntity employeeFromDB = getEmployeeByIdDB(newEmployeeId);
        assertFalse(employeeFromDB.getIsActive());
    }

    @Test
    @DisplayName("Создание сотрудника без необязательных полей")
    @Tag("Позитивный")
    void createEmployeeWithoutOptionalFields() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setLastName(RUSSIAN_LASTNAME);
        request.setFirstName(RUSSIAN_NAME);
        request.setPhone(EMPLOYEE_PHONE);
        request.setIsActive();

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

        EmployeeEntity employeeFromDB = getEmployeeByIdDB(newEmployeeId);
        assertNull(employeeFromDB.getMiddleName());
        assertNull(employeeFromDB.getBirthdate());
        assertNull(employeeFromDB.getAvatarUrl());
    }

    @Test
    @DisplayName("Получение информации о конкретном сотруднике")
    @Tag("Позитивный")
    void getEmployeeById() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request = createEmployeeRequest(request, russian);

        int employeeId = given()
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

        given()
                .basePath("employee/" + employeeId)
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("lastName", Matchers.equalTo(RUSSIAN_LASTNAME))
                .body("firstName", Matchers.equalTo(RUSSIAN_NAME))
                .body("phone", Matchers.equalTo(EMPLOYEE_PHONE));
    }

    @Test
    @DisplayName("Проверка валидации телефонного номера")
    @Tag("Негативный")
    void createEmployeeWithInvalidPhone() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request = createEmployeeRequest(request, russian);
        request.setPhone("invalid_phone");

        given()
                .basePath("employee")
                .body(request)
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}