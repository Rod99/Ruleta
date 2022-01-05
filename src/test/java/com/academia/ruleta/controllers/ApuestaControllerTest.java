package com.academia.ruleta.controllers;

import com.academia.ruleta.entities.Apuesta;
import com.academia.ruleta.entities.Ruleta;
import com.academia.ruleta.repositories.ApuestaRepository;
import com.academia.ruleta.repositories.RuletaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ApuestaControllerTest {

    private final ApuestaRepository apuestaRepository = Mockito.mock(ApuestaRepository.class);
    private final RuletaRepository ruletaRepository = Mockito.mock(RuletaRepository.class);

    @Autowired
    ApuestaController apuestaController = new ApuestaController(apuestaRepository, ruletaRepository);

    @BeforeEach
    void setUp() {
        Set<Apuesta> apuestas = new HashSet<>();
        apuestas.add(new Apuesta(1, 25, null, 1000));
        Ruleta ruleta = new Ruleta(1, true, 5, apuestas);

        Mockito.when(ruletaRepository.findById(1)).thenReturn(Optional.of(ruleta));
    }

    @Test
    void crearApuesta() {
        Apuesta apuesta = new Apuesta(1, 25, null, 1000);
        ResponseEntity<Apuesta> expectedApuesta = new ResponseEntity<>(apuesta, HttpStatus.CREATED);
        assertEquals(expectedApuesta, apuestaController.crearApuesta(1, apuesta));
    }

    @Test
    void cerrarApuestas() {
        Set<Apuesta> apuestas = new HashSet<>();
        apuestas.add(new Apuesta(1, 25, null, 1000));
        Ruleta ruleta = new Ruleta(1, true, 5, apuestas);

        ResponseEntity<Set<Apuesta>> expectedApuestas = new ResponseEntity<>(ruleta.getApuestas(), HttpStatus.OK);
        assertEquals(expectedApuestas, apuestaController.cerrarApuestas(1));
    }
}