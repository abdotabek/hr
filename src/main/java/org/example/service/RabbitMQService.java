package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.entity.Branch;
import org.example.entity.Employee;
import org.example.entity.Task;
import org.example.repository.BranchRepository;
import org.example.repository.CompanyRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    RabbitTemplate rabbitTemplate;
    CompanyRepository companyRepository;
    BranchRepository branchRepository;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(Long employeeId) {

        try {
            List<Task> taskList = taskRepository.findByEmployeeId(employeeId);
            if (!taskList.isEmpty()) {
                taskRepository.deleteAll(taskList);
            }
            employeeRepository.deleteById(employeeId);
        } catch (Exception e) {
            System.out.println("Failed to delete employee with id " + employeeId + ": " + e.getMessage());
        }
    }

    public void deleteEmployee(List<Long> employeeIds) {

        final int delayMilliseconds = 10_000;  // интервал 10 секунд

        int[] delayCount = {0};
        employeeIds.forEach(employeeId -> {
            int currentDelay = delayMilliseconds + delayCount[0];
            delayCount[0] += delayMilliseconds;

            scheduler.schedule(() -> {
                rabbitTemplate.convertAndSend(MyConstants.EMPLOYEE_QUEUE_NAME, employeeId);
            }, currentDelay, TimeUnit.MILLISECONDS);
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

        final int delayMilliseconds = 10_000;
        int[] delayCount = {0};

        companyIds.forEach(companyId -> {
            List<Employee> employeeList = employeeRepository.findByCompanyId(companyId);
            employeeList.forEach(employee -> {
                rabbitTemplate.convertAndSend(MyConstants.EMPLOYEE_QUEUE_NAME, employee.getId());
            });

            List<Branch> branchList = branchRepository.findByCompanyId(companyId);
            branchList.forEach(branch -> {
                rabbitTemplate.convertAndSend(MyConstants.BRANCH_QUEUE_NAME, branch.getId());
            });

            int currentDelay = delayMilliseconds + delayCount[0];
            delayCount[0] += delayMilliseconds;
            scheduler.schedule(() -> {

                rabbitTemplate.convertAndSend(MyConstants.COMPANY_QUEUE_NAME, companyId);
            }, currentDelay, TimeUnit.MILLISECONDS);
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

