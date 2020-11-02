package br.com.fcoromoto.desafio.iliaponto.repositories;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BatidaRepository extends JpaRepository<Batida, Long> {

    @Query("Select b.dataHora from Batida b where  date(b.dataHora) = date(:dia) order by b.dataHora")
    List<LocalDateTime> getRegistrosPorDia(LocalDate dia);

    @Query("Select b.dataHora from Batida b where DATE_FORMAT(b.dataHora, '%Y-%m') = :dia order by b.dataHora")
    List<LocalDateTime> getRegistrosPorMes(String dia);

}

