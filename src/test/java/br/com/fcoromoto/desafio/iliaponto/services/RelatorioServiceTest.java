package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio.RelatorioDto;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
class RelatorioServiceTest {

    private static final LocalDateTime dataHoraBase = LocalDateTime.of(2020, 11, 2, 8, 0, 0);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RelatorioService relatorioService;

    @Test
    void testarDependencias() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(relatorioService);
    }

    @Test
    @Transactional
    void deveGerarRelatorioSucesso() {
        entityManager.persist(Batida.of(dataHoraBase));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(4)));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(5)));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(9)));

        entityManager.persist(Batida.of(dataHoraBase.plusDays(1)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(4)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(5)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(9)));

        entityManager.persist(Batida.of(dataHoraBase.plusDays(2)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(2).plusHours(4)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(2).plusHours(5)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(2).plusHours(9)));

        entityManager.persist(Alocacao.builder(dataHoraBase.toLocalDate(), Duration.ofHours(8), "Projeto Teste"));
        entityManager.persist(Alocacao.builder(dataHoraBase.plusDays(1).toLocalDate(), Duration.ofHours(8), "Projeto Teste"));

        RelatorioDto relatorioDto = relatorioService.gerarRelatorio("2020-11");
        Assertions.assertEquals(Duration.ofHours(16), relatorioDto.getHorasTrabalhadas());
        Assertions.assertEquals(Duration.ZERO, relatorioDto.getHorasExcedentes());
        Assertions.assertEquals(Duration.ofHours(8), relatorioDto.getHorasDevidas());
    }

}