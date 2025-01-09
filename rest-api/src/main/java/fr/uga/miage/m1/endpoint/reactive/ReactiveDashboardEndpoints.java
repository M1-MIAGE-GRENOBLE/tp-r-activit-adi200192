package fr.uga.miage.m1.endpoint.reactive;

import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequestMapping("/reactive/dashboard/")
public interface ReactiveDashboardEndpoints {

    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "409")
    @PostMapping("/")
    Mono<Map<CryptoTypeResponse, CryptoResponse>> createDashboardCrypto(@RequestBody DashboardCreationRequest dashboardToCreate);

    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404")
    @GetMapping("/fluctuation/last")
    Mono<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getLastDashboardFluctuation();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fluctuation/all")
    Mono<Map<CryptoTypeResponse, List<CryptoFluctuationResponse>>> getAllDashboardFluctuation();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fluctuation/")
    Flux<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getDashboardFluctuation();


    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404")
    @GetMapping("/dashboard")
    Mono<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getDashboardCrypto();




}
