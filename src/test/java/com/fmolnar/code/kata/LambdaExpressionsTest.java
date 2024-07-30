package com.fmolnar.code.kata;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LambdaExpressionsTest {

    private static final Student UNKNOWN_STUDENT = null;

    @Test
    void testLambdaExpressions() {
        LambaExp codeBloc = (s) -> System.out.printf(s);
    }

    @Test
    void testOptional() {
        Student student = new Student(new FirstName("Alma"), new Age(15));
        Optional<Student> studentOptional = Optional.ofNullable(student);
        Optional<Student> studentOptNull = Optional.ofNullable(null);

        if (student == null) {
            System.out.println("Null");
        } else {
            System.out.println(student);
        }


        if (studentOptional.isPresent()) {
            System.out.println("Student: " + studentOptional.get());
        }

        studentOptional.ifPresent(System.out::println);

        Student student1 = getStudent(null);

        System.out.println("Get Student Classic: " + student1);
        Student student2 = getStudentFromOptional(Optional.empty());
        Student student3 = getStudentFromOptional(studentOptional);
        System.out.println("getStudentFromOptional vrai optional: " + student3);
        System.out.println("getStudentFromOptional vrai optional null vagy empty:" + student2);

        Student student4 = getStudentFromOptionalGenerated(studentOptional);

        System.out.println("Student Optional vrai Student:  " + student4);


    }

    @Test
    void testStreams() {
        List<Student> lists = getAllAStudents();

        lists.stream().filter(s -> s.age().age > 18).sorted((s1, s2) -> s2.age().age - s1.age().age).limit(2).forEach(System.out::println);
    }

    @Test
    void testMapConvert() {
        getAllAStudents().stream().map(s -> s.firstName().value()).sorted().forEach(System.out::println);
    }

    @Test
    void testFlatMap() {
        Team football = new Team(new SportType("football"), getAllAStudents());
        Team basketball = new Team(new SportType("basketball"), getAllBStudents());

        List<Team> teams = List.of(football, basketball);

        List<Student> allstudents = teams.stream().flatMap(team -> team.students.stream()).collect(Collectors.toList());
        allstudents.forEach(System.out::println);
    }

    @Test
    void testFilteringDistinctSortedSkipLimit() {
        Team group1 = new Team(new SportType("Fishing"), getAllAStudents());
        Team group2 = new Team(new SportType("Hunting"), getAllBStudents());

        List<Team> dreamTeam = List.of(group1, group2);

        List<Student> allStudents = Stream.of(group1, group2).map(Team::students).flatMap(students -> students.stream()).toList();

        List<Integer> topTwoAges = allStudents.stream().map(s -> s.age().age)
                .filter(a -> 18 < a)
                .distinct()
                .sorted((age1, age2) -> age2 - age1)
                .skip(2)
                .limit(2)
                .toList();

        System.out.println(topTwoAges);
    }

    @Test
    void testMinMax() {
        getAllAStudents().stream().map(s -> s.age().age).min(Comparator.comparingInt(a -> a)).ifPresent(s -> System.out.println("Min value is: " + s));
        getAllAStudents().stream().map(s -> s.age().age).max(Comparator.comparingInt(a -> a)).ifPresent(s -> System.out.println("Max value is: " + s));
    }

    @Test
    void testFindAnyFindFirst() {
        List<Student> students = new ArrayList<>();
        getAllAStudents().stream().filter(s -> s.age().age == 18).findFirst().map(s -> s.firstName().value()).ifPresent(s -> System.out.println("Elso 18 eves: " + s));
        getAllAStudents().stream().filter(s -> s.age().age == 18).findAny().map(s -> s.firstName().value()).ifPresent(s -> System.out.println("findAny 18 eves: " + s));
    }

    @Test
    void testCount(){
        System.out.println("Baskedtball count: " + getAllAStudents().stream().count());
    }

    @Test
    void allMatchNoneMatchEtc(){
        System.out.println("AllMatch (45 evnel fiatalabbak): " + getAllAStudents().stream().allMatch(s->s.age().age<45));
        System.out.println("AnyMatch Bob: " + getAllAStudents().stream().anyMatch(s->s.firstName().firstName.equals("Bob")));
        System.out.println("NoneMatch Bob: " + getAllAStudents().stream().noneMatch(s->s.firstName().firstName.equals("Bob")));
    }

    @Test
    void testTerminationOperation(){
        Map<String, Student> students = getAllAStudents().stream().collect(Collectors.toMap(s-> s.firstName.firstName, Function.identity()));
        System.out.println(students);
    }

    @Test
    void testSummarizing(){
        IntSummaryStatistics summaryStatistics = getAllAStudents().stream().map(s->s.age().age).collect(Collectors.summarizingInt(a->a));

        System.out.println(summaryStatistics);
    }

    @Test
    void concatFields(){
        System.out.println(getAllAStudents().stream().map(s->s.firstName().firstName).sorted(Comparator.reverseOrder()).collect(Collectors.joining(",","Starting: ", " Ending")));
    }

    record Team(SportType name, List<Student> students) {

    }

    public record SportType(String sport) {
        public SportType {
            assertThat(sport).isNotBlank();
        }
    }

    private static List<Student> getAllBStudents() {
        List<Student> lists = List.of(new Student(new FirstName("Bob1"), new Age(18)),
                new Student(new FirstName("Ted1"), new Age(17)),
                new Student(new FirstName("Zeka1"), new Age(19)),
                new Student(new FirstName("Johnny1"), new Age(20)),
                new Student(new FirstName("Eric1"), new Age(21)));
        return lists;
    }

    private static List<Student> getAllAStudents() {
        List<Student> lists = List.of(new Student(new FirstName("Bob"), new Age(18)),
                new Student(new FirstName("Ted"), new Age(17)),
                new Student(new FirstName("Zeka"), new Age(19)),
                new Student(new FirstName("Johnny"), new Age(20)),
                new Student(new FirstName("Eric"), new Age(21)));
        return lists;
    }


    Student getStudentFromOptional(Optional<Student> student) {
        return student.orElse(UNKNOWN_STUDENT);
    }

    Student getStudentFromOptionalGenerated(Optional<Student> student) {
        return student.orElseGet(() -> UNKNOWN_STUDENT);
    }


    Student getStudent(Student student) {
        return student == null ? UNKNOWN_STUDENT : student;
    }


    @FunctionalInterface
    interface LambaExp {
        void someMethod(String s);
    }

    record Student(FirstName firstName, Age age) {

    }

    record Age(Integer age) {
        public Age {
            assertThat(age).isNotNegative();
        }
    }

    record FirstName(String firstName) {
        public FirstName {
            assertThat(firstName).isNotBlank();
        }

        String value() {
            return firstName;
        }
    }
}
