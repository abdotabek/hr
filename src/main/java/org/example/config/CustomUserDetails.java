package org.example.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.EmployeeRole;
import org.example.dto.enums.GeneralStatus;
import org.example.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    String firstName;
    String lastName;
    String phone;
    String password;
    EmployeeRole role;
    GeneralStatus status;
    Long employeeId;

    public CustomUserDetails(Employee employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.phone = employee.getPhoneNumber();
        this.password = employee.getPassword();
        this.role = employee.getRole();
        this.status = employee.getStatus();
        this.employeeId = employee.getId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return status.equals(GeneralStatus.ACTIVE);
        return GeneralStatus.ACTIVE.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return GeneralStatus.ACTIVE.equals(status);
//        return true;
    }

}
