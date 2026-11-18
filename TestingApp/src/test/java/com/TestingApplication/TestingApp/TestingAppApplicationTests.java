package com.TestingApplication.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@SpringBootTest
class TestingAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("hello")
	void display(){
		System.out.println("used to display the test with the name that is assigned");
	}

	@Test
	void testDivideTwoNumbers_whenDenominatorIsZero_thenThrowArithmeticException(){

		assertThatThrownBy(() -> divideTwoNumbers(2, 0)).isInstanceOf(ArithmeticException.class);
	}

	int divideTwoNumbers(int a, int b){
		try {
			return a / b;
		} catch (ArithmeticException e){
			log.error("Arithmetic error occurred");
			throw new ArithmeticException("Arithmetic error occurred");
		}
	}

}
