package APIDataClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void setIsActive(boolean b) {
    }

    public void setActive(boolean b) {
    }

    public void setActive(boolean b) {
    }
}