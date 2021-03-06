package com.semantify.webapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * POJOs: Auxiliary classes to built a response with JSON format
 */

/**
 * Helps to build the success messages
 */

class Messages {

    boolean success = true;
    Date time = null;

    protected void setTime(){
        this.time = new Date();
    }


}


class RequestQuery {

    String data;

    public RequestQuery(){
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
       this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuffer(" Data : ").append(this.data).toString();
    }

}


class RequestOntology {

    String schema;
    String instance;
    String ontName;
    String ontFormat;

    public RequestOntology(){
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getOntName() {
        return ontName;
    }

    public void setOntName(String ontName) {
        this.ontName = ontName;
    }

    public String getOntFormat() {
        return ontFormat;
    }

    public void setOntFormat(String ontFormat) {
        this.ontFormat = ontFormat;
    }

}


class Success extends Messages {

    String data = null;

    public Success () {}

    public Success(String data) {
        setTime();
        this.data = data;
    }

}


class ResponseQuery extends Messages {

    String data = null;
    String stats = null;

    public ResponseQuery () {}

    public ResponseQuery(String data, String stats) {
        setTime();
        this.data = data;
        this.stats = stats;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

}


/**
 * Helps to build the error messages
 */
class Error extends Messages {

    String message = "";
    boolean success = false;

    public Error () {}

    public Error(String message) {
        setTime();
        this.message = message;
    }

}

/**
 * Get ontology method in format:
 * {
 *   data: 'pure ontology text',
 *   time: Date.now(),
 *   success: true
 * }
 */
class Ontology extends Messages {

    String id = null;
    String name = null;
    String data = null;

    public Ontology() {}

    public Ontology(String data) {
        setTime();
        this.data = data;
    }

    public Ontology(String id, String name) {
        setTime();
        this.id = id;
        this.name = name;
    }

    public Ontology(String id, String name, String data) {
        setTime();
        this.id = id;
        this.name = name;
        this.data = data;
    }

}

/**
 * List of ontologies in format:
 * {
 *   data: [{'name': ',,,,', id: '...'},{'name': '...', id: ',,'}],
 *   time: Date.now(),
 *   success: true
 * }
 */
class OntologyList extends Messages {

     ArrayList<Ontology> data;

     public OntologyList () {}

     public OntologyList(ArrayList<Ontology> data) {
         setTime();
         this.data = data;
     }

}
