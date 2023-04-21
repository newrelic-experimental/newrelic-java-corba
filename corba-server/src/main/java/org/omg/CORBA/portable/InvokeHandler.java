package org.omg.CORBA.portable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class InvokeHandler {

	@Trace
	public OutputStream _invoke(String method, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CORBA","InvokeHandler",getClass().getSimpleName(),method});
		return Weaver.callOriginal();
	}
}
