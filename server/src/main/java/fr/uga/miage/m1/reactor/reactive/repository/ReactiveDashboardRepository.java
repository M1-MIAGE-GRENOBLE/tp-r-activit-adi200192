package fr.uga.miage.m1.reactor.reactive.repository;

import fr.uga.miage.m1.reactor.entity.DashboardEntity;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ReactiveDashboardRepository {
    private DashboardEntity dashboardEntity = null;

    public Mono<DashboardEntity> create(DashboardToCreate dashboard) {
        return null; /*Mono.just(dashboardEntity)
                .doOnEach(dashboardEntitySignal -> this.dashboardEntity = dashboardEntitySignal.get());*/
    }

    public Mono<Void> deleteDashboard() {
        return Mono.empty()
                .doOnEach(o -> this.dashboardEntity = null)
                .then();

    }
}
