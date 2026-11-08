package com.allpatekplatform.shared.domain.model.aggregates;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

/**
 * Base class for all domain aggregate roots.
 *
 * <p>Extends Spring Data Commons' {@link AbstractAggregateRoot} to gain
 * domain event registration support ({@code registerEvent}) without
 * pulling in any JPA or persistence concern. Identity and auditing are
 * intentionally left to the infrastructure layer.</p>
 *
 * @param <T> the concrete aggregate root type
 */
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>>
        extends AbstractAggregateRoot<T> {

    protected void registerDomainEvent(Object event) {
        super.registerEvent(event);
    }

    @Override
    public Collection<Object> domainEvents() {
        return super.domainEvents();
    }

    @Override
    public void clearDomainEvents() {
        super.clearDomainEvents();
    }
}
