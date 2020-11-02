package br.com.fcoromoto.desafio.iliaponto.repositories;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {

    @Query("Select a.tempo from Alocacao a where a.data = :dia ")
    List<Duration> getTempoAlocadoPorDia(LocalDate dia);

    @Query("Select a from Alocacao a where DATE_FORMAT(a.data, '%Y-%m') = :data order by a.data")
    List<Alocacao> getRegistrosPorMes(String data);
}
