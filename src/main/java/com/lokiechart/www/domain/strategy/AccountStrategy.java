package com.lokiechart.www.domain.strategy;

import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
@ToString
@Getter
public class AccountStrategy {

    private AccountResponse accountResponse;
    private OrderStrategies orderStrategies;

    private AccountStrategy(AccountResponse accountResponse, Set<OrderStrategy> orderStrategies) {
        this.accountResponse = accountResponse;
        this.orderStrategies = new OrderStrategies(orderStrategies);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AccountResponse accountResponse;
        private Set<OrderStrategy> orderStrategies;

        public Builder accountResponse(final AccountResponse accountResponse) {
            this.accountResponse = accountResponse;
            return this;
        }

        public Builder orderStrategies(final Set<OrderStrategy> orderStrategies) {
            this.orderStrategies = orderStrategies;
            return this;
        }

        public Builder orderStrategies(final OrderStrategy... orderStrategies) {
            this.orderStrategies = new HashSet<>(Arrays.asList(orderStrategies));
            return this;
        }

        public AccountStrategy build() {
            return new AccountStrategy(this.accountResponse, this.orderStrategies);
        }
    }
}
