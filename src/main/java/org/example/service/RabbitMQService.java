package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.entity.Branch;
import org.example.entity.Employee;
import org.example.repository.BranchRepository;
import org.example.repository.CompanyRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    RabbitTemplate rabbitTemplate;
    CompanyRepository companyRepository;
    BranchRepository branchRepository;


    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(String message) {
        Long employeeId = Long.parseLong(message);
        employeeRepository.deleteById(employeeId);  // Удаляем сотрудника
    }

    public void deleteEmployee(List<Long> employeeIds) {
        final int delayMilliseconds = 30_0000; // задержка 30 секунд

        employeeIds.forEach(employeeId -> {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", delayMilliseconds);

            Message message = new Message(employeeId.toString().getBytes(), messageProperties);
            rabbitTemplate.send(MyConstants.EMPLOYEE_QUEUE_EXCHANGE, EMPLOYEE_QUEUE_ROUTING_KEY, message);
        });
    }


    @RabbitListener(queues = MyConstants.TASK_QUEUE_NAME)
    public void receiveTaskDeleteMessage(String message) {
        try {
            Long taskId = Long.parseLong(message);
            taskRepository.deleteById(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    public void deleteCompany(List<Long> companyIds) {


        companyIds.forEach(companyId -> {
            List<Employee> employeeList = employeeRepository.findByCompanyId(companyId);
            employeeList.forEach(employee -> {
                rabbitTemplate.convertAndSend(MyConstants.EMPLOYEE_QUEUE_NAME, employee.getId());
            });

            List<Branch> branchList = branchRepository.findByCompanyId(companyId);
            branchList.forEach(branch -> {
                rabbitTemplate.convertAndSend(MyConstants.BRANCH_QUEUE_NAME, branch.getId());
            });

            rabbitTemplate.convertAndSend(MyConstants.COMPANY_QUEUE_NAME, companyId);
        });
    }

    @RabbitListener(queues = MyConstants.COMPANY_QUEUE_NAME)
    public void receiveCompanyDeleteMessage(Long companyId) {

        try {
            companyRepository.deleteById(companyId);
            System.out.println("company with Id " + companyId + " has been deleted.");
        } catch (Exception e) {
            System.out.println("Failed to delete company with id " + companyId + ": " + e.getMessage());
        }
    }


    public void deleteBranch(List<Long> ids) {
        ids.forEach(branchId -> {
            rabbitTemplate.convertAndSend(MyConstants.BRANCH_QUEUE_NAME, branchId);
        });
    }

    @RabbitListener(queues = MyConstants.BRANCH_QUEUE_NAME)
    public void receiveBranchDeleteMessage(String message) {
        try {
            Long branchId = Long.parseLong(message);
            branchRepository.deleteById(branchId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }
}

