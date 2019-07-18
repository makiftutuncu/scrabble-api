package com.github.makiftutuncu.scrabbleapi;

import com.github.makiftutuncu.scrabbleapi.utilities.FlywayMigrations;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
@WebAppConfiguration
public abstract class SpringTest {
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void buildMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        FlywayMigrations flyway = (FlywayMigrations) wac.getBean("flywayMigrations");
        flyway.migrate();
    }
}
