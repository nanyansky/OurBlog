package com.digging;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OurBlogApplicationTests {

    @Test
    void contextLoads() {
        String key = "1_0";
        String tmpKey = ((String)key).split("_")[1];
        System.out.println(tmpKey);
    }

}
