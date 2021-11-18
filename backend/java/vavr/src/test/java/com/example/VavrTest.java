package com.example;

import io.vavr.API;
import io.vavr.Function2;
import io.vavr.Lazy;
import io.vavr.Predicates;
import io.vavr.Tuple1;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.collection.Tree;
import io.vavr.collection.TreeSet;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import static org.junit.jupiter.api.Assertions.*;

class VavrTest {

    @Test
    public void example_1() throws Exception {
        // functions
        Function2<Integer, Integer, Integer> sum = Function2.of((a, b) -> a + b);
        Function2<Integer, Integer, Integer> sumMethodRef = Function2.of(Integer::sum);

        // tuples
        Tuple3<Integer, Integer, Integer> tuple3 = new Tuple3<>(1, 2, 3);
        Integer firstElement = tuple3._1();
        Integer flat = tuple3.apply((a, b, c) -> a + b + c);
    }

    @Test
    public void example_2() throws Exception {
        Option<String> option = Option.of("42")
                .map(String::toUpperCase);

        List<Integer> integers = List.of(Option.of(42), Option.of(54))
                .flatMap(optionn -> optionn);
    }

    @Test
    public void example_3() throws Exception {
        Try.of(() -> new URI(""))
                .recoverWith(URISyntaxException.class, x -> Try.of(() -> new URI("")))
                .map(uri -> uri.toString())
                .filter(i -> true);
    }

    @Test
    public void example_4() throws Exception {
        Supplier<Integer> sup = () -> {
            System.out.println("computing");
            return 42;
        };

        sup.get();
        sup.get();
        sup.get();
        //
        Lazy<Integer> lazy = Lazy.of(() -> {
            System.out.println("computing");
            return 42;
        }).map(i -> i + 1);
        lazy.get();
        lazy.get();
        lazy.get();
    }

    @Test
    public void example_5() throws Exception {
        List<Integer> list = List.of(1, 2, 3);

        List<Integer> dropped = list.drop(2);
        java.util.List<Integer> immutableList = dropped.asJava();
        System.out.println(list);

        System.out.println(list.zipWithIndex());

        list.collect(Collectors.toList());
    }

    @Test
    public void example_6() throws Exception {
        Stream<Integer> s = Stream.iterate(0, i -> i + 1)
                .take(10);

        System.out.println(s);
    }

    @Test
    public void example_7() throws Exception {
            List.of(1,2)
                    .asJava();

        TreeSet<Integer> of = TreeSet.of(1, 2, 3, 4);
    }

    @Test
    public void example_8() throws Exception {
        Object a = "a";
        Match(a).of(
            Case($(instanceOf(String.class)), ""),
            Case($(instanceOf(Integer.class)), ""));
    }
}
