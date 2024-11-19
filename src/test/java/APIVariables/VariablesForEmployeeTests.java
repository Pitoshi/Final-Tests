package APIVariables;

import APIDataClasses.CreateEmployeeRequest;
import io.qameta.allure.Step;
import java.io.IOException;
import java.util.function.Function;
import static APIHelpers.DBQuery.createNewCompanyDB;


public class VariablesForEmployeeTests {

    public static String COMPANY_NAME = "ООО \"Цветочек\"";

    public static String RUSSIAN_NAME = "Антон";

    public static String RUSSIAN_LASTNAME = "Смирнов";

    public static String RUSSIAN_MIDDLE_NAME = "Васильевич";

    public static String LATIN_NAME = "Sam";

    public static String LATIN_LASTNAME = "Landson";

    public static String EMPLOYEE_PHONE = "+79112121314";

    public static String EMPLOYEE_BIRTHDAY = "1985-09-08";

    public static String EMPLOYEE_EMAIL = "sidorov@mail.ru";

    public static String EMPLOYEE_URL = "sidorov.ru";

    public static String EMPLOYEE_CHANGED_EMAIL = "rok22@mail.ru";

    public static String EMPLOYEE_CHANGED_URL = "rok.ru";

    public static String EMPLOYEE_CHANGED_PHONE = "89214565658";

    public static String EMPLOYEE_CHANGED_BIRTHDAY = "1921-10-09";

    public static String CHANGED_LASTNAME = "Рок";

    public static String SPECIAL_CHARACTERS = "!@#$%^&*_+:?!№;%:?*";

    public static String MORE_THAN_20_CHARACTERS = "Больше, чем можно вставить в таблицу";

    public static String CHARACTERS_20 = "Это можно вставить20";

    public static String CHARACTERS_15 = "Это можно встав";

    public static int NEGATIVE_ID = -5;

    public static int NEW_COMPANY;

    static {
        try {
            NEW_COMPANY = createNewCompanyDB();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String WRONG_USER_TOKEN = "88JhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4iLCJpYXQiOjE3MjU4MDE0NzMsImV4cCI6MTcyNTgwMjM3M30.Du5xSPmXw9rB-L2lf6OmjBLf7aGdEisq04D0Kyqpx1g";

    @Step("Запрос на создание сотрудника через API")
    public static CreateEmployeeRequest createEmployeeRequest(
            CreateEmployeeRequest employee,
            Function<CreateEmployeeRequest,
                    CreateEmployeeRequest> rule) {
        return rule.apply(employee);
    }

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> russian =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(RUSSIAN_NAME);
                    e.setLastName(RUSSIAN_LASTNAME);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(EMPLOYEE_PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> latin =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(LATIN_NAME);
                    e.setLastName(LATIN_LASTNAME);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(EMPLOYEE_PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> fullFieldsRussian =

            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(RUSSIAN_NAME);
                    e.setLastName(RUSSIAN_LASTNAME);
                    e.setMiddleName(RUSSIAN_MIDDLE_NAME);
                    e.setBirthdate(EMPLOYEE_BIRTHDAY);
                    e.setEmail(EMPLOYEE_EMAIL);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(EMPLOYEE_PHONE);
                    e.setActive(true);
                    e.setUrl(EMPLOYEE_URL);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> employeeNoCompany =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(RUSSIAN_NAME);
                    e.setLastName(RUSSIAN_LASTNAME);
                    e.setCompanyId(NEGATIVE_ID);
                    e.setPhone(EMPLOYEE_PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> employeeChange =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setLastName(CHANGED_LASTNAME);
                    e.setEmail(EMPLOYEE_CHANGED_EMAIL);
                    e.setPhone(EMPLOYEE_CHANGED_PHONE);
                    e.setActive(true);
                    e.setUrl(EMPLOYEE_CHANGED_URL);
                    return e;
                }
            };


    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> specialCharacters =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(SPECIAL_CHARACTERS);
                    e.setLastName(SPECIAL_CHARACTERS);
                    e.setMiddleName(SPECIAL_CHARACTERS);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(EMPLOYEE_CHANGED_PHONE);
                    e.setUrl(EMPLOYEE_CHANGED_URL);
                    e.setEmail(EMPLOYEE_CHANGED_EMAIL);
                    e.setBirthdate(EMPLOYEE_CHANGED_BIRTHDAY);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> incorrectEmail =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(RUSSIAN_NAME);
                    e.setLastName(RUSSIAN_LASTNAME);
                    e.setEmail(LATIN_NAME);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(EMPLOYEE_PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> fullFieldsMax =

            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(CHARACTERS_20);
                    e.setLastName(CHARACTERS_20);
                    e.setMiddleName(CHARACTERS_20);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(CHARACTERS_15);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> fieldsMoreThanMax =

            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(MORE_THAN_20_CHARACTERS);
                    e.setLastName(MORE_THAN_20_CHARACTERS);
                    e.setMiddleName(MORE_THAN_20_CHARACTERS);
                    e.setCompanyId(NEW_COMPANY);
                    e.setPhone(CHARACTERS_20);
                    return e;
                }
            };

}


