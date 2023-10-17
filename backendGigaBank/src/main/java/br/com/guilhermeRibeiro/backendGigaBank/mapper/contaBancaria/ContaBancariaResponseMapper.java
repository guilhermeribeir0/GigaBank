package br.com.guilhermeRibeiro.backendGigaBank.mapper.contaBancaria;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.contaBancaria.ContaBancariaResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.ContaBancaria;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ContaBancariaResponseMapper {

    public abstract ContaBancariaResponse modelToResponse(ContaBancaria contaBancaria);

    @AfterMapping
    protected void mapearCliente(@MappingTarget ContaBancariaResponse response) {
        response.getCliente().setCpf("***"+response.getCliente().getCpf().substring(3, 9)+"**");
    }
}
