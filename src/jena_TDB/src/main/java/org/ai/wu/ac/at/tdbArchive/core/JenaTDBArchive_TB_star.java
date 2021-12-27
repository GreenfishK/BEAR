package org.ai.wu.ac.at.tdbArchive.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ai.wu.ac.at.tdbArchive.solutions.DiffSolution;
import org.ai.wu.ac.at.tdbArchive.tools.JenaTDBArchive_query;
import org.ai.wu.ac.at.tdbArchive.utils.QueryResult;
import org.ai.wu.ac.at.tdbArchive.utils.QueryUtils;
import org.ai.wu.ac.at.tdbArchive.utils.TaskCallable;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.jena.query.ARQ;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.sparql.mgt.Explain;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
/*import com.hp.hpl.jena.query.Dataset;
 import com.hp.hpl.jena.query.Query;
 import com.hp.hpl.jena.query.QueryExecution;
 import com.hp.hpl.jena.query.QueryExecutionFactory;
 import com.hp.hpl.jena.query.QueryFactory;
 import com.hp.hpl.jena.query.QuerySolution;
 import com.hp.hpl.jena.query.ResultSet;
 import com.hp.hpl.jena.rdf.model.Literal;
 import com.hp.hpl.jena.rdf.model.RDFNode;
 import com.hp.hpl.jena.tdb.TDBFactory;
 import com.hp.hpl.jena.util.FileManager;
 */
import org.ai.wu.ac.at.tdbArchive.api.JenaTDBArchive;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.time.ZoneOffset;
//import org.apache.jena.system.JenaSystem;

public class JenaTDBArchive_TB_star implements JenaTDBArchive {

	private int TOTALVERSIONS = 0;
	private String initialVersionTS;
	private String outputTime = "timeApp.txt";
	private Dataset dataset;
	private Boolean measureTime = false;
	private static String metadataVersions = "<http://www.w3.org/2002/07/owl#versionInfo>";

	// private static String metadataVersions = "<http://example.org/isVersion>";

	/**
	 * @param outputTime
	 */
	public void setOutputTime(String outputTime) {
		this.outputTime = outputTime;
		this.measureTime = true;
	}

	public JenaTDBArchive_TB_star() throws FileNotFoundException {
		this.measureTime = false;
	}

	/**
	 * Load Jena TDB from directory
	 * 
	 * @param directory
	 */
	public void load(String directory) {
		// Initialize Jena
		FileManager fm = FileManager.get();
		fm.addLocatorClassLoader(JenaTDBArchive_query.class.getClassLoader());
		dataset = TDBFactory.createDataset(directory);

		/*
		 * Get number of distinct versions.
		 */
		String cntVersionsQ = QueryUtils.getVersionInfos();
		Query query1 = QueryFactory.create(cntVersionsQ);
		QueryExecution qexec1 = QueryExecutionFactory.create(query1, dataset);
		ResultSet results = qexec1.execSelect();
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			int cntVersions = soln.getLiteral("cnt_versions").getInt();
			System.out.println("Number of distinct versions:" + cntVersions); //DEBUG
			String initVersionTS = soln.getLiteral("initial_version_ts").getString();
			System.out.println("Initial version timestamp:" + initVersionTS); //DEBUG

			this.TOTALVERSIONS = cntVersions;
			this.initialVersionTS = initVersionTS;
		}
	}

	/**
	 * Gets the diff of the provided query between the two given versions
	 * 
	 * @param startVersionQuery
	 * @param endVersionQuery
	 * @param TP
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public DiffSolution diffQuerying(int startVersionQuery, int endVersionQuery, String TP) throws InterruptedException, ExecutionException {
        //TODO: implement, if necessary 
		return null;
	}

	/**
	 * Reads input file with a Resource, and gets the diff result of the lookup of the provided Resource with the provided rol (Subject, Predicate,
	 * Object) for all versions between 0 and consecutive jumps
	 * 
	 * @param queryFile
	 * @param rol
	 * @param jump
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public ArrayList<Map<Integer, DiffSolution>> bulkAlldiffQuerying(String queryFile, String rol, int jump) throws InterruptedException,
			ExecutionException, IOException {
     	//TODO: implement, if necessary 
		return null;
	}

	/**
	 * Gets the result of the provided query in the provided version
	 * 
	 * @param version
	 * @param queryString
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<String> matQuery(int version, String queryString) throws InterruptedException, ExecutionException {
     	//TODO: implement, if necessary 
		return null;
	}

	/**
	 * Reads input file with a Resource and a Version, and gets the result of a lookup of the provided Resource with the provided rol (Subject,
	 * Predicate, Object) in such Version
	 * 
	 * @param queryFile
	 * @param rol
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<ArrayList<String>> bulkMatQuerying(String queryFile, String rol) throws FileNotFoundException, IOException,
			InterruptedException, ExecutionException {
     	//TODO: implement, if necessary 
		return null;
	}

	/**
	 * Reads input file with a Resource, and gets the result of a lookup of the provided Resource with the provided rol (Subject, Predicate, Object)
	 * for every version
	 * 
	 * @param queryFile
	 * @param rol
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ArrayList<Map<Integer, ArrayList<String>>> bulkAllMatQuerying(String queryFile, String rol) throws FileNotFoundException, IOException,
		InterruptedException, ExecutionException {
		ArrayList<Map<Integer, ArrayList<String>>> ret = new ArrayList<Map<Integer, ArrayList<String>>>();

		File inputFile = new File(queryFile);
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = "";

		TreeMap<Integer, DescriptiveStatistics> vStats = new TreeMap<Integer, DescriptiveStatistics>();
		for (int i = 0; i < TOTALVERSIONS; i++) {
			vStats.put(i, new DescriptiveStatistics());
		}

		Boolean askQuery = rol.equalsIgnoreCase("SPO") && false;
		DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX");
		OffsetDateTime version_ts = OffsetDateTime.parse(this.initialVersionTS, DATE_TIME_FORMATTER);

		int lines = 0;
		while ((line = br.readLine()) != null) {
			lines++;
			String[] parts = line.split(" ");

			// String element = parts[0];

			/*
			 * warmup the system
			 */
			warmup();

			Map<Integer, ArrayList<String>> solutions = new HashMap<Integer, ArrayList<String>>();
			System.err.println("Query " + lines);
			for (int i = 0; i < TOTALVERSIONS; i++) {
				// System.out.println("\n Query at version " + i);
				long startTime = System.currentTimeMillis();
				String queryString = QueryUtils.createLookupQueryRDFStar(rol, parts, version_ts.toString());
                int limit = QueryUtils.getLimit(parts);
				//System.out.println(queryString); //DEBUG
				Query query = QueryFactory.create(queryString);
				if (true || !askQuery)
					solutions.put(i, materializeQuery(i, query, limit));
				else
					solutions.put(i, materializeASKQuery(i, query));
				long endTime = System.currentTimeMillis();
				//System.out.println("bulkAllMatQuerying: Time:" + (endTime - startTime)); //DEBUG

				vStats.get(i).addValue((endTime - startTime));
				version_ts = version_ts.plusSeconds(1);

			}
			ret.add(solutions);
		}
		br.close();

		if (measureTime) {
			// PrintWriter pw = new PrintWriter(new File(outputDIR + "/res-dynmat-" + inputFile.getName()));
			PrintWriter pw = new PrintWriter(new File(outputTime));
			pw.println("##ver, min, mean, max, stddev, count");
			for (Entry<Integer, DescriptiveStatistics> ent : vStats.entrySet()) {
				pw.println(ent.getKey() + " " + ent.getValue().getMin() + " " + ent.getValue().getMean() + " " + ent.getValue().getMax() + " "
						+ ent.getValue().getStandardDeviation() + " " + ent.getValue().getN()+" "+ent.getValue().getSum());
			}
			pw.close();
		}
		return ret;
	}

	/**
	 * @param staticVersionQuery
	 * @param query
	 * @return
	 */
	private ArrayList<String> materializeQuery(int staticVersionQuery, Query query, int limit) throws InterruptedException, ExecutionException {
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		ArrayList<String> ret = new ArrayList<String>();
		qexec.getContext().set(ARQ.symLogExec, Explain.InfoLevel.NONE);

		ResultSet results = qexec.execSelect();

		Boolean higherVersion = false;

		// Iterator<QuerySolution> sortResults = orderedResultSet(results, "graph");

		while (results.hasNext() && !higherVersion && limit-- > 0) {
			// numRows++;
			QuerySolution soln = results.next();

			String rowResult = QueryUtils.serializeSolutionFilterOutGraphs(soln);

			// System.out.println(rowResult);
			ret.add(rowResult);
		}
		qexec.close();
		return ret;
	}

	/**
	 * @param staticVersionQuery
	 * @param query
	 * @return
	 */
	private ArrayList<String> materializeASKQuery(int staticVersionQuery, Query query) throws InterruptedException, ExecutionException {
     	//TODO: implement, if necessary 
		return null;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private static Iterator<QuerySolution> orderedResultSet(ResultSet resultSet, final String sortingVariableName) {
		List<QuerySolution> list = new ArrayList<QuerySolution>();

		while (resultSet.hasNext()) {
			list.add(resultSet.nextSolution());
		}

		Collections.sort(list, new Comparator<QuerySolution>() {

			public int compare(QuerySolution a, QuerySolution b) {

				return a.getResource(sortingVariableName).toString().compareTo(b.getResource(sortingVariableName).toString());

			}
		});
		return list.iterator();
	}

	/**
	 * Get the results of the provided query in all versions
	 * 
	 * @param TP
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<Integer, ArrayList<String>> verQuery(String TP) throws InterruptedException, ExecutionException {
		Map<Integer, ArrayList<String>> ret = new HashMap<Integer, ArrayList<String>>();
     	//TODO: implement, if necessary 
		return null;
	}

	/**
	 * Reads input file with a Resource, and gets all result of the lookup of the provided Resource with the provided rol (Subject, Predicate, Object)
	 * for all versions
	 * 
	 * @param queryFile
	 * @param rol
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public ArrayList<Map<Integer, ArrayList<String>>> bulkAllVerQuerying(String queryFile, String rol) throws InterruptedException,
			ExecutionException, IOException {
     	//TODO: implement, if necessary 
		return null;
	}

	/**
	 * Warmup the system
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void warmup() throws InterruptedException, ExecutionException {
		Query query = QueryFactory.create(createWarmupQuery());
		long startTime = System.currentTimeMillis();
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		qexec.getContext().set(ARQ.symLogExec, Explain.InfoLevel.NONE);

		ResultSet results = qexec.execSelect();

		Iterator<QuerySolution> sortResults = orderedResultSet(results, "graph");
		HashSet<String> finalResults = new HashSet<String>();
		while (sortResults.hasNext()) {
			QuerySolution soln = sortResults.next();
			// assume we have a graph variable as a response
			String graphResponse = soln.getResource("graph").toString();
			// System.out.println("--graphResponse:" + graphResponse);
			finalResults.add(graphResponse);
		}

		long endTime = System.currentTimeMillis();
		// System.out.println("Warmup Time:" + (endTime - startTime));

		qexec.close();

	}

	private static String createWarmupQuery() {
		String queryString = "SELECT ?element1 ?element2 ?element3 ?graph WHERE { " + "GRAPH ?graph{" + " ?element1 ?element2 ?element3 ."

		+ "}}" + "LIMIT 100";

		return queryString;
	}

	/**
	 * close Jena TDB and release resources
	 * 
	 * @throws RuntimeException
	 */
	public void close() throws RuntimeException {
		dataset.end();
	}
}
