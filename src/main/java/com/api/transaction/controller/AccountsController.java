package com.api.transaction.controller;

import com.api.transaction.domain.dto.AccountDto;
import com.api.transaction.domain.dto.OperationDto;
import com.api.transaction.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class AccountsController {

    @Autowired
    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    // METHODS FOR ACCOUNTS: get, post and get by type

    /**
     * GET Method
     */

    @GetMapping(value = "/accounts/get")
    public List<AccountDto> getAccounts() {
        return this.accountsService.getAccounts();
    }

    /**
     * POST Method
     * Only id, client, type and amount are required. Interest set to 0 by default.
     */

    @PostMapping(value = "/accounts/add")
    public void addAcounts(@RequestBody AccountDto accountDto) {
        this.accountsService.addAccounts(accountDto);
    }

    /**
     * Gets the list of accounts by type
     *
     * @param account_type
     */

    @GetMapping(value = "/accounts/get/{account_type}")
    public List<AccountDto> getByAccountType(@PathVariable String account_type) {
        List<AccountDto> accounts = this.accountsService.getAccounts();

        List<AccountDto> result = new ArrayList<AccountDto>();
        for (int x = 0; x < accounts.size(); x++) {
            if (account_type.equals(accounts.get(x).getAccount_type())) {
                result.add(accounts.get(x));
            }
        }
        return result;
    }

    // GET METHOD FOR OPERATIONS

    /**
     * GET Method
     */

    @GetMapping(value = "/operations/get")
    public List<OperationDto> getOperations() {
        return this.accountsService.getOperations();
    }

    // METHODS FOR BOTH ACCOUNTS AND OPERATIONS: interest, transfer, deposit and withdraw

    /**
     * Add interest to savings accounts
     *
     * @param account_id
     * @param interest_date (yyyy-mm-dd format)
     * */

    @PostMapping(value = "/operations/interest/{account_id}/{interest_date}")
    public void interest(@PathVariable long account_id, @PathVariable Date interest_date){
        this.accountsService.addInterest(account_id, interest_date);
    }

    /**
     * Makes a deposit of money by having the account Id
     *
     * @param account_id
     * @param deposit_amount
     */

    @PostMapping(value = "/operations/deposit/{account_id}/{deposit_amount}")
    public void makeDeposit(@PathVariable long account_id, @PathVariable double deposit_amount) {
        this.accountsService.depositMoney(account_id, deposit_amount);
    }

    /**
     * Withdraws money
     *
     * @param account_id
     * @param withdraw_amount
     */

    @PostMapping(value = "/operations/withdraw/{account_id}/{withdraw_amount}")
    public void withdrawMoney(@PathVariable long account_id, @PathVariable double withdraw_amount) {
        this.accountsService.withdrawMoney(account_id, withdraw_amount);
    }

    /**
     * Transfers money from one account to other
     *
     * @param source_account_id
     * @param destination_account_id
     * @param deposit_amount
     */

    @PostMapping(value = "/operations/transfer/{source_account_id}/{destination_account_id}/{deposit_amount}")
    public void transferMoney(@PathVariable long source_account_id, @PathVariable long destination_account_id,
                              @PathVariable double deposit_amount) {
        this.accountsService.transferMoney(source_account_id, destination_account_id, deposit_amount);
    }

    /**
     * Undo operations such as deposit, withdraw and tranfer
     *
     * @param operation_id
     * */

    @PostMapping(value = "/operations/undo/{operation_id}")
    public void undoOperations(@PathVariable int operation_id) {
        this.accountsService.undoOperation(operation_id);
    }

}