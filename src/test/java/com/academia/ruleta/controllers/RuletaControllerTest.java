package com.academia.ruleta.controllers;

import com.academia.ruleta.entities.Apuesta;
import com.academia.ruleta.entities.Ruleta;
import com.academia.ruleta.repositories.RuletaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RuletaControllerTest {


    private final RuletaRepository ruletaRepository = Mockito.mock(RuletaRepository.class);

    @Autowired
    RuletaController ruletaController = new RuletaController(ruletaRepository);

    @BeforeEach
    void setUp() {

        Set<Apuesta> apuestas = new HashSet<>();
        apuestas.add(new Apuesta(1, 25, null, 1000));
        Ruleta ruleta = new Ruleta(1, false, 5, apuestas);

        List<Ruleta> ruletas = new ArrayList<>();
        ruletas.add(ruleta);

        Mockito.when(ruletaRepository.findAll()).thenReturn(ruletas);
        Mockito.when(ruletaRepository.save(ruleta)).thenReturn(ruleta);
        Mockito.when(ruletaRepository.findById(1)).thenReturn(Optional.of(ruleta));
    }

    @Test
    void getRuletas() {
        String ruletas = ruletaController.getRuletas();
        assertEquals("[Ruleta{id = 1, estado = false, numeroApuestas = 5, apuestas = [Apuesta(id=1, numero=25, color=null, cantidad=1000)]}]",
                ruletas
        );
    }

    @Test
    void addRuleta() {

        ResponseEntity<Integer> expectedResponse = new ResponseEntity<>(new Ruleta().getId(), HttpStatus.CREATED);

        assertEquals(expectedResponse, ruletaController.addRuleta());

    }

    @Test
    void abrirRuleta() {

        Ruleta ruleta = new Ruleta();
        ruleta.setEstado(true);
        Optional<ResponseEntity<Integer>> expectedOptional = Optional.of(new ResponseEntity<>(ruleta.getId(), HttpStatus.OK));
        assertEquals(expectedOptional, ruletaController.abrirRuleta(new Ruleta().getId()));
    }

}