package com.fmolnar.code.kata.greetings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GreetingsTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    Sender sender;

    @InjectMocks
    Sender senderReminder = new SenderReminder();

    @Test
    void shouldSendSmsOnJohnBirthday() {
        when(personRepository.findAll()).thenReturn(List.of(makePerson1()));
        new Greetings(11, 13, personRepository, sender).sendGreetings();
        verify(sender).send(any(), eq(new BirthdayWish(new Prenom("John"), new PhoneNumber("0644444444"),
                new Wish("Happy birthday, dear John"))));

    }

    @Test
    void shouldSendSmsOnTihannyBirthday() {
        when(personRepository.findAll()).thenReturn(List.of(makePerson1(), makePerson2()));
        new Greetings(11, 12, personRepository, sender).sendGreetings();
        verify(sender).send(any(), eq(new BirthdayWish(new Prenom("Tifanny"), new PhoneNumber("0644444445"),
                new Wish("Happy birthday, dear Tifanny"))));
    }

    @Test
    void shouldSendSmsOnPeter2902Birthday() {
        when(personRepository.findAll()).thenReturn(List.of(makePerson3()));
        new Greetings(2, 28, personRepository, sender).sendGreetings();
        verify(sender).send(any(), eq(new BirthdayWish(new Prenom("Peter"), new PhoneNumber("0644444445"),
                new Wish("Happy birthday, dear Peter"))));
    }

    @Test
    void shouldNotSendSms() {
        when(personRepository.findAll()).thenReturn(List.of());
        new Greetings(11, 14, personRepository, sender).sendGreetings();
        verify(sender, never()).send(any(), any());
    }

    private Person makePerson3() {
        return new Person(new Prenom("Peter"), new NomFamille("Gayet"),
                new BirthDay(LocalDate.of(1988, 2, 29)), new PhoneNumber("0644444445"));
    }

    private Person makePerson2() {
        return new Person(new Prenom("Tifanny"), new NomFamille("Gayet"),
                new BirthDay(LocalDate.of(1990, 11, 12)), new PhoneNumber("0644444445"));
    }

    private static Person makePerson1() {
        return new Person(new Prenom("John"), new NomFamille("Doe"),
                new BirthDay(LocalDate.of(1982, 11, 13)), new PhoneNumber("0644444444"));
    }
}
