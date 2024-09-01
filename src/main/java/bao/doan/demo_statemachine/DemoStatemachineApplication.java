package bao.doan.demo_statemachine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DemoStatemachineApplication  implements ApplicationListener<ApplicationStartedEvent> {
    private final StateMachine<State, StateEvent> stateMachine;
    public static void main(String[] args) {
        SpringApplication.run(DemoStatemachineApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Starting state machine");
        stateMachine.startReactively().subscribe();
        log.info("State machine started");
    }
}
