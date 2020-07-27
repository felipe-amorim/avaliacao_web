package support;

import cucumber.api.PickleStepTestStep;
import cucumber.api.event.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class Listener extends Core implements ConcurrentEventListener {

    private final EventHandler<TestStepStarted> testStepStartedHandler = new EventHandler<TestStepStarted>() {
        @Override
        public void receive(TestStepStarted event) {
            try {
                //PickleStepTestStep testStep = (PickleStepTestStep) event.testStep;
                setBdd(((PickleStepTestStep) event.testStep).getStepText());
            } catch (Exception ignored) {
            }
        }
    };
    private final EventHandler<TestStepFinished> testStepFinishedHandler = new EventHandler<TestStepFinished>() {
        @Override
        public void receive(TestStepFinished event) {
        }
    };

    private final EventHandler<TestCaseStarted> testCaseStartedHandler = new EventHandler<TestCaseStarted>() {
        @Override
        public void receive(TestCaseStarted event) {
            String[] path = event.testCase.getUri().split("/");
            setFeature(path[path.length - 1].replace(".feature", ""));
            setScenario(event.testCase.getName());
        }
    };

    private final EventHandler<TestCaseFinished> testCaseFinishedHandler = new EventHandler<TestCaseFinished>() {
        @Override
        public void receive(TestCaseFinished event) {
            if (event.result.getStatus().toString().equals("FAILED")) {
                bddSection.fail(event.result.getError());
                extent.flush();
            }
        }
    };

    private EventHandler<TestRunStarted> runStartHandler = new EventHandler<TestRunStarted>() {

        @Override
        public void receive(TestRunStarted event) {
            setWebDriver();
            try {
                FileUtils.deleteDirectory(new File(reportLoc));
            } catch (IOException e) {
                e.printStackTrace();
            }
            createReport();
        }
    };
    private EventHandler<TestRunFinished> runFinishHandler = new EventHandler<TestRunFinished>() {
        @Override
        public void receive(TestRunFinished event) {
            killWebDriver();
        }
    };

    @Override
    public void setEventPublisher(final EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStartedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, testCaseFinishedHandler);
        publisher.registerHandlerFor(TestStepStarted.class, testStepStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, runFinishHandler);
        publisher.registerHandlerFor(TestRunStarted.class, runStartHandler);
    }

}
