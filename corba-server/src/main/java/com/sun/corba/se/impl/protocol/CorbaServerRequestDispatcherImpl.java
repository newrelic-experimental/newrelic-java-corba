package com.sun.corba.se.impl.protocol;

import org.omg.CORBA.Any;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.sun.corba.se.impl.corba.ServerRequestImpl;
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.spi.oa.ObjectAdapter;
import com.sun.corba.se.spi.protocol.CorbaMessageMediator;

@Weave
public abstract class CorbaServerRequestDispatcherImpl {
	
	protected abstract String opAndId(CorbaMessageMediator paramCorbaMessageMediator);

	@Trace
	public void dispatch(MessageMediator messageMediator) {
		CorbaMessageMediator request = (CorbaMessageMediator) messageMediator;
		String operationName = request.getOperationName();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "CORBA", new String[]{operationName});
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CorbaServerRequestDispatcherImpl","dispatch",operationName});
		NewRelic.addCustomParameter("Message ID", request.getRequestIdInteger());
		NewRelic.addCustomParameter("GIOP Verstion", request.getGIOPVersion().toString());
		String opandid = opAndId(request);
		if(opandid != null && !opandid.isEmpty()) {
			NewRelic.addCustomParameter("OP and ID", opandid);
		}
		Weaver.callOriginal();
	}
	
	@Trace
	protected CorbaMessageMediator dispatchToServant(Object servant,CorbaMessageMediator req,byte[] objectId, ObjectAdapter objectAdapter) {
		String operationName = req.getOperationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CorbaServerRequestDispatcherImpl","dispatchToServant",operationName});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected CorbaMessageMediator handleDynamicResult(ServerRequestImpl sreq,CorbaMessageMediator req) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","CorbaServerRequestDispatcherImpl","handleDynamicResult",sreq.operation()});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected CorbaMessageMediator sendingReply(CorbaMessageMediator req) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","CorbaServerRequestDispatcherImpl","sendingReply",req.getOperationName()});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected CorbaMessageMediator sendingReply(CorbaMessageMediator req, Any excany) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","CorbaServerRequestDispatcherImpl","sendingReply",req.getOperationName()});
		return Weaver.callOriginal();
	}
}
