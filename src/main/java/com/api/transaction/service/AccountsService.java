package com.api.transaction.service;


import com.api.transaction.domain.dto.AccountDto;
import com.api.transaction.domain.dto.InterestDto;
import com.api.transaction.domain.dto.OperationDto;
import com.api.transaction.domain.entity.Account;
import com.api.transaction.domain.entity.Interest;
import com.api.transaction.domain.entity.Operation;
import com.api.transaction.repository.AccountsRepository;
import com.api.transaction.repository.InterestsRepository;
import com.api.transaction.repository.OperationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountsService{

    private AccountsRepository accountsRepository;
    private OperationsRepository operationsRepository;
    private InterestsRepository interestsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, OperationsRepository operationsRepository, InterestsRepository interestsRepository) {
        this.accountsRepository = accountsRepository;
        this.operationsRepository = operationsRepository;
        this.interestsRepository = interestsRepository;
    }

    public List<AccountDto> getAccounts() {
        List<AccountDto> accountsDto = new ArrayList<AccountDto>();
        List<Account> accounts = this.accountsRepository.findAll();
        for (int x = 0; x < accounts.size(); x++) {
            AccountDto account = new AccountDto();
            account.setAccount_id(accounts.get(x).getAccount_id());
            account.setClient(accounts.get(x).getClient());
            account.setAccount_type(accounts.get(x).getAccount_type());
            account.setAccount_amount(accounts.get(x).getAccount_amount());
            InterestDto interestDto = new InterestDto();
            interestDto.setInterest_id(accounts.get(x).getInterest().getInterest_id());
            interestDto.setPercentage(accounts.get(x).getInterest().getPercentage());
            interestDto.setInterest_init_date(accounts.get(x).getInterest().getInterest_init_date());
            interestDto.setInterest_final_date(accounts.get(x).getInterest().getInterest_final_date());
            account.setInterest(interestDto);
            accountsDto.add(account);
        }
        return accountsDto;
    }

    public List<OperationDto> getOperations() {
        List<OperationDto> operationsDto = new ArrayList<OperationDto>();
        List<Operation> operations = this.operationsRepository.findAll();
        for (int x = 0; x < operations.size(); x++) {
            OperationDto operation = new OperationDto();
            operation.setOperation_id(operations.get(x).getOperation_id());
            operation.setOperation_type(operations.get(x).getOperation_type());
            operation.setAmount(operations.get(x).getAmount());
            operation.setSource_account_id(operations.get(x).getSource_account_id());
            operation.setDestination_account_id(operations.get(x).getDestination_account_id());
            operationsDto.add(operation);
        }
        return operationsDto;
    }

    public void addAccounts(AccountDto accountDto) {
        Account account = new Account();
        account.setAccount_id(accountDto.getAccount_id());
        account.setAccount_type(accountDto.getAccount_type());
        account.setAccount_amount(accountDto.getAccount_amount());
        account.setClient(accountDto.getClient());
        Interest interest = this.interestsRepository.findById((long) 0).get();
        account.setInterest(interest);
        this.accountsRepository.save(account);
    }

    public void addInterest(long account_id, Date interest_date){
        Account interest_account = this.accountsRepository.findById(account_id).get();

        if (interest_account.getAccount_type().equals("Ahorro")) {
            //Get list of interest so that we can compare the given date with all the interest time intervals
            List<InterestDto> interestsDto = new ArrayList<InterestDto>();
            List<Interest> interests = this.interestsRepository.findAll();
            for(int x=0; x<interests.size();x++) {
                InterestDto interest = new InterestDto();
                interest.setInterest_id(interests.get(x).getInterest_id());
                interest.setPercentage(interests.get(x).getPercentage());
                interest.setInterest_init_date(interests.get(x).getInterest_init_date());
                interest.setInterest_final_date(interests.get(x).getInterest_final_date());
                interestsDto.add(interest);
                //Condition to select the proper interest
                if(interest.getInterest_init_date().compareTo(interest_date) < 0 && interest.getInterest_final_date().compareTo(interest_date) > 0) {
                    //Set the selected interest for the given date
                    Interest interest_entity = new Interest();
                    interest_entity.setInterest_id(interest.getInterest_id());
                    interest_entity.setPercentage(interest.getPercentage());
                    interest_entity.setInterest_init_date(interest.getInterest_init_date());
                    interest_entity.setInterest_final_date(interest.getInterest_final_date());
                    interest_account.setInterest(interest_entity);
                    //Add interest to account_amount
                    interest_account.setAccount_amount(interest_account.getAccount_amount()*(1+interest_entity.getPercentage()*0.01));
                    this.accountsRepository.save(interest_account);
                }
            }

        }else {
            System.out.println("El tipo de cuenta indicado no es apto para aplicar un interés");
        }

    }

    public void depositMoney(long account_id, double deposit_amount) {
        /**
         * Add the money to deposit_account
         */
        Account deposit_account = this.accountsRepository.findById(account_id).get();
        deposit_account.setAccount_amount(deposit_account.getAccount_amount() + deposit_amount);
        this.accountsRepository.save(deposit_account);

        /**
         * Register the operation
         */
        Operation operation = new Operation("deposit", deposit_amount, 0, account_id);
        this.operationsRepository.save(operation);
    }

    public void withdrawMoney(long account_id, double withdraw_amount) {

        int current_account_limit = 1600;
        Account withdraw_account = this.accountsRepository.findById(account_id).get();

        switch (withdraw_account.getAccount_type()) {

            /**
             * Savings accounts case: The withdraw limit is equal to the account balance
             */

            case "Ahorro":

                if (withdraw_account.getAccount_amount() > withdraw_amount) {

                    withdraw_account.setAccount_amount(withdraw_account.getAccount_amount() - withdraw_amount);
                    this.accountsRepository.save(withdraw_account);

                    /**
                     * Register the operation
                     */
                    Operation operation = new Operation("withdraw", withdraw_amount, account_id, 0);
                    this.operationsRepository.save(operation);

                } else {
                    System.out.println("La cantidad que se desea retirar supera el límite de saldo de la cuenta.");
                }
                ;
                break;

            /**
             * Current accounts case: There's a withdraw limit that can't be exceeded
             * (balance can be negative)
             */

            case "Corriente":

                if (current_account_limit > withdraw_amount) {
                    withdraw_account.setAccount_amount(withdraw_account.getAccount_amount() - withdraw_amount);
                    this.accountsRepository.save(withdraw_account);
                    /**
                     * Register the operation
                     */
                    Operation operation = new Operation("withdraw", withdraw_amount, account_id, 0);
                    this.operationsRepository.save(operation);

                } else {
                    System.out
                            .println("La cantidad que se desea retirar supera el límite permitido para su tipo de cuenta.");
                }
                ;
                break;
        }
    }

    public void transferMoney(long source_account_id, long destination_account_id, double transfer_amount) {

        Account source_account = this.accountsRepository.findById(source_account_id).get();
        Account destination_account = this.accountsRepository.findById(destination_account_id).get();

        /**
         * Transfer operations are only allowed for current accounts
         */

        if (source_account.getAccount_type().equals("Corriente")
                && destination_account.getAccount_type().equals("Corriente")) {

            source_account.setAccount_amount(source_account.getAccount_amount() - transfer_amount);
            destination_account.setAccount_amount(destination_account.getAccount_amount() + transfer_amount);
            this.accountsRepository.save(source_account);
            this.accountsRepository.save(destination_account);

            /**
             * Register the operation
             */
            Operation operation = new Operation("transfer", transfer_amount, source_account_id, destination_account_id);
            this.operationsRepository.save(operation);

        } else {
            System.out.println("El tipo de cuenta no es válido para realizar transferencias.");
        }
    }

    public void undoOperation(int operation_id) {
        Operation operation = this.operationsRepository.findById((long) operation_id).get();

        switch (operation.getOperation_type()) {
            case "deposit":
                Account deposit_account = this.accountsRepository.findById(operation.getDestination_account_id()).get();
                deposit_account.setAccount_amount(deposit_account.getAccount_amount() - operation.getAmount());
                this.accountsRepository.save(deposit_account);
                this.operationsRepository.deleteById((long) operation_id);
                ;
                break;
            case "withdraw":
                Account withdraw_account = this.accountsRepository.findById(operation.getSource_account_id()).get();
                withdraw_account.setAccount_amount(withdraw_account.getAccount_amount() + operation.getAmount());
                this.accountsRepository.save(withdraw_account);
                this.operationsRepository.deleteById((long) operation_id);
                ;
                break;
            case "transfer":
                Account source_account = this.accountsRepository.findById(operation.getSource_account_id()).get();
                Account destination_account = this.accountsRepository.findById(operation.getDestination_account_id()).get();
                source_account.setAccount_amount(source_account.getAccount_amount() + operation.getAmount());
                destination_account.setAccount_amount(destination_account.getAccount_amount() - operation.getAmount());
                this.accountsRepository.save(source_account);
                this.accountsRepository.save(destination_account);
                this.operationsRepository.deleteById((long) operation_id);
                ;
                break;
        }
    }

}
