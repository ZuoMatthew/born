package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorConfigurationImpl implements ProcessorConfiguration {

	private OWLOntology ontology;
	private String bayesianNetwork = "";
	private String query = "";
	private String outputFileName = "";
	private QueryProcessor queryProcessor = null;
	private boolean showingLog = true;

	public ProcessorConfigurationImpl() {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			this.ontology = manager.createOntology();
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public OWLOntology getOntology() {
		return ontology;
	}

	@Override
	public void setOntology(OWLOntology ontologyInputStream) {
		Objects.requireNonNull(ontologyInputStream);
		this.ontology = ontologyInputStream;
	}

	@Override
	public String getBayesianNetwork() {
		return bayesianNetwork;
	}

	@Override
	public void setBayesianNetwork(String bayesianNetwork) {
		Objects.requireNonNull(bayesianNetwork);
		this.bayesianNetwork = bayesianNetwork;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getOutputFileName() {
		return outputFileName;
	}

	@Override
	public void setOutputFileName(String outputFileName) {
		Objects.requireNonNull(outputFileName);
		this.outputFileName = outputFileName;
	}

	@Override
	public QueryProcessor getQueryProcessor() {
		return queryProcessor;
	}

	@Override
	public void setQueryProcessor(QueryProcessor queryProcessor) {
		Objects.requireNonNull(queryProcessor);
		this.queryProcessor = queryProcessor;
	}

	@Override
	public boolean isShowingLog() {
		return showingLog;
	}

	@Override
	public void setShowingLog(boolean showingLog) {
		this.showingLog = showingLog;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof AnnotatorConfiguration)) {
			return false;
		} else {
			ProcessorConfiguration other = (ProcessorConfiguration) obj;
			return getOntology().equals(other.getOntology()) && getBayesianNetwork().equals(other.getBayesianNetwork())
					&& getQuery().equals(other.getQuery()) && getOutputFileName().equals(other.getOutputFileName())
					&& getQueryProcessor().equals(other.getQueryProcessor())
					&& (isShowingLog() == other.isShowingLog());
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.ontology + " " + this.bayesianNetwork + " " + this.query + " " + this.outputFileName + " "
				+ this.queryProcessor + " " + this.showingLog;
	}

	/**
	 * Returns a string after reading the reader.
	 * 
	 * @param reader
	 *            reader
	 * @return a string after reading the reader
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public static String read(Reader reader) throws IOException {
		Objects.requireNonNull(reader);
		StringBuffer sbuf = new StringBuffer();
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); !Objects.isNull(line); line = input.readLine()) {
			sbuf.append(line);
			sbuf.append(Symbol.NEW_LINE_CHAR);
		}
		return sbuf.toString();
	}

	/**
	 * Transfers the content read from a Reader to a Writer.
	 * 
	 * @param reader
	 *            reader
	 * @param writer
	 *            writer
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public static void write(Reader reader, Writer writer) throws IOException {
		Objects.requireNonNull(reader);
		Objects.requireNonNull(writer);
		BufferedWriter output = new BufferedWriter(writer);
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); !Objects.isNull(line); line = input.readLine()) {
			output.write(line);
			output.newLine();
		}
		output.flush();
	}

	/**
	 * Returns an OWL ontology after reading the input stream.
	 * 
	 * @param input
	 *            input stream
	 * @return an OWL ontology after reading the input stream
	 * @throws OWLOntologyCreationException
	 *             if something goes wrong with ontology creation
	 */
	public static OWLOntology readOntology(InputStream input) throws OWLOntologyCreationException {
		Objects.requireNonNull(input);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

}