package com.fmolnar.code.year2024.day17;

import java.util.ArrayList;
import java.util.List;

public class Day17_2024 {


}

record StepComputer(long registerA, long registerB, long registerC, String program, List<String> ouput) {

    String touOutput() {
        return String.join(",", ouput);
    }

    public StepComputer executeStep() {
        String[] instructions = program().split(",");

        StepComputer step = this;
        long operandValue = step.getOperandValue(instructions[1]);
        //System.out.println("Operand : " + operandValue);
        double diviseur = Math.pow(2.0, operandValue);
        if ("0".equals(instructions[0])) {
            double result = step.registerA() / diviseur;
            return new StepComputer((long) result, step.registerB(), step.registerC(), "", step.ouput());
        } else if ("1".equals(instructions[0])) {
            Long result = step.registerB() ^ Long.valueOf(instructions[1]);
            return new StepComputer(step.registerA(), result, step.registerC(), "", step.ouput());
        } else if ("2".equals(instructions[0])) {
            Long result = operandValue % 8l;
            return new StepComputer(step.registerA(), result, step.registerC(), "", step.ouput());
        } else if ("3".equals(instructions[0])) {
            if (step.registerA() == 0l) {
                return new StepComputer(step.registerA(), step.registerB(), step.registerC(), "", step.ouput());
            }
            return new StepComputer(step.registerA(), step.registerB(), step.registerC(), instructions[1], step.ouput());
        } else if ("4".equals(instructions[0])) {
            Long result = step.registerB() ^ step.registerC();
            return new StepComputer(step.registerA(), result, step.registerC(), "", step.ouput());
        } else if ("5".equals(instructions[0])) {
            String result = String.valueOf(operandValue % 8);
            List<String> ouputs = new ArrayList<>(step.ouput());
            ouputs.add(result);
            return new StepComputer(step.registerA(), step.registerB(), step.registerC(), "", ouputs);
        } else if ("6".equals(instructions[0])) {
            double result = step.registerA() / diviseur;
            System.out.println("result: " + result);
            return new StepComputer(step.registerA(), (long) result, step.registerC(), "", step.ouput());
        } else if ("7".equals(instructions[0])) {
            double result = step.registerA() / diviseur;
            System.out.println("result: " + result);
            return new StepComputer(step.registerA(), step.registerB(), (long) result, "", step.ouput());
        }


        return null;
    }

    public long getOperandValue(String instruction) {
        return switch (instruction) {
            case "0" -> 0l;
            case "1" -> 1l;
            case "2" -> 2l;
            case "3" -> 3l;
            case "4" -> registerA;
            case "5" -> registerB;
            case "6" -> registerC;
            case "7" -> 7l;
            default -> throw new IllegalStateException("Unexpected value: " + instruction);
        };
    }
}

record WrapStepCounter(StepComputer stepComputer) {
    StepComputer fin() {
        StepComputer actualStep = new StepComputer(stepComputer.registerA(), stepComputer.registerB(), stepComputer.registerC(), stepComputer.program(), new ArrayList<>());

        String instructions = stepComputer.program();
        String[] instruction = instructions.split(",");
        for (int index = 0; index < instruction.length; index = index + 2) {
            if (instruction.length <= index + 1) {
                break;
            }
            String inputActuel = instruction[index] + "," + instruction[index + 1];
            System.out.println(inputActuel);
            System.out.println("Before: " + actualStep.toString());
            StepComputer step = new StepComputer(actualStep.registerA(), actualStep.registerB(), actualStep.registerC(), inputActuel, actualStep.ouput());
            actualStep = step.executeStep();
            System.out.println("After: " + actualStep.toString());
            if (!"".equals(actualStep.program())) {
                index = Integer.valueOf(actualStep.program()) * 2 - 2;
            }
        }
        return actualStep;

    }
}
