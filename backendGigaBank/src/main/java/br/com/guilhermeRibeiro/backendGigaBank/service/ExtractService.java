package br.com.guilhermeRibeiro.backendGigaBank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.guilhermeRibeiro.backendGigaBank.dto.request.extract.ExtractRequest;
import org.springframework.stereotype.Service;

import br.com.guilhermeRibeiro.backendGigaBank.entity.Account;
import br.com.guilhermeRibeiro.backendGigaBank.entity.Extract;
import br.com.guilhermeRibeiro.backendGigaBank.exception.ContaNaoCadastradaException;
import br.com.guilhermeRibeiro.backendGigaBank.exception.NaoExisteMovimentosException;
import br.com.guilhermeRibeiro.backendGigaBank.repository.ExtratoRepository;
import br.com.guilhermeRibeiro.backendGigaBank.entity.enums.OperationType;

@Service
public class ExtractService {

    private final ExtratoRepository extratoRepository;
    private final AccountService accountService;

    public ExtractService(
            ExtratoRepository extratoRepository,
            AccountService accountService
    ) {
        this.extratoRepository = extratoRepository;
        this.accountService = accountService;
    }

    public void createExtract(Account account, OperationType operationType, BigDecimal value) {
        Extract extract = new Extract(account, operationType, value);
        extratoRepository.save(extract);
    }

    public List<Extract> findExtractByPeriod(ExtractRequest request) {
        Account account = accountService.findAccountByAgencyAndNumber(request.getAgency(), request.getAcoountNumber());
        if (Objects.isNull(account)) {
            throw new ContaNaoCadastradaException(request.getAgency(), request.getAcoountNumber());
        }
        List<Extract> movements = extratoRepository.findByContaBancariaId(account.getId());
        if (Objects.isNull(movements) || movements.isEmpty()) {
            throw new NaoExisteMovimentosException();
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateFormated = LocalDateTime.parse(request.getStartDate() + " 00:00:00", dateFormat);
        LocalDateTime endDateFormated = LocalDateTime.parse(request.getEndDate() + " 23:59:59", dateFormat);

        return movements.stream()
                    .filter(movement -> movement.getOperationDate().isAfter(startDateFormated)
                        && movement.getOperationDate().isBefore(endDateFormated))
                .collect(Collectors.toList());
    }

}
