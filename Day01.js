"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
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
    'L82'
];
function initLeft() {
    let value = new Array();
    for (let i = 0; i < 100; i++) {
        let dic = {
            i: (i === 99 ? 0 : (i + 1))
        };
        value.push(dic);
    }
    return value;
}
function initRight() {
    let value;
    for (let i = 99; 0 < i; i--) {
        let dic = {
            i: (i === 0 ? 99 : (i - 1))
        };
        value.push(dic);
    }
    return value;
}
function helloWorld() {
    let counterZeroPoint = 0;
    let LEFTS = initLeft();
    let RIGHTS = initRight();
    input.forEach((value) => {
        if (value.charAt(0) === 'L') {
            console.log(LEFTS[0]);
        }
        console.log("Itt vagyok");
    });
    console.log('Benne vagyok a hello Worldben');
}
helloWorld();
