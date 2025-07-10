package com.parameta.employee.employeeIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.parameta.employee.EmployeeApplication;
import com.parameta.employee.presentation.response.CreateEmployeeResponse;
import com.parameta.employee.presentation.response.PeriodInfo;

@SpringBootTest(classes = EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EmployeeTest {

    @Autowired
    private WebTestClient webTestClient;

    String firstName = "Juan";
    String lastName = "Perez";
    String documentType = "CC";
    String documentNumber = "123456";
    LocalDate birthDate = LocalDate.parse("1990-01-01");
    LocalDate joinDate = LocalDate.parse("2020-01-01");
    String position = "Developer";
    Double salary = 1000.0;
    PeriodInfo tenure = PeriodInfo.from(joinDate, LocalDate.now());
    PeriodInfo age = PeriodInfo.from(birthDate, LocalDate.now());

    @Test
    @DisplayName("Create Employee")
    void shouldCreateACompletelyEmployee() {

        String params = String.format(
                "firstName=%s&lastName=%s&documentType=%s&documentNumber=%s&birthDate=%s&joinDate=%s&position=%s&salary=%s",
                firstName, lastName, documentType, documentNumber, birthDate, joinDate, position,
                salary);

        URI createEmployeeUrl = URI.create("http://localhost:8081/api/v1/employees/create?" + params);

        final ResponseSpec clienteResponse = webTestClient.get()
                .uri(createEmployeeUrl)
                .exchange();

        clienteResponse.expectStatus().isCreated()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.firstName").isEqualTo(firstName)
                .jsonPath("$.lastName").isEqualTo(lastName)
                .jsonPath("$.documentType").isEqualTo(documentType)
                .jsonPath("$.documentNumber").isEqualTo(documentNumber)
                .jsonPath("$.birthDate").isEqualTo(birthDate)
                .jsonPath("$.joinDate").isEqualTo(joinDate)
                .jsonPath("$.position").isEqualTo(position)
                .jsonPath("$.salary").isEqualTo(salary);
    }

    @Test
    @DisplayName("Create Employee response with aditional information")
    void shouldCreateEmployeeAndGetAdditionalInfo() {

        String params = String.format(
                "firstName=%s&lastName=%s&documentType=%s&documentNumber=%s&birthDate=%s&joinDate=%s&position=%s&salary=%s",
                firstName, lastName, documentType, documentNumber, birthDate, joinDate, position,
                salary);

        URI createEmployeeUrl = URI.create("http://localhost:8081/api/v1/employees/create?" + params);

        final ResponseSpec clienteResponse = webTestClient.get()
                .uri(createEmployeeUrl)
                .exchange();

        clienteResponse.expectStatus().isCreated()
                .expectHeader().contentType("application/json")
                .expectBody(CreateEmployeeResponse.class)
                .consumeWith(response -> {
                    CreateEmployeeResponse employeeResponse = response.getResponseBody();
                    assertNotNull(employeeResponse.getId());
                    assertEquals(employeeResponse.getTenure(), tenure);
                    assertEquals(employeeResponse.getAge(), age);
                });
    }

    @Test
    @DisplayName("Create Employee with invalid BirthDate")
    void shouldFailToCreateEmployeeWithInvalidBirthDate() {

        String invalidBirthDate = "01-1990-01";

        String params = String.format(
                "firstName=%s&lastName=%s&documentType=%s&documentNumber=%s&birthDate=%s&joinDate=%s&position=%s&salary=%s",
                firstName, lastName, documentType, documentNumber, invalidBirthDate, joinDate, position,
                salary);

        URI createEmployeeUrl = URI.create("http://localhost:8081/api/v1/employees/create?" + params);

        final ResponseSpec clienteResponse = webTestClient.get()
                .uri(createEmployeeUrl)
                .exchange();

        clienteResponse.expectStatus().isBadRequest()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.birthDate").exists();
    }

    @Test
    @DisplayName("Create Employee with invalid JoinDate")
    void shouldFailToCreateEmployeeWithInvalidJoinDate() {

        String invalidJoinDate = "01-01-2020";

        String params = String.format(
                "firstName=%s&lastName=%s&documentType=%s&documentNumber=%s&birthDate=%s&joinDate=%s&position=%s&salary=%s",
                firstName, lastName, documentType, documentNumber, birthDate, invalidJoinDate, position,
                salary);

        URI createEmployeeUrl = URI.create("http://localhost:8081/api/v1/employees/create?" + params);

        final ResponseSpec clienteResponse = webTestClient.get()
                .uri(createEmployeeUrl)
                .exchange();

        clienteResponse.expectStatus().isBadRequest()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.joinDate").exists();
    }

    @Test
    @DisplayName("Create Employee with less than 18 years old")
    void shouldFailToCreateEmployeeWithLessThan18YearsOld() {

        LocalDate underageBirthDate = LocalDate.now().minusYears(17);
        String params = String.format(
                "firstName=%s&lastName=%s&documentType=%s&documentNumber=%s&birthDate=%s&joinDate=%s&position=%s&salary=%s",
                firstName, lastName, documentType, documentNumber, underageBirthDate, joinDate,
                position, salary);

        URI createEmployeeUrl = URI.create("http://localhost:8081/api/v1/employees/create?" + params);

        final ResponseSpec clienteResponse = webTestClient.get()
                .uri(createEmployeeUrl)
                .exchange();

        clienteResponse.expectStatus().isBadRequest()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.errorCode").isEqualTo("INVALID_ARGUMENT")
                .jsonPath("$.message").isEqualTo("The employee must be at least 18 years old.");
    }
}
