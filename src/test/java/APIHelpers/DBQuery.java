package APIHelpers;

import DBConnection.EmployeeEntity;
import DBConnection.MyPUI;
import DBConnection.CompanyEntity;
import io.qameta.allure.Step;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import static APIVariables.VariablesForEmployeeTests.*;
import static properties.GetProperties.getProperties;


public class DBQuery {

    private static EntityManager entityManager;

    @Step("Подключение к БД")
    public static void connectionDB() {
        Properties properties = getProperties();
        PersistenceUnitInfo pui = new MyPUI(properties);

        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory entityManagerFactory = hibernatePersistenceProvider.createContainerEntityManagerFactory(pui, pui.getProperties());
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Step("Создаем в БД новую компанию")
    public static int createNewCompanyDB() throws IOException {
        connectionDB();

        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(COMPANY_NAME);
        newCompany.setActive(true);

        entityManager.getTransaction().begin();
        entityManager.persist(newCompany);
        entityManager.getTransaction().commit();
        return newCompany.getId();
    }

    @Step("Возвращаем полученную информацию по сотруднику по его ID")
    public static EmployeeEntity getEmployeeByIdDB(int employeeID) {
        connectionDB();
        return entityManager.find(EmployeeEntity.class, employeeID);
    }

    @Step("Создаем в БД нового сотрудника")
    public static EmployeeEntity createEmployeeDB(EmployeeEntity employee, Function<EmployeeEntity, EmployeeEntity> rule) {
        connectionDB();

        rule.apply(employee);
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        return employee;
    }

    public static Function<EmployeeEntity, EmployeeEntity> russianDB = new Function<EmployeeEntity, EmployeeEntity>() {
        @Override
        public EmployeeEntity apply(EmployeeEntity e) {
            e.setFirstName(RUSSIAN_NAME);
            e.setLastName(RUSSIAN_LASTNAME);
            e.setCompanyId(NEW_COMPANY);
            e.setPhone(EMPLOYEE_PHONE);
            e.setActive(true);
            return e;
        }
    };

    public static Function<EmployeeEntity, EmployeeEntity> latinDB = new Function<EmployeeEntity, EmployeeEntity>() {
        @Override
        public EmployeeEntity apply(EmployeeEntity e) {
            e.setFirstName(LATIN_NAME);
            e.setLastName(LATIN_LASTNAME);
            e.setCompanyId(NEW_COMPANY);
            e.setPhone(EMPLOYEE_PHONE);
            e.setActive(true);
            return e;
        }
    };

    public static Function<EmployeeEntity, EmployeeEntity> fullFieldsRussianDB = new Function<EmployeeEntity, EmployeeEntity>() {
        @Override
        public EmployeeEntity apply(EmployeeEntity e) {
            e.setFirstName(RUSSIAN_NAME);
            e.setLastName(RUSSIAN_LASTNAME);
            e.setMiddleName(RUSSIAN_MIDDLE_NAME);
            e.setBirthdate(Date.valueOf(EMPLOYEE_BIRTHDAY));
            e.setEmail(EMPLOYEE_EMAIL);
            e.setCompanyId(NEW_COMPANY);
            e.setPhone(EMPLOYEE_PHONE);
            e.setActive(true);
            e.setAvatar_url(EMPLOYEE_URL);
            return e;
        }
    };

    public static Function<EmployeeEntity, EmployeeEntity> specialCharactersDB = new Function<EmployeeEntity, EmployeeEntity>() {
        @Override
        public EmployeeEntity apply(EmployeeEntity e) {
            e.setFirstName(SPECIAL_CHARACTERS);
            e.setLastName(SPECIAL_CHARACTERS);
            e.setMiddleName(SPECIAL_CHARACTERS);
            e.setCompanyId(NEW_COMPANY);
            e.setPhone(EMPLOYEE_PHONE);
            e.setActive(true);
            return e;
        }
    };

    @Step("Удаление тестовых данных из БД")
    public static void deleteTestsCreatedData() {
        connectionDB();
        TypedQuery<EmployeeEntity> queryEmployee =
                entityManager.createQuery("SELECT e FROM EmployeeEntity e JOIN CompanyEntity c ON c.id = e.companyId WHERE c.name LIKE :companyName",
                        EmployeeEntity.class);

        queryEmployee.setParameter("companyName", "%Цветочек%");

        List<EmployeeEntity> employees = queryEmployee.getResultList();


        for (EmployeeEntity employee : employees) {
            entityManager.getTransaction().begin();
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
        }

        TypedQuery<CompanyEntity> queryCompany =
                entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.name LIKE :companyName",
                        CompanyEntity.class);

        queryCompany.setParameter("companyName", "%Цветочек%");

        List<CompanyEntity> companies = queryCompany.getResultList();

        for (CompanyEntity company : companies) {
            entityManager.getTransaction().begin();
            entityManager.remove(company);
            entityManager.getTransaction().commit();
        }

    }

}
