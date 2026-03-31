package com.VitorsosterF.exercicioPraticoAPIREST.service;
 
import com.VitorsosterF.exercicioPraticoAPIREST.model.Caminhao;
import com.VitorsosterF.exercicioPraticoAPIREST.repository.CaminhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
 
@Component
public class CaminhaoRelatorioJob {
 
    @Autowired
    private CaminhaoRepository caminhaoRepository;
 
    @Scheduled(cron = "0 * * * * *")
    public void gerarRelatorio() {
        String horario = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
 
        List<Caminhao> caminhoes = caminhaoRepository.findAll();
 
        System.out.println("====================================================");
        System.out.println("  RELATÓRIO PERIÓDICO DE CAMINHÕES CADASTRADOS");
        System.out.println("  Gerado em: " + horario);
        System.out.println("====================================================");
 
        if (caminhoes.isEmpty()) {
            System.out.println("  Nenhum caminhão cadastrado no momento.");
        } else {
            System.out.printf("  Total de caminhões cadastrados: %d%n", caminhoes.size());
            System.out.println("----------------------------------------------------");
            System.out.printf("  %-5s %-15s %-15s %-6s %-12s %-12s%n",
                    "ID", "Modelo", "Marca", "Ano", "Preço (R$)", "Carga (t)");
            System.out.println("----------------------------------------------------");
 
            for (Caminhao c : caminhoes) {
                System.out.printf("  %-5d %-15s %-15s %-6d %-12.2f %-12.2f%n",
                        c.getId(),
                        c.getModelo(),
                        c.getMarca(),
                        c.getAno(),
                        c.getPreco(),
                        c.getCapacidadeCarga());
            }
 
            // Estatísticas extras
            double precoMedio = caminhoes.stream()
                    .mapToDouble(Caminhao::getPreco)
                    .average()
                    .orElse(0);
 
            double cargaTotal = caminhoes.stream()
                    .mapToDouble(Caminhao::getCapacidadeCarga)
                    .sum();
 
            System.out.println("----------------------------------------------------");
            System.out.printf("  Preço médio:        R$ %.2f%n", precoMedio);
            System.out.printf("  Capacidade total:   %.2f toneladas%n", cargaTotal);
        }
 
        System.out.println("====================================================\n");
    }
}