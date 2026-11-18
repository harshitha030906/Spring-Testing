package com.TestingApplication.TestingApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
