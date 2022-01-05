package com.academia.ruleta.controllers;

import com.academia.ruleta.entities.Apuesta;
import com.academia.ruleta.entities.Ruleta;
import com.academia.ruleta.repositories.ApuestaRepository;
import com.academia.ruleta.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/apuestas")
public class ApuestaController {

    @Autowired
    private ApuestaRepository apuestaRepository;
    @Autowired
    private RuletaRepository ruletaRepository;

    public ApuestaController(ApuestaRepository apuestaRepository, RuletaRepository ruletaRepository) {
        this.apuestaRepository = apuestaRepository;
        this.ruletaRepository = ruletaRepository;
    }

    /**
     * Metodo para crear una nueva apuesta, dado un id de ruleta y una apuesta
     * @param id Id de la ruleta a la que se le agregara la apuesta
     * @param apuesta Apuesta a agregar
     * @return Un ResponseEntity con el estado de la operacion y la apuesta creada
     */
    @PostMapping("/{id}")
    public ResponseEntity<Apuesta> crearApuesta(@PathVariable Integer id, @RequestBody @Valid Apuesta apuesta) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(id);
        if (ruleta.isPresent() && ruleta.get().isEstado()) {
            ruleta.get().setNumeroApuestas(ruleta.get().getNumeroApuestas() + 1);
            ruleta.get().getApuestas().add(apuesta);
            ruletaRepository.save(ruleta.get());
            apuestaRepository.save(apuesta);
            return new ResponseEntity<>(apuesta, HttpStatus.CREATED);
        } else {
            throw new RuntimeException("La ruleta no existe o esta cerrada");
        }
    }

    /**
     * Metodo para cerrar las apuestas y la ruleta de la que se hayan realizado
     * @param id Id de la ruleta
     * @return Un ResponseEntity con el estado de la operacion y la ruleta cerrada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Set<Apuesta>> cerrarApuestas(@PathVariable Integer id) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(id);
        if (ruleta.isPresent() && ruleta.get().isEstado()) {
            ruleta.get().setEstado(false);
            return new ResponseEntity<>(ruleta.get().getApuestas(), HttpStatus.OK);
        } else {
            throw new RuntimeException("La ruleta no existe o esta cerrada");
        }
    }
}
