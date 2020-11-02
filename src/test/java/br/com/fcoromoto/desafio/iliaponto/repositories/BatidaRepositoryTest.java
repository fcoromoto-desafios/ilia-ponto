package br.com.fcoromoto.desafio.iliaponto.repositories;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class BatidaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BatidaRepository repository;


    @Test
    void deveRetornarQuatro() {
        LocalDateTime hoje = LocalDateTime.of(2020, 11, 1, 0, 0);

        entityManager.persist(Batida.of(hoje));
        entityManager.persist(Batida.of(hoje.plusHours(1)));
        entityManager.persist(Batida.of(hoje.plusHours(2)));
        entityManager.persist(Batida.of(hoje.plusHours(3)));
        entityManager.persist(Batida.of(hoje.plusDays(1)));
        entityManager.persist(Batida.of(hoje.minusDays(1)));

        List<LocalDateTime> registros = repository.getRegistrosPorDia(hoje.toLocalDate());

        assertSame(4, registros.size());

    }

    @Test
    void deveRetornarRegistrosPorMes() {

        LocalDateTime dataHoraBase = LocalDateTime.of(2020, 11, 2, 8, 0, 0);

        entityManager.persist(Batida.of(dataHoraBase));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(4)));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(5)));
        entityManager.persist(Batida.of(dataHoraBase.plusHours(9)));

        entityManager.persist(Batida.of(dataHoraBase.plusDays(1)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(4)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(5)));
        entityManager.persist(Batida.of(dataHoraBase.plusDays(1).plusHours(9)));

        entityManager.persist(Batida.of(dataHoraBase.plusMonths(1).plusHours(9)));
        entityManager.persist(Batida.of(dataHoraBase.plusMonths(1).plusHours(9)));

        List<LocalDateTime> registrosPorMes = repository.getRegistrosPorMes("2020-11");
        assertSame(8, registrosPorMes.size());

    }
}