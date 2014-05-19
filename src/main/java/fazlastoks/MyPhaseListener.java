package fazlastoks;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import fazlastoks.admin.CrudBase;

public class MyPhaseListener implements javax.faces.event.PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out
				.println("-------------------------------------------------------------");

	}

	@Override
	public void beforePhase(PhaseEvent event) {

		System.out.println("---------------------- " + event.getPhaseId()
				+ " -----------------");

		if (event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
			try {
				Map<String, Object> viewMap = FacesContext.getCurrentInstance()
						.getViewRoot().getViewMap();
				for (Map.Entry<String, Object> view : viewMap.entrySet()) {

					if (view.getValue() instanceof CrudBase) {
						CrudBase base = (CrudBase) view.getValue();

						base.setHasMessage(false);
					}

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.ANY_PHASE;
	}

}
