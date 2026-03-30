package com.VitorsosterF.exercicioPraticoAPIREST.service;

import com.VitorsosterF.exercicioPraticoAPIREST.model.Moto;
import org.springframework.stereotype.Service;

@Service
public class MotoValidationService {

    public void validarMoto(Moto moto) {
        new Thread(() -> {
            try {
                System.out.println("Iniciando validação da moto ID: " + moto.getId());

                Thread.sleep(5000); 

                if (moto.getAno() < 2000) {
                    System.out.println("Moto ID " + moto.getId() + " inválida: ano muito antigo");
                } else if (moto.getCilindrada() < 50) {
                    System.out.println("Moto ID " + moto.getId() + " inválida: cilindrada baixa");
                } else {
                    System.out.println("Moto ID " + moto.getId() + " validada com sucesso");
                }

            } catch (InterruptedException e) {
                System.out.println("Erro na validação da moto ID: " + moto.getId());
            }
        }).start();
    }
}