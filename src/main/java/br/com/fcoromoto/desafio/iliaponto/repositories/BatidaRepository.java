package br.com.fcoromoto.desafio.iliaponto.repositories;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BatidaRepository extends JpaRepository<Batida, Long> {

    @Query("Select b.dataHora from Batida b where to_char(b.dataHora, 'YYYY-MM-DD') = to_char(:dia, 'YYYY-MM-DD') order by b.dataHora")
    List<LocalDateTime> getRegistrosPorDia(LocalDate dia);

    @Query("Select b.dataHora from Batida b where to_char(b.dataHora, 'YYYY-MM') = :dia order by b.dataHora")
    List<LocalDateTime> getRegistrosPorMes(String dia);
}
