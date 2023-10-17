package br.com.guilhermeRibeiro.backendGigaBank.mapper.cliente;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.cliente.ClienteResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Cliente;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClienteResponseMapper {

    public abstract ClienteResponse modelToResponse(Cliente cliente);

    public abstract List<ClienteResponse> modelListToResponseList(List<Cliente> clientes);

    @AfterMapping
    protected void mascararCpf(@MappingTarget ClienteResponse clienteResponse) {
        clienteResponse.setCpf("***"+clienteResponse.getCpf().substring(3, 9)+"**");
    }
}
