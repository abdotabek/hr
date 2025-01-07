package org.example.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomUserDetailsService implements UserDetailsService {

    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username = login or phone or email
        Optional<Employee> optional = employeeRepository.findByPhoneNumber(username);
//        Optional<Employee> optional = employeeRepository.findByPhoneNumberAndStatus(username, GeneralStatus.ACTIVE);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Employee employee = optional.get();
        return new CustomUserDetails(employee);
    }


}
