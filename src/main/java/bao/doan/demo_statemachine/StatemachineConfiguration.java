package bao.doan.demo_statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.Optional;

@Configuration
@EnableStateMachine
@Slf4j
public class StatemachineConfiguration extends EnumStateMachineConfigurerAdapter<State, StateEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<State, StateEvent> states) throws Exception {
        states.withStates().initial(State.INIT).state(State.IN_PROGRESS).end(State.DONE);
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<State, StateEvent> transitions) throws Exception {
        transitions.withExternal().source(State.INIT).target(State.IN_PROGRESS).event(StateEvent.NEXT_STEP_EVENT);

        transitions.withExternal().source(State.IN_PROGRESS).target(State.DONE).event(StateEvent.SUCCESS_EVENT);

        transitions.withExternal().source(State.IN_PROGRESS).source(State.INIT).target(State.FAILED).event(StateEvent.FAILED_EVENT);

    }

//    @Override
//    public void configure(StateMachineConfigurationConfigurer<State, StateEvent> config) throws Exception {
//        config.withConfiguration().listener(new StateMachineListenerAdapter<>() {
//            @Override
//            public void stateChanged(org.springframework.statemachine.state.State<State, StateEvent> from, org.springframework.statemachine.state.State<State, StateEvent> to) {
//                log.info("State changed from {} with event: {} to {} with event: {}", Optional.ofNullable(from).map(org.springframework.statemachine.state.State::getId).orElse(null), Optional.ofNullable(from).
//                        map(org.springframework.statemachine.state.State::getDeferredEvents).orElse(null), to.getId(), to.);
//            }
//        });
//    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, StateEvent> config) throws Exception {
        config.withConfiguration().listener(new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(org.springframework.statemachine.state.State<State, StateEvent> from, org.springframework.statemachine.state.State<State, StateEvent> to) {
                log.info("State changed from {} to {}", from != null ? from.getId() : "none", to.getId());
            }

            @Override
            public void transition(org.springframework.statemachine.transition.Transition<State, StateEvent> transition) {
                if (transition.getSource() != null && transition.getTarget() != null) {
                    log.info("Transitioned from {} to {} with event {}", transition.getSource().getId(), transition.getTarget().getId(), transition.getTrigger().getEvent());
                }
            }
        });
    }
}
