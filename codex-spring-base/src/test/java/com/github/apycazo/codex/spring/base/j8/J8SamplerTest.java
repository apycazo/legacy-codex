package com.github.apycazo.codex.spring.base.j8;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class J8SamplerTest
{
    @Test
    public void optionalUsage ()
    {
        String str = "string";
        String emptyStr = "";
        String nullStr = null;

        String result;
        result = Optional.ofNullable(nullStr).orElse("str is null");
        assertEquals("str is null", result);

        result = Optional.ofNullable(emptyStr).orElse("str is null");
        assertEquals("", result);

        result = Optional.ofNullable(str).orElse("str is null");
        assertEquals(str, result);
    }

    @Test
    public void predicateUsage()
    {
        Predicate<Integer> isEven = (x) -> x % 2 == 0;

        assertTrue(isEven.test(2));
        assertFalse(isEven.test(1));
    }

    @Test
    public void functionUsage()
    {
        Function<String, Integer> sizeOf = (x) -> x == null ? 0 : x.trim().length();

        assertEquals(5, sizeOf.apply("12345").intValue());
    }

    @Test
    public void consumerUsage()
    {

        List<String> numberList = new LinkedList<>(Arrays.asList("1,2,3,4".split(",")));
        Consumer<List<String>> consumer = (x) -> x.add("5");

        assertEquals(4, numberList.size());
        consumer.accept(numberList);
        assertEquals(5, numberList.size());
    }

    @Test
    public void supplierUsage ()
    {
        Supplier<Integer> get5 = () -> 5;
        assertEquals(5, get5.get().intValue());
    }

    @Test
    public void doubleColonUsage ()
    {
        J8Sampler.Counter counter = new J8Sampler.Counter();
        // Counter implements interface 'CountCharacters' which we want to use
        J8Sampler.CountCharacters c = counter::processValue;

        assertEquals(5, c.processValue("12345").intValue());

        Function<Integer, Integer> addOne = J8Sampler::inc;
        assertEquals(5, addOne.apply(4).intValue());
    }

    @Test
    public void valueSorting ()
    {
        List<J8Sampler.NumberHolder> list = new LinkedList<>();
        list.add(J8Sampler.NumberHolder.builder().value(3).build());
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());

        list.sort(Comparator.comparing(J8Sampler.NumberHolder::getValue).reversed());

        assertEquals(3, list.size());

        assertEquals(3, list.get(0).getValue().intValue());
        assertEquals(2, list.get(1).getValue().intValue());
        assertEquals(1, list.get(2).getValue().intValue());
    }

    @Test
    public void listToMap ()
    {
        List<J8Sampler.NumberHolder> list = new LinkedList<>();
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());
        list.add(J8Sampler.NumberHolder.builder().value(3).build());

        Map<Integer, J8Sampler.NumberHolder> map = list.stream()
                .collect(Collectors.toMap(J8Sampler.NumberHolder::getValue, x -> x));

        assertTrue(map.containsKey(1));
        assertEquals(1, map.get(1).getValue().intValue());
        assertTrue(map.containsKey(2));
        assertEquals(2, map.get(2).getValue().intValue());
        assertTrue(map.containsKey(1));
        assertEquals(3, map.get(3).getValue().intValue());
    }

    @Test
    public void filterMapCollect ()
    {
        // Convert this list to List<Integer> of odd values
        List<J8Sampler.NumberHolder> list = new LinkedList<>();
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());
        list.add(J8Sampler.NumberHolder.builder().value(3).build());

        List<Integer> oddValues = list.stream()
                .filter(value -> value.getValue() % 2 != 0)
                .map(J8Sampler.NumberHolder::getValue)
                .collect(Collectors.toList());

        assertEquals(2, oddValues.size());
        assertEquals(1, oddValues.get(0).intValue());
        assertEquals(3, oddValues.get(1).intValue());
    }

    @Test
    public void mapReduceToFindLowerValue ()
    {
        List<J8Sampler.NumberHolder> list = new LinkedList<>();
        list.add(J8Sampler.NumberHolder.builder().value(3).build());
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());

        Integer lowestValue = list.stream()
                .map(J8Sampler.NumberHolder::getValue)
                .reduce(Integer.MAX_VALUE, (a,b) -> a < b ? a : b);

        assertEquals(1, lowestValue.intValue());
    }

    @Test
    public void groupBySample ()
    {
        List<J8Sampler.NumberHolder> list = new LinkedList<>();
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());
        list.add(J8Sampler.NumberHolder.builder().value(1).build());
        list.add(J8Sampler.NumberHolder.builder().value(2).build());

        // Groups those with the same value
        Map<Integer, List<J8Sampler.NumberHolder>> groups = list.stream()
                .collect(Collectors.groupingBy(J8Sampler.NumberHolder::getValue));

        assertTrue(groups.containsKey(1));
        assertEquals(2, groups.get(1).size());
        assertEquals(1, groups.get(1).get(0).getValue().intValue());
        assertEquals(1, groups.get(1).get(1).getValue().intValue());

        assertTrue(groups.containsKey(2));
        assertEquals(2, groups.get(2).size());
        assertEquals(2, groups.get(2).get(0).getValue().intValue());
        assertEquals(2, groups.get(2).get(1).getValue().intValue());
    }
}