package com.example.demo.creational.singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SingletonClassEager {

    private static final SingletonClassEager INSTANCE = new SingletonClassEager();

    public static SingletonClassEager getInstance() {
        return INSTANCE;
    }
}

/* CONS
 * May lead to resource wastage. Because instance of class is created always, whether it is required or not.
 * CPU time is also wasted in creation of instance if it is not required.
 * Exception handling is not possible
 * */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SingletonClassEager1 {

    public static SingletonClassEager1 instance;

    static {
        // exception handling can be here
        instance = new SingletonClassEager1();
    }
}

/* CONS
* May lead to resource wastage. Because instance of class is created always, whether it is required or not.
* CPU time is also wasted in creation of instance if it is not required.
* */
