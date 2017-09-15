package generic_tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Artem_Lodygin on 13-Sep-17.
 */
@Component
public class MyClass {
    @Autowired
    private String autoField;

    void printString() {
        System.out.println(autoField);
    }
}
