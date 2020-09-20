package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.CoinsInfrastructureException;

public interface CoinsInfrastructure {

    void takeMoney(double total) throws CoinsInfrastructureException;

}// CoinsInfrastructure
