package med.voll.api.domain.consulta.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCancelamentoConsulta dados) {
        Consulta consulta = consultaRepository.findById(dados.idConsulta())
                .orElseThrow(() -> new ValidacaoException("Id da consulta informado não existe!"));

        LocalDateTime agora = LocalDateTime.now();
        long diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência de 24 horas!");
        }


    }


}
