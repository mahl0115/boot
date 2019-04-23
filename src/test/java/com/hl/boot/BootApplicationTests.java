package com.hl.boot;

import com.hl.boot.controller.TestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
    }

    @Test
    public void contextLoads() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test").accept(MediaType.APPLICATION_JSON))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andDo(MockMvcResultHandlers.print())
           .andReturn();
    }

    @Test
    public void test() {
        //log4j2->org.apache.logging.slf4j.Log4jLoggerFactory
        //logback->ch.qos.logback.classic.util.ContextSelectorStaticBinder
//        LoggerContext loggerContext = (LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
//        Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
//        for (LoggerConfig loggerConfig : map.values()) {
//            String key = loggerConfig.getName();
//            if (StringUtils.isBlank(key)) {
//                key = "root";
//            }
//            System.out.println(key);
//        }
    }

}
