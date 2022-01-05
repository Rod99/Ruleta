package com.academia.ruleta.controllers;


import com.academia.ruleta.entities.Ruleta;
import com.academia.ruleta.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ruletas")
public class RuletaController {

    @Autowired
    private RuletaRepository ruletaRepository;

    public RuletaController(RuletaRepository ruletaRepository) {
        this.ruletaRepository = ruletaRepository;
    }

    /**
     * Metodo que devuelve todas las ruletas existentes
     *
     * @return String con todas las ruletas
     */
    @GetMapping
    public String getRuletas(){
        return ruletaRepository.findAll().toString();
    }

    /**
     * Metodo que permite crear una ruleta
     *
     * @return ResponseEntity con la ruleta creada y el estado de la peticion
     */
    @PostMapping
    public ResponseEntity<Integer> addRuleta(){
        Ruleta ruleta = new Ruleta();
        ruletaRepository.save(ruleta);
        return new ResponseEntity<>(ruleta.getId(), HttpStatus.CREATED);
    }

    /**
     * Metodo que permite abrir una ruleta
     *
     * @param id id de la ruleta
     * @return ResponseEntity con la ruleta abierta y el estado de la peticion,
     * en caso de no existir la ruleta retorna un Optional.empty()
     */
    @PutMapping("/abrirRuleta/{id}")
    public Optional<ResponseEntity<Integer>> abrirRuleta(@PathVariable Integer id){
        Optional<Ruleta> ruleta = ruletaRepository.findById(id);
        if(ruleta.isPresent()){
            ruleta.get().setEstado(true);
            ruletaRepository.save(ruleta.get());
            return Optional.of(new ResponseEntity<>(ruleta.get().getId(), HttpStatus.OK));
        }
        return Optional.empty();
    }
}
