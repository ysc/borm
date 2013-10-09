package org.apdplat.demo.gora;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

import org.apache.avro.util.Utf8;
import org.apache.gora.query.Query;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.apdplat.demo.gora.generated.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonManager {
	  private static final Logger log = LoggerFactory.getLogger(PersonManager.class);	  
	  private DataStore<String, Person> dataStore; 	  
	  public PersonManager() {
	    try {
	      init();
	    } catch (IOException ex) {
	      throw new RuntimeException(ex);
	    }
	  }
	  private void init() throws IOException {
		Configuration  conf = new Configuration();
	    dataStore = DataStoreFactory.getDataStore(String.class, Person.class, conf);
	  }
	  private void parse(String input) throws IOException, ParseException, Exception {
	    log.info("解析文件:" + input);
	    BufferedReader reader = new BufferedReader(new FileReader(input));
	    long lineCount = 0;
	    try {
	      String line = reader.readLine();
	      do {
	        Person person = parseLine(line);
	        
	        if(person != null) {
	          //入库
	          storePerson(person.getIdcard().toString(), person);
	        }
	        lineCount++;
	        line = reader.readLine();
	      } while(line != null);
	      
	    } finally {
	      reader.close();  
	    }
	    log.info("文件解析完毕. 总人数:" + lineCount);
	  }
	  private Person parseLine(String line) throws ParseException {
	    String[] attrs = line.split(" ");
	    String idCard = attrs[0];
	    String name = attrs[1];
	    String age = attrs[2];

	    Person person = new Person();
	    person.setIdcard(new Utf8(idCard));
	    person.setName(new Utf8(name));
	    person.setAge(new Utf8(age));
	    
	    return person;
	  }
	  private void storePerson(String key, Person person) throws IOException, Exception {
		log.info("保存人员信息: " + person.getIdcard()+"\t"+person.getName()+"\t"+person.getAge());
	    dataStore.put(key, person);
	  }
	  private void get(String key) throws IOException, Exception {
	    Person person = dataStore.get(key);
	    printPerson(person);
	  }
	  private void query(String key) throws IOException, Exception {
	    Query<String, Person> query = dataStore.newQuery();
	    query.setKey(key);
	    
	    Result<String, Person> result = query.execute();
	    
	    printResult(result);
	  }
	  private void query(String startKey, String endKey) throws IOException, Exception {
	    Query<String, Person> query = dataStore.newQuery();
	    query.setStartKey(startKey);
	    query.setEndKey(endKey);
	    
	    Result<String, Person> result = query.execute();
	    
	    printResult(result);
	  }
	  private void delete(String key) throws Exception {
	    dataStore.delete(key);
	    dataStore.flush();
	    log.info("身份证号码为:" + key + " 的人员信息被删除");
	  }
	  private void deleteByQuery(String startKey, String endKey) throws IOException, Exception {
	    Query<String, Person> query = dataStore.newQuery();
	    query.setStartKey(startKey);
	    query.setEndKey(endKey);
	    
	    dataStore.deleteByQuery(query);
	    log.info("身份证号码从 " + startKey + " 到 " + endKey + " 的人员信息被删除");
	  }
	  private void printResult(Result<String, Person> result) throws IOException, Exception {	    
	    while(result.next()) {
	    	String resultKey = result.getKey();
	    	Person resultPerson = result.get();
	      
	    	System.out.println(resultKey + ":");
	    	printPerson(resultPerson);
	    }
	    
	    System.out.println("人数:" + result.getOffset());
	  }
	  private void printPerson(Person person) {
	    if(person == null) {
	      System.out.println("没有结果"); 
	    } else {
	      System.out.println(person.getIdcard()+"\t"+person.getName()+"\t"+person.getAge());
	    }
	  }
	  private void close() throws IOException, Exception {
	    if(dataStore != null)
	      dataStore.close();
	  }	  
	  private static final String USAGE = "PersonManager -parse <input_person_file>\n" +
	                                      "           -get <idcard>\n" +
	                                      "           -query <idcard>\n" +
	                                      "           -query <startIdcard> <endIdcard>\n" +
	  		                              "           -delete <idcard>\n" +
	  		                              "           -deleteByQuery <startIdcard> <endIdcard>\n";
	  
	  public static void main(String[] args) throws Exception {
	    if(args.length < 2) {
	      System.err.println(USAGE);
	      System.exit(1);
	    }
	    
	    PersonManager manager = new PersonManager();
	    
	    if("-parse".equals(args[0])) {
	      manager.parse(args[1]);
	    } else if("-get".equals(args[0])) {
	      manager.get(args[1]);
	    } else if("-query".equals(args[0])) {
	      if(args.length == 2) 
	        manager.query(args[1]);
	      else 
	        manager.query(args[1], args[2]);
	    } else if("-delete".equals(args[0])) {
	      manager.delete(args[1]);
	    } else if("-deleteByQuery".equalsIgnoreCase(args[0])) {
	      manager.deleteByQuery(args[1], args[2]);
	    } else {
	      System.err.println(USAGE);
	      System.exit(1);
	    }
	    
	    manager.close();
	  }
}
