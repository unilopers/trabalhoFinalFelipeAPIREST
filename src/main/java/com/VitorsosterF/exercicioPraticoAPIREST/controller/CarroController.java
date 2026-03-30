package com.VitorsosterF.exercicioPraticoAPIREST.controller;

import com.VitorsosterF.exercicioPraticoAPIREST.model.Carro;
import com.VitorsosterF.exercicioPraticoAPIREST.queue.CarroIntegracaoQueue;
import com.VitorsosterF.exercicioPraticoAPIREST.repository.CarroRepository;
import com.VitorsosterF.exercicioPraticoAPIREST.service.CarroThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carro")
public class CarroController {

    @Autowired
    private final CarroRepository carroRepository;

    @Autowired
    private CarroIntegracaoQueue integracaoQueue;

    public CarroController(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @PostMapping
    public Carro criarCarro(@RequestBody Carro carro) {
        Carro carroSalvo = carroRepository.save(carro);
        carroThreadService.processarEmBackground(carroSalvo);
        return carroSalvo;
    }

    @GetMapping
    public List<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Carro buscarPorId(@PathVariable Integer id) {
        return carroRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Carro atualizarCarro(@PathVariable Integer id, @RequestBody Carro novoCarro) {
        return carroRepository.findById(id).map(carro -> {
            carro.setMarca(novoCarro.getMarca());
            carro.setModelo(novoCarro.getModelo());
            carro.setAno(novoCarro.getAno());
            return carroRepository.save(carro);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public boolean deletar(@PathVariable Integer id) {
        if (carroRepository.existsById(id)) {
            carroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}