package generic_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * Created by Artem_Lodygin on 15-Sep-17.
 */
public class TestJacksonSerialization {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerializeWithParent() throws Exception {
        final JacksonTestClass test = new JacksonTestClass();
        test.childField = 10;
        test.setField(20);

        final String serialized = objectMapper.writerFor(JacksonTestClass.class).writeValueAsString(test);
        System.out.println(serialized);

    }
}
