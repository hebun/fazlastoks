package fazlastoks;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;


public class MyPhaseListener implements javax.faces.event.PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("-------------------------------------------------------------");
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("---------------------- "+event.getPhaseId()+" -----------------");
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.ANY_PHASE;
	}

}
