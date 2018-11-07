package com.container.containerweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ContainerWebApplicationTests {

//    @Test
public static void main(String[] args)  {
        for (int i = 0; i < 100; i++) {
            System.out.println(System.nanoTime());
        }
    }

}
