package com.semantify.webapp;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;

import java.util.*;

public class RDFStoreController {

    static String schemas = "schema";
    static Dataset dataset = TDBFactory.createDataset(schemas);

    /*
    static String instances = "instances";
    static Dataset datasetTwo = TDBFactory.createDataset(instances);
    */

    public static void main(String[] args) {

        RDFStoreController storeController = new RDFStoreController();

        /* fill the dataset */
        //storeController.fillDataset(storeController);

        /* query section */
        String query = "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "select * " +
                "where { ?a ?b ?c }";

        //storeController.queryOntology("product", query);
        storeController.listOntologies();
        //storeController.cleanDataset();

    }

    /**
     * Auxiliar function to print easier
     * @param mensaje is an String object
     */
    public static void imprime(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Auxiliar method to delete all the models in the dataset
     */
    private void cleanDataset() {

        Iterator list = dataset.listNames();
        String nameModel = "";

        while ( list.hasNext() ) {
            nameModel = list.next().toString();
            dataset.removeNamedModel(nameModel);
        }

    }

    /**
     * Auxiliar method to create dummy model examples
     */
    private void fillDataset( RDFStoreController storeController ) {

        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("tbox", "data/tbox.owl");
        dictionary.put("abox", "data/abox.owl");
        dictionary.put("product", "data/Product.owl");

        Set allKeys = dictionary.keySet();
        Iterator iterKeys = allKeys.iterator();

        while ( iterKeys.hasNext() ) {

            String nameSchema = (String) iterKeys.next();
            String pathSchema = dictionary.get( nameSchema );
            storeController.storeOntology(nameSchema, pathSchema);
        }
    }

    /**
     * From nameSchema looks for the correspondent model and execute the query
     * @param nameSchema is the schema where we want to execute the query
     * @param queryString is the value of the query
     */
    public void queryOntology(String nameSchema, String queryString) {

        Model model = null;
        dataset.begin(ReadWrite.READ);

        try {

            model = dataset.getNamedModel(nameSchema);
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet rs = qe.execSelect();
            ResultSetFormatter.out(System.out, rs, query);
            qe.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            dataset.end();
        }

    }

    /**
     * From the name of the Schema returns the correspondent model
     * @param nameSchema
     * @return m, the model
     */
    public Model getSchemaByName(String nameSchema) {

        Model m = null;
        dataset.begin(ReadWrite.READ);

        try {
            m = dataset.getNamedModel(nameSchema);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            dataset.end();
        }

        return m;

    }

    /**
     * Looks on the dataset variable and returns a list of all the models
     * @return ontologies
     */
    public List<String> listOntologies() {

        List<String> ontologies = new ArrayList<String>();

        dataset.begin(ReadWrite.READ);

        try {

            Iterator list = dataset.listNames();

            while ( list.hasNext() ) {
                String modelName = (String) list.next();
                //imprime(modelName);
                ontologies.add( modelName );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            dataset.end();
        }

        return ontologies;

    }

    /**
     * Stores the ontology using the name and path of the Schema
     * @param nameSchema
     * @param pathSchema
     */
    public void storeOntology(String nameSchema, String pathSchema) {

        dataset.begin(ReadWrite.WRITE);

        try {

            /* Create the model */
            Model model = ModelFactory.createDefaultModel();
            model.read(pathSchema);

            dataset.getNamedModel(pathSchema);
            dataset.addNamedModel(nameSchema, model);
            dataset.commit();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            dataset.end();
        }

    }

}