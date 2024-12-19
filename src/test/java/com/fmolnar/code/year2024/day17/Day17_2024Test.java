package com.fmolnar.code.year2024.day17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day17_2024Test {

    @ParameterizedTest
    @MethodSource("opcodeDivision")
    void shouldMakeOpCodeDisvision(StepComputer stepComputerInit, StepComputer finalStepComputer) {

        Day17_2024 day172024 = new Day17_2024();
        assertThat(stepComputerInit.executeStep()).isEqualTo(finalStepComputer);
    }

    @ParameterizedTest
    @MethodSource("opcodeBitwise")
    void shouldMakeOpCodeBitwise(StepComputer stepComputerInit, StepComputer finalStepComputer) {

        Day17_2024 day172024 = new Day17_2024();
        assertThat(stepComputerInit.executeStep()).isEqualTo(finalStepComputer);
    }

    @Test
    void shouldMakeOpjnzCodebstZSO() {
        String input = "5,0,5,1,5,4";
        String[] inputs = input.split(",");
        StepComputer newS = new StepComputer(10, 0, 0, input, new ArrayList<>());
        StepComputer finalStep = new WrapStepCounter(newS).fin();

        assertThat(finalStep.touOutput()).isEqualTo("0,1,2");
    }

    @Test
    void shouldMakeOpjnzCodebstZSO2() {
        List<String> szamok = new ArrayList<>();
        String input = "0,1,5,4,3,0";
        StepComputer newS = new StepComputer(2024, 0, 0, input, szamok);
        StepComputer finalStep = new WrapStepCounter(newS).fin();

        assertThat(finalStep.touOutput()).isEqualTo("4,2,5,6,7,7,7,7,3,1,0");
        assertThat(finalStep.registerA()).isEqualTo(0);
    }

    @Test
    void shouldMakeOpjnzCodebstTest() {
        String input = "0,1,5,4,3,0";
        List<String> szamok = new ArrayList<>();
        StepComputer newS = new StepComputer(729, 0, 0, input, szamok);
        StepComputer finalStep = new WrapStepCounter(newS).fin();

        assertThat(finalStep.touOutput()).isEqualTo("4,6,3,5,6,3,5,2,1,0");
    }
    

    @Test
    void shouldMakeExerciceDebug() {
        String input = "2,4,1,5,7,5,0,3,1,6,4,3,5,5";
        List<String> szamok = new ArrayList<>();
        StepComputer newS = new StepComputer(59590048l, 0, 0, input, szamok);
        StepComputer finalStep = new WrapStepCounter(newS).fin();

        assertThat(finalStep.touOutput()).isEqualTo("6");
    }

    @Test
    void shouldMakeExercice1() {
        String input = "2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0";
        List<String> szamok = new ArrayList<>();
        StepComputer newS = new StepComputer(59590048l, 0, 0, input, szamok);
        StepComputer finalStep = new WrapStepCounter(newS).fin();

        assertThat(finalStep.touOutput()).isEqualTo("6,5,7,4,5,7,3,1,0");
    }

    @ParameterizedTest
    @MethodSource("opcodejnzbxc")
    void shouldMakeOpjnzCodebst(StepComputer stepComputerInit, StepComputer finalStepComputer) {

        assertThat(stepComputerInit.executeStep()).isEqualTo(finalStepComputer);
    }

    private static Stream<Arguments> opcodejnzbxc() {
        return Stream.of(
//                Arguments.of(new StepComputer(1, 0, 9, "3,6", List.of()),
//                        new StepComputer(1, 0, 9, "", List.of())),
//                Arguments.of(new StepComputer(0, 0, 9, "3,6", List.of()),
//                        new StepComputer(0, 0, 9, "6", List.of())),
                Arguments.of(new StepComputer(0, 2024, 43690, "4,0", List.of()),
                        new StepComputer(0, 44354, 43690, "", List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("opcodebst")
    void shouldMakeOpCodebst(StepComputer stepComputerInit, StepComputer finalStepComputer) {

        Day17_2024 day172024 = new Day17_2024();
        assertThat(stepComputerInit.executeStep()).isEqualTo(finalStepComputer);
    }

    private static Stream<Arguments> opcodebst() {
        return Stream.of(
                Arguments.of(new StepComputer(0, 0, 9, "2,6", List.of()),
                        new StepComputer(0, 1, 9, "", List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("opcodeout")
    void shouldMakeOpCoOut(StepComputer stepComputerInit, StepComputer finalStepComputer) {
        Day17_2024 day172024 = new Day17_2024();
        assertThat(stepComputerInit.executeStep()).isEqualTo(finalStepComputer);
    }

    private static Stream<Arguments> opcodeout() {
        return Stream.of(
                Arguments.of(new StepComputer(10, 0, 0, "5,0", new ArrayList<>()),
                        new StepComputer(10, 0, 0, "", List.of("0")))
        );
    }

    private static Stream<Arguments> opcodeBitwise() {
        return Stream.of(
                Arguments.of(new StepComputer(0, 29, 0, "1,7", List.of()),
                        new StepComputer(0, 26, 0, "", List.of()))
        );
    }

    private static Stream<Arguments> opcodeDivision() {
        return Stream.of(
                Arguments.of(new StepComputer(2, 0, 0, "0,1", List.of()),
                        new StepComputer(1, 0, 0, "", List.of())),
                Arguments.of(new StepComputer(4, 2, 0, "0,5", List.of()),
                        new StepComputer(1, 2, 0, "", List.of()))
        );
    }
}