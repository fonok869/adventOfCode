export type Direction = "L" | "R";

const input = [
    'L68',
    'L30',
    'R48',
    'L5',
    'R60',
    'L55',
    'L1',
    'L99',
    'R14',
    'L82']


function initRight(): Record<number, number>[] {
    let value: Record<number, number>[] = [];
    for (let number = 0; number < 100; number++) {
        if (number === 99) {
            value.push({99: 0})
        }
        value.push({[number]: number + 1})
    }
    return value;
}


function initLeft(): Record<number, number>[] {
    let value: Record<number, number>[] = [];
    for (let number = 99; 0 < number; number--) {
        if (number === 0) {
            value.push({0: 99})
        }
        value.push({[number]: number - 1})
    }
    return value;
}

function helloWorld(): void {


    // Create a single Record object
    let value: Record<number, number> = {};

// Populate with key:value pairs
    for (let i = 1; i <= 99; i++) {
        value[i] = i + 1;
    }

    // Get the value of key 50 - much simpler!
    console.log(value[50]); // Output: 51
    let counterZeroPoint = 0;
    let LEFTS: Record<number, number>[] = initLeft();
    let RIGHTS: Record<number, number>[] = initRight();
    let actualIndex = 50;
    input.forEach((value) => {
        if (value.charAt(0) === 'L') {
            const steps = Number(value.substring(1));
            for (let index = 0; index < steps; index++) {
                const actualValue = LEFTS[actualIndex];
                actualIndex = actualValue;
            }

        } else if (value.charAt(0) === 'R') {
            const steps = Number(value.substring(1));

        }
        console.log("Itt vagyok")


    })
    console.log('Benne vagyok a hello Worldben');
}

helloWorld();