package com.VitorsosterF.exercicioPraticoAPIREST.service;

import com.VitorsosterF.exercicioPraticoAPIREST.model.Carro;
import org.springframework.stereotype.Service;

@Service
public class CarroProcessamentoService {

    public void processarDadosCarro(Carro carro) {
        System.out.println("[PROCESSAMENTO] Iniciando processamento de dados para o carro: "
                + carro.getMarca() + " " + carro.getModelo() + " (id=" + carro.getId() + ")");

        try {
            Thread.sleep(1000);
            System.out.println("[PROCESSAMENTO] Etapa 1 concluída - Validação de dados do carro id=" + carro.getId());

            Thread.sleep(1000);
            int anoAtual = java.time.Year.now().getValue();
            int idadeVeiculo = anoAtual - carro.getAno();
            System.out.println("[PROCESSAMENTO] Etapa 2 concluída - Idade do veículo: " + idadeVeiculo
                    + " anos (carro id=" + carro.getId() + ")");

            Thread.sleep(500);
            System.out.println("[PROCESSAMENTO] Etapa 3 concluída - Auditoria registrada para carro id=" + carro.getId());

            System.out.println("[PROCESSAMENTO] Processamento finalizado com sucesso para carro id=" + carro.getId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[PROCESSAMENTO] Processamento interrompido para carro id=" + carro.getId()
                    + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[PROCESSAMENTO] Erro inesperado ao processar carro id=" + carro.getId()
                    + ": " + e.getMessage());
        }
    }
}
