package com.jpsolanoc.clientcore.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CustomerTest {
    @Test
    void testCustomerAttributes() {
        // Arrange: Crear un cliente
        Customer customer = new Customer();
        customer.setPassword("securepassword");
        customer.setState(true);

        // Act & Assert: Verificar los atributos de customer
        assertEquals("securepassword", customer.getPassword());
        assertTrue(customer.getState());
    }

    @Test
    void testCustomerInheritance() {
        // Arrange: Crear un cliente y establecer atributos heredados de Person
        Customer customer = new Customer();
        customer.setName("John Solano");
        customer.setGender("Masculino");
        customer.setAge(37);
        customer.setDni("1234567890");
        customer.setAddress("Loja, Ecuador");
        customer.setPhone("0987654321");

        // Act & Assert: Verificar que los atributos de Person se hayan establecido correctamente
        assertEquals("John Solano", customer.getName());
        assertEquals("Masculino", customer.getGender());
        assertEquals(37, customer.getAge());
        assertEquals("1234567890", customer.getDni());
        assertEquals("Loja, Ecuador", customer.getAddress());
        assertEquals("0987654321", customer.getPhone());
    }
}
