package generic_tests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Artem_Lodygin on 13-Sep-17.
 */
public class SpringAutowireTest {
    private static ApplicationContext context;

    @BeforeClass
    public static void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(MyClass.class, MockProvider.class);
    }

    @Test
    public void testMockContext() throws Exception {
        final MyClass bean = context.getBean(MyClass.class);
        bean.printString();
    }

    @Configuration
    static class MockProvider {
        @Bean
        String getAutoField() {
            return "Spring-injected mock dependency!";
        }
    }
}
