package com.sun.corba.se.spi.protocol;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.UnknownException;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.servicecontext.ServiceContexts;

@Weave(type=MatchType.Interface)
public abstract class CorbaProtocolHandler {

	@Trace
	public void handleRequest(RequestMessage requestMessage, CorbaMessageMediator paramCorbaMessageMediator) {
		String operationName = "UnknownOperation";
		if(requestMessage != null) {
			String temp = requestMessage.getOperation();
			if(temp != null && !temp.isEmpty()) {
				operationName = temp.trim();
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CorbaProtocolHandler",getClass().getSimpleName(),operationName});
		Weaver.callOriginal();
	}

	@Trace
	public void handleRequest(LocateRequestMessage requestMessage, CorbaMessageMediator paramCorbaMessageMediator) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CorbaProtocolHandler",getClass().getSimpleName(),"handleLocateRequestMessage"});
		Weaver.callOriginal();
	}

	@Trace
	public abstract CorbaMessageMediator createResponse(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts);

	@Trace
	public abstract CorbaMessageMediator createUserExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts);

	@Trace
	public CorbaMessageMediator createUnknownExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, UnknownException paramUnknownException) {
		NewRelic.noticeError(paramUnknownException);
		return Weaver.callOriginal();
	}

	@Trace
	public CorbaMessageMediator createSystemExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, SystemException paramSystemException, ServiceContexts paramServiceContexts) {
		NewRelic.noticeError(paramSystemException);
		return Weaver.callOriginal();
	}

	@Trace
	public abstract CorbaMessageMediator createLocationForward(CorbaMessageMediator paramCorbaMessageMediator, IOR paramIOR, ServiceContexts paramServiceContexts);

	@Trace
	public void handleThrowableDuringServerDispatch(CorbaMessageMediator paramCorbaMessageMediator, Throwable paramThrowable, CompletionStatus paramCompletionStatus) {
		NewRelic.noticeError(paramThrowable);
		Weaver.callOriginal();
	}

}
