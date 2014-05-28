package fazlastoks;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import fazlastoks.admin.CrudBase;

public class MyPhaseListener implements javax.faces.event.PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		long time = 0;
		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {

			time = System.currentTimeMillis() - start;

			System.out.println("--------------------  time: " + time
					+ "  -----------------------------------------");
		} else {
			System.out
					.println("-------------------------------------------------------------------------------");

		}
	}

	long start = 0;

	@Override
	public void beforePhase(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
			this.start = System.currentTimeMillis();
		}
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
