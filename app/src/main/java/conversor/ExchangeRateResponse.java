package conversor;

import java.util.Map;

public record ExchangeRateResponse(Map<String, Double> conversion_rates) {

}
