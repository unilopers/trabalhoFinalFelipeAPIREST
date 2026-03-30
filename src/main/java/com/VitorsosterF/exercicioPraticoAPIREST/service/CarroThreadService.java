package com.VitorsosterF.exercicioPraticoAPIREST.service;

import com.VitorsosterF.exercicioPraticoAPIREST.model.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarroThreadService {

    @Autowired
    private CarroProcessamentoService carroProcessamentoService;

    public void processarEmBackground(Carro carro) {
        Thread thread = new Thread(() -> {
            System.out.println("[THREAD] Nova thread iniciada para processamento do carro id="
                    + carro.getId() + " | Thread: " + Thread.currentThread().getName());
            carroProcessamentoService.processarDadosCarro(carro);
            System.out.println("[THREAD] Thread finalizada para carro id=" + carro.getId()
                    + " | Thread: " + Thread.currentThread().getName());
        });

        thread.setName("carro-processamento-" + carro.getId());
        thread.setDaemon(true);
        thread.start();

        System.out.println("[THREAD] Thread disparada para carro id=" + carro.getId()
                + " - API retornou sem aguardar o processamento.");
    }
}