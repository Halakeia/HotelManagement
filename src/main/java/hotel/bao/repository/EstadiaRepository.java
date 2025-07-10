package hotel.bao.repository;

import hotel.bao.entities.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM Estadia e
            WHERE e.quarto.id = :quartoId
            AND (
                (:dataEntrada BETWEEN e.dataEntrada AND e.dataSaida)
                OR (:dataSaida BETWEEN e.dataEntrada AND e.dataSaida)
                OR (e.dataEntrada BETWEEN :dataEntrada AND :dataSaida)
            )
            """)
    boolean existsByQuartoIdAndPeriodoSobreposicao(
            Long quartoId,
            LocalDate dataEntrada,
            LocalDate dataSaida
    );

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM Estadia e
            WHERE e.quarto.id = :quartoId
            AND e.id != :estadiaId
            AND (
                (:dataEntrada BETWEEN e.dataEntrada AND e.dataSaida)
                OR (:dataSaida BETWEEN e.dataEntrada AND e.dataSaida)
                OR (e.dataEntrada BETWEEN :dataEntrada AND :dataSaida)
            )
            """)
    boolean existsByQuartoIdAndPeriodoSobreposicaoExcluindoEstadia(
            Long quartoId,
            LocalDate dataEntrada,
            LocalDate dataSaida,
            Long estadiaId
    );

    @Query("SELECT e FROM Estadia e WHERE e.cliente.id = :clienteId")
    List<Estadia> findByClienteId(Long clienteId);
}
