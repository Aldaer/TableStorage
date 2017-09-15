package generic_tests;

/**
 * Created by Artem_Lodygin on 15-Sep-17.
 */
public class JacksonTestClass extends JacksonParent{
    public int childField;
}

class JacksonParent {
    public int getPrivateField() {
        return parentField;
    }

    public void setField(int value) {
        this.parentField = value;
    }

    private int parentField;
}

