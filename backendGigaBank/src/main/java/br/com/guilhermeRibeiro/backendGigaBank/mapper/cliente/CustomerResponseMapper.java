package br.com.guilhermeRibeiro.backendGigaBank.mapper.cliente;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import br.com.guilhermeRibeiro.backendGigaBank.dto.response.cliente.CustomerResponse;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Customer;

@Mapper(componentModel = "spring")
public abstract class CustomerResponseMapper {

    public abstract CustomerResponse modelToResponse(Customer customer);

    public abstract List<CustomerResponse> modelListToResponseList(List<Customer> customerList);

    @AfterMapping
    protected void mascararCpf(@MappingTarget CustomerResponse clienteResponse) {
        clienteResponse.setCpf("***"+clienteResponse.getCpf().substring(3, 9)+"**");
    }
}
