package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import models.Entity;

public class YAMLImplementation extends API{

	@Override
	public void openFile(File file) {
		Yaml yaml = new Yaml(new Constructor(Entity.class));
		InputStream inputStream = this.getClass().getResourceAsStream("podaci.yaml");
		//Entity e = yaml.load(inputStream);
		//System.out.println(e.toString());
		
	    for (Object e : yaml.loadAll(inputStream)) {
			System.out.println("Ime i id: ");
			System.out.println(((Entity) e).getId() + " " + ((Entity) e).getName() + "\n");
			System.out.println("Propertiji:");
			for (Map.Entry<String,Object> entry : ((Entity) e).getProperties().entrySet()) {
	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			}
			System.out.println();
			System.out.println("Entitiji");
			for (Map.Entry<String, Entity> entry : ((Entity) e).getEntities().entrySet()) {
	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			}
			System.out.println("-----------------");
	    }
		

	}

}
