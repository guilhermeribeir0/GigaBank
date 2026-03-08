package br.com.guilhermeRibeiro.backendGigaBank.mapper.account;

import org.mapstruct.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.dto.response.account.AccountResponse;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    public abstract AccountResponse modelToResponse(Account account);

    @AfterMapping
    protected void maskCpfCustomer(@MappingTarget AccountResponse response) {
        response.getCustomer().setCpf("***" + response.getCustomer().getCpf().substring(3, 9) + "**");
    }
}
