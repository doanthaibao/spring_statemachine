package bao.doan.demo_statemachine;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class Controller {
    private final StateMachine<State, StateEvent> stateMachine;

    @GetMapping("/test")
    public String test() {
        var message = MessageBuilder.withPayload(StateEvent.NEXT_STEP_EVENT).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
        return "OK";
    }
}
