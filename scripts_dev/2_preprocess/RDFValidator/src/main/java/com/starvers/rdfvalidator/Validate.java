package com.starvers.rdfvalidator;

import org.apache.jena.graph.Graph;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFLib;
import org.apache.jena.riot.Lang;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.query.ARQ;
import org.apache.jena.riot.RiotException;

import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.RDFHandlerException;


import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Scanner;


public class Validate {

    public static void main(String [] args) throws Exception {
        ARQ.init();
        
        String [] splitFileName = args[0].split("\\.");
        String logFile = args[1];
        String parser = args[2];

        String extension = splitFileName[splitFileName.length - 1];
        final Graph g = ModelFactory.createDefaultModel().getGraph();
        final StreamRDF dest = StreamRDFLib.graph(g); 
        ArrayList<Integer> invalidLines = new ArrayList<Integer>();
        FileInputStream inputStream = null;
        Scanner sc = null;
        int i = 0;

        try {
            inputStream = new FileInputStream(new File(args[0]));
            sc = new Scanner(inputStream, "UTF-8");

            if (parser.equals("jena")) {
                Lang l = null;
                if (extension.equals("nt")) {
                    l = Lang.NT;
                } else if (extension.equals("nq")) {
                    l = Lang.NQ;
                } else {
                    System.out.println("Extension must be .nt or .nq");
                }

                while (sc.hasNextLine()) {
                    String triple = sc.nextLine();
                    try {
                        org.apache.jena.riot.RDFParser.fromString(triple).lang(l).parse(dest);
                    } catch(RiotException e) {
                        System.out.println("RiotException: " + e.getMessage());
                        System.out.println("Invalid line: " + Integer.toString(i+1));
                        invalidLines.add(i+1);
                    } catch(Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                        System.out.println("Invalid line: " + Integer.toString(i+1));
                        invalidLines.add(i+1);
                    }  catch(Error e) {
                        System.out.println("Exception: " + e.getMessage());
                        System.out.println("Invalid line: " + Integer.toString(i+1));
                        invalidLines.add(i+1);
                    }                                
                    i++;
                }
            } else if (parser.equals("rdf4j")) {
                org.eclipse.rdf4j.rio.RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

                while (sc.hasNextLine()) {
                    InputStream triple = null;
                    try {
                        triple = new ByteArrayInputStream(sc.nextLine().getBytes());
                        rdfParser.parse(triple);
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (RDFParseException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Invalid line: " + Integer.toString(i+1));
                            invalidLines.add(i+1);
                    }
                    catch (RDFHandlerException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Invalid line: " + Integer.toString(i+1));
                            invalidLines.add(i+1);
                    }
                    finally {
                        triple.close();
                    }
                    i++;
                }

            } else throw new Exception("Parser must be one of: jena, rdf4j");

            
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }

        System.out.println("Number of lines in " + args[0] + ": " + Integer.toString(i));
        System.out.println("Number of invalid lines: " + invalidLines.size());
        try {
            FileWriter writer = new FileWriter(logFile); 
            for(Integer invalidLine: invalidLines) {
                writer.write(Integer.toString(invalidLine) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
