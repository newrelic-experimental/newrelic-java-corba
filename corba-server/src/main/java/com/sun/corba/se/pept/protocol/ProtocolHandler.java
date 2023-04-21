package com.sun.corba.se.pept.protocol;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ProtocolHandler {

	@Trace(dispatcher=true)
	public boolean handleRequest(MessageMediator paramMessageMediator) {
		return Weaver.callOriginal();
	}
}
