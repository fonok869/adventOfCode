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


function initLeft(): Array<Record<number, number>> {
    let value = new Array<Record<number, number>>();
    for (let i = 0; i < 100; i++) {
        let dic = {
            i: (i === 99 ? 0 : (i + 1))
        } as Record<number, number>;

        value.push(dic)
    }
    return value;
}

function initRight(): Record<number, number>[] {
    let value: Record<number, number>[];
    for (let i = 99; 0 < i; i--) {
        let dic = {
            i: (i === 0 ? 99 : (i - 1))
        } as Record<number, number>;
        value.push(dic);
    }
    return value;
}

function helloWorld(): void {
    let counterZeroPoint = 0;
    let LEFTS: Array<Record<number, number>> = initLeft();
    let RIGHTS: Array<Record<number, number>> = initRight();
    input.forEach((value) => {
        if (value.charAt(0) === 'L') {
            console.log(LEFTS[0])
        }
        console.log("Itt vagyok")


    })
    console.log('Benne vagyok a hello Worldben');
}

helloWorld();