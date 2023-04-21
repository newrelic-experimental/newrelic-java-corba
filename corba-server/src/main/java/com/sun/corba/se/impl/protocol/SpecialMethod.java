package com.sun.corba.se.impl.protocol;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.sun.corba.se.spi.oa.ObjectAdapter;
import com.sun.corba.se.spi.protocol.CorbaMessageMediator;

@Weave(type=MatchType.BaseClass)
public abstract class SpecialMethod {
	
	public abstract String getName();

	@Trace
	public CorbaMessageMediator invoke(Object paramObject, CorbaMessageMediator paramCorbaMessageMediator, byte[] paramArrayOfByte, ObjectAdapter paramObjectAdapter) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","SpecialMethod",getClass().getSimpleName(),getName()});
		return Weaver.callOriginal();
	}
}
