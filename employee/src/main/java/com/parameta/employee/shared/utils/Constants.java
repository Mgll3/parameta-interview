package com.parameta.employee.shared.utils;

public final class Constants {

    public static final String EXAMPLE_NOT_FOUND = "Employee not found";
    public static final String SOAP_EMPLOYEE_SERVICE_URL = "http://localhost:8080/ws";
    public static final String SOAP_INVALID_RESPONSE = "SOAP service returned invalid response or missing ID";
    public static final String ERROR_EMPLOYEE_MIN_AGE = "The employee must be at least 18 years old.";
    public static final String SOAP_CLIENT_PACKAGE = "com.parameta.employee.infraestructure.soap.client";
    public static final String ERROR_SOAP_FAULT_CLIENT = "SOAP_FAULT_CLIENT_ERROR";
    public static final String ERROR_INVALID_ARGUMENT = "INVALID_ARGUMENT";
    public static final String ERROR_INTERNAL_SERVER = "INTERNAL_SERVER_ERROR";
    public static final String LOG_WEB_EXCHANGE_BIND_EXCEPTION = "WebExchangeBindException: {}";
    public static final String LOG_VALIDATION_ERROR = "Validation error: {}";

    private Constants() {
        // Private constructor to prevent instantiation

    }
}
