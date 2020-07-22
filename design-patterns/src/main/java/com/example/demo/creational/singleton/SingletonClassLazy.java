package com.example.demo.creational.singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SingletonClassLazy {

    private static SingletonClassLazy instance = null;

    public static SingletonClassLazy getInstance() {
        if (instance == null) {
            instance = new SingletonClassLazy();
        }
        return instance;
    }
}

/* CONS
* Every time a condition of null has to be checked.
* instance can’t be accessed directly.
* In multithreaded environment, it may break singleton property.
* */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SingletonClassLazy1 {

    private static SingletonClassLazy1 instance = null;

    synchronized public static SingletonClassLazy1 getInstance() {
        if (instance == null) {
            instance = new SingletonClassLazy1();
        }
        return instance;
    }
}

/* CONS
* getInstance() method is synchronized so it causes slow performance as multiple threads can’t access it simultaneously.
* */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SingletonClassLazy2 {

    private static SingletonClassLazy2 instance = null;

    public static SingletonClassLazy2 getInstance() {
        if (instance == null) {
            synchronized (SingletonClassLazy2.class) {
                if (instance == null) {
                    instance = new SingletonClassLazy2();
                }
            }
        }
        return instance;
    }
}

/* CONS
* First time, it can affect performance.
* */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SingletonClassLazy4 {

    private static class LazyHolder {
        private static final SingletonClassLazy4 instance = new SingletonClassLazy4();
    }

    public static SingletonClassLazy4 getInstance() {
        return LazyHolder.instance;
    }
}

/* CONS
 * First time, it can affect performance.
 * */
