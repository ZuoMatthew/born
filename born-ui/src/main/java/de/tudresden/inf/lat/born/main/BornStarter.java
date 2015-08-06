package de.tudresden.inf.lat.born.main;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyLoaderListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.ui.BornController;
import de.tudresden.inf.lat.born.ui.BornModel;
import de.tudresden.inf.lat.born.ui.BornView;

/**
 * This is used to start the system.
 * 
 * @author Julian Mendez
 */
public class BornStarter implements OWLOntologyChangeListener,
		OWLOntologyLoaderListener {

	private final OWLOntologyManager ontologyManager;
	private final BornController panel;

	/**
	 * Constructs a new starter.
	 * 
	 * @param manager
	 *            OWL ontology manager
	 */
	public BornStarter(OWLOntologyManager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.ontologyManager = manager;
		this.panel = new BornController(new BornView(new BornModel()),
				this.ontologyManager);
		getOWLOntologyManager().addOntologyLoaderListener(this);
		getOWLOntologyManager().addOntologyChangeListener(this);
		reset();
	}

	@Override
	public void finishedLoadingOntology(LoadingFinishedEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		reset();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.ontologyManager;
	}

	public BornController getPanel() {
		return this.panel;
	}

	@Override
	public void ontologiesChanged(List<? extends OWLOntologyChange> change) {
		if (change == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		reset();
	}

	public void reset() {
		this.panel.reset();
	}

	@Override
	public void startedLoadingOntology(LoadingStartedEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("Null argument.");
		}
	}

	public void removeListeners() {
		// TODO
	}

}