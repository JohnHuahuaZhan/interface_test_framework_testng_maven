package interface_test_framework_testng_maven.test.process;

public abstract class AbstractJSONHttpPrePostExceptionProcessor extends AbstractJSONHttpRequestProcessor {
    protected IHttpPrePostExceptionCallback rulePrePostCallback;


    public void setRulePrePostCallback(IHttpPrePostExceptionCallback rulePrePostCallback) {
        this.rulePrePostCallback = rulePrePostCallback;
    }

    public AbstractJSONHttpPrePostExceptionProcessor(IHttpPrePostExceptionCallback rulePrePostCallback) {
        this.rulePrePostCallback = rulePrePostCallback;
    }


}
