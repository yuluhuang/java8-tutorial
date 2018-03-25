package com.company;

import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author ylh
 * @create 2018-03-24
 */
public class TestLambda {

    class Em {
        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Test
    public void test() {
        List<Em> ems = Arrays.asList(
                new Em(),
                new Em()
        );

        ems.stream()
                .filter((e) -> e.getName() == "1")
                .limit(2)
                .forEach(System.out::println);

        // 并行
        ems.parallelStream()
                .filter((e) -> e.getName() == "1")
                .limit(2)
                .forEach(System.out::println);


        ems.stream()
                .map(Em::getName)
                .forEach(System.out::print);
    }


    @Test
    public void test1() {
        int num = 0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Lambda" + num);
            }
        };
        r.run();
        System.out.println("--------------------------------");

        Runnable re = () -> System.out.println("Hello Lambda" + num);
        re.run();
    }


    @Test
    public void test2() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("1234567890");
    }


    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式借接口");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4() {
        Comparator<Integer> com = (Integer x, Integer y) -> Integer.compare(x, y);
        Comparator<Integer> com2 = (x, y) -> Integer.compare(x, y);
    }

    @Test
    public void test5() {

    }


    @Test
    public void test6() {
        Integer num = operation(100, (x) -> x * x);
    }

    public Integer operation(Integer num, MyFun mf) {
        return mf.getValue(num);
    }


    /**
     * Function：接受一个参数，返回一个参数。
     * Consumer：接受一个参数，不返回参数。
     * Predicate：用于测试是否符合条件。
     */
    @Test
    public void test7() {
        String name = "";
        String name1 = "12345";
        System.out.println(validInput(name, inputStr -> inputStr.isEmpty() ? "名字不能为空" : inputStr));
        System.out.println(validInput(name1, inputStr -> inputStr.length() > 3 ? "名字过长" : inputStr));

    }

    public String validInput(String name, Function<String, String> function) {
        return function.apply(name);
    }


    @Test
    public void test8() {
        String name = "";
        String name1 = "12345";
        validInput2(name, inputStr -> System.out.println(inputStr.isEmpty() ? "名字不能为空" : "111"));
        validInput2(name1, inputStr -> System.out.println(inputStr.length() > 3 ? "名字过长" : "222"));

    }

    public void validInput2(String name, Consumer<String> function) {
        function.accept(name);
    }


    public void test9() {
        String name = "";
        String name1 = "12345";
        System.out.println(validInput3(name, inputStr -> inputStr.isEmpty()));
        System.out.println(validInput3(name1, inputStr -> inputStr.length() > 3));

    }

    public boolean validInput3(String name, Predicate<String> function) {
        return function.test(name);
    }


    @Test
    public void test10() {
        File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isHidden();
            }
        });

        File[] hiddenFiles2 = new File(".").listFiles(File::isHidden);


    }

    List<Em> ems = Arrays.asList(
            new Em(),
            new Em()
    );

    @Test
    public void test11() {
        Collections.sort(ems, (e1, e2) -> {
            return Integer.compare(e1.getAge(), e2.getAge());
        });
        for (Em em : ems) {
            System.out.println(em);
        }
    }


    @Test
    public void test12() {
        Integer trimStr = strHandler(123, str -> str + 1);
        System.out.println(trimStr);
    }

    public Integer strHandler(Integer str, MyFun mf) {
        return mf.getValue(str);
    }


    @Test
    public void test13() {
        op(100L, 200L, (x, y) -> x + y);
    }

    public void op(Long l1, Long l2, MyFunction<Long, Long> mf) {
        System.out.println(mf.getValue(l1, l2));
    }


    @Test
    public void test14() {
        //简单的,只有一行
        Function<Integer, String> function1 = (x) -> "test result: " + x;

        //标准的,有花括号, return, 分号.
        Function<String, String> function2 = (x) -> {
            return "after function1";
        };
//        System.out.println(function1.apply(6));
        System.out.println(function1.andThen(function2).apply(6));
    }

    /**
     * Consumer<T> : 消费型接口
     * void accept(T t)
     *
     * Supplier<T> : 供给型接口
     * T get()
     * Function<T, R> : 函数型接口
     * R apply(T, t)
     * Predicate<T> : 断言型接口
     * boolean test(T t)
     */
    @Test
    public void test15() {
        happy(1000, m -> System.out.println(m));
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    @Test
    public void test16() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = supplier.get();
            list.add(n);
        }
        return list;
    }

    @Test
    public void test17() {
        String newStr = strHandler("sdasdfsa", (str) -> str.trim());
    }

    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }


}
