package com.VitorsosterF.exercicioPraticoAPIREST.repository;
 
import com.VitorsosterF.exercicioPraticoAPIREST.model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {
}