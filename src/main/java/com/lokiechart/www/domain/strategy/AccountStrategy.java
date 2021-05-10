package com.lokiechart.www.domain.strategy;

import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
@Getter
public class AccountStrategy {
    private Account account;
    private Set<OrderStrategy> orderStrategies;

    private AccountStrategy(Account account, Set<OrderStrategy> orderStrategies) {
        this.account = account;
        this.orderStrategies = orderStrategies;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Account account;
        private Set<OrderStrategy> orderStrategies;

        public Builder account(final Account account) {
            this.account = account;
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
            return new AccountStrategy(this.account, this.orderStrategies);
        }
    }
}
