package clean.architecture.pizza.adapters.secondaries.infrastructure.coins;

import com.clean.architecture.pizza.core.exceptions.CoinsInfrastructureException;
import com.clean.architecture.pizza.core.ports.CoinsInfrastructure;

public class CoinsInfrastructureImpl implements CoinsInfrastructure {

    @Override
    public void takeMoney(double total) throws CoinsInfrastructureException {
        System.out.println("Processing...");
    }// takeMoney()

}// CoinsInfrastructureImpl()
