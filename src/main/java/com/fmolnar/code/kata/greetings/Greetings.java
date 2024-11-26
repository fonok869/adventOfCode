package com.fmolnar.code.kata.greetings;

public class Greetings {
    private final int month;
    private final int today;
    private final PersonRepository personRepository;
    private final Sender send;
    private static final String GREETINGS = "Happy birthday, dear %s";

    public Greetings(int todayMonth, int today, PersonRepository persons, Sender send) {
        this.month = todayMonth;
        this.today = today;
        this.personRepository = persons;
        this.send = send;
    }

    public void sendGreetings() {
        for (Person person : personRepository.findAll()) {
            if (hasBirthDay(person)) {
                send.send(person, makeBirthdayWish(person));
            }
        }
    }

    private boolean hasBirthDay(Person person) {
        return person.birthDay().hasBirthDay(month, today);
    }

    private BirthdayWish makeBirthdayWish(Person person) {

        return new BirthdayWish(makeGreetings(person));
    }

    private static Wish makeGreetings(Person person) {
        return new Wish(GREETINGS.formatted(person.prenom().value()));
    }
}
