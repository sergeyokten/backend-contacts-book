package oktenweb.backendcontactsbook.utils;

import java.lang.reflect.Method;

public class Log {
    public void print(Class clazz, Method method, String msg) {
        System.out.println(msg + " CLASS - " + clazz.getName() + " METHOD - " + method.getName()  );

    }
}
