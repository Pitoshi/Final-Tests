package APIDataClasses;

import java.util.Objects;

public class CreateEmployeeRequest {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int companyId;
    private String email;
    private String url;
    private String phone;
    private String birthdate;
    private boolean isActive;

    // Конструктор по умолчанию
    public CreateEmployeeRequest() {}

    // Конструктор со всеми полями
    public CreateEmployeeRequest(int id, String firstName, String lastName, String middleName,
                                 int companyId, String email, String url, String phone,
                                 String birthdate, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyId = companyId;
        this.email = email;
        this.url = url;
        this.phone = phone;
        this.birthdate = birthdate;
        this.isActive = isActive;
    }

    // Геттеры
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public int getCompanyId() { return companyId; }
    public String getEmail() { return email; }
    public String getUrl() { return url; }
    public String getPhone() { return phone; }
    public String getBirthdate() { return birthdate; }
    public boolean isActive() { return isActive; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public void setEmail(String email) { this.email = email; }
    public void setUrl(String url) { this.url = url; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateEmployeeRequest that = (CreateEmployeeRequest) o;
        return id == that.id &&
                companyId == that.companyId &&
                isActive == that.isActive &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(url, that.url) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName,
                companyId, email, url, phone, birthdate, isActive);
    }

    @Override
    public String toString() {
        return "CreateEmployeeRequest{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", companyId=" + companyId +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}