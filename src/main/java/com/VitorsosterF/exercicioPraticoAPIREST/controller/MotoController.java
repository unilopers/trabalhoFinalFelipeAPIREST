package com.VitorsosterF.exercicioPraticoAPIREST.controller;

import com.VitorsosterF.exercicioPraticoAPIREST.model.Moto;
import com.VitorsosterF.exercicioPraticoAPIREST.repository.MotoRepository;
import com.VitorsosterF.exercicioPraticoAPIREST.service.MotoValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moto")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private MotoValidationService motoValidationService;

    @GetMapping
    public List<Moto> listar() {
        return motoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        Optional<Moto> moto = motoRepository.findById(id);
        if (moto.isPresent()) {
            return ResponseEntity.ok(moto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Moto criar(@RequestBody Moto moto) {
        Moto motoSalva = motoRepository.save(moto);

        motoValidationService.validarMoto(motoSalva);

        return motoSalva;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody Moto dadosMoto) {
        return motoRepository.findById(id)
                .map(motoExistente -> {
                    motoExistente.setModelo(dadosMoto.getModelo());
                    motoExistente.setMarca(dadosMoto.getMarca());
                    motoExistente.setAno(dadosMoto.getAno());
                    motoExistente.setPreco(dadosMoto.getPreco());
                    motoExistente.setCilindrada(dadosMoto.getCilindrada());
                    Moto motoAtualizada = motoRepository.save(motoExistente);
                    return ResponseEntity.ok(motoAtualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (motoRepository.existsById(id)) {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
