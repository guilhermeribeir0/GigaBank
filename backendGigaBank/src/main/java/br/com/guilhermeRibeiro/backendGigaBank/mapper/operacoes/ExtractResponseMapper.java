package br.com.guilhermeRibeiro.backendGigaBank.mapper.operacoes;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.operacoes.ExtractResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extract;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtractResponseMapper {

    ExtractResponse modelToResponse(Extract extract);

    List<ExtractResponse> modelListToResponseList(List<Extract> extracts);
}
