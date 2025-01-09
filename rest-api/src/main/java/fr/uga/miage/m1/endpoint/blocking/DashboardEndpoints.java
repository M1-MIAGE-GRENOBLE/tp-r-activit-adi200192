package fr.uga.miage.m1.endpoint.blocking;

import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/dashboard")
public interface DashboardEndpoints {

    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "409")
    @PostMapping("/")
    Map<CryptoTypeResponse, CryptoResponse> createDashboardCrypto(@RequestBody DashboardCreationRequest dashboardToCreate);

    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404")
    @GetMapping("/fluctuation/last")
    Map<CryptoTypeResponse, CryptoFluctuationResponse> getLastDashboardFluctuation();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fluctuation/all")
    Map<CryptoTypeResponse, List<CryptoFluctuationResponse>> getAllDashboardFluctuation();


    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404")
    @GetMapping("/")
    Map<CryptoTypeResponse, CryptoResponse> getDashboardCrypto();



}
