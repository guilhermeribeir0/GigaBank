package br.com.guilhermeRibeiro.backendGigaBank.mapper.operacoes;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes.ExtratoResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extrato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtratoResponseMapper {

    ExtratoResponse modelToResponse(Extrato extrato);

    List<ExtratoResponse> modelListToResponseList(List<Extrato> extratos);
}
